package fi.danielsan.donkino.data.api;

import fi.danielsan.donkino.data.api.models.events.Events;
import fi.danielsan.donkino.data.api.models.schedule.Schedule;
import fi.danielsan.donkino.data.api.models.theatres.TheatreAreas;
import retrofit2.Response;
import retrofit2.adapter.rxjava.Result;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface KinoService {

    @GET("xml/Events/")
    Observable<Events> getEvent(@Query("eventId") long eventId,
                                @Query("includeGallery") boolean value);

    @GET("xml/Events/")
    Observable<Events> getEvents(@Query("listType") String listType,
                                 @Query("includeGallery") boolean value);

    @GET("xml/Schedule/")
    Observable<Response<Schedule>> getSchedule(@Query("area") int area,
                                               @Query("dt") String dateTime);

    @GET("xml/TheatreAreas/")
    Observable<TheatreAreas> getTheatreAreas();
}
