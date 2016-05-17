package fi.danielsan.donkino.ui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

import fi.danielsan.donkino.R;
import fi.danielsan.donkino.ui.detail.DetailActivity;
import fi.danielsan.donkino.ui.licenses.LicenseActivity;
import timber.log.Timber;

public abstract class Navigator {

    private Navigator(){}

    private static final String EXTRA_CUSTOM_TABS_SESSION = "android.support.customtabs.extra.SESSION";
    private static final String EXTRA_CUSTOM_TABS_TOOLBAR_COLOR = "android.support.customtabs.extra.TOOLBAR_COLOR";
    private static final String EXTRA_CUSTOM_TABS_EXIT_ANIMATION_BUNDLE = "android.support.customtabs.extra.EXIT_ANIMATION_BUNDLE";

    public static final String EVENT_ID = "EventId";

    public static void navigateToDetailActivity(@NonNull Activity activity, long eventId, View view){
        Intent activityIntent = new Intent(activity, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(EVENT_ID, eventId);
        activityIntent.putExtras(bundle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && view != null) {
            activity.startActivity(activityIntent);
        } else {
            activity.startActivity(activityIntent);
        }
    }

    public static void navigateToLicenseActivity(@NonNull Activity activity){
        Intent activityIntent = new Intent(activity, LicenseActivity.class);
        activity.startActivity(activityIntent);
    }

    //TODO: Back up to browser?
    public static void startYoutubeIntent(@NonNull Activity activity, @NonNull String videoId){
        Timber.d("Youtube intent: " + videoId);
        if (isAppInstalled(activity.getPackageManager(), "com.google.android.youtube")){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
            intent.putExtra("VIDEO_ID", videoId);
            activity.startActivity(intent);
        }
    }

    //TODO: Check if browser is installed / something can handle this?
    public static void startVideoIntent(@NonNull Activity activity, @NonNull String url) {
        Timber.d("Browser intent: " + url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(url), "video/mp4");
        activity.startActivity(intent);
    }

    //TODO: Should packageManager be injected?
    private static boolean isAppInstalled(PackageManager packageManager, String packageName) {
        Intent mIntent = packageManager.getLaunchIntentForPackage(packageName);
        return mIntent != null;
    }

    public static void startBrowserIntent(@NonNull Activity activity, @NonNull String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        Bundle extras = new Bundle();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            extras.putBinder(EXTRA_CUSTOM_TABS_SESSION, null);
        }
        intent.putExtra(EXTRA_CUSTOM_TABS_TOOLBAR_COLOR, ContextCompat.getColor(activity, R.color.colorPrimary));

        Bundle exitBundle = ActivityOptions.makeCustomAnimation(activity,
                R.anim.slide_in_left, R.anim.slide_out_right)
                .toBundle();
        intent.putExtra(EXTRA_CUSTOM_TABS_EXIT_ANIMATION_BUNDLE, exitBundle);

        Bundle startBundle = ActivityOptions.makeCustomAnimation(activity,
                R.anim.slide_in_right, R.anim.slide_out_left)
                .toBundle();

        intent.putExtras(extras);
        activity.startActivity(intent, startBundle);
    }
}
