package fi.danielsan.donkino.data.storage.preferences;

public interface EventPreference {
    void saveNowInTheatersTimeStamp();
    void saveComingSoonTimeStamp();
    boolean hasNowInTheatersExpired();
    boolean hasComingSoonExpired();
}
