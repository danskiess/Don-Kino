package fi.danielsan.donkino.di.components;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteOpenHelper;

import org.threeten.bp.format.DateTimeFormatter;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import fi.danielsan.donkino.data.api.KinoService;
import fi.danielsan.donkino.di.modules.AppModule;
import fi.danielsan.donkino.di.modules.NetworkModule;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    Application application();

    @Named(AppModule.DD_MM_YYYY_FORMAT) DateTimeFormatter fullDateFormatter();
    @Named(AppModule.HH_MM_FORMAT) DateTimeFormatter hoursFormatter();

    KinoService kinoService();
    SharedPreferences sharedPreferences();
    SQLiteOpenHelper sqliteOpenHelper();
    Resources resources();
}