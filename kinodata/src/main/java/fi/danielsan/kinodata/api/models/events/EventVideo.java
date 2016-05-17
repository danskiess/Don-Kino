package fi.danielsan.kinodata.api.models.events;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "EventVideo", strict = false)
public class EventVideo implements Parcelable {

    @Element(name = "Location")
    private String location;

    public EventVideo() {
    }

    protected EventVideo(Parcel in) {
        this.location = in.readString();
    }

    public String getLocation() {
        return location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.location);
    }

    public static final Creator<EventVideo> CREATOR = new Creator<EventVideo>() {
        public EventVideo createFromParcel(Parcel source) {
            return new EventVideo(source);
        }

        public EventVideo[] newArray(int size) {
            return new EventVideo[size];
        }
    };
}
