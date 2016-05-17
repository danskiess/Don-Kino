package fi.danielsan.donkino.di.components;

import dagger.Component;
import fi.danielsan.donkino.di.ActivityScope;
import fi.danielsan.donkino.di.modules.NetworkModule;
import fi.danielsan.donkino.di.modules.ScheduleModule;
import fi.danielsan.donkino.ui.main.schedule.ScheduleFragment;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = ScheduleModule.class)
public interface ScheduleComponent extends AppComponent{
    void inject(ScheduleFragment scheduleFragment);
}