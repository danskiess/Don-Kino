package fi.danielsan.donkino.di.modules;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.widget.GridLayoutManager;

import org.threeten.bp.format.DateTimeFormatter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import fi.danielsan.donkino.R;
import fi.danielsan.donkino.data.api.KinoService;
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
import fi.danielsan.donkino.ui.main.events.EventsAdapter;
import fi.danielsan.donkino.ui.main.events.EventsPrimaryListDelegate;
import fi.danielsan.donkino.ui.main.events.interactors.EventsInteractor;
import fi.danielsan.donkino.ui.main.events.interactors.EventsInteractorImpl;
import fi.danielsan.donkino.ui.main.events.presenter.EventsPresenter;
import fi.danielsan.donkino.ui.main.events.presenter.EventsPresenterImpl;
import rx.subscriptions.CompositeSubscription;

@Module
public class EventsModule {

    @Provides
    public EventsPresenter providesEventsPresenter(EventsInteractor eventsInteractor){
        return new EventsPresenterImpl(eventsInteractor);
    }

    @Provides
    public EventsInteractor providesEventsInteractor(EventsRepository eventsRepository){
        return new EventsInteractorImpl(eventsRepository, new CompositeSubscription());
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
    public EventDAO providesEventDAO(SQLiteOpenHelper sqLiteOpenHelper,
                                     EventStatementFactory eventStatementFactory){
        return new EventDAOImpl(sqLiteOpenHelper, eventStatementFactory);
    }

    @Provides
    public EventStatementFactory providesEventStatementFactory(){
        return new EventStatementFactoryImpl();
    }

    @Provides
    public EventPreference providesEventPreference(SharedPreferences sharedPreferences,
                                                   @Named(AppModule.DD_MM_YYYY_FORMAT) DateTimeFormatter dateTimeFormatter){
        return new EventPreferenceWrapper(sharedPreferences, dateTimeFormatter);
    }

    @Provides
    public EventCache providesEventCache(){
        return new EventCacheImpl();
    }

    @Provides
    public EventsAdapter providesEventsAdapter(EventsPrimaryListDelegate eventsPrimaryListDelegate){
        return new EventsAdapter(eventsPrimaryListDelegate);
    }

    @Provides
    public EventsPrimaryListDelegate providesListDelegate(){
        return new EventsPrimaryListDelegate(R.layout.row_event);
    }

    @Provides
    public GridLayoutManager providesGridLayoutManager(Application application, Resources resources){
        return new GridLayoutManager(application, resources.getInteger(R.integer.grid_cell_count));
    }
}
