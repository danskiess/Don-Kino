package fi.danielsan.donkino.data.api.models.schedule;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.threeten.bp.LocalDateTime;

@Root(name = "Show", strict = false)
public class Show implements Parcelable {

    @Element(name = "ID")
    private int id;

    @Element(name = "dttmShowStart")
    private LocalDateTime showStart;

    @Element(name = "dttmShowEnd")
    private LocalDateTime showEnd;

    @Element(name = "EventID")
    private int eventId;

    @Element(name = "Title")
    private String title;

    @Element(name = "OriginalTitle")
    private String originalTitle;

    @Element(name = "TheatreID")
    private String theatreId;

    @Element(name = "Theatre")
    private String theatre;

    @Element(name = "TheatreAndAuditorium")
    private String theatreAndAuditorium;

    @Element(name = "ShowURL")
    private String showUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getShowStart() {
        return showStart;
    }

    public void setShowStart(LocalDateTime showStart) {
        this.showStart = showStart;
    }

    public LocalDateTime getShowEnd() {
        return showEnd;
    }

    public void setShowEnd(LocalDateTime showEnd) {
        this.showEnd = showEnd;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(String theatreId) {
        this.theatreId = theatreId;
    }

    public String getTheatre() {
        return theatre;
    }

    public void setTheatre(String theatre) {
        this.theatre = theatre;
    }

    public String getTheatreAndAuditorium() {
        return theatreAndAuditorium;
    }

    public void setTheatreAndAuditorium(String theatreAuditorium) {
        this.theatreAndAuditorium = theatreAuditorium;
    }

    public String getShowUrl() {
        return showUrl;
    }

    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeSerializable(this.showStart);
        dest.writeSerializable(this.showEnd);
        dest.writeInt(this.eventId);
        dest.writeString(this.title);
        dest.writeString(this.originalTitle);
        dest.writeString(this.theatreId);
        dest.writeString(this.theatre);
        dest.writeString(this.theatreAndAuditorium);
        dest.writeString(this.showUrl);
    }

    public Show() {
    }

    protected Show(Parcel in) {
        this.id = in.readInt();
        this.showStart = (LocalDateTime) in.readSerializable();
        this.showEnd = (LocalDateTime) in.readSerializable();
        this.eventId = in.readInt();
        this.title = in.readString();
        this.originalTitle = in.readString();
        this.theatreId = in.readString();
        this.theatre = in.readString();
        this.theatreAndAuditorium = in.readString();
        this.showUrl = in.readString();
    }

    public static final Parcelable.Creator<Show> CREATOR = new Parcelable.Creator<Show>() {
        public Show createFromParcel(Parcel source) {
            return new Show(source);
        }

        public Show[] newArray(int size) {
            return new Show[size];
        }
    };
}
