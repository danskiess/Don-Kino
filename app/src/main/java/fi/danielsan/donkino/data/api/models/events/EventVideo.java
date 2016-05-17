package fi.danielsan.donkino.data.api.models.events;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "EventVideo", strict = false)
public class EventVideo implements Parcelable {

    @Element(name = "Location")
    private String location;

    @Element(name = "MediaResourceSubType")
    private String mediaResourceSubType;

    @Element(name = "MediaResourceFormat")
    private String mediaResourceFormat;

    public EventVideo() {
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMediaResourceSubType() {
        return mediaResourceSubType;
    }

    public void setMediaResourceSubType(String mediaResourceSubType) {
        this.mediaResourceSubType = mediaResourceSubType;
    }

    public String getMediaResourceFormat() {
        return mediaResourceFormat;
    }

    public void setMediaResourceFormat(String mediaResourceFormat) {
        this.mediaResourceFormat = mediaResourceFormat;
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
        dest.writeString(this.mediaResourceSubType);
        dest.writeString(this.mediaResourceFormat);
    }

    protected EventVideo(Parcel in) {
        this.location = in.readString();
        this.mediaResourceSubType = in.readString();
        this.mediaResourceFormat = in.readString();
    }

    public static final Parcelable.Creator<EventVideo> CREATOR = new Parcelable.Creator<EventVideo>() {
        public EventVideo createFromParcel(Parcel source) {
            return new EventVideo(source);
        }

        public EventVideo[] newArray(int size) {
            return new EventVideo[size];
        }
    };
}
