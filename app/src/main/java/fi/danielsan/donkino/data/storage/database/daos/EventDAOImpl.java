package fi.danielsan.donkino.data.storage.database.daos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import fi.danielsan.donkino.data.api.models.base.Images;
import fi.danielsan.donkino.data.api.models.events.Event;
import fi.danielsan.donkino.data.api.models.events.EventType;
import fi.danielsan.donkino.data.api.models.events.EventVideo;
import fi.danielsan.donkino.data.api.models.events.GalleryImage;
import fi.danielsan.donkino.data.api.models.events.Videos;
import fi.danielsan.donkino.data.storage.database.DatabaseContract;
import fi.danielsan.donkino.utils.DbUtils;
import timber.log.Timber;

public class EventDAOImpl implements EventDAO {

    private final SQLiteOpenHelper sqLiteOpenHelper;
    private final EventStatementFactory eventStatementFactory;

    public EventDAOImpl(SQLiteOpenHelper sqLiteOpenHelper,
                        EventStatementFactory eventStatementFactory) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
        this.eventStatementFactory = eventStatementFactory;
    }

    @Override
    public List<Event> findAll() {
        return null;
    }

    @Override
    public Event findById(final long eventId) {
        final SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseContract.Events.TABLE_NAME + " WHERE " + DatabaseContract.Events._ID + " = " + eventId, null);
        Event event = null;
        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                Images images = new Images();
                List<GalleryImage> galleryImages = new ArrayList<>();
                Videos videos = new Videos();

                event = new Event();
                event = getEventFromCursor(cursor, event);
                event.setImages(getImagesByEventId(sqLiteDatabase, images, event.getId()));
                event.setGalleryImages(getGalleryImagesByEventId(sqLiteDatabase, galleryImages, event.getId()));
                event.setVideos(getVideosByEventId(sqLiteDatabase, videos, event.getId()));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return event;
    }

    private Event getEventFromCursor(Cursor cursor, Event eventToReturn){
        eventToReturn.setId(DbUtils.getInt(cursor, DatabaseContract.Events._ID));
        eventToReturn.setTitle(DbUtils.getString(cursor, DatabaseContract.Events.TITLE));
        eventToReturn.setOriginalTitle(DbUtils.getString(cursor, DatabaseContract.Events.ORIGINAL_TITLE));
        eventToReturn.setLengthInMinutes(DbUtils.getString(cursor, DatabaseContract.Events.LENGTH_IN_MINUTES));
        eventToReturn.setGenres(DbUtils.getString(cursor, DatabaseContract.Events.GENRES));
        eventToReturn.setProductionYear(DbUtils.getString(cursor, DatabaseContract.Events.PRODUCTION_YEAR));
        eventToReturn.setLocalRelease(LocalDateTime.parse(DbUtils.getString(cursor, DatabaseContract.Events.LOCAL_RELEASE_DATE)));
        eventToReturn.setRating(DbUtils.getString(cursor, DatabaseContract.Events.RATING));
        eventToReturn.setEventType(DbUtils.getString(cursor, DatabaseContract.Events.EVENT_TYPE));
        eventToReturn.setShortSynopsis(DbUtils.getString(cursor,DatabaseContract.Events.SHORT_SYNOPSIS));
        eventToReturn.setSynopsis(DbUtils.getString(cursor, DatabaseContract.Events.SYNOPSIS));
        eventToReturn.setEventUrl(DbUtils.getString(cursor, DatabaseContract.Events.EVENT_URL));
        eventToReturn.setNowInTheaters(DbUtils.getBoolean(cursor, DatabaseContract.Events.NOW_IN_THEATERS));
        eventToReturn.setComingSoon(DbUtils.getBoolean(cursor, DatabaseContract.Events.COMING_SOON));
        return eventToReturn;
    }

    private Images getImagesByEventId(SQLiteDatabase sqLiteDatabase, Images imagesToReturn, int eventId){
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseContract.Images.TABLE_NAME + " WHERE " + DatabaseContract.Images.FK_EVENT_ID + " = " + eventId, null);
            if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                imagesToReturn.setMicroImagePortrait(DbUtils.getString(cursor, DatabaseContract.Images.MICRO_IMAGE_PORTRAIT));
                imagesToReturn.setSmallImagePortrait(DbUtils.getString(cursor, DatabaseContract.Images.SMALL_IMAGE_PORTRAIT));
                imagesToReturn.setMediumImagePortrait(DbUtils.getString(cursor, DatabaseContract.Images.MEDIUM_IMAGE_PORTRAIT));
                imagesToReturn.setLargeImagePortrait(DbUtils.getString(cursor, DatabaseContract.Images.LARGE_IMAGE_PORTRAIT));
                imagesToReturn.setSmallImageLandscape(DbUtils.getString(cursor, DatabaseContract.Images.SMALL_IMAGE_LANDSCAPE));
                imagesToReturn.setLargeImageLandscape(DbUtils.getString(cursor, DatabaseContract.Images.LARGE_IMAGE_LANDSCAPE));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return imagesToReturn;
    }

    private List<GalleryImage> getGalleryImagesByEventId(SQLiteDatabase sqLiteDatabase, List<GalleryImage> imagesToReturn, int eventId){
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseContract.GalleryImages.TABLE_NAME + " WHERE " + DatabaseContract.Images.FK_EVENT_ID + " = " + eventId, null);
            if (cursor.getCount() > 0) {
                GalleryImage galleryImage;
                while (cursor.moveToNext()) {
                    galleryImage = new GalleryImage();
                    galleryImage.setLocation(DbUtils.getString(cursor, DatabaseContract.GalleryImages.LOCATION));
                    galleryImage.setThumbnailLocation(DbUtils.getString(cursor, DatabaseContract.GalleryImages.THUMBNAIL_LOCATION));
                    imagesToReturn.add(galleryImage);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return imagesToReturn;
    }

    private Videos getVideosByEventId(SQLiteDatabase sqLiteDatabase, Videos videosToReturn, int eventId){
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseContract.VideosTable.TABLE_NAME + " WHERE " + DatabaseContract.Images.FK_EVENT_ID + " = " + eventId, null);

            if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                EventVideo eventVideo = new EventVideo();
                eventVideo.setLocation(DbUtils.getString(cursor, DatabaseContract.VideosTable.LOCATION));
                eventVideo.setMediaResourceSubType(DbUtils.getString(cursor, DatabaseContract.VideosTable.MEDIA_RESOURCE_SUB_TYPE));
                eventVideo.setMediaResourceFormat(DbUtils.getString(cursor, DatabaseContract.VideosTable.MEDIA_RESOURCE_FORMAT));
                videosToReturn.setEventVideo(eventVideo);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return videosToReturn;
    }

    @Override
    public List<Event> findAllByEventType(EventType eventTypenum) {
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query;

            if (eventTypenum == EventType.NOW_IN_THEATERS) {
                query = "SELECT * FROM "
                        + DatabaseContract.Events.TABLE_NAME
                        + " WHERE " + DatabaseContract.Events.NOW_IN_THEATERS + " = "
                        + 1
                        + " ORDER BY " + DatabaseContract.Events.TITLE + " ASC";
            } else {
                query = "SELECT * FROM "
                        + DatabaseContract.Events.TABLE_NAME
                        + " WHERE " + DatabaseContract.Events.COMING_SOON + " = "
                        + 1
                        + " ORDER BY " + DatabaseContract.Events.TITLE + " ASC";
            }

            cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                List<Event> events = new LinkedList<>();

                Event event;
                Images image;
                while (cursor.moveToNext()) {
                    event = new Event();
                    image = new Images();
                    event = getEventFromCursor(cursor, event);
                    event.setImages(getImagesByEventId(sqLiteDatabase, image, event.getId()));
                    events.add(event);
                }
                return events;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public boolean addEvent(Event event) {
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        sqLiteDatabase.beginTransaction();

        SQLiteStatement eventStatement = eventStatementFactory.getEventStatement(sqLiteDatabase);
        SQLiteStatement galleryStatement = eventStatementFactory.getGalleryStatement(sqLiteDatabase);
        SQLiteStatement imagesStatement = eventStatementFactory.getImagesStatement(sqLiteDatabase);
        SQLiteStatement videoStatement = eventStatementFactory.getVideoStatement(sqLiteDatabase);

        try {
            long eventId = bindEventToSQLiteStatement(eventStatement, event).executeInsert();

            for (GalleryImage galleryImage : event.getGalleryImages()) {
                bindEventGalleryToSQLiteStatement(galleryStatement, galleryImage, eventId).execute();
                galleryStatement.clearBindings();
            }

            bindEventImagesToSQLiteStatement(imagesStatement, event, eventId).execute();
            if (event.getVideos().getEventVideo() != null) {
                bindVideoToSQLiteStatement(videoStatement, event.getVideos(), eventId).execute();
            }

            eventStatement.clearBindings();
            imagesStatement.clearBindings();
            videoStatement.clearBindings();
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            Timber.w("addEvent", e);
            return false;
        } finally {
            sqLiteDatabase.endTransaction();
            eventStatement.close();
            galleryStatement.close();
            imagesStatement.close();
            videoStatement.close();
        }
        return true;
    }

    @Override
    public boolean addEvents(List<Event> events) {
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        sqLiteDatabase.beginTransaction();

        SQLiteStatement eventStatement = eventStatementFactory.getEventStatement(sqLiteDatabase);
        SQLiteStatement galleryStatement = eventStatementFactory.getGalleryStatement(sqLiteDatabase);
        SQLiteStatement imagesStatement = eventStatementFactory.getImagesStatement(sqLiteDatabase);
        SQLiteStatement videoStatement = eventStatementFactory.getVideoStatement(sqLiteDatabase);

        try {
            long eventId;
            for (Event event : events) {
                eventId = bindEventToSQLiteStatement(eventStatement, event).executeInsert();
                for (GalleryImage galleryImage : event.getGalleryImages()) {
                    bindEventGalleryToSQLiteStatement(galleryStatement, galleryImage, eventId).execute();
                    galleryStatement.clearBindings();
                }

                bindEventImagesToSQLiteStatement(imagesStatement, event, eventId).execute();
                if (event.getVideos().getEventVideo() != null) {
                    bindVideoToSQLiteStatement(videoStatement, event.getVideos(), eventId).execute();
                }

                eventStatement.clearBindings();
                imagesStatement.clearBindings();
                videoStatement.clearBindings();
            }
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            Timber.w("addEvents", e);
            return false;
        } finally {
            sqLiteDatabase.endTransaction();
            eventStatement.close();
            galleryStatement.close();
            imagesStatement.close();
            videoStatement.close();
        }
        return true;
    }

    private SQLiteStatement bindEventToSQLiteStatement(SQLiteStatement sqLiteStatement, Event event) {
        sqLiteStatement.bindLong(1, event.getId());
        sqLiteStatement.bindString(2, event.getTitle());
        sqLiteStatement.bindString(3, event.getOriginalTitle());
        sqLiteStatement.bindString(4, event.getLengthInMinutes());
        sqLiteStatement.bindString(5, event.getGenres());
        sqLiteStatement.bindString(6, event.getProductionYear());
        sqLiteStatement.bindString(7, event.getRating());
        sqLiteStatement.bindString(8, event.getLocalRelease().toString());
        sqLiteStatement.bindString(9, event.getEventType());
        sqLiteStatement.bindString(10, returnEmptyStringsIfNull(event.getShortSynopsis()));
        sqLiteStatement.bindString(11, returnEmptyStringsIfNull(event.getSynopsis()));
        sqLiteStatement.bindString(12, event.getEventUrl());
        sqLiteStatement.bindLong(13, event.isNowInTheaters() ? 1 : 0); //nowInTheaters
        sqLiteStatement.bindLong(14, event.isComingSoon() ? 1 : 0); //comingSoon
        return sqLiteStatement;
    }

    private SQLiteStatement bindEventImagesToSQLiteStatement(SQLiteStatement sqLiteStatement, Event event, long eventId) {
        sqLiteStatement.bindString(1, returnEmptyStringsIfNull(event.getImages().getMicroImagePortrait()));
        sqLiteStatement.bindString(2, returnEmptyStringsIfNull(event.getImages().getSmallImagePortrait()));
        sqLiteStatement.bindString(3, returnEmptyStringsIfNull(event.getImages().getMediumImagePortrait()));
        sqLiteStatement.bindString(4, returnEmptyStringsIfNull(event.getImages().getLargeImagePortrait()));
        sqLiteStatement.bindString(5, returnEmptyStringsIfNull(event.getImages().getSmallImageLandscape()));
        sqLiteStatement.bindString(6, returnEmptyStringsIfNull(event.getImages().getLargeImageLandscape()));
        sqLiteStatement.bindLong(7, eventId);
        return sqLiteStatement;
    }

    private SQLiteStatement bindEventGalleryToSQLiteStatement(SQLiteStatement sqLiteStatement, GalleryImage galleryImage, long eventId) {
        sqLiteStatement.bindString(1, returnEmptyStringsIfNull(galleryImage.getThumbnailLocation()));
        sqLiteStatement.bindString(2, returnEmptyStringsIfNull(galleryImage.getLocation()));
        sqLiteStatement.bindLong(3, eventId);
        return sqLiteStatement;
    }

    private SQLiteStatement bindVideoToSQLiteStatement(SQLiteStatement sqLiteStatement, Videos videos, long eventId) {
        sqLiteStatement.bindString(1, returnEmptyStringsIfNull(videos.getEventVideo().getLocation()));
        sqLiteStatement.bindString(2, returnEmptyStringsIfNull(videos.getEventVideo().getMediaResourceSubType()));
        sqLiteStatement.bindString(3, returnEmptyStringsIfNull(videos.getEventVideo().getMediaResourceFormat()));
        sqLiteStatement.bindLong(4, eventId);
        return sqLiteStatement;
    }

    private String returnEmptyStringsIfNull(String entry){
        if (entry == null){
            return "";
        }
        return entry;
    }

    @Override
    public boolean updateEvent(Event event) {
        return false;
    }

    @Override
    public boolean deleteEvent(Event event) {
        return false;
    }

    @Override
    public boolean deleteEventsWhere(EventType event) {
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        SQLiteStatement sqLiteStatement;
        if (event == EventType.NOW_IN_THEATERS){
            sqLiteStatement = sqLiteDatabase.compileStatement("DELETE FROM "
                    + DatabaseContract.Events.TABLE_NAME + " WHERE " + DatabaseContract.Events.NOW_IN_THEATERS + " = 1");
        } else {
            sqLiteStatement = sqLiteDatabase.compileStatement("DELETE FROM "
                    + DatabaseContract.Events.TABLE_NAME + " WHERE " + DatabaseContract.Events.COMING_SOON + " = 1");
        }

        return sqLiteStatement.executeUpdateDelete() > 0;
    }
}
