package fi.danielsan.donkino.ui.main.schedule.interactors;

import fi.danielsan.donkino.data.api.models.schedule.Schedule;

public interface ScheduleInteractor {

    interface ScheduleCallback{
        void onScheduleLoaded(Schedule schedule);
        void onScheduleError(Throwable error);
    }

    void setCallback(ScheduleInteractor.ScheduleCallback scheduleCallback);
    void loadSchedule(int theaterId, String date);
    void filterSchedule(int theaterId, String date, String movieNameFilter);
    void cancelRequest();
}
