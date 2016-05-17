package fi.danielsan.donkino.data.storage.repository.events;

import fi.danielsan.donkino.data.api.models.events.Event;
import fi.danielsan.donkino.data.api.models.events.EventType;
import fi.danielsan.donkino.data.api.models.events.Events;
import rx.Observable;

public interface EventsRepository {
    Observable<Event> getEventByEventId(long eventId);
    Observable<Events> getEvents(EventType eventType);
}
