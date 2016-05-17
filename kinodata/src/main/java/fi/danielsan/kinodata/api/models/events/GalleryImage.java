package fi.danielsan.kinodata.api.models.events;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "GalleryImage", strict = false)
public class GalleryImage implements Parcelable {

    @Element(name = "Location")
    private String location;

    @Element(name = "ThumbnailLocation")
    private String thumbnailLocation;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getThumbnailLocation() {
        return thumbnailLocation;
    }

    public void setThumbnailLocation(String thumbnailLocation) {
        this.thumbnailLocation = thumbnailLocation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.location);
        dest.writeString(this.thumbnailLocation);
    }

    public GalleryImage() {
    }

    protected GalleryImage(Parcel in) {
        this.location = in.readString();
        this.thumbnailLocation = in.readString();
    }

    public static final Creator<GalleryImage> CREATOR = new Creator<GalleryImage>() {
        public GalleryImage createFromParcel(Parcel source) {
            return new GalleryImage(source);
        }

        public GalleryImage[] newArray(int size) {
            return new GalleryImage[size];
        }
    };
}
