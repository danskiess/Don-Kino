package fi.danielsan.donkino.ui.licenses.presenter;

import fi.danielsan.donkino.ui.licenses.interactors.LicenseInteractor;

public class LicensePresenterImpl implements LicensePresenter,
        LicenseInteractor.LicenseCallback {


    private final LicenseInteractor licenseInteractor;
    private LicenseView licenseView;

    public LicensePresenterImpl(LicenseInteractor licenseInteractor) {
        this.licenseInteractor = licenseInteractor;
    }

    @Override
    public void setView(LicenseView licenseView) {
        this.licenseView = licenseView;
        licenseInteractor.setCallback(this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {
        licenseInteractor.cancel();
    }

    @Override
    public void loadLicenses() {
        licenseInteractor.loadLicenses();
    }

    @Override
    public void onLicensesLoaded(String license) {
        licenseView.showLicenses(license);
    }

}
