package fi.danielsan.donkino.di.modules;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import fi.danielsan.donkino.R;
import fi.danielsan.donkino.data.api.KinoService;
import fi.danielsan.donkino.data.api.models.events.GalleryImage;
import fi.danielsan.donkino.data.storage.cache.event.EventCache;
import fi.danielsan.donkino.data.storage.cache.event.EventCacheImpl;
import fi.danielsan.donkino.data.storage.database.daos.EventStatementFactory;
import fi.danielsan.donkino.data.storage.database.daos.EventStatementFactoryImpl;
import fi.danielsan.donkino.data.storage.database.daos.EventDAO;
import fi.danielsan.donkino.data.storage.database.daos.EventDAOImpl;
import fi.danielsan.donkino.data.storage.preferences.EventPreference;
import fi.danielsan.donkino.data.storage.preferences.EventPreferenceWrapper;
import fi.danielsan.donkino.data.storage.repository.events.EventsRepository;
import fi.danielsan.donkino.data.storage.repository.events.EventsRepositoryImpl;
import fi.danielsan.donkino.di.ActivityScope;
import fi.danielsan.donkino.misc.KinoOrientationHelper;
import fi.danielsan.donkino.misc.KinoOrientationHelperImpl;
import fi.danielsan.donkino.ui.base.AdapterDelegate;
import fi.danielsan.donkino.ui.detail.GalleryAdapter;
import fi.danielsan.donkino.ui.detail.GalleryPrimaryAdapterDelegate;
import fi.danielsan.donkino.ui.detail.interactor.DetailInteractor;
import fi.danielsan.donkino.ui.detail.interactor.DetailInteractorImpl;
import fi.danielsan.donkino.ui.detail.presenter.DetailPresenter;
import fi.danielsan.donkino.ui.detail.presenter.DetailPresenterImpl;
import rx.subscriptions.CompositeSubscription;

@Module
public class DetailEventModule {

    @Provides
    public DetailPresenter providesEventsPresenter(DetailInteractor detailInteractor,
                                                   KinoOrientationHelper kinoOrientationHelper,
                                                   @Named(AppModule.DD_MM_YYYY_FORMAT) DateTimeFormatter dateTimeFormatter){
        return new DetailPresenterImpl(detailInteractor, kinoOrientationHelper, dateTimeFormatter);
    }

    @Provides
    public KinoOrientationHelper providesKinoOrientationHelper(Resources resources){
        return new KinoOrientationHelperImpl(resources);
    }

    @Provides
    public DetailInteractor providesEventsInteractor(EventsRepository eventsRepository){
        return new DetailInteractorImpl(eventsRepository, new CompositeSubscription());
    }

    @ActivityScope
    @Provides
    public EventsRepository providesEventsRepository(KinoService kinoService,
                                                     EventDAO eventDAO,
                                                     EventCache eventCache,
                                                     EventPreference eventPreference){
        return new EventsRepositoryImpl(kinoService, eventDAO, eventCache, eventPreference);
    }

    @Provides
    public EventPreference providesEventPreference(SharedPreferences sharedPreferences,
                                                   @Named(AppModule.DD_MM_YYYY_FORMAT) DateTimeFormatter dateTimeFormatter){
        return new EventPreferenceWrapper(sharedPreferences, dateTimeFormatter);
    }

    @Provides
    public EventDAO providesEventDAO(SQLiteOpenHelper sqLiteOpenHelper,
                                     EventStatementFactory eventStatementFactory){
        return new EventDAOImpl(sqLiteOpenHelper, eventStatementFactory);
    }

    @Provides
    public EventStatementFactory providesEventStatementFactory(){
        return new EventStatementFactoryImpl();
    }

    @Provides
    public EventCache providesEventCache(){
        return new EventCacheImpl();
    }

    @Provides
    public GridLayoutManager providesGridLayoutManager(Application application){
        return new GridLayoutManager(application, 1, GridLayoutManager.HORIZONTAL, false);
    }

    @Provides
    public GalleryAdapter providesGalleryAdapter(AdapterDelegate<List<GalleryImage>> adapterDelegate){
        return new GalleryAdapter(adapterDelegate);
    }

    @Provides
    public AdapterDelegate<List<GalleryImage>> providesPrimaryAdapterDelegate(){
        return new GalleryPrimaryAdapterDelegate(R.layout.row_gallery_image);
    }
}
