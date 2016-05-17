package fi.danielsan.donkino.di.components;

import dagger.Component;
import fi.danielsan.donkino.di.ActivityScope;
import fi.danielsan.donkino.di.modules.LicenseModule;
import fi.danielsan.donkino.ui.licenses.LicenseActivity;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = LicenseModule.class)
public interface LicenseComponent {
    void inject(LicenseActivity licenseActivity);
}
