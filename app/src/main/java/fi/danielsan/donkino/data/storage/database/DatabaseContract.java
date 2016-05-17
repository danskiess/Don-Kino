package fi.danielsan.donkino.data.storage.database;

import android.provider.BaseColumns;

public final class DatabaseContract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "kino.db";
    private static final String TEXT_TYPE = " TEXT";

    private DatabaseContract(){}

    public static abstract class Theaters implements BaseColumns {

        public static final String TABLE_NAME  = "TheaterTable";
        public static final String THEATER_ID  = "TheaterId";
        public static final String THEATER_NAME = "theaterName";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                THEATER_ID + " INTEGER UNIQUE, " +
                THEATER_NAME + TEXT_TYPE
                + " )";


        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Shows implements BaseColumns {

        public static final String TABLE_NAME  = "ShowsTable";

        public static final String DATE_START = "dateStart";
        public static final String DATE_END = "dateEnd";
        public static final String EVENT_ID = "eventId"; //Unique eventId (aka movie, theater performance...)
        public static final String TITLE = "title";
        public static final String ORIGINAL_TITLE = "originalTitle";
        public static final String THEATRE_ID = "theatreId";
        public static final String THEATRE = "theatre";
        public static final String THEATRE_AUDITORIUM = "theatreAuditorium";
        public static final String SHOW_URL = "showUrl";
        public static final String QUERY_ID = "queryId";
        public static final String QUERY_TIMESTAMP = "queryTimeStamp";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                EVENT_ID + " INTEGER, " +
                TITLE + " TEXT, " +
                ORIGINAL_TITLE + " TEXT, " +
                DATE_START + " DATETIME ," +
                DATE_END + " DATETIME ," +
                THEATRE_ID + " TEXT, " +
                THEATRE + " TEXT, " +
                THEATRE_AUDITORIUM + " TEXT, " +
                SHOW_URL + " TEXT, " +
                QUERY_ID + " INTEGER, " +
                QUERY_TIMESTAMP + " TEXT " +
                ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Events implements BaseColumns {

        public static final String TABLE_NAME  = "EventsTable";

        public static final String TITLE = "title";
        public static final String ORIGINAL_TITLE = "originalTitle";
        public static final String LENGTH_IN_MINUTES = "lengthInMinutes";
        public static final String GENRES = "genres";
        public static final String PRODUCTION_YEAR = "productionYear";
        public static final String LOCAL_RELEASE_DATE = "localRelease";
        public static final String RATING = "rating";
        public static final String EVENT_TYPE = "eventType";
        public static final String SHORT_SYNOPSIS = "shortSynopsis";
        public static final String SYNOPSIS = "synopsis";
        public static final String EVENT_URL = "eventUrl";
        public static final String NOW_IN_THEATERS = "nowInTheaters";
        public static final String COMING_SOON = "comingSoon";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                TITLE + " TEXT, " +
                ORIGINAL_TITLE + " TEXT, " +
                LENGTH_IN_MINUTES + " TEXT, " +
                GENRES + " TEXT, " +
                PRODUCTION_YEAR + " TEXT, " +
                RATING + " TEXT, " +
                LOCAL_RELEASE_DATE + " DATETIME, " +
                EVENT_TYPE + " TEXT, " +
                SHORT_SYNOPSIS + " TEXT, " +
                SYNOPSIS + " TEXT, " +
                EVENT_URL + " TEXT, " +
                NOW_IN_THEATERS + " INTEGER, " +
                COMING_SOON + " INTEGER " +
                ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Images implements BaseColumns {

        public static final String TABLE_NAME  = "ImagesTable";

        public static final String MICRO_IMAGE_PORTRAIT = "microImagePortrait";
        public static final String SMALL_IMAGE_PORTRAIT = "smallImagePortrait";
        public static final String MEDIUM_IMAGE_PORTRAIT = "mediumImagePortrait";
        public static final String LARGE_IMAGE_PORTRAIT = "largeImagePortrait";
        public static final String SMALL_IMAGE_LANDSCAPE = "smallImageLandscape";
        public static final String LARGE_IMAGE_LANDSCAPE = "largeImageLandscape";
        public static final String FK_EVENT_ID = "fkEventId";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                MICRO_IMAGE_PORTRAIT + " TEXT ," +
                SMALL_IMAGE_PORTRAIT + " TEXT, " +
                MEDIUM_IMAGE_PORTRAIT + " TEXT, " +
                LARGE_IMAGE_PORTRAIT + " TEXT, " +
                SMALL_IMAGE_LANDSCAPE + " TEXT, " +
                LARGE_IMAGE_LANDSCAPE + " TEXT, " +
                FK_EVENT_ID + " INTEGER, " +
                " FOREIGN KEY (" + FK_EVENT_ID + ") REFERENCES " + Events.TABLE_NAME + "("+ Events._ID + ") ON DELETE CASCADE" +
                ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class GalleryImages implements BaseColumns {

        public static final String TABLE_NAME  = "GalleryImagesTable";

        public static final String LOCATION = "location";
        public static final String THUMBNAIL_LOCATION = "thumbnailLocation";
        public static final String FK_EVENT_ID = "fkEventId";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                LOCATION + " TEXT, " +
                THUMBNAIL_LOCATION + " TEXT, " +
                FK_EVENT_ID + " INTEGER, " +
                " FOREIGN KEY (" + FK_EVENT_ID + ") REFERENCES " + Events.TABLE_NAME + "("+ Events._ID + ") ON DELETE CASCADE" +
                ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class VideosTable implements BaseColumns {

        public static final String TABLE_NAME  = "VideosTable";

        public static final String LOCATION = "location";
        public static final String MEDIA_RESOURCE_SUB_TYPE = "mediaResourceSubType";
        public static final String MEDIA_RESOURCE_FORMAT = "mediaResourceFormat";
        public static final String FK_EVENT_ID = "fkEventId";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                LOCATION + " TEXT, " +
                MEDIA_RESOURCE_SUB_TYPE + " TEXT, " +
                MEDIA_RESOURCE_FORMAT + " TEXT, " +
                FK_EVENT_ID + " INTEGER, " +
                " FOREIGN KEY (" + FK_EVENT_ID + ") REFERENCES " + Events.TABLE_NAME + "("+ Events._ID + ") ON DELETE CASCADE" +
                ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
