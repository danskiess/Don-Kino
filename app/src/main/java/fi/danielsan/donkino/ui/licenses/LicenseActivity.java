package fi.danielsan.donkino.ui.licenses;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import fi.danielsan.donkino.KinoApplication;
import fi.danielsan.donkino.R;
import fi.danielsan.donkino.ui.licenses.presenter.LicensePresenter;

public class LicenseActivity extends AppCompatActivity implements LicensePresenter.LicenseView {

    @Bind(R.id.license_container)
    LinearLayout licenseContainer;

    @Inject
    LicensePresenter licensePrenteter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        ButterKnife.bind(this);
        ((KinoApplication) getApplication()).getLicenseComponent().inject(this);
        licensePrenteter.setView(this);
        licensePrenteter.loadLicenses();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void showLicenses(String license) {
        addContent(license);
    }

    public void addContent(@NonNull String licenseContent){
        TextView textView = new TextView(this);
        textView.setText(licenseContent);
        licenseContainer.addView(textView);
    }
}
