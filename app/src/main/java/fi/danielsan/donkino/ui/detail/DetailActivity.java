package fi.danielsan.donkino.ui.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.Bind;
import butterknife.ButterKnife;
import fi.danielsan.donkino.R;
import fi.danielsan.donkino.data.api.models.VideoType;
import fi.danielsan.donkino.ui.Navigator;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity implements EventDetailListener{

    @Bind(R.id.detail_appbarlayout)
    AppBarLayout appBarLayout;

    @Bind(R.id.detail_header_imageview)
    ImageView headerImage;

    @Bind(R.id.detail_movie_release)
    TextView releaseDateTextView;

    @Bind(R.id.detail_play_icon)
    ImageView playIcon;

    private DetailEventFragment detailEventFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int alpha = ((int) (255 - Math.abs((float) 2 * verticalOffset/appBarLayout.getTotalScrollRange()) * 255));
                playIcon.getBackground().setAlpha(Math.max(0, alpha));

                int alpha2 = ((int) (255 - Math.abs((float) verticalOffset/appBarLayout.getTotalScrollRange()) * 255));
                releaseDateTextView.getBackground().setAlpha(alpha2);
                releaseDateTextView.setTextColor(Color.argb(alpha2, 255, 255, 255));
            }
        });

        Bundle bundle = getIntent().getExtras();
        long eventId = bundle.getLong(Navigator.EVENT_ID);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.detail_fragment_container) == null) {
            detailEventFragment = DetailEventFragment.getInstance(eventId);
            fragmentManager.beginTransaction()
                    .add(R.id.detail_fragment_container, detailEventFragment)
                    .commit();
        } else {
            detailEventFragment = (DetailEventFragment) fragmentManager.findFragmentById(R.id.detail_fragment_container);
        }
    }

    @Override
    public void showHeaderImage(@NonNull String url) {
        Glide.with(this)
                .load(url)
                .crossFade()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.ic_mood_bad_black_24dp)
                .into(headerImage);
    }

    @Override
    public void showReleaseDate(@NonNull String date) {
        releaseDateTextView.setVisibility(View.VISIBLE);
        releaseDateTextView.setText(date);
    }

    @Override
    public void enableAndSetVideoUrl(@NonNull final VideoType videoType, @NonNull final String url) {
        playIcon.setVisibility(View.VISIBLE);
        playIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoType == VideoType.YOUTUBE) {
                    Navigator.startYoutubeIntent(DetailActivity.this, url);
                } else {
                    Navigator.startVideoIntent(DetailActivity.this, url);
                }
            }
        });
    }
}
