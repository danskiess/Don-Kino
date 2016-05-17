package fi.danielsan.donkino.ui.detail.interactor;

import fi.danielsan.donkino.data.api.models.events.Event;
import fi.danielsan.donkino.data.storage.repository.events.EventsRepository;
import fi.danielsan.donkino.ui.detail.presenter.DetailPresenter;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class DetailInteractorImpl implements DetailInteractor{

    private final EventsRepository eventsRepository;

    private CompositeSubscription compositeSubscription;
    private DetailCallback detailCallback;

    public DetailInteractorImpl(EventsRepository eventsRepository, CompositeSubscription compositeSubscription) {
        this.eventsRepository = eventsRepository;
        this.compositeSubscription = compositeSubscription;
    }

    @Override
    public void setCallback(DetailCallback detailCallback) {
        this.detailCallback = detailCallback;
    }

    @Override
    public void loadEvent(final long eventId) {
        if (compositeSubscription.isUnsubscribed()){
            compositeSubscription = new CompositeSubscription();
        }

        Subscription subscription = eventsRepository.getEventByEventId(eventId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Event>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("loadEvent, onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("loadEvent, onError: " + e.getMessage());
                        detailCallback.onEventError(e);
                    }

                    @Override
                    public void onNext(Event event) {
                        Timber.d("loadEvent, onNext");
                        detailCallback.onEventLoaded(event);
                    }
                });

        compositeSubscription.add(subscription);
    }

    @Override
    public void cancelRequest() {
        compositeSubscription.unsubscribe();
    }
}
