package fi.danielsan.donkino.data.date;


import java.util.List;

public interface MovieDateProvider {
    List<MovieDate> getNextSevenDays();
}
