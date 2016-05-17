package fi.danielsan.donkino.ui.main;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import fi.danielsan.donkino.R;
import fi.danielsan.donkino.ui.main.events.EventsFragment;
import fi.danielsan.donkino.ui.main.schedule.ScheduleFragment;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    private Resources resources;

    public SectionsPagerAdapter(@NonNull FragmentManager fm, @NonNull Resources resources) {
        super(fm);
        this.resources = resources;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return ScheduleFragment.newInstance();
            case 1:
                return EventsFragment.newInstance();
        }
        throw new IllegalArgumentException("Max value should be " + (getCount() - 1) + " but was " + position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return resources.getString(R.string.tab_schedule);
            case 1:
                return resources.getString(R.string.tab_program);
        }
        throw new IllegalArgumentException("Max value should be " + (getCount() - 1) + " but was " + position);
    }
}