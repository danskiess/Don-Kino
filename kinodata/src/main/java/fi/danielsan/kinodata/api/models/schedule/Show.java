package fi.danielsan.kinodata.api.models.schedule;

import android.os.Parcel;

import org.joda.time.DateTime;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "Show", strict = false)
public class Show extends Program {

    @Element(name = "dttmShowStart")
    private DateTime showStart;
    @Element(name = "dttmShowEnd")
    private DateTime showEnd;
    @Element(name = "EventID")
    private int eventId;
    @Element(name = "Theatre")
    private String theatre;
    @Element(name = "TheatreAuditorium")
    private String theatreAuditorium;
    @Element(name = "ShowURL")
    private String showUrl;
    @Element(name = "EventURL")
    private String eventUrl;

    public DateTime getShowStart() {
        return showStart;
    }

    public void setShowStart(DateTime showStart) {
        this.showStart = showStart;
    }

    public DateTime getShowEnd() {
        return showEnd;
    }

    public void setShowEnd(DateTime showEnd) {
        this.showEnd = showEnd;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTheatre() {
        return theatre;
    }

    public void setTheatre(String theatre) {
        this.theatre = theatre;
    }

    public String getTheatreAuditorium() {
        return theatreAuditorium;
    }

    public void setTheatreAuditorium(String theatreAuditorium) {
        this.theatreAuditorium = theatreAuditorium;
    }

    public String getShowUrl() {
        return showUrl;
    }

    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeSerializable(this.showStart);
        dest.writeSerializable(this.showEnd);
        dest.writeInt(this.eventId);
        dest.writeString(this.theatre);
        dest.writeString(this.theatreAuditorium);
        dest.writeString(this.showUrl);
        dest.writeString(this.eventUrl);
    }

    public Show() {
    }

    protected Show(Parcel in) {
        super(in);
        this.showStart = (DateTime) in.readSerializable();
        this.showEnd = (DateTime) in.readSerializable();
        this.eventId = in.readInt();
        this.theatre = in.readString();
        this.theatreAuditorium = in.readString();
        this.showUrl = in.readString();
        this.eventUrl = in.readString();
    }

    public static final Creator<Show> CREATOR = new Creator<Show>() {
        public Show createFromParcel(Parcel source) {
            return new Show(source);
        }

        public Show[] newArray(int size) {
            return new Show[size];
        }
    };
}
