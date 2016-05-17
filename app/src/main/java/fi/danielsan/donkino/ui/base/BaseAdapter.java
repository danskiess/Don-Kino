package fi.danielsan.donkino.ui.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import fi.danielsan.donkino.misc.ItemClickListener;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    abstract public void setClickListener(@NonNull ItemClickListener itemClickListener);
    abstract public void setItems(@NonNull List<T> items);
}
