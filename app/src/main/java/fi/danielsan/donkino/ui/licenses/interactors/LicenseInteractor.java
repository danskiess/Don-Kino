package fi.danielsan.donkino.ui.licenses.interactors;

public interface LicenseInteractor {

    interface LicenseCallback{
        void onLicensesLoaded(String license);
    }

    void cancel();
    void loadLicenses();
    void setCallback(LicenseCallback licenseCallback);
}
