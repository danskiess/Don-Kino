package fi.danielsan.donkino.misc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import fi.danielsan.donkino.utils.Utils;

public class PaddingDecorator extends RecyclerView.ItemDecoration {

    private final int extraPadding;

    public PaddingDecorator(Context context) {
        super();
        extraPadding = (int) Utils.convertDpToPixel(10, context);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        final int itemPosition = parent.getChildAdapterPosition(view);
        if (itemPosition == RecyclerView.NO_POSITION) {
            return;
        }

        if(itemPosition == state.getItemCount()-1) {
            outRect.bottom = extraPadding;
        }

    }
}
