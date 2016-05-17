package fi.danielsan.donkino.ui.main.schedule.interactors;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import fi.danielsan.donkino.data.api.models.schedule.Schedule;
import fi.danielsan.donkino.data.api.models.schedule.Show;
import fi.danielsan.donkino.data.storage.repository.schedule.ScheduleRepository;
import fi.danielsan.donkino.misc.EmptySubjectException;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

public class ScheduleInteractorImpl implements ScheduleInteractor, Observer<Schedule> {

    private final ScheduleRepository scheduleRepository;

    private CompositeSubscription compositeSubscription;
    private ScheduleCallback scheduleCallback;

    private PublishSubject<String> filterPublishSubject = PublishSubject.create();

    public ScheduleInteractorImpl(ScheduleRepository scheduleRepository, CompositeSubscription compositeSubscription) {
        this.scheduleRepository = scheduleRepository;
        this.compositeSubscription = compositeSubscription;
    }

    @Override
    public void setCallback(ScheduleCallback scheduleCallback) {
        this.scheduleCallback = scheduleCallback;
    }

    @Override
    public void loadSchedule(final int theaterId, final String date) {
        if (compositeSubscription.hasSubscriptions()){
            compositeSubscription.clear();
        } else if (compositeSubscription.isUnsubscribed()){
            compositeSubscription = new CompositeSubscription();
        }

        Subscription subscription = scheduleRepository.getSchedule(theaterId, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Schedule>() {
                    @Override
                    public void call(Schedule schedule) {
                        setupFilterObservable(theaterId, date);
                    }
                })
                .subscribe(this);

        compositeSubscription.add(subscription);
    }

    private void setupFilterObservable(final int theaterId, final String date){
        Subscription subs = filterPublishSubject
                .debounce(1, TimeUnit.SECONDS)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(final String lowerCapsTitle) {
                        Subscription innerSubs = scheduleRepository.getSchedule(theaterId, date)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .flatMap(new Func1<Schedule, Observable<List<Show>>>() {
                                    @Override
                                    public Observable<List<Show>> call(Schedule schedule) {
                                        return Observable.just(schedule.getShowList());
                                    }
                                })
                                .flatMap(new Func1<List<Show>, Observable<Show>>() {
                                    @Override
                                    public Observable<Show> call(List<Show> shows) {
                                        return Observable.from(shows);
                                    }
                                })
                                .filter(new Func1<Show, Boolean>() {
                                    @Override
                                    public Boolean call(Show show) {
                                        return show.getTitle().toLowerCase().contains(lowerCapsTitle);
                                    }
                                }).toList()
                                .flatMap(new Func1<List<Show>, Observable<Schedule>>() {
                                    @Override
                                    public Observable<Schedule> call(List<Show> shows) {
                                        return Observable.just(new Schedule(shows));
                                    }
                                }).subscribe(ScheduleInteractorImpl.this);

                        compositeSubscription.add(innerSubs);
                    }
                });

        compositeSubscription.add(subs);
    }


    @Override
    public void filterSchedule(int theaterId, String date, String movieNameFilter) {
        if (compositeSubscription.isUnsubscribed()){
            compositeSubscription = new CompositeSubscription();
            setupFilterObservable(theaterId, date);
        }

        if (filterPublishSubject.hasObservers()) {
            final String lowerCapsTitle = movieNameFilter.toLowerCase();
            filterPublishSubject.onNext(lowerCapsTitle);
        } else {
            scheduleCallback.onScheduleError(new EmptySubjectException());
        }
    }

    @Override
    public void onCompleted() {}

    @Override
    public void onError(Throwable e) {
        scheduleCallback.onScheduleError(e);
    }

    @Override
    public void onNext(Schedule schedule) {
        scheduleCallback.onScheduleLoaded(schedule);
    }

    @Override
    public void cancelRequest() {
        compositeSubscription.unsubscribe();
    }
}
