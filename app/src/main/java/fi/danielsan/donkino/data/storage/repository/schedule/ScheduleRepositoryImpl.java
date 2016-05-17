package fi.danielsan.donkino.data.storage.repository.schedule;

import java.util.IllegalFormatCodePointException;
import java.util.List;

import fi.danielsan.donkino.data.api.KinoService;
import fi.danielsan.donkino.data.api.models.schedule.Schedule;
import fi.danielsan.donkino.data.api.models.schedule.Show;
import fi.danielsan.donkino.data.storage.cache.schedule.ScheduleCache;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import timber.log.Timber;

public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final KinoService kinoService;
    private final ScheduleCache scheduleCache;

    public ScheduleRepositoryImpl(KinoService kinoService, ScheduleCache scheduleCache) {
        this.kinoService = kinoService;
        this.scheduleCache = scheduleCache;
    }

    public Observable<Schedule> getSchedule(final int theaterId, final String dateTime) {
        return Observable.concat(
                loadFromMemory(theaterId, dateTime),
                loadFromNetwork(theaterId, dateTime))
                .first(new Func1<Schedule, Boolean>() {
                    @Override
                    public Boolean call(Schedule schedule) {
                        return schedule.getShowList().size() > 0;
                    }
                });
    }

    private Observable<Schedule> loadFromMemory(int theaterId, final String dateTime) {
        return Observable.just(new Schedule(scheduleCache.getSchedule(theaterId, dateTime)))
                .compose(logSource("memory"));
    }

    private Observable<Schedule> loadFromNetwork(final int theaterId, final String dateTime) {
        return kinoService.getSchedule(theaterId, dateTime)
                .flatMap(new Func1<Response<Schedule>, Observable<Schedule>>() {
                    @Override
                    public Observable<Schedule> call(Response<Schedule> scheduleResponse) {
                        if (scheduleResponse.isSuccess()){
                            Schedule schedule = scheduleResponse.body();
                            if (schedule.getShowList().size() > 0) {
                                return Observable.just(schedule);
                            }
                        }
                        return Observable.empty();
                    }
                })
                .doOnNext(new Action1<Schedule>() {
                    @Override
                    public void call(Schedule schedule) {
                        saveScheduleToMemory(theaterId, dateTime, schedule.getShowList());
                    }
                })
                .compose(logSource("network"));
    }

    private void saveScheduleToMemory(int theaterId, String dateTime, List<Show> scheduleToSave) {
        scheduleCache.saveSchedule(theaterId, dateTime, scheduleToSave);
    }

    private Observable.Transformer<Schedule, Schedule> logSource(final String source) {
        return new Observable.Transformer<Schedule, Schedule>() {
            @Override
            public Observable<Schedule> call(Observable<Schedule> listObservable) {
                return listObservable.doOnNext(new Action1<Schedule>() {
                    @Override
                    public void call(Schedule schedule) {
                        if (schedule == null) {
                            Timber.d(source + " does not have any data.");
                        } else if (schedule.getShowList().size() == 0) {
                            Timber.d(source + " size 0");
                        } else {
                            Timber.d(source + " has the data you are looking for!");
                        }
                    }
                });
            }
        };
    }
}
