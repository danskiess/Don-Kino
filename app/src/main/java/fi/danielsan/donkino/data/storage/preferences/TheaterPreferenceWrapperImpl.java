package fi.danielsan.donkino.data.storage.preferences;

import android.content.SharedPreferences;


import org.threeten.bp.Duration;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import timber.log.Timber;

public class TheaterPreferenceWrapperImpl implements TheaterPreference{

    private final String EXPIRATION_DATE = "ExpirationDate";
    private final String PREFERRED_THEATER = "PreferredTheater";
    private final int DEFAULT_HELSINKI_REGION = 1014;

    private final SharedPreferences sharedPreferences;
    private final DateTimeFormatter dateTimeFormatter;

    public TheaterPreferenceWrapperImpl(SharedPreferences sharedPreferences, DateTimeFormatter dateTimeFormatter) {
        this.sharedPreferences = sharedPreferences;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public int getPreferredTheater(){
        return sharedPreferences.getInt(PREFERRED_THEATER, DEFAULT_HELSINKI_REGION);
    }

    public void savePreferredTheater(int preference){
        sharedPreferences.edit().putInt(PREFERRED_THEATER, preference).apply();
        sharedPreferences.edit().putString(EXPIRATION_DATE, dateTimeFormatter.format(LocalDate.now())).apply();
    }

    @Override
    public boolean hasExpired() {
        String date = sharedPreferences.getString(EXPIRATION_DATE, null);
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
