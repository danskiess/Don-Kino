package fi.danielsan.donkino.data.storage.repository.theater;

import java.util.List;

import fi.danielsan.donkino.data.api.KinoService;
import fi.danielsan.donkino.data.api.models.theatres.TheatreArea;
import fi.danielsan.donkino.data.api.models.theatres.TheatreAreas;
import fi.danielsan.donkino.data.storage.database.daos.TheaterDAO;
import fi.danielsan.donkino.data.storage.preferences.TheaterPreference;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import timber.log.Timber;

public class TheaterRepositoryImpl implements TheaterRepository {

    private final KinoService kinoService;
    private final TheaterDAO theaterDAO;
    private final TheaterPreference theaterPreferenceWrapper;

    public TheaterRepositoryImpl(KinoService kinoService, TheaterDAO theaterDAO, TheaterPreference theaterPreferenceWrapper) {
        this.kinoService = kinoService;
        this.theaterDAO = theaterDAO;
        this.theaterPreferenceWrapper = theaterPreferenceWrapper;
    }

    @Override
    public void save(TheatreArea theatreArea) {
        theaterDAO.insertTheater(theatreArea);
    }

    @Override
    public void saveBatch(List<TheatreArea> theatreAreas) {
        for (TheatreArea theatreArea : theatreAreas){
            theaterDAO.insertTheater(theatreArea);
        }
    }

    @Override
    public Observable<List<TheatreArea>> queryAll() {
        return Observable.concat(
                loadFromDatabase(),
                loadFromNetwork()
        ).first(new Func1<List<TheatreArea>, Boolean>() {
            @Override
            public Boolean call(List<TheatreArea> theatreAreas) {
                return theatreAreas.size() > 0;
            }
        });
    }

    private Observable<List<TheatreArea>> loadFromDatabase(){
        return Observable.create(new Observable.OnSubscribe<List<TheatreArea>>() {
            @Override
            public void call(Subscriber<? super List<TheatreArea>> subscriber) {
                subscriber.onNext(theaterDAO.findAll());
                subscriber.onCompleted();
            }
        }).skipWhile(new Func1<List<TheatreArea>, Boolean>() {
            @Override
            public Boolean call(List<TheatreArea> theatreAreas) {
                return theaterPreferenceWrapper.hasExpired();
            }
        }).compose(logSource("database"));
    }

    private Observable<List<TheatreArea>> loadFromNetwork(){
        return kinoService.getTheatreAreas().flatMap(new Func1<TheatreAreas, Observable<List<TheatreArea>>>() {
            @Override
            public Observable<List<TheatreArea>> call(TheatreAreas theatreAreas) {
                return Observable.just(theatreAreas.getTheatreAreaList());
            }
        }).doOnNext(new Action1<List<TheatreArea>>() {
            @Override
            public void call(List<TheatreArea> theatreAreas) {
                saveBatch(theatreAreas);
            }
        }).compose(logSource("network"));
    }

    //http://blog.danlew.net/2015/06/22/loading-data-from-multiple-sources-with-rxjava/
    public static Observable.Transformer<List<TheatreArea>, List<TheatreArea>> logSource(final String source) {
        return new Observable.Transformer<List<TheatreArea>, List<TheatreArea>>() {
            @Override
            public Observable<List<TheatreArea>> call(Observable<List<TheatreArea>> listObservable) {
                return listObservable.doOnNext(new Action1<List<TheatreArea>>() {
                    @Override
                    public void call(List<TheatreArea> theatreAreas) {
                        if (theatreAreas == null) {
                            Timber.d(source + " does not have any data.");
                        } else if (theatreAreas.size() == 0) {
                            Timber.d(source + " size 0");
                        } else {
                            Timber.d(source + " has the data you are looking for!");
                        }
                    }
                });
            }
        };
    }

    @Override
    public TheatreArea findById(Integer integer) {
        return null;
    }

    @Override
    public void delete(TheatreArea entity) {

    }

    @Override
    public Observable<Integer> getPreferredTheaterId() {
        return Observable.just(theaterPreferenceWrapper.getPreferredTheater());
    }

    @Override
    public void savePreferredTheater(int preferredTheater) {
        theaterPreferenceWrapper.savePreferredTheater(preferredTheater);
    }
}