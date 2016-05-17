package fi.danielsan.donkino.ui.licenses.presenter;

import fi.danielsan.donkino.ui.base.Presenter;

public interface LicensePresenter extends Presenter<LicensePresenter.LicenseView>{

    interface LicenseView{
        void showLicenses(String license);
    }

    void loadLicenses();
}
