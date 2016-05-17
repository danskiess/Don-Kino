package fi.danielsan.donkino.ui.detail;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fi.danielsan.donkino.R;
import fi.danielsan.donkino.data.api.models.events.GalleryImage;
import fi.danielsan.donkino.ui.base.AdapterDelegate;
import fi.danielsan.donkino.misc.ItemClickListener;

public class GalleryPrimaryAdapterDelegate implements AdapterDelegate<List<GalleryImage>> {

    private final int layout;
    private ItemClickListener<GalleryImage, View> clickListener;

    public GalleryPrimaryAdapterDelegate(@LayoutRes int layout) {
        this.layout = layout;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new GalleryImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull List<GalleryImage> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        GalleryImageViewHolder galleryImageViewHolder = (GalleryImageViewHolder) holder;
        galleryImageViewHolder.bindDetails(items.get(position));
        galleryImageViewHolder.setClickListener(items.get(position));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setClickListener(@NonNull ItemClickListener itemClickListener) {
        clickListener = itemClickListener;
    }

    public class GalleryImageViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.detail_gallery_image)
        ImageView galleryImageView;

        public GalleryImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindDetails(@NonNull GalleryImage galleryImage){
            setEventImage(galleryImage.getThumbnailLocation());
        }

        private void setEventImage(@NonNull String imageUrl) {
            Glide.with(galleryImageView.getContext())
                    .load(imageUrl)
                    .centerCrop()
                    .crossFade()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.ic_mood_bad_black_24dp)
                    .into(galleryImageView);
        }

        private void setClickListener(@NonNull final GalleryImage galleryImage){
            if (clickListener == null) return;

            galleryImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(galleryImage, galleryImageView);
                }
            });
        }
    }
}
