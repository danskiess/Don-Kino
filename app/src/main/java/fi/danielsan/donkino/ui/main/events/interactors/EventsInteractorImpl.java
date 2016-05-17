package fi.danielsan.donkino.ui.main.events.interactors;

import fi.danielsan.donkino.data.api.models.events.EventType;
import fi.danielsan.donkino.data.api.models.events.Events;
import fi.danielsan.donkino.data.storage.repository.events.EventsRepository;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class EventsInteractorImpl implements EventsInteractor {

    private final EventsRepository eventsRepository;

    private CompositeSubscription compositeSubscription;
    private EventsCallback eventsCallback;

    public EventsInteractorImpl(EventsRepository eventsRepository, CompositeSubscription compositeSubscription) {
        this.eventsRepository = eventsRepository;
        this.compositeSubscription = compositeSubscription;
    }

    @Override
    public void loadNowInTheaters() {
        if (compositeSubscription.isUnsubscribed()){
            compositeSubscription = new CompositeSubscription();
        } else if (compositeSubscription.hasSubscriptions()){
            compositeSubscription.clear();
        }

        Subscription subscription = eventsRepository.getEvents(EventType.NOW_IN_THEATERS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Events>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        eventsCallback.onEventsError(e);
                    }

                    @Override
                    public void onNext(Events events) {
                        eventsCallback.onEventsLoaded(events.getEventList());
                    }
                });

        compositeSubscription.add(subscription);
    }

    @Override
    public void loadSoonInTheaters() {
        if (compositeSubscription.hasSubscriptions()){
            compositeSubscription.clear();
        } else if (compositeSubscription.isUnsubscribed()){
            compositeSubscription = new CompositeSubscription();
        }

        Subscription subscription = eventsRepository.getEvents(EventType.COMING_SOON)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Events>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        eventsCallback.onEventsError(e);
                    }

                    @Override
                    public void onNext(Events events) {
                        eventsCallback.onEventsLoaded(events.getEventList());
                    }
                });

        compositeSubscription.add(subscription);
    }

    @Override
    public void setCallback(EventsCallback eventsCallback) {
        this.eventsCallback = eventsCallback;
    }

    @Override
    public void cancelRequest() {
        compositeSubscription.unsubscribe();
    }
}
