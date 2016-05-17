package fi.danielsan.donkino.data.storage.preferences;

import android.content.SharedPreferences;

import org.threeten.bp.Duration;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import timber.log.Timber;

public class EventPreferenceWrapper implements EventPreference{

    private final String NOW_IN_THEATERS_EXPIRATION_DATE = "EventNowInTheatersExpirationDate";
    private final String COMING_SOON_EXPIRATION_DATE = "EventComingSoonExpirationDate";

    private final SharedPreferences sharedPreferences;
    private final DateTimeFormatter dateTimeFormatter;

    public EventPreferenceWrapper(SharedPreferences sharedPreferences, DateTimeFormatter dateTimeFormatter) {
        this.sharedPreferences = sharedPreferences;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public void saveNowInTheatersTimeStamp() {
        sharedPreferences.edit().putString(NOW_IN_THEATERS_EXPIRATION_DATE, dateTimeFormatter.format(LocalDate.now())).apply();
    }

    @Override
    public void saveComingSoonTimeStamp() {
        sharedPreferences.edit().putString(COMING_SOON_EXPIRATION_DATE, dateTimeFormatter.format(LocalDate.now())).apply();
    }

    @Override
    public boolean hasNowInTheatersExpired() {
        String date = sharedPreferences.getString(NOW_IN_THEATERS_EXPIRATION_DATE, null);
        if (date != null){
            LocalDate previousDate = LocalDate.parse(date, dateTimeFormatter);
            LocalDate today = LocalDate.now();
            long days = Duration.between(previousDate.atStartOfDay(), today.atStartOfDay()).toDays();
            Timber.d("hasComingSoonExpired, Day difference: " + days);
            if (days < 1){
                Timber.d("hasNowInTheatersExpired, hasNotExpired!");
                return false;
            }
        }
        Timber.d("hasNowInTheatersExpired, hasExpired");
        return true;
    }

    @Override
    public boolean hasComingSoonExpired() {
        String date = sharedPreferences.getString(COMING_SOON_EXPIRATION_DATE, null);
        if (date != null){
            LocalDate previousDate = LocalDate.parse(date, dateTimeFormatter);
            LocalDate today = LocalDate.now();
            long days = Duration.between(previousDate.atStartOfDay(), today.atStartOfDay()).toDays();
            Timber.d("hasComingSoonExpired, Day difference: " + days);
            if (days < 1){
                Timber.d("hasNowInTheatersExpired, hasNotExpired!");
                return false;
            }
        }
        Timber.d("hasNowInTheatersExpired, hasExpired");
        return true;
    }
}
