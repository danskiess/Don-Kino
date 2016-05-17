package fi.danielsan.donkino.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteOpenHelper;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.Locale;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fi.danielsan.donkino.KinoApplication;
import fi.danielsan.donkino.data.storage.database.KinoHelper;

@Module
public class AppModule {

    private final String KINO_PREFERENCES = "KinoPreferences";
    public static final String DD_MM_YYYY_FORMAT = "dd.MM.yyyy";
    public static final String HH_MM_FORMAT = "HH.mm";

    private KinoApplication kinoApplication;

    public AppModule(KinoApplication kinoApplication) {
        this.kinoApplication = kinoApplication;
    }

    @Provides
    public Application provideApplication() {
        return kinoApplication;
    }

    @Provides
    @Singleton
    public SQLiteOpenHelper providesSQLiteOpenHelper(Application application){
        return new KinoHelper(application);
    }

    @Provides
    @Singleton
    public SharedPreferences providesSharedPreferences(Application application){
        return application.getSharedPreferences(KINO_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    @Named(DD_MM_YYYY_FORMAT)
    public DateTimeFormatter providesDateTimeFormatter1(){
        return DateTimeFormatter.ofPattern(DD_MM_YYYY_FORMAT);
    }

    @Provides
    @Singleton
    @Named(HH_MM_FORMAT)
    public DateTimeFormatter providesDateTimeFormatter2(){
        return DateTimeFormatter.ofPattern(HH_MM_FORMAT);
    }

    @Provides
    @Singleton
    public Resources providesResources(Application application){
        return application.getResources();
    }
}