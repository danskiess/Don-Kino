package fi.danielsan.donkino.ui.detail;

import android.support.annotation.NonNull;

import fi.danielsan.donkino.data.api.models.VideoType;

public interface EventDetailListener {
    void showHeaderImage(@NonNull String url);
    void showReleaseDate(@NonNull String date);
    void enableAndSetVideoUrl(@NonNull VideoType eventType, @NonNull String url);
}
