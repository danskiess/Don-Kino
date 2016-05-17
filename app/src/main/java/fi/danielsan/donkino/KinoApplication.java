package fi.danielsan.donkino;

import android.app.Application;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.SwipeDismissBehavior;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.core.CrashlyticsCore;
import com.jakewharton.threetenabp.AndroidThreeTen;

import fi.danielsan.donkino.di.components.AppComponent;
import fi.danielsan.donkino.di.components.DaggerAppComponent;
import fi.danielsan.donkino.di.components.DaggerDetailEventComponent;
import fi.danielsan.donkino.di.components.DaggerEventsComponent;
import fi.danielsan.donkino.di.components.DaggerLicenseComponent;
import fi.danielsan.donkino.di.components.DaggerScheduleComponent;
import fi.danielsan.donkino.di.components.DetailEventComponent;
import fi.danielsan.donkino.di.components.EventsComponent;
import fi.danielsan.donkino.di.components.LicenseComponent;
import fi.danielsan.donkino.di.components.ScheduleComponent;
import fi.danielsan.donkino.di.modules.AppModule;
import fi.danielsan.donkino.di.modules.DetailEventModule;
import fi.danielsan.donkino.di.modules.EventsModule;
import fi.danielsan.donkino.di.modules.LicenseModule;
import fi.danielsan.donkino.di.modules.ScheduleModule;
import fi.danielsan.donkino.misc.ReleaseTree;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class KinoApplication extends Application {

    private AppComponent appComponent;

    private ScheduleComponent scheduleComponent;
    private EventsComponent eventsComponent;
    private LicenseComponent licenseComponent;
    private DetailEventComponent detailEventComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDebugging();
        setupStrictMode();
        setupCrashlytics();
        setupAndroidThreeTen();
        setupGraph();
    }

    private void setupDebugging(){
        if (BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }
    }

    private void setupStrictMode() {
        if (BuildConfig.DEBUG){
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .build());
        }
    }

    private void setupCrashlytics(){
        CrashlyticsCore core = new CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG)
                .build();

        Fabric.with(this, new Crashlytics.Builder().core(core).build());
    }

    private void setupAndroidThreeTen() {
        AndroidThreeTen.init(this);
    }

    private void setupGraph(){
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public ScheduleComponent getScheduleComponent(){
        if (scheduleComponent == null) {
            scheduleComponent = DaggerScheduleComponent.builder()
                    .appComponent(appComponent)
                    .scheduleModule(new ScheduleModule())
                    .build();
        }
        return scheduleComponent;
    }

    public EventsComponent getEventsComponent(){
        if (eventsComponent == null) {
            eventsComponent = DaggerEventsComponent.builder()
                    .appComponent(appComponent)
                    .eventsModule(new EventsModule())
                    .build();
        }
        return eventsComponent;
    }

    public DetailEventComponent getDetailEventComponent(){
        if (detailEventComponent == null) {
            detailEventComponent = DaggerDetailEventComponent.builder()
                    .appComponent(appComponent)
                    .detailEventModule(new DetailEventModule())
                    .build();
        }
        return detailEventComponent;
    }

    public LicenseComponent getLicenseComponent(){
        if (licenseComponent == null) {
            licenseComponent = DaggerLicenseComponent.builder()
                    .appComponent(appComponent)
                    .licenseModule(new LicenseModule())
                    .build();
        }
        return licenseComponent;
    }
}
