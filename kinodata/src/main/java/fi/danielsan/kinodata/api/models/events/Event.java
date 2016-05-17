package fi.danielsan.kinodata.api.models.events;

import android.os.Parcel;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

import fi.danielsan.kinodata.api.models.base.Program;


@Root(name = "Event", strict = false)
public class Event extends Program {

    @Element(name = "EventType")
    private String eventType;
    @Element(name = "ShortSynopsis", required = false)
    private String shortSynopsis;
    @Element(name = "Synopsis", required = false)
    private String synopsis;
    @Element(name = "EventURL")
    private String eventUrl;
    @ElementList(name = "Gallery")
    private List<GalleryImage> galleryImages;
    @Element(name = "Videos")
    private Videos videos;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getShortSynopsis() {
        return shortSynopsis;
    }

    public void setShortSynopsis(String shortSynopsis) {
        this.shortSynopsis = shortSynopsis;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public List<GalleryImage> getGalleryImages() {
        return galleryImages;
    }

    public void setGalleryImages(List<GalleryImage> galleryImages) {
        this.galleryImages = galleryImages;
    }

    public Videos getVideos() {
        return videos;
    }

    public void setVideos(Videos videos) {
        this.videos = videos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.eventType);
        dest.writeString(this.shortSynopsis);
        dest.writeString(this.synopsis);
        dest.writeString(this.eventUrl);
        dest.writeTypedList(galleryImages);
        dest.writeParcelable(this.videos, 0);
    }

    public Event() {
    }

    protected Event(Parcel in) {
        super(in);
        this.eventType = in.readString();
        this.shortSynopsis = in.readString();
        this.synopsis = in.readString();
        this.eventUrl = in.readString();
        this.galleryImages = in.createTypedArrayList(GalleryImage.CREATOR);
        this.videos = in.readParcelable(Videos.class.getClassLoader());
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}