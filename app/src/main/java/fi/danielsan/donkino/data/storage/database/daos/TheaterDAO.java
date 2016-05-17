package fi.danielsan.donkino.data.storage.database.daos;

import java.util.List;

import fi.danielsan.donkino.data.api.models.theatres.TheatreArea;

public interface TheaterDAO {
    List<TheatreArea> findAll();
    boolean insertTheater(TheatreArea theatreArea);
    boolean updateTheater(TheatreArea theatreArea);
    boolean deleteTheater(TheatreArea theatreArea);
}
