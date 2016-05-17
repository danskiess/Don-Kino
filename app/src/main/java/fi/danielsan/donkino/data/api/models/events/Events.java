package fi.danielsan.donkino.data.api.models.events;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "Events", strict = false)
public class Events {

    public Events() {}

    public Events(List<Event> eventList) {
        this.eventList = eventList;
    }

    @ElementList(inline = true)
    private List<Event> eventList;

    public List<Event> getEventList() {
        return eventList;
    }
}
