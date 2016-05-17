package fi.danielsan.donkino.ui.main.schedule.interactors;

import java.util.List;

import fi.danielsan.donkino.data.api.models.theatres.TheatreArea;

public interface TheaterInteractor {

    interface TheaterCallback{
        void onTheatreAreasLoaded(List<TheatreArea> theatreAreas);
        void onPreferredTheaterLoaded(int theaterId);
        void onTheaterError(Throwable throwable);
    }

    void setCallback(TheaterInteractor.TheaterCallback theaterCallback);
    void loadTheaters();
    void savePreferredTheater(int theaterId);
    void cancelRequest();
}

