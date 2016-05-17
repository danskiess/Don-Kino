package fi.danielsan.donkino.ui.main.events;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fi.danielsan.donkino.data.api.models.events.Event;
import fi.danielsan.donkino.ui.base.AdapterDelegate;
import fi.danielsan.donkino.ui.base.BaseAdapter;
import fi.danielsan.donkino.misc.ItemClickListener;

public class EventsAdapter extends BaseAdapter<Event> {

    private AdapterDelegate<List<Event>> primaryAdapterDelegate;
    private List<Event> events = new ArrayList<>();

    public EventsAdapter(AdapterDelegate primaryAdapterDelegate) {
        this.primaryAdapterDelegate = primaryAdapterDelegate;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return primaryAdapterDelegate.onCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        primaryAdapterDelegate.onBindViewHolder(events, position, holder);
    }

    @Override
    public long getItemId(int position) {
        return events.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    @Override
    public void setClickListener(@NonNull ItemClickListener itemClickListener) {
        primaryAdapterDelegate.setClickListener(itemClickListener);
    }

    @Override
    public void setItems(@NonNull List<Event> items) {
        clear();
        events.addAll(items);
        int max = events.size();
        notifyItemRangeInserted(0, max);
    }

    public void clear(){
        int max = events.size();
        events.clear();
        notifyItemRangeRemoved(0, max);
    }
}
