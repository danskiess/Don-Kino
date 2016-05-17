package fi.danielsan.donkino.di.modules;

import android.app.Application;
import android.content.res.AssetManager;

import dagger.Module;
import dagger.Provides;
import fi.danielsan.donkino.di.ActivityScope;
import fi.danielsan.donkino.ui.licenses.LicenseReader;
import fi.danielsan.donkino.ui.licenses.LicenseReaderImpl;
import fi.danielsan.donkino.ui.licenses.interactors.LicenseInteractor;
import fi.danielsan.donkino.ui.licenses.interactors.LicenseInteractorImpl;
import fi.danielsan.donkino.ui.licenses.presenter.LicensePresenter;
import fi.danielsan.donkino.ui.licenses.presenter.LicensePresenterImpl;

@Module
public class LicenseModule {

    @Provides
    public LicensePresenter providesLicensePresenter(LicenseInteractor licenseInteractor){
        return new LicensePresenterImpl(licenseInteractor);
    }

    @Provides
    public LicenseInteractor providesLicenseInteractor(LicenseReader licenseReader){
        return new LicenseInteractorImpl(licenseReader);
    }

    @Provides
    public LicenseReader providesLicenseReader(AssetManager assetManager){
        return new LicenseReaderImpl(assetManager);
    }

    @Provides
    @ActivityScope
    public AssetManager providesAssertManager(Application application){
        return application.getAssets();
    }
}
