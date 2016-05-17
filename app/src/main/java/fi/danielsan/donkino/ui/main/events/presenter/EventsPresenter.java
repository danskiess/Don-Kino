package fi.danielsan.donkino.ui.main.events.presenter;

import java.util.List;

import fi.danielsan.donkino.data.api.models.events.Event;
import fi.danielsan.donkino.data.api.models.events.EventType;
import fi.danielsan.donkino.ui.base.Presenter;

public interface EventsPresenter extends Presenter<EventsPresenter.EventsView> {

    interface EventsView {
        void showLoading();
        void hideLoading();
        void setEvents(List<Event> events);

        void showNetworkError();
        void showEmptyResponseError();
        void showCasualError();

        void hideErrorMessage();
    }

    void loadNowInTheaters();
    void loadSoonInTheaters();
}
