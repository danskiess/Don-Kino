package fi.danielsan.donkino.ui.main.events;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fi.danielsan.donkino.R;
import fi.danielsan.donkino.data.api.models.events.Event;
import fi.danielsan.donkino.ui.base.AdapterDelegate;
import fi.danielsan.donkino.misc.ItemClickListener;

public class EventsPrimaryListDelegate implements AdapterDelegate<List<Event>> {

    private final int layout;
    private ItemClickListener<Event, View> clickListener;

    public EventsPrimaryListDelegate(@LayoutRes int layout) {
        this.layout = layout;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull List<Event> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        EventsViewHolder eventsViewHolder = (EventsViewHolder) holder;
        eventsViewHolder.bindDetails(items.get(position));
        eventsViewHolder.setClickListener(items.get(position));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setClickListener(@NonNull ItemClickListener itemClickListener) {
        clickListener = itemClickListener;
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.event_container)
        RelativeLayout eventContainer;

        @Bind(R.id.event_title)
        TextView eventTitle;

        @Bind(R.id.event_image)
        ImageView eventImage;

        public EventsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindDetails(@NonNull Event event){
            setEventTitle(event.getTitle());
            setEventImage(event.getImages().getLargeImageLandscape());
        }

        private void setEventTitle(@NonNull String title) {
            eventTitle.setText(title);
        }

        private void setEventImage(@NonNull  String imageUrl) {
            Glide.with(eventImage.getContext())
                    .load(imageUrl)
                    .centerCrop()
                    .crossFade()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.ic_mood_bad_black_24dp)
                    .into(eventImage);
        }

        private void setClickListener(@NonNull final Event event){
            if (clickListener == null) return;

            eventContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(event, eventContainer);
                }
            });
        }
    }
}
