package fi.danielsan.donkino.data.storage.repository.schedule;


import fi.danielsan.donkino.data.api.models.schedule.Schedule;
import rx.Observable;

public interface ScheduleRepository {
    Observable<Schedule> getSchedule(int theaterId, String dateTime);
}
