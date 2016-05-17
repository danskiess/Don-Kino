package fi.danielsan.donkino.data.storage.preferences;

public interface TheaterPreference {
    int getPreferredTheater();
    void savePreferredTheater(int preference);
    boolean hasExpired();
}
