package fi.danielsan.donkino.data.storage.repository.events;

import java.util.ArrayList;
import java.util.List;

import fi.danielsan.donkino.data.api.KinoService;
import fi.danielsan.donkino.data.api.models.events.Event;
import fi.danielsan.donkino.data.api.models.events.EventType;
import fi.danielsan.donkino.data.api.models.events.Events;
import fi.danielsan.donkino.data.storage.cache.event.EventCache;
import fi.danielsan.donkino.data.storage.database.daos.EventDAO;
import fi.danielsan.donkino.data.storage.preferences.EventPreference;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import timber.log.Timber;

public class EventsRepositoryImpl implements EventsRepository {

    private final KinoService kinoService;
    private final EventDAO eventDAO;
    private final EventCache eventCache;
    private final EventPreference eventPreference;

    public EventsRepositoryImpl(KinoService kinoService, EventDAO eventDAO, EventCache eventCache, EventPreference eventPreference) {
        this.kinoService = kinoService;
        this.eventDAO = eventDAO;
        this.eventCache = eventCache;
        this.eventPreference = eventPreference;
    }

    @Override
    public Observable<Event> getEventByEventId(long eventId) {
        return Observable.concat(
                loadEventFromDatabase(eventId),
                loadEventFromNetwork(eventId)
        ).first(new Func1<Event, Boolean>() {
            @Override
            public Boolean call(Event event) {
                return event != null;
            }
        });
    }

    private Observable<? extends Event> loadEventFromDatabase(final long eventId) {
        return Observable.just((eventDAO.findById(eventId)))
                .compose(logEventSource("database"));
    }

    private Observable<? extends Event> loadEventFromNetwork(final long eventId) {
        return kinoService.getEvent(eventId, true)
                .flatMap(new Func1<Events, Observable<List<Event>>>() {
                    @Override
                    public Observable<List<Event>> call(Events events) {
                        return Observable.just(events.getEventList());
                    }
                })
                .flatMap(new Func1<List<Event>, Observable<Event>>() {
                    @Override
                    public Observable<Event> call(List<Event> events) {
                        return Observable.from(events);
                    }
                }).compose(logEventSource("network"));
    }

    //http://blog.danlew.net/2015/06/22/loading-data-from-multiple-sources-with-rxjava/
    public static Observable.Transformer<Event, Event> logEventSource(final String source) {
        return new Observable.Transformer<Event, Event>() {
            @Override
            public Observable<Event> call(Observable<Event> listObservable) {
                return listObservable.doOnNext(new Action1<Event>() {
                    @Override
                    public void call(Event event) {
                        if (event == null) {
                            Timber.d(source + " does not have any data.");
                        } else {
                            Timber.d(source + " has the data you are looking for!");
                        }
                    }
                });
            }
        };
    }

    @Override
    public Observable<Events> getEvents(EventType eventType) {
        return Observable.concat(
                loadEventsFromCache(eventType),
                loadEventsFromDatabase(eventType),
                loadEventsFromNetwork(eventType)
        ).first(new Func1<Events, Boolean>() {
            @Override
            public Boolean call(Events events) {
                return events.getEventList().size() > 0;
            }
        });
    }

    private Observable<? extends Events> loadEventsFromCache(EventType eventType) {
        return Observable.just(new Events(eventCache.getFromCache(eventType)))
                .compose(logSource("memory"));
    }

    private Observable<? extends Events> loadEventsFromDatabase(final EventType eventType) {
        if (eventType == EventType.NOW_IN_THEATERS){
            if (eventPreference.hasNowInTheatersExpired()){
                Timber.d("Deleting now in theaters");
                eventDAO.deleteEventsWhere(eventType);
                return Observable.empty();
            }
        } else {
            if (eventPreference.hasComingSoonExpired()){
                Timber.d("Deleting upcoming");
                eventDAO.deleteEventsWhere(eventType);
                return Observable.empty();
            }
        }

        return Observable.just(new Events(eventDAO.findAllByEventType(eventType)))
                .doOnNext(new Action1<Events>() {
                    @Override
                    public void call(Events events) {
                        eventCache.saveToCache(eventType, events.getEventList());
                    }
                })
                .compose(logSource("database"));
    }

    private Observable<Events> loadEventsFromNetwork(final EventType eventType){
        return kinoService.getEvents(eventType.getName(), true)
                .flatMap(new Func1<Events, Observable<List<Event>>>() {
                    @Override
                    public Observable<List<Event>> call(Events events) {
                        return Observable.just(events.getEventList());
                    }
                })
                .flatMap(new Func1<List<Event>, Observable<Event>>() {
                    @Override
                    public Observable<Event> call(List<Event> events) {
                        return Observable.from(events);
                    }
                })
                .filter(new Func1<Event, Boolean>() {
                    @Override
                    public Boolean call(Event event) {
                        return event.getEventType().equalsIgnoreCase("Movie");
                    }
                }).toList()
                .flatMap(new Func1<List<Event>, Observable<Events>>() {
                    @Override
                    public Observable<Events> call(List<Event> events) {
                        return Observable.just(new Events(events));
                    }
                })
                .doOnNext(new Action1<Events>() {
                    @Override
                    public void call(Events events) {
                        List<Event> eventsToSave = new ArrayList<>();

                        for (Event event : events.getEventList()){
                            if (eventType == EventType.NOW_IN_THEATERS) {
                                event.setNowInTheaters(true);
                            } else {
                                event.setComingSoon(true);
                            }

                            if (eventDAO.findById(event.getId()) == null) {
                                eventsToSave.add(event);
                            } else {
                                eventDAO.updateEvent(event);
                            }
                        }

                        if (eventsToSave.size() > 0){
                            boolean success = eventDAO.addEvents(eventsToSave);
                            if (success){
                                if (eventType == EventType.NOW_IN_THEATERS){
                                    eventPreference.saveNowInTheatersTimeStamp();
                                } else {
                                    eventPreference.saveComingSoonTimeStamp();
                                }
                            }
                            eventCache.saveToCache(eventType, eventsToSave);
                        }
                    }
                })
                .compose(logSource("network"));
    }

    //http://blog.danlew.net/2015/06/22/loading-data-from-multiple-sources-with-rxjava/
    public static Observable.Transformer<Events, Events> logSource(final String source) {
        return new Observable.Transformer<Events, Events>() {
            @Override
            public Observable<Events> call(Observable<Events> listObservable) {
                return listObservable.doOnNext(new Action1<Events>() {
                    @Override
                    public void call(Events events) {
                        if (events == null) {
                            Timber.d(source + " does not have any data.");
                        } else if (events.getEventList().size() == 0) {
                            Timber.d(source + " size 0");
                        } else {
                            Timber.d(source + " has the data you are looking for!");
                        }
                    }
                });
            }
        };
    }
}
