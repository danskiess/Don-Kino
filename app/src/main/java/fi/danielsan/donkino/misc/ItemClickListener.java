package fi.danielsan.donkino.misc;

public interface ItemClickListener<M, V> {
    void onItemClicked(M item, V view);
}