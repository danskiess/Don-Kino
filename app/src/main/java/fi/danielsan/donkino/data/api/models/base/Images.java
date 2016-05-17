package fi.danielsan.donkino.data.api.models.base;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Images", strict = false)
public class Images implements Parcelable {

    @Element(name = "EventMicroImagePortrait", required = false)
    private String microImagePortrait;

    @Element(name = "EventSmallImagePortrait", required = false)
    private String smallImagePortrait;

    @Element(name = "EventMediumImagePortrait", required = false)
    private String mediumImagePortrait;

    @Element(name = "EventLargeImagePortrait", required = false)
    private String largeImagePortrait;

    @Element(name = "EventSmallImageLandscape", required = false)
    private String smallImageLandscape;

    @Element(name = "EventLargeImageLandscape", required = false)
    private String largeImageLandscape;

    public Images() {}

    protected Images(Parcel in) {
        this.microImagePortrait = in.readString();
        this.smallImagePortrait = in.readString();
        this.mediumImagePortrait = in.readString();
        this.largeImagePortrait = in.readString();
        this.smallImageLandscape = in.readString();
        this.largeImageLandscape = in.readString();
    }

    public String getMicroImagePortrait() {
        return microImagePortrait;
    }

    public void setMicroImagePortrait(String microImagePortrait) {
        this.microImagePortrait = microImagePortrait;
    }

    public String getSmallImagePortrait() {
        return smallImagePortrait;
    }

    public void setSmallImagePortrait(String smallImagePortrait) {
        this.smallImagePortrait = smallImagePortrait;
    }

    public String getMediumImagePortrait() {
        return mediumImagePortrait;
    }

    public void setMediumImagePortrait(String mediumImagePortrait) {
        this.mediumImagePortrait = mediumImagePortrait;
    }

    public String getLargeImagePortrait() {
        return largeImagePortrait;
    }

    public void setLargeImagePortrait(String largeImagePortrait) {
        this.largeImagePortrait = largeImagePortrait;
    }

    public String getSmallImageLandscape() {
        return smallImageLandscape;
    }

    public void setSmallImageLandscape(String smallImageLandscape) {
        this.smallImageLandscape = smallImageLandscape;
    }

    public String getLargeImageLandscape() {
        return largeImageLandscape;
    }

    public void setLargeImageLandscape(String largeImageLandscape) {
        this.largeImageLandscape = largeImageLandscape;
    }

    @Override
    public String toString() {
        return microImagePortrait +
                "\n" +
                smallImagePortrait +
                "\n" +
                mediumImagePortrait +
                "\n" +
                largeImagePortrait +
                "\n" +
                smallImageLandscape +
                "\n" +
                largeImageLandscape +
                "\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.microImagePortrait);
        dest.writeString(this.smallImagePortrait);
        dest.writeString(this.mediumImagePortrait);
        dest.writeString(this.largeImagePortrait);
        dest.writeString(this.smallImageLandscape);
        dest.writeString(this.largeImageLandscape);
    }

    public static final Creator<Images> CREATOR = new Creator<Images>() {
        public Images createFromParcel(Parcel source) {
            return new Images(source);
        }

        public Images[] newArray(int size) {
            return new Images[size];
        }
    };
}