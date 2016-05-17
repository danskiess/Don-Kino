package fi.danielsan.donkino.misc;

public interface KinoOrientationHelper {

    enum Orientation{
        PORTRAIT, LANDSCAPE;
    }

    Orientation getOrientation();
}
