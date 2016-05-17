package fi.danielsan.donkino.data.storage.cache.event;

import java.util.List;

import fi.danielsan.donkino.data.api.models.events.EventType;
import fi.danielsan.donkino.data.api.models.events.Event;

public interface EventCache {
    List<Event> getFromCache(EventType eventType);
    void saveToCache(EventType eventType, List<Event> listToCache);
}
