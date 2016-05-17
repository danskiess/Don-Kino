package fi.danielsan.donkino.data.api.models.schedule;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "Schedule", strict = false)
public class Schedule implements Parcelable {

    @ElementList(name = "Shows")
    private List<Show> showList;

    public Schedule() {
    }

    public Schedule(List<Show> list) {
        this.showList = list;
    }

    protected Schedule(Parcel in) {
        this.showList = in.createTypedArrayList(Show.CREATOR);
    }

    public List<Show> getShowList() {
        return showList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(showList);
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        public Schedule createFromParcel(Parcel source) {
            return new Schedule(source);
        }

        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };
}
