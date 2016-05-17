package fi.danielsan.donkino.ui.main.schedule;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fi.danielsan.donkino.R;
import fi.danielsan.donkino.data.api.models.schedule.Show;
import fi.danielsan.donkino.misc.ItemClickListener;
import fi.danielsan.donkino.ui.base.SelectableAdapterDelegate;

public class SchedulePrimaryListDelegate implements SelectableAdapterDelegate<List<Show>> {

    private final int layout;
    private final DateTimeFormatter dateTimeFormatter;

    private ItemClickListener<Show, Integer> clickListener;
    private List<Integer> integers = new ArrayList<>();

    public SchedulePrimaryListDelegate(@LayoutRes int layout, DateTimeFormatter dateTimeFormatter) {
        this.layout = layout;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull List<Show> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        Show show = items.get(position);
        ScheduleViewHolder scheduleViewHolder = (ScheduleViewHolder) holder;
        scheduleViewHolder.bindDetails(show);
        scheduleViewHolder.setClickListener(show);

        if (integers.contains(show.getId())) {
            scheduleViewHolder.expand(scheduleViewHolder, show);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setClickListener(@NonNull ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public void clearSelected(){
        integers.clear();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.schedule_container)
        LinearLayout linearLayout;

        @Bind(R.id.schedule_movie_title)
        TextView movieTitle;

        @Bind(R.id.schedule_theater_name)
        TextView theater;

        @Bind(R.id.schedule_start_time)
        TextView movieStartTime;

        @Bind(R.id.schedule_end_time)
        TextView movieEndTime;

        @Bind(R.id.schedule_expandable_container)
        LinearLayout view;

        @Bind(R.id.schedule_expandable_info)
        LinearLayout infoTextView;

        @Bind(R.id.schedule_expandable_ticket)
        LinearLayout ticketTextView;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            linearLayout.setTag(this);
        }

        public void bindDetails(@NonNull Show show){
            setMovieTitle(show.getTitle());
            setMovieTheater(show.getTheatreAndAuditorium());
            setMovieStartTime(dateTimeFormatter.format(show.getShowStart()));
            setMovieEndTime(dateTimeFormatter.format(show.getShowEnd()));
        }

        private void setMovieTitle(String title) {
            movieTitle.setText(title);
        }

        private void setMovieTheater(String movieTheater) {
            theater.setText(movieTheater);
        }

        private void setMovieStartTime(String startTime) {
            movieStartTime.setText(startTime);
        }

        private void setMovieEndTime(String endTime) {
            movieEndTime.setText(endTime);
        }

        private void setClickListener(@NonNull final Show show){
            if (clickListener == null) return;

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ScheduleViewHolder holder = (ScheduleViewHolder) v.getTag();

                    if (view.getVisibility() == View.VISIBLE){
                        integers.remove(integers.indexOf(show.getId()));
                        view.setVisibility(View.GONE);
                        holder.setIsRecyclable(true);
                        infoTextView.setOnClickListener(null);
                        ticketTextView.setOnClickListener(null);
                    } else {
                        integers.add(show.getId());
                        expand(holder, show);
                    }
                }
            });
        }

        public void expand(RecyclerView.ViewHolder viewHolder, final Show show){
            infoTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(show, ScheduleConstants.INFO);
                }
            });

            ticketTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(show, ScheduleConstants.TICKET);
                }
            });

            view.setVisibility(View.VISIBLE);
            viewHolder.setIsRecyclable(false);
        }
    }
}
