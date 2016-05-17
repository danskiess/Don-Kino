package fi.danielsan.donkino.ui.main.events.interactors;

import java.util.List;

import fi.danielsan.donkino.data.api.models.events.Event;
import fi.danielsan.donkino.data.api.models.events.EventType;

public interface EventsInteractor {

    interface EventsCallback{
        void onEventsLoaded(List<Event> events);
        void onEventsError(Throwable throwable);
    }

    void loadNowInTheaters();
    void loadSoonInTheaters();
    void setCallback(EventsInteractor.EventsCallback eventsCallback);
    void cancelRequest();
}
