package fi.danielsan.donkino.ui.base;

public interface Presenter<T> {
    void setView(T view);
    void onResume();
    void onPause();
}
