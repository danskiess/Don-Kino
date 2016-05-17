package fi.danielsan.donkino.misc;

import android.content.res.Configuration;
import android.content.res.Resources;

import timber.log.Timber;

public class KinoOrientationHelperImpl implements KinoOrientationHelper {

    private final Resources resources;

    public KinoOrientationHelperImpl(Resources resources) {
        this.resources = resources;
    }

    @Override
    public Orientation getOrientation() {
        if (resources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            return Orientation.PORTRAIT;
        } else {
            return Orientation.LANDSCAPE;
        }
    }
}
