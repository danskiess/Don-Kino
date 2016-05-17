package fi.danielsan.kinodata.api.models.events;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Videos", strict = false)
public class Videos implements Parcelable {

    @Element(name = "EventVideo", required = false)
    private EventVideo eventVideo;

    public Videos() {
    }

    protected Videos(Parcel in) {
        this.eventVideo = in.readParcelable(EventVideo.class.getClassLoader());
    }

    public EventVideo getEventVideo() {
        return eventVideo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.eventVideo, 0);
    }

    public static final Creator<Videos> CREATOR = new Creator<Videos>() {
        public Videos createFromParcel(Parcel source) {
            return new Videos(source);
        }

        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };
}