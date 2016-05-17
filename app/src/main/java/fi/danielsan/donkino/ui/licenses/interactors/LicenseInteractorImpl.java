package fi.danielsan.donkino.ui.licenses.interactors;

import java.io.IOException;

import fi.danielsan.donkino.ui.licenses.LicenseReader;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class LicenseInteractorImpl implements LicenseInteractor {

    private final LicenseReader licenseReader;

    private LicenseCallback licenseCallback;
    private Subscription subscription;

    public LicenseInteractorImpl(LicenseReader licenseReader) {
        this.licenseReader = licenseReader;
    }

    @Override
    public void cancel() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void loadLicenses() {
        subscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String licenses = licenseReader.readLicenses();
                    subscriber.onNext(licenses);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    Observable.error(e);
                }
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                licenseCallback.onLicensesLoaded(s);
            }
        });
    }

    @Override
    public void setCallback(LicenseCallback licenseCallback) {
        this.licenseCallback = licenseCallback;
    }
}
