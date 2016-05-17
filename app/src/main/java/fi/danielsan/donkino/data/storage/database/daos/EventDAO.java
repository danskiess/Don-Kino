package fi.danielsan.donkino.data.storage.database.daos;

import java.util.List;
import java.util.concurrent.Callable;

import fi.danielsan.donkino.data.api.models.events.Event;
import fi.danielsan.donkino.data.api.models.events.EventType;

public interface EventDAO {
    List<Event> findAll();
    Event findById(long eventId);
    List<Event> findAllByEventType(EventType eventType);
    boolean addEvent(Event event);
    boolean addEvents(List<Event> events);
    boolean updateEvent(Event event);
    boolean deleteEvent(Event event);
    boolean deleteEventsWhere(EventType event);
}
