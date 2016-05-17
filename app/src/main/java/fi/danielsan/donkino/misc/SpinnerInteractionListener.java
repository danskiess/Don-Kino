package fi.danielsan.donkino.misc;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

//http://stackoverflow.com/questions/27745948/android-spinner-onitemselected-called-multiple-times-after-screen-rotation
public class SpinnerInteractionListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

    private final OnSpinnerViewListener onSpinnerViewListener;
    boolean userSelect = false;

    public SpinnerInteractionListener(OnSpinnerViewListener onSpinnerViewListener) {
        this.onSpinnerViewListener = onSpinnerViewListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        userSelect = true;
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (userSelect) {
            onSpinnerViewListener.onSpinnerItemClick(view, pos);
            userSelect = false;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}