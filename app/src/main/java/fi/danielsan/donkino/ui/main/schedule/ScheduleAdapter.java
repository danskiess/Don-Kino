package fi.danielsan.donkino.ui.main.schedule;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import fi.danielsan.donkino.data.api.models.schedule.Show;
import fi.danielsan.donkino.ui.base.AdapterDelegate;
import fi.danielsan.donkino.misc.ItemClickListener;
import fi.danielsan.donkino.ui.base.SelectableAdapterDelegate;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int SECTION_CELL = 0;
    private final int PRIMARY_CELL = 1;

    private SelectableAdapterDelegate<List<Show>> primaryAdapterDelegate;
    private AdapterDelegate<Map<Integer, String>> scheduleSectionDelegate;

    private List<Show> shows = new ArrayList<>();
    private Map<Integer, String> sectionMap = new HashMap<>();

    public ScheduleAdapter(SelectableAdapterDelegate schedulePrimaryListDelegate, AdapterDelegate scheduleSectionDelegate) {
        this.primaryAdapterDelegate = schedulePrimaryListDelegate;
        this.scheduleSectionDelegate = scheduleSectionDelegate;
    }

    public void setClickListener(@NonNull ItemClickListener itemClickListener) {
        primaryAdapterDelegate.setClickListener(itemClickListener);
    }

    //Generify?
    public void setItems(@NonNull List<Show> shows){
        removeAll();
        this.shows.addAll(shows);
        ShowsToSectionsConverter showsToSectionsConverter = new ShowsToSectionsConverter();
        sectionMap = showsToSectionsConverter.getSections(shows);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SECTION_CELL){
            return scheduleSectionDelegate.onCreateViewHolder(parent);
        } else if (viewType == PRIMARY_CELL) {
            return primaryAdapterDelegate.onCreateViewHolder(parent);
        }
        throw new IllegalStateException("ads");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (sectionMap.containsKey(position)) {
            scheduleSectionDelegate.onBindViewHolder(sectionMap, position, holder);
        } else {
            position = subtractFromPosition(position);
            primaryAdapterDelegate.onBindViewHolder(shows, position, holder);
        }
    }

    private int subtractFromPosition(int position){
        int newPosition = position;
        for (Map.Entry<Integer, String> entry : sectionMap.entrySet()) {
            if (position == 0) {
            } else if (position >= entry.getKey()) {
                newPosition--;
            } else {
                break;
            }
        }
        return newPosition;
    }

    @Override
    public int getItemCount() {
        return shows.size() + (sectionMap == null ? 0 : sectionMap.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (sectionMap.containsKey(position)) {
            return SECTION_CELL;
        } else {
            return PRIMARY_CELL;
        }
    }

    public void removeAll(){
        shows.clear();
        sectionMap.clear();
        primaryAdapterDelegate.clearSelected();
        notifyDataSetChanged();
    }

    private class ShowsToSectionsConverter{

        public Map<Integer, String> getSections(List<Show> shows){

            Map<Integer, Integer> map = new TreeMap<>();
            for (Show show : shows){
                addKey(map, show.getShowStart().getHour());
            }

            Map<Integer, String> secondMap = new TreeMap<>(); //SparseArray
            int lastValue = 0;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (lastValue == 0) {
                    secondMap.put(lastValue, String.valueOf(entry.getKey()));
                    lastValue = entry.getValue();
                } else {
                    secondMap.put(lastValue + 1, String.valueOf(entry.getKey()));
                    lastValue = entry.getValue() + lastValue + 1;
                }
            }
            map.clear();

            return secondMap;
        }

        private void addKey(Map<Integer, Integer> map, int key){
            if (map.containsKey(key)){
                map.put(key, map.get(key) + 1);
            } else {
                map.put(key, 1);
            }
        }
    }
}
