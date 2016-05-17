package fi.danielsan.donkino.ui.detail.presenter;


import org.threeten.bp.format.DateTimeFormatter;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import fi.danielsan.donkino.data.api.models.events.Event;
import fi.danielsan.donkino.data.api.models.VideoType;
import fi.danielsan.donkino.misc.KinoOrientationHelper;
import fi.danielsan.donkino.ui.detail.interactor.DetailInteractor;
import retrofit2.adapter.rxjava.HttpException;
import timber.log.Timber;

public class DetailPresenterImpl implements DetailPresenter, DetailInteractor.DetailCallback {

    private final DetailInteractor detailInteractor;
    private final KinoOrientationHelper kinoOrientationHelper;
    private final DateTimeFormatter dateTimeFormatter;

    private DetailView detailView;

    public DetailPresenterImpl(DetailInteractor detailInteractor,
                               KinoOrientationHelper kinoOrientationHelper,
                               DateTimeFormatter dateTimeFormatter) {
        this.detailInteractor = detailInteractor;
        this.kinoOrientationHelper = kinoOrientationHelper;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public void setView(DetailView detailView) {
        this.detailView = detailView;
        detailInteractor.setCallback(this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {
        detailInteractor.cancelRequest();
    }

    @Override
    public void loadEvent(long eventId) {
        detailView.hideErrorMessage();
        detailView.showLoading();
        detailInteractor.loadEvent(eventId);
    }

    @Override
    public void onEventLoaded(Event event) {
        detailView.hideLoading();
        detailView.setEvent(event);
        detailView.setReleaseDate(dateTimeFormatter.format(event.getLocalRelease()));
        if (event.getImages() != null) {
            if (event.getImages().getLargeImageLandscape() != null) {
                if (kinoOrientationHelper.getOrientation() == KinoOrientationHelper.Orientation.PORTRAIT){
                    detailView.setEventHeaderImage(event.getImages().getLargeImageLandscape());
                } else {
                    detailView.setEventHeaderImage(event.getImages().getMediumImagePortrait());
                }
            }
        }

        if (event.getVideos() != null) {
            if (event.getVideos().getEventVideo() != null)  {
                if (event.getVideos().getEventVideo().getLocation() != null) {
                    String url = event.getVideos().getEventVideo().getLocation();
                    String videoType = event.getVideos().getEventVideo().getMediaResourceFormat();
                    if (videoType.equalsIgnoreCase("YouTubeVideo")) {
                        detailView.setVideoUrl(VideoType.YOUTUBE, url);
                    } else {
                        detailView.setVideoUrl(VideoType.OTHER, url);
                    }
                }
            }
        }
    }

    @Override
    public void onEventError(Throwable throwable) {
        Timber.w(throwable, "onEventError");

        detailView.hideLoading();

        if (throwable instanceof UnknownHostException || throwable instanceof HttpException
                || throwable instanceof SocketTimeoutException) {
            detailView.showNetworkError();
        } else {
            detailView.showCasualError();
        }
    }
}
