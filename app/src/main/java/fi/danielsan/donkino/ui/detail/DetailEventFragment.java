package fi.danielsan.donkino.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fi.danielsan.donkino.KinoApplication;
import fi.danielsan.donkino.R;
import fi.danielsan.donkino.data.api.models.VideoType;
import fi.danielsan.donkino.data.api.models.events.Event;
import fi.danielsan.donkino.ui.base.BaseFragment;
import fi.danielsan.donkino.ui.detail.presenter.DetailPresenter;
import timber.log.Timber;

public class DetailEventFragment extends BaseFragment implements DetailPresenter.DetailView {

    public static final String EVENT_ID = "EventId";

    public static DetailEventFragment getInstance(long eventId){
        DetailEventFragment detailEventFragment = new DetailEventFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EVENT_ID, eventId);
        detailEventFragment.setArguments(bundle);
        return detailEventFragment;
    }

    @Bind(R.id.detail_nested_scroll_view)
    NestedScrollView nestedScrollView;

    @Bind(R.id.detail_movie_title)
    TextView movieTitleTextView;

    @Bind(R.id.detail_movie_genres)
    TextView genresTextView;

    @Bind(R.id.detail_movie_synopsis)
    TextView synopsisTextView;

    @Bind(R.id.detail_movie_age)
    TextView ratingTextView;

    @Bind(R.id.detail_movie_length)
    TextView lengthTextView;

    @Bind(R.id.error_container)
    LinearLayout errorContainer;

    @Bind(R.id.error_message)
    TextView errorTextView;

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    @Inject
    DetailPresenter detailPresenter;

    @Inject
    GalleryAdapter galleryAdapter;

    @Inject
    GridLayoutManager gridLayoutManager;

    private long eventId;
    private EventDetailListener eventDetailListener;

    public DetailEventFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventId = getArguments().getLong(EVENT_ID);
        } else {
            throw new IllegalArgumentException("Must supply eventId as fragment argument");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            eventDetailListener = (EventDetailListener) context;
        } catch (Exception e) {
            Timber.e(e.getMessage());
        }
    }

    @Override
    public void injectDependencies() {
        ((KinoApplication) getActivity().getApplication()).getDetailEventComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailPresenter.setView(this);
        detailPresenter.loadEvent(eventId);
    }

    @Override
    public void onResume() {
        super.onResume();
        detailPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        detailPresenter.onPause();
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
    public void setEvent(Event event) {
        bindValues(event);
    }

    private void bindValues(final Event event){
        movieTitleTextView.setText(event.getTitle());
        genresTextView.setText(event.getGenres());
        synopsisTextView.setText(event.getSynopsis());
        ratingTextView.setText(event.getRating());
        lengthTextView.setText(String.format(getResources().getString(R.string.movie_length), event.getLengthInMinutes()));
        onDetailsSetAnimateContainer();
        setScrollState();
    }

    private void setScrollState() {
        /*
        ViewTreeObserver viewTreeObs = nestedScrollView.getViewTreeObserver();
        viewTreeObs.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                nestedScrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height = nestedScrollView.getMeasuredHeight();
                Timber.i("nestedScrollView height: " + height);

                int containerHeight = nestedScrollView.getChildAt(0).getHeight();
                Timber.i("nestedScrollView containerHeight: " + containerHeight);

                int appHeight = getActivity().findViewById(R.id.detail_appbarlayout).getHeight();
                Timber.i("appBarHeight: " + appHeight);

                int combinedHeight = movieTitleTextView.getHeight() + genresTextView.getHeight()
                        + lengthTextView.getHeight() + synopsisTextView.getHeight();
                Timber.i("combinedHeight: " + combinedHeight);

                if (heightPixels > containerHeight) {
                    CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsing_toolbar_layout);
                    AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) collapsingToolbar.getLayoutParams();
                    params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
                    collapsingToolbar.setLayoutParams(params);
                }
            }
        });
         */
    }

    private void onDetailsSetAnimateContainer(){


        Animation slideIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_top);
        slideIn.setInterpolator(new AccelerateDecelerateInterpolator());
        slideIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                nestedScrollView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        nestedScrollView.startAnimation(slideIn);
    }

    @Override
    public void setEventHeaderImage(String url) {
        eventDetailListener.showHeaderImage(url);
    }

    @Override
    public void setVideoUrl(VideoType videoType, String url) {
        eventDetailListener.enableAndSetVideoUrl(videoType, url);
    }

    @Override
    public void setReleaseDate(String date) {
        eventDetailListener.showReleaseDate(date);
    }

    @Override
    public void showNetworkError() {
        nestedScrollView.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.VISIBLE);
        errorTextView.setText(R.string.error_network);
    }

    @Override
    public void showResponseEmptyError() {
        nestedScrollView.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.VISIBLE);
        errorTextView.setText(R.string.error_empty_response);
    }

    @Override
    public void showCasualError() {
        nestedScrollView.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.VISIBLE);
        errorTextView.setText(R.string.error_casual);
    }

    @OnClick(R.id.error_try_again)
    public void onTryAgainClicked(){
        detailPresenter.loadEvent(eventId);
    }

    @Override
    public void hideErrorMessage() {
        errorContainer.setVisibility(View.GONE);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
