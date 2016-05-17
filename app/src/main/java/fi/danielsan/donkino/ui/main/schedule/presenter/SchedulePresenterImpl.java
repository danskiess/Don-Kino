package fi.danielsan.donkino.ui.main.schedule.presenter;


import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.NoSuchElementException;

import fi.danielsan.donkino.data.api.models.schedule.Schedule;
import fi.danielsan.donkino.data.api.models.theatres.TheatreArea;
import fi.danielsan.donkino.misc.EmptySubjectException;
import fi.danielsan.donkino.ui.main.schedule.interactors.ScheduleInteractor;
import fi.danielsan.donkino.ui.main.schedule.interactors.TheaterInteractor;
import retrofit2.adapter.rxjava.HttpException;
import timber.log.Timber;

public class SchedulePresenterImpl implements SchedulePresenter, ScheduleInteractor.ScheduleCallback,
        TheaterInteractor.TheaterCallback {

    private final ScheduleInteractor scheduleInteractor;
    private final TheaterInteractor theaterInteractor;

    private SchedulePresenter.ScheduleView view;
    private String lastFilterString; // Skip first filter 'event'

    public SchedulePresenterImpl(ScheduleInteractor scheduleInteractor, TheaterInteractor theaterInteractor) {
        this.scheduleInteractor = scheduleInteractor;
        this.theaterInteractor = theaterInteractor;
    }

    @Override
    public void setView(SchedulePresenter.ScheduleView view) {
        this.view = view;
        scheduleInteractor.setCallback(this);
        theaterInteractor.setCallback(this);
    }

    @Override
    public void onResume() {}

    @Override
    public void onPause() {
        scheduleInteractor.cancelRequest();
        theaterInteractor.cancelRequest();
    }

    @Override
    public void loadTheaters() {
        view.hideErrorMessage();
        theaterInteractor.loadTheaters();
    }

    @Override
    public void loadSchedule(int theaterId, String date) {
        view.hideErrorMessage();
        view.showLoading();
        theaterInteractor.savePreferredTheater(theaterId);
        scheduleInteractor.loadSchedule(theaterId, date);
    }

    @Override
    public void filterSchedule(int theaterName, String date, String movieNameFilter) {
        if (lastFilterString != null) {
            view.showLoading();
            scheduleInteractor.filterSchedule(theaterName, date, movieNameFilter);
        }
        lastFilterString = movieNameFilter;
    }

    @Override
    public void retry() {
        view.hideErrorMessage();
        view.hideSearchBar();
        view.hideSearchView();
        view.showLoading();
        theaterInteractor.loadTheaters();
    }

    @Override
    public void onScheduleLoaded(Schedule schedule) {
        view.hideErrorMessage();
        view.hideLoading();
        view.setSchedule(schedule);
    }

    @Override
    public void onScheduleError(Throwable throwable) {
        Timber.w(throwable, "onScheduleError");

        view.hideLoading();

        if (throwable instanceof NoSuchElementException) {
            view.showEmptyResponseError();
        } else if (throwable instanceof EmptySubjectException) {
            view.showFilterError();
        } else if (throwable instanceof UnknownHostException || throwable instanceof HttpException
                || throwable instanceof SocketTimeoutException) {
            view.showNetworkError();
        } else {
            view.showCasualError();
        }
    }

    @Override
    public void onTheatreAreasLoaded(List<TheatreArea> theatreAreas) {
        view.setTheaters(theatreAreas);
    }

    @Override
    public void onPreferredTheaterLoaded(int theaterId) {
        view.setPreferredTheaterId(theaterId);
    }

    @Override
    public void onTheaterError(Throwable throwable) {
        Timber.w(throwable, "onTheaterError");

        view.hideLoading();

        if (throwable instanceof NoSuchElementException) {
            view.showEmptyResponseError();
        } else if (throwable instanceof UnknownHostException || throwable instanceof HttpException
                || throwable instanceof SocketTimeoutException) {
            view.showNetworkError();
        } else {
            view.showCasualError();
        }
    }
}
