package fi.danielsan.donkino.ui.detail.interactor;

import fi.danielsan.donkino.data.api.models.events.Event;

public interface DetailInteractor {

    interface DetailCallback{
        void onEventLoaded(Event event);
        void onEventError(Throwable error);
    }

    void setCallback(DetailInteractor.DetailCallback detailCallback);
    void loadEvent(long eventId);
    void cancelRequest();
}
