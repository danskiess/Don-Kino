package fi.danielsan.donkino.data.api.models.events;

public enum EventType {

    NOW_IN_THEATERS("nowInTheaters"), COMING_SOON("ComingSoon");

    private String name;

    EventType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
