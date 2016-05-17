package fi.danielsan.donkino.ui.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fi.danielsan.donkino.data.api.models.events.GalleryImage;
import fi.danielsan.donkino.ui.base.AdapterDelegate;
import fi.danielsan.donkino.ui.base.BaseAdapter;
import fi.danielsan.donkino.misc.ItemClickListener;

public class GalleryAdapter extends BaseAdapter<GalleryImage> {

    private final AdapterDelegate<List<GalleryImage>> primaryAdapterDelegate;

    private List<GalleryImage> galleryImages = new ArrayList<>();

    public GalleryAdapter(AdapterDelegate<List<GalleryImage>> primaryAdapterDelegate) {
        this.primaryAdapterDelegate = primaryAdapterDelegate;
    }

    @Override
    public void setClickListener(@NonNull ItemClickListener itemClickListener) {
        primaryAdapterDelegate.setClickListener(itemClickListener);
    }

    @Override
    public void setItems(@NonNull List<GalleryImage> items) {
        galleryImages.clear();
        galleryImages.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return primaryAdapterDelegate.onCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        primaryAdapterDelegate.onBindViewHolder(galleryImages, position, holder);
    }

    @Override
    public int getItemCount() {
        return galleryImages.size();
    }
}
