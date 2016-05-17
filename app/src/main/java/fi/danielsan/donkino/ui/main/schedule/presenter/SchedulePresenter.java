package fi.danielsan.donkino.ui.main.schedule.presenter;

import java.util.List;

import fi.danielsan.donkino.data.api.models.schedule.Schedule;
import fi.danielsan.donkino.data.api.models.theatres.TheatreArea;
import fi.danielsan.donkino.data.api.models.theatres.TheatreAreas;
import fi.danielsan.donkino.ui.base.Presenter;

public interface SchedulePresenter extends Presenter<SchedulePresenter.ScheduleView> {

    interface ScheduleView {
        void showLoading();
        void hideLoading();
        void setSchedule(Schedule schedule);
        void setTheaters(List<TheatreArea> theaters);
        void setPreferredTheaterId(int theaterId);

        void showNetworkError();
        void showEmptyResponseError();
        void showCasualError();
        void showFilterError();

        void hideSearchView();
        void hideSearchBar();
        void hideErrorMessage();

    }

    void loadTheaters();
    void loadSchedule(int theaterId, String date);
    void filterSchedule(int theaterId, String date, String movieNameFilter);
    void retry();
}
