package fi.danielsan.donkino.data.api.models.theatres;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "TheatreArea")
public class TheatreArea implements Comparable<TheatreArea>, Parcelable {

    @Element(name = "ID")
    private int id;
    @Element(name = "Name")
    private String name;

    public TheatreArea() {}

    public TheatreArea(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected TheatreArea(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTheaterName() {
        return TextUtils.substring(name, name.indexOf(":") + 1 , name.length());
    }

    public boolean isChild(){
        return name.contains(":");
    }

    @Override
    public String toString() {
        return "Theater id: " + id + ", theater name: " + name;
    }

    @Override
    public int compareTo(TheatreArea another) {
        return this.name.compareTo(another.name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    public static final Creator<TheatreArea> CREATOR = new Creator<TheatreArea>() {
        public TheatreArea createFromParcel(Parcel source) {
            return new TheatreArea(source);
        }

        public TheatreArea[] newArray(int size) {
            return new TheatreArea[size];
        }
    };
}