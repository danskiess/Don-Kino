package fi.danielsan.donkino.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import fi.danielsan.donkino.R;

public abstract class AnimUtils {

    public static void animateDown(@NonNull final Context context, @NonNull final View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_and_slide_down);
        animation.setAnimationListener(new SlideDownAnimationListener(view));
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        view.setVisibility(View.INVISIBLE);
        view.invalidate();
        view.startAnimation(animation);
    }

    private static class SlideDownAnimationListener implements Animation.AnimationListener {

        private final View view;

        public SlideDownAnimationListener(View view) {
            this.view = view;
        }

        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation) {
            view.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    }

    public static void animateUp(@NonNull final Context context, @NonNull final View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_out_and_slide_up);
        SlideUpAnimationListener slideUpAnimationListener = new SlideUpAnimationListener(view);
        animation.setAnimationListener(slideUpAnimationListener);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        view.startAnimation(animation);
    }

    private static class SlideUpAnimationListener implements Animation.AnimationListener {

        private final View view;

        public SlideUpAnimationListener(View view) {
            this.view = view;
        }

        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation) {
            view.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    }
}
