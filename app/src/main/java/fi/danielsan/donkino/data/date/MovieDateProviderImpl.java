package fi.danielsan.donkino.data.date;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.TextStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieDateProviderImpl implements MovieDateProvider {

    private final LocalDate localDate;
    private final DateTimeFormatter dateTimeFormatter;

    public MovieDateProviderImpl(LocalDate localDate, DateTimeFormatter dateTimeFormatter) {
        this.localDate = localDate;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public List<MovieDate> getNextSevenDays() {
        List<MovieDate> movieDateCollection = new ArrayList<>();

        final int DESIRED_SIZE = 7;

        LocalDate localDateTemp;
        for (int i = 0; i < DESIRED_SIZE; ++i) {
            localDateTemp = localDate.plusDays(i);
            movieDateCollection.add(createMovieDate(localDateTemp));
        }

        return movieDateCollection;
    }

    private MovieDate createMovieDate(LocalDate localDate) {
        String weekDay = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault());
        String date = dateTimeFormatter.format(localDate);
        return new MovieDateImpl(weekDay, date);
    }
}
