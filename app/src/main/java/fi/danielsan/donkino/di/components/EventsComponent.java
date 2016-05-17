package fi.danielsan.donkino.di.components;

import dagger.Component;
import fi.danielsan.donkino.di.ActivityScope;
import fi.danielsan.donkino.di.modules.EventsModule;
import fi.danielsan.donkino.ui.main.events.EventsFragment;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = EventsModule.class)
public interface EventsComponent {
    void inject(EventsFragment eventsFragment);
}
