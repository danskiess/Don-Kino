package fi.danielsan.donkino.di.components;

import dagger.Component;
import fi.danielsan.donkino.di.ActivityScope;
import fi.danielsan.donkino.di.modules.DetailEventModule;
import fi.danielsan.donkino.ui.detail.DetailEventFragment;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = DetailEventModule.class)
public interface DetailEventComponent {
    void inject(DetailEventFragment detailEventFragment);
}