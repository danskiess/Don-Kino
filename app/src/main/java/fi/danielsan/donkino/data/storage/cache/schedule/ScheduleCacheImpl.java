package fi.danielsan.donkino.data.storage.cache.schedule;

import java.util.Collections;
import java.util.List;

import fi.danielsan.donkino.data.api.models.schedule.Show;
import timber.log.Timber;

public class ScheduleCacheImpl implements ScheduleCache {

    private List<Show> shows;
    private int lastTheaterId;
    private String lastDateTime;

    @Override
    public List<Show> getSchedule(int theaterId, String dateTime) {
        if (lastDateTime == null) {
            return Collections.emptyList();
        }
        if (lastTheaterId == theaterId && lastDateTime.equalsIgnoreCase(dateTime)){
            return shows;
        }
        return Collections.emptyList();
    }

    @Override
    public void saveSchedule(int theaterId, String dateTime, List<Show> listToCache) {
        lastTheaterId = theaterId;
        lastDateTime = dateTime;
        shows = listToCache;
    }
}
