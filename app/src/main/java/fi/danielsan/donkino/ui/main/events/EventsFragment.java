package fi.danielsan.donkino.ui.main.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fi.danielsan.donkino.KinoApplication;
import fi.danielsan.donkino.R;
import fi.danielsan.donkino.data.api.models.events.Event;
import fi.danielsan.donkino.ui.Navigator;
import fi.danielsan.donkino.ui.base.ListFragment;
import fi.danielsan.donkino.ui.main.events.presenter.EventsPresenter;
import fi.danielsan.donkino.misc.ItemClickListener;

public class EventsFragment extends ListFragment implements EventsPresenter.EventsView,
        ItemClickListener<Event, View>{

    private final String SELECTED_EVENT_TYPE = "SelectedEventType";

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.error_container)
    LinearLayout errorContainer;

    @Bind(R.id.error_message)
    TextView errorTextView;

    @Inject
    EventsPresenter eventsPresenter;

    @Inject
    EventsAdapter eventsAdapter;

    @Inject
    GridLayoutManager gridLayoutManager;

    private int selectedAction;

    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void injectDependencies() {
        ((KinoApplication) getActivity().getApplication()).getEventsComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventsPresenter.setView(this);
        if (savedInstanceState != null) {
            long type = savedInstanceState.getLong(SELECTED_EVENT_TYPE, R.id.menu_now_in_theaters);
            if (type == R.id.menu_now_in_theaters){
                loadNowInTheaters();
            } else {
                loadSoonInTheaters();
            }
        } else {
            loadNowInTheaters();
        }
    }

    @Override
    public void setupRecyclerView() {
        eventsAdapter.setClickListener(this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(eventsAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        eventsPresenter.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(SELECTED_EVENT_TYPE, selectedAction);
    }

    @Override
    public void onPause() {
        super.onPause();
        eventsPresenter.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_events, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_now_in_theaters:
                loadNowInTheaters();
                break;
            case R.id.menu_upcoming:
                loadSoonInTheaters();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadNowInTheaters(){
        eventsPresenter.loadNowInTheaters();
        selectedAction = R.id.menu_now_in_theaters;
        eventsAdapter.clear();
    }
    private void loadSoonInTheaters(){
        eventsPresenter.loadSoonInTheaters();
        selectedAction = R.id.menu_upcoming;
        eventsAdapter.clear();
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
    public void setEvents(List<Event> events) {
        eventsAdapter.setItems(events);

        Animation slideIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_top);
        slideIn.setInterpolator(new AccelerateDecelerateInterpolator());
        slideIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        recyclerView.startAnimation(slideIn);
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
    public void hideErrorMessage() {
        errorContainer.setVisibility(View.GONE);
    }

    @Override
    public void onItemClicked(Event event, View view) {
        Navigator.navigateToDetailActivity(getActivity(), event.getId(), view);
    }

    @OnClick(R.id.error_try_again)
    public void onTryAgainClicked(){
        eventsPresenter.loadNowInTheaters();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
