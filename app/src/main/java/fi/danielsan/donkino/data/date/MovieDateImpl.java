package fi.danielsan.donkino.data.date;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieDateImpl implements MovieDate {

    private final String dayOfWeek;
    private final String date;

    public MovieDateImpl(String dayOfWeek, String date) {
        this.dayOfWeek = dayOfWeek;
        this.date = date;
    }

    @Override
    public String getDay() {
        return dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1, dayOfWeek.length());
    }

    @Override
    public String getFullDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dayOfWeek);
        dest.writeString(this.date);
    }

    protected MovieDateImpl(Parcel in) {
        this.dayOfWeek = in.readString();
        this.date = in.readString();
    }

    public static final Creator<MovieDateImpl> CREATOR = new Creator<MovieDateImpl>() {
        public MovieDateImpl createFromParcel(Parcel source) {
            return new MovieDateImpl(source);
        }

        public MovieDateImpl[] newArray(int size) {
            return new MovieDateImpl[size];
        }
    };
}
