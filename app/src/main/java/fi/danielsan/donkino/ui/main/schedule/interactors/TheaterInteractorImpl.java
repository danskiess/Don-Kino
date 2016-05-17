package fi.danielsan.donkino.ui.main.schedule.interactors;

import java.util.List;

import fi.danielsan.donkino.data.api.models.theatres.TheatreArea;
import fi.danielsan.donkino.data.storage.repository.theater.TheaterRepository;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class TheaterInteractorImpl implements TheaterInteractor {

    private final TheaterRepository theaterRepository;

    private CompositeSubscription compositeSubscription;
    private TheaterInteractor.TheaterCallback theaterCallback;

    public TheaterInteractorImpl(TheaterRepository theaterRepository, CompositeSubscription compositeSubscription) {
        this.theaterRepository = theaterRepository;
        this.compositeSubscription = compositeSubscription;
    }

    @Override
    public void setCallback(TheaterInteractor.TheaterCallback theaterCallback) {
        this.theaterCallback = theaterCallback;
    }

    @Override
    public void loadTheaters() {
        if (compositeSubscription.isUnsubscribed()){
            compositeSubscription = new CompositeSubscription();
        }

        Subscription subscription = theaterRepository.queryAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        compositeSubscription.add(theaterRepository.getPreferredTheaterId()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<Integer>() {
                                    @Override
                                    public void call(Integer preferredTheaterId) {
                                        Timber.i("savePref");
                                        theaterCallback.onPreferredTheaterLoaded(preferredTheaterId);
                                    }
                                }));
                    }
                }).subscribe(new Subscriber<List<TheatreArea>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        theaterCallback.onTheaterError(e);
                    }

                    @Override
                    public void onNext(List<TheatreArea> theatreAreas) {
                        theaterCallback.onTheatreAreasLoaded(theatreAreas);
                    }
                });

        compositeSubscription.add(subscription);
    }

    @Override
    public void savePreferredTheater(int theaterId) {
        if (theaterId != 0) {
            theaterRepository.savePreferredTheater(theaterId);
        }
    }

    @Override
    public void cancelRequest() {
        compositeSubscription.unsubscribe();
    }
}
