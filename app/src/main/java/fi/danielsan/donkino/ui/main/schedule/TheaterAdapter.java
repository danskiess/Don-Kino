package fi.danielsan.donkino.ui.main.schedule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fi.danielsan.donkino.R;
import fi.danielsan.donkino.data.api.models.theatres.TheatreArea;

public class TheaterAdapter extends ArrayAdapter {

    private List<TheatreArea> theatreAreas = new ArrayList<>();

    public TheaterAdapter(@NonNull Context context) {
        super(context, R.layout.spinner_one_item_layout);
    }

    public void setTheaters(@NonNull List<TheatreArea> theaters){
        theatreAreas.clear();
        theatreAreas.addAll(theaters);
        notifyDataSetChanged();
    }

    public int getTheaterPositionBy(int id){
        if (theatreAreas.isEmpty()) return 0;
        for (TheatreArea theatreArea : theatreAreas) {
            if (id == theatreArea.getId()) {
                return getPosition(theatreArea);
            }
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_one_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.text1 = (TextView) convertView.findViewById(R.id.spinner_title);
            convertView.setTag(R.layout.spinner_one_item_layout, viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.layout.spinner_one_item_layout);
        }

        setItemTitle(position, viewHolder.text1);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder2 viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_one_item_dropdown_layout, null);
            viewHolder = new ViewHolder2();
            viewHolder.text1 = (TextView) convertView.findViewById(R.id.spinner_title);
            convertView.setTag(R.layout.spinner_one_item_dropdown_layout, viewHolder);
        } else {
            viewHolder = (ViewHolder2) convertView.getTag(R.layout.spinner_one_item_dropdown_layout);
        }

        setItemTitle(position, viewHolder.text1);
        return convertView;
    }

    private void setItemTitle(int position, @NonNull TextView textView) {
        TheatreArea theatreArea = theatreAreas.get(position);
        textView.setText(theatreArea.getTheaterName());
    }

    @Override
    public int getCount() {
        return theatreAreas.size();
    }

    @Override
    public Object getItem(int position) {
        return theatreAreas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return theatreAreas.get(position).getId();
    }

    @Override
    public int getPosition(Object item) {
        return theatreAreas.indexOf(item);
    }

    static class ViewHolder {
        TextView text1;
    }

    static class ViewHolder2 {
        TextView text1;
    }
}