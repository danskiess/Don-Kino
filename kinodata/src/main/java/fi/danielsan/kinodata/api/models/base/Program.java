package fi.danielsan.kinodata.api.models.base;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;
import org.simpleframework.xml.Element;

public class Program implements Parcelable {

    @Element(name = "ID")
    private int id;

    @Element(name = "Title")
    private String title;

    @Element(name = "OriginalTitle")
    private String originalTitle;

    @Element(name = "LengthInMinutes")
    private String lengthInMinutes;

    @Element(name = "Genres")
    private String genres;

    @Element(name = "ProductionYear")
    private int productionYear;

    @Element(name ="dtLocalRelease")
    private DateTime localRelease;

    @Element(name = "Rating")
    private String rating;

    @Element(name = "Images")
    private Images images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(String lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public DateTime getLocalRelease() {
        return localRelease;
    }

    public void setLocalRelease(DateTime localRelease) {
        this.localRelease = localRelease;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.originalTitle);
        dest.writeString(this.lengthInMinutes);
        dest.writeString(this.genres);
        dest.writeInt(this.productionYear);
        dest.writeSerializable(this.localRelease);
        dest.writeString(this.rating);
        dest.writeParcelable(this.images, 0);
    }

    public Program() {
    }

    protected Program(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.originalTitle = in.readString();
        this.lengthInMinutes = in.readString();
        this.genres = in.readString();
        this.productionYear = in.readInt();
        this.localRelease = (DateTime) in.readSerializable();
        this.rating = in.readString();
        this.images = in.readParcelable(Images.class.getClassLoader());
    }

}