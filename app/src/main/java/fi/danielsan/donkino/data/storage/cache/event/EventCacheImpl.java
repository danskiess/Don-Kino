package fi.danielsan.donkino.data.storage.cache.event;

import java.util.Collections;
import java.util.List;

import fi.danielsan.donkino.data.api.models.events.Event;
import fi.danielsan.donkino.data.api.models.events.EventType;

public class EventCacheImpl implements EventCache {

    private EventType lastEventType;
    private List<Event> cachedEvents;

    @Override
    public List<Event> getFromCache(EventType eventType) {
        if (lastEventType != null) {
            if (eventType == lastEventType){
                return cachedEvents;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void saveToCache(EventType eventType, List<Event> listToCache) {
        lastEventType = eventType;
        cachedEvents = listToCache;
    }
}
