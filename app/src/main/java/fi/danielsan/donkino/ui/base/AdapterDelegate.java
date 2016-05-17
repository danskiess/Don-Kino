package fi.danielsan.donkino.ui.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import fi.danielsan.donkino.misc.ItemClickListener;

public interface AdapterDelegate<T> {

    @NonNull
    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);

    void onBindViewHolder(@NonNull T items, int position, @NonNull RecyclerView.ViewHolder holder);

    void setClickListener(@NonNull ItemClickListener itemClickListener);
}

