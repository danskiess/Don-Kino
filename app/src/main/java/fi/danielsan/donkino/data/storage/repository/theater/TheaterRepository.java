package fi.danielsan.donkino.data.storage.repository.theater;

import fi.danielsan.donkino.data.api.models.theatres.TheatreArea;
import fi.danielsan.donkino.data.storage.repository.Repository;
import rx.Observable;

public interface TheaterRepository extends Repository<TheatreArea, Integer> {
    Observable<Integer> getPreferredTheaterId();
    void savePreferredTheater(int preferredTheater);
}
