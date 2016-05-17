package fi.danielsan.donkino.data.api.models.theatres;


import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.Arrays;
import java.util.List;

@Root(name = "TheatreAreas", strict = false)
public class TheatreAreas implements Parcelable {

    @ElementList(inline = true, name = "TheatreArea")
    private List<TheatreArea> theatreAreaList;

    public TheatreAreas(List<TheatreArea> theatreAreas) {
        this.theatreAreaList = theatreAreas;
    }

    protected TheatreAreas(Parcel in) {
        this.theatreAreaList = in.createTypedArrayList(TheatreArea.CREATOR);
    }

    public List<TheatreArea> getTheatreAreaList() {
        return theatreAreaList;
    }

    @Override
    public String toString() {
        return Arrays.toString(theatreAreaList.toArray());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(theatreAreaList);
    }

    public static final Creator<TheatreAreas> CREATOR = new Creator<TheatreAreas>() {
        public TheatreAreas createFromParcel(Parcel source) {
            return new TheatreAreas(source);
        }

        public TheatreAreas[] newArray(int size) {
            return new TheatreAreas[size];
        }
    };
}