package fi.danielsan.kinodata.api;

import fi.danielsan.kinodata.api.models.events.Events;
import fi.danielsan.kinodata.api.models.schedule.Schedule;
import fi.danielsan.kinodata.api.models.theatres.TheatreAreas;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface KinoService {

    @GET("/Events/")
    Observable<Events> getEvents(@Query("listType") String listType,
                                 @Query("eventId") String eventId,
                                 @Query("includeGallery") String value);

    @GET("/Schedule/")
    Observable<Schedule> getSchedule(@Query("area") String area,
                                     @Query("dt") String dateTime);

    @GET("/Schedule/")
    Observable<Schedule> getScheduleForSingleEvent(@Query("area") String area,
                                                   @Query("dt") String dt,
                                                   @Query("eventId") String eventId);

    @GET("/TheatreAreas/")
    Observable<TheatreAreas> getTheatreAreas();
}
