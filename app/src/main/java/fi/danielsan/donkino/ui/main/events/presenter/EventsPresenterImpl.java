package fi.danielsan.donkino.ui.main.events.presenter;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.NoSuchElementException;

import fi.danielsan.donkino.data.api.models.events.Event;
import fi.danielsan.donkino.data.api.models.events.EventType;
import fi.danielsan.donkino.ui.main.events.interactors.EventsInteractor;
import retrofit2.adapter.rxjava.HttpException;
import timber.log.Timber;

public class EventsPresenterImpl implements EventsPresenter, EventsInteractor.EventsCallback{

    private final EventsInteractor eventsInteractor;
    private EventsView eventsView;

    public EventsPresenterImpl(EventsInteractor eventsInteractor) {
        this.eventsInteractor = eventsInteractor;
    }

    @Override
    public void loadNowInTheaters() {
        eventsView.hideErrorMessage();
        eventsView.showLoading();
        eventsInteractor.loadNowInTheaters();
    }

    @Override
    public void loadSoonInTheaters() {
        eventsView.hideErrorMessage();
        eventsView.showLoading();
        eventsInteractor.loadSoonInTheaters();
    }

    @Override
    public void setView(EventsView view) {
        eventsView = view;
        eventsInteractor.setCallback(this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {
        eventsInteractor.cancelRequest();
    }

    @Override
    public void onEventsLoaded(List<Event> events) {
        eventsView.hideLoading();
        eventsView.setEvents(events);
    }

    @Override
    public void onEventsError(Throwable throwable) {
        Timber.w(throwable, "onEventsError");

        eventsView.hideLoading();

        if (throwable instanceof NoSuchElementException) {
            eventsView.showEmptyResponseError();
        } else if (throwable instanceof UnknownHostException || throwable instanceof HttpException
                || throwable instanceof SocketTimeoutException) {
            eventsView.showNetworkError();
        } else {
            eventsView.showCasualError();
        }
    }
}
