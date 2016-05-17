package fi.danielsan.donkino.ui.main.schedule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fi.danielsan.donkino.R;
import fi.danielsan.donkino.data.date.MovieDate;

public class SpinnerDateAdapter extends ArrayAdapter {

    private List<MovieDate> movieDates;

    public SpinnerDateAdapter(@NonNull Context context, @NonNull List<MovieDate> movieDates) {
        super(context, R.layout.spinner_two_item_layout);
        this.movieDates = movieDates;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_two_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.text1 = (TextView) convertView.findViewById(R.id.spinner_date_title);
            viewHolder.text2 = (TextView) convertView.findViewById(R.id.spinner_date_subtitle);
            convertView.setTag(R.layout.spinner_two_item_layout, viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.layout.spinner_two_item_layout);
        }

        setTextToViews(position, viewHolder.text1, viewHolder.text2);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder2 viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_two_item_dropdown_layout, null);
            viewHolder = new ViewHolder2();
            viewHolder.text1 = (TextView) convertView.findViewById(R.id.spinner_date_title);
            viewHolder.text2 = (TextView) convertView.findViewById(R.id.spinner_date_subtitle);
            convertView.setTag(R.layout.spinner_two_item_dropdown_layout, viewHolder);
        } else {
            viewHolder = (ViewHolder2) convertView.getTag(R.layout.spinner_two_item_dropdown_layout);
        }

        setTextToViews(position, viewHolder.text1, viewHolder.text2);
        return convertView;
    }

    private void setTextToViews(int position, TextView text1, TextView text2) {
        if (position == 0) {
            text1.setText(R.string.date_today);
        } else if (position == 1) {
            text1.setText(R.string.date_tomorrow);
        } else {
            text1.setText(movieDates.get(position).getDay());
        }
        text2.setText(movieDates.get(position).getFullDate());
    }

    @Override
    public int getCount() {
        return movieDates.size();
    }

    @Override
    public Object getItem(int position) {
        return movieDates.get(position);
    }

    static class ViewHolder {
        TextView text1;
        TextView text2;
    }

    static class ViewHolder2 {
        TextView text1;
        TextView text2;
    }
}