package fi.danielsan.donkino.ui.main.schedule;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import fi.danielsan.donkino.R;
import fi.danielsan.donkino.ui.base.AdapterDelegate;
import fi.danielsan.donkino.misc.ItemClickListener;

public class ScheduleSectionDelegate implements AdapterDelegate<Map<Integer, String>> {

    private int layout;

    public ScheduleSectionDelegate(@LayoutRes int layout) {
        this.layout = layout;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Map<Integer, String> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        SectionViewHolder scheduleViewHolder = (SectionViewHolder) holder;
        scheduleViewHolder.setSectionTitle(items.get(position));
    }

    @Override
    public void setClickListener(@NonNull ItemClickListener itemClickListener) {
        //Not used
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.schedule_section_title)
        TextView sectionTitle;

        public SectionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setSectionTitle(@NonNull String title){
            sectionTitle.setText(title + ":00");
        }

    }
}
