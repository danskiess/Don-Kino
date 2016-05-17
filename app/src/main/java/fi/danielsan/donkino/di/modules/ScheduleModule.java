package fi.danielsan.donkino.di.modules;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.TemporalField;
import org.threeten.bp.temporal.WeekFields;

import java.util.Locale;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import fi.danielsan.donkino.R;
import fi.danielsan.donkino.data.storage.cache.schedule.ScheduleCache;
import fi.danielsan.donkino.data.storage.cache.schedule.ScheduleCacheImpl;
import fi.danielsan.donkino.data.storage.preferences.TheaterPreferenceWrapperImpl;
import fi.danielsan.donkino.data.api.KinoService;
import fi.danielsan.donkino.data.date.MovieDateProvider;
import fi.danielsan.donkino.data.date.MovieDateProviderImpl;
import fi.danielsan.donkino.data.storage.database.daos.TheaterDAO;
import fi.danielsan.donkino.data.storage.database.daos.TheaterDAOImpl;
import fi.danielsan.donkino.data.storage.repository.theater.TheaterRepository;
import fi.danielsan.donkino.data.storage.repository.theater.TheaterRepositoryImpl;
import fi.danielsan.donkino.di.ActivityScope;
import fi.danielsan.donkino.ui.base.AdapterDelegate;
import fi.danielsan.donkino.ui.base.SelectableAdapterDelegate;
import fi.danielsan.donkino.ui.main.schedule.ScheduleAdapter;
import fi.danielsan.donkino.ui.main.schedule.SchedulePrimaryListDelegate;
import fi.danielsan.donkino.ui.main.schedule.ScheduleSectionDelegate;
import fi.danielsan.donkino.ui.main.schedule.TheaterAdapter;
import fi.danielsan.donkino.ui.main.schedule.SpinnerDateAdapter;
import fi.danielsan.donkino.ui.main.schedule.interactors.ScheduleInteractor;
import fi.danielsan.donkino.ui.main.schedule.interactors.ScheduleInteractorImpl;
import fi.danielsan.donkino.ui.main.schedule.interactors.TheaterInteractor;
import fi.danielsan.donkino.ui.main.schedule.interactors.TheaterInteractorImpl;
import fi.danielsan.donkino.ui.main.schedule.presenter.SchedulePresenter;
import fi.danielsan.donkino.ui.main.schedule.presenter.SchedulePresenterImpl;
import fi.danielsan.donkino.data.storage.repository.schedule.ScheduleRepository;
import fi.danielsan.donkino.data.storage.repository.schedule.ScheduleRepositoryImpl;
import rx.subscriptions.CompositeSubscription;

@Module
public class ScheduleModule {

    @Provides
    public SchedulePresenter providesSchedulePresenter(ScheduleInteractor scheduleInteractor,
                                                       TheaterInteractor theaterInteractor){
        return new SchedulePresenterImpl(scheduleInteractor, theaterInteractor);
    }

    @Provides
    public ScheduleInteractor providesScheduleInteractor(ScheduleRepository scheduleRepository){
        return new ScheduleInteractorImpl(scheduleRepository, new CompositeSubscription());
    }

    @Provides
    @ActivityScope
    public ScheduleRepository providesScheduleRepository(KinoService kinoService, ScheduleCache scheduleCache){
        return new ScheduleRepositoryImpl(kinoService, scheduleCache);
    }

    @Provides
    public ScheduleCache providesScheduleCache(){
        return new ScheduleCacheImpl();
    }

    @Provides
    public TheaterInteractor providesTheaterInteractor(TheaterRepository theaterRepository){
        return new TheaterInteractorImpl(theaterRepository, new CompositeSubscription());
    }

    @Provides
    @ActivityScope
    public TheaterRepository providesTheaterRepository(KinoService kinoService,
                                                       TheaterDAO theaterDAO,
                                                       TheaterPreferenceWrapperImpl theaterPreferenceWrapper){
        return new TheaterRepositoryImpl(kinoService, theaterDAO, theaterPreferenceWrapper);
    }

    @Provides
    public TheaterDAO providesTheaterDAO(SQLiteOpenHelper sqLiteOpenHelper){
        return new TheaterDAOImpl(sqLiteOpenHelper);
    }

    @Provides
    public TheaterPreferenceWrapperImpl providesTheaterPreferences(SharedPreferences sharedPreferences,
                                                                   @Named(AppModule.DD_MM_YYYY_FORMAT) DateTimeFormatter dateTimeFormatter){
        return new TheaterPreferenceWrapperImpl(sharedPreferences, dateTimeFormatter);
    }

    @Provides
    public ScheduleAdapter providesScheduleAdapter(@Named("SchedulePrimaryDelegate") SelectableAdapterDelegate schedulePrimaryListDelegate,
                                                   @Named("ScheduleSectionDelegate") AdapterDelegate scheduleSectionDelegate){
        return new ScheduleAdapter(schedulePrimaryListDelegate, scheduleSectionDelegate);
    }

    @Provides
    @Named("SchedulePrimaryDelegate")
    public SelectableAdapterDelegate providesPrimaryListDelegate(@Named(AppModule.HH_MM_FORMAT) DateTimeFormatter dateTimeFormatter){
        return new SchedulePrimaryListDelegate(R.layout.row_schedule, dateTimeFormatter);
    }

    @Provides
    @Named("ScheduleSectionDelegate")
    public AdapterDelegate providesSectionListDelegate(){
        return new ScheduleSectionDelegate(R.layout.row_schedule_section_title);
    }

    @Provides
    public SpinnerDateAdapter providesSpinnerDateAdapter(Application application, MovieDateProvider movieDateProvider){
        return new SpinnerDateAdapter(application, movieDateProvider.getNextSevenDays());
    }

    @Provides
    public MovieDateProvider providesMovieDateProvider(@Named(AppModule.DD_MM_YYYY_FORMAT) DateTimeFormatter dateTimeFormatter){
        return new MovieDateProviderImpl(LocalDate.now(), dateTimeFormatter);
    }

    @Provides
    public TheaterAdapter providesTheaterAdapter(Application application){
        return new TheaterAdapter(application);
    }
}
