package fi.danielsan.donkino.data.storage.cache.schedule;

import java.util.List;

import fi.danielsan.donkino.data.api.models.schedule.Show;

public interface ScheduleCache {
    List<Show> getSchedule(int theaterId, String dateTime);
    void saveSchedule(int theaterId, String dateTime, List<Show> listToCache);
}
