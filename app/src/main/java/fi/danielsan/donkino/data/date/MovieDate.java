package fi.danielsan.donkino.data.date;

import android.os.Parcelable;
import android.support.annotation.NonNull;

public interface MovieDate extends Parcelable {
    @NonNull String getDay();
    @NonNull String getFullDate();
}
