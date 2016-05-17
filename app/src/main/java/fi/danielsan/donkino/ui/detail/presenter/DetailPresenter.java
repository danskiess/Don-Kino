package fi.danielsan.donkino.ui.detail.presenter;

import fi.danielsan.donkino.data.api.models.events.Event;
import fi.danielsan.donkino.ui.base.Presenter;
import fi.danielsan.donkino.data.api.models.VideoType;

public interface DetailPresenter extends Presenter<DetailPresenter.DetailView> {

    interface DetailView {
        void showLoading();
        void hideLoading();

        void setEvent(Event event);
        void setEventHeaderImage(String url);
        void setVideoUrl(VideoType videoType, String url);
        void setReleaseDate(String date);

        void showNetworkError();
        void showResponseEmptyError();
        void showCasualError();
        void hideErrorMessage();
    }

    void loadEvent(long eventId);
}
