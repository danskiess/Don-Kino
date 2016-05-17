package fi.danielsan.donkino.ui.main.schedule;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fi.danielsan.donkino.KinoApplication;
import fi.danielsan.donkino.R;
import fi.danielsan.donkino.data.api.models.schedule.Schedule;
import fi.danielsan.donkino.data.api.models.schedule.Show;
import fi.danielsan.donkino.data.api.models.theatres.TheatreArea;
import fi.danielsan.donkino.data.date.MovieDate;
import fi.danielsan.donkino.misc.DividerDecorator;
import fi.danielsan.donkino.misc.ItemClickListener;
import fi.danielsan.donkino.misc.OnSpinnerViewListener;
import fi.danielsan.donkino.misc.PaddingDecorator;
import fi.danielsan.donkino.misc.SpinnerInteractionListener;
import fi.danielsan.donkino.ui.Navigator;
import fi.danielsan.donkino.ui.base.ListFragment;
import fi.danielsan.donkino.ui.main.schedule.presenter.SchedulePresenter;
import fi.danielsan.donkino.utils.AnimUtils;

public class ScheduleFragment extends ListFragment implements SchedulePresenter.ScheduleView,
        ItemClickListener<Show, Integer>, OnSpinnerViewListener, SearchView.OnQueryTextListener {

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.error_container)
    LinearLayout errorContainer;

    @Bind(R.id.error_message)
    TextView errorTextView;

    @Bind(R.id.error_try_again)
    TextView tryAgainTextView;

    @Bind(R.id.search_card)
    CardView searchCardView;

    @Bind(R.id.theater_spinner)
    Spinner theaterSpinner;

    @Bind(R.id.date_spinner)
    Spinner dateSpinner;

    SearchView searchView;

    Menu menu;

    @Inject
    SchedulePresenter schedulePresenter;

    @Inject
    ScheduleAdapter scheduleAdapter;

    @Inject
    TheaterAdapter spinnerTheaterAdapter;

    @Inject
    SpinnerDateAdapter spinnerDateAdapter;

    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void injectDependencies() {
        ((KinoApplication) getActivity().getApplication()).getScheduleComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SpinnerInteractionListener spinnerInteractionListener = new SpinnerInteractionListener(this); //TODO: Inject these?
        dateSpinner.setOnTouchListener(spinnerInteractionListener);
        dateSpinner.setOnItemSelectedListener(spinnerInteractionListener);
        dateSpinner.setAdapter(spinnerDateAdapter);

        theaterSpinner.setOnTouchListener(spinnerInteractionListener);
        theaterSpinner.setOnItemSelectedListener(spinnerInteractionListener);
        theaterSpinner.setAdapter(spinnerTheaterAdapter);

        schedulePresenter.setView(this);
        schedulePresenter.loadTheaters();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_schedule, menu);
        this.menu = menu;
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setQueryHint(getString(R.string.filter_hint));
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_filter:
                if (searchCardView.getVisibility() == View.INVISIBLE
                        || searchCardView.getVisibility() == View.GONE) {
                    AnimUtils.animateDown(getContext(), searchCardView);
                    hideSearchView();
                } else {
                    hideSearchBar();
                }
                break;
            case R.id.menu_search:
                hideSearchBar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setupRecyclerView() {
        scheduleAdapter.setClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); //TODO: Inject these?
        recyclerView.addItemDecoration(new PaddingDecorator(getContext())); //TODO: Inject these?
        recyclerView.addItemDecoration(new DividerDecorator(getContext(), R.drawable.divider)); //TODO: Inject these?
        recyclerView.setAdapter(scheduleAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        schedulePresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        schedulePresenter.onPause();
    }

    @Override
    public void onDestroyOptionsMenu() {
        if (searchView != null) {
            searchView.setOnQueryTextListener(null);
            searchView = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setSchedule(Schedule schedule) {
        scheduleAdapter.setItems(schedule.getShowList());
    }

    @Override
    public void setTheaters(List<TheatreArea> theaters) {
        spinnerTheaterAdapter.setTheaters(theaters);
    }

    @Override
    public void setPreferredTheaterId(int theaterId) {
        String date = ((MovieDate) dateSpinner.getItemAtPosition(dateSpinner.getSelectedItemPosition())).getFullDate();
        int position = spinnerTheaterAdapter.getTheaterPositionBy(theaterId);
        theaterSpinner.setSelection(position);
        schedulePresenter.loadSchedule(theaterId, date);
    }

    @Override
    public void showNetworkError() {
        errorContainer.setVisibility(View.VISIBLE);
        errorTextView.setText(R.string.error_network);
    }

    @Override
    public void showEmptyResponseError() {
        errorContainer.setVisibility(View.VISIBLE);
        errorTextView.setText(R.string.error_empty_response);
    }

    @Override
    public void showCasualError() {
        errorContainer.setVisibility(View.VISIBLE);
        errorTextView.setText(R.string.error_casual);
    }

    @Override
    public void showFilterError() {
        errorContainer.setVisibility(View.VISIBLE);
        errorTextView.setText(R.string.error_filter);
    }

    @Override
    public void hideSearchView() {
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        if (menuItem.isActionViewExpanded()){
            MenuItemCompat.collapseActionView(menuItem);
        }
    }

    @Override
    public void hideSearchBar() {
        if (searchCardView.getVisibility() == View.VISIBLE){
            AnimUtils.animateUp(getContext(), searchCardView);
        }
    }

    @Override
    public void hideErrorMessage() {
        errorContainer.setVisibility(View.GONE);
    }

    //Theater/Date click
    @Override
    public void onSpinnerItemClick(View view, int position) {
        int theaterId = (int) theaterSpinner.getItemIdAtPosition(theaterSpinner.getSelectedItemPosition());
        String date = ((MovieDate) dateSpinner.getItemAtPosition(dateSpinner.getSelectedItemPosition())).getFullDate();
        scheduleAdapter.removeAll();
        schedulePresenter.loadSchedule(theaterId, date);
    }

    //Schedule item click
    @Override
    public void onItemClicked(Show item, Integer integer) {
        if (integer == ScheduleConstants.INFO){
            Navigator.navigateToDetailActivity(getActivity(), item.getEventId(), null);
        } else if (integer == ScheduleConstants.TICKET){
            Navigator.startBrowserIntent(getActivity(), item.getShowUrl());
        }
    }

    //Try again
    @OnClick(R.id.error_try_again)
    public void onTryAgainClicked(){
        schedulePresenter.retry();
    }

    //Related to searchView
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String title) {
        int theaterId = (int) theaterSpinner.getItemIdAtPosition(theaterSpinner.getSelectedItemPosition());
        String date = ((MovieDate) dateSpinner.getItemAtPosition(dateSpinner.getSelectedItemPosition())).getFullDate();
        schedulePresenter.filterSchedule(theaterId, date, title);
        return true;
    }

    public boolean onBackPressed(){
        if (searchCardView.getVisibility() == View.VISIBLE) {
            hideSearchBar();
            return true;
        }
        return false;
    }

}
