package fi.danielsan.donkino.data.storage.database.daos;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import fi.danielsan.donkino.data.storage.database.DatabaseContract;

public class EventStatementFactoryImpl implements EventStatementFactory {

    @Override
    public SQLiteStatement getEventStatement(SQLiteDatabase sqLiteDatabase) {
        return sqLiteDatabase.compileStatement("INSERT OR IGNORE INTO "
                + DatabaseContract.Events.TABLE_NAME
                + "(" + DatabaseContract.Events._ID
                + ", " + DatabaseContract.Events.TITLE
                + ", " + DatabaseContract.Events.ORIGINAL_TITLE
                + ", " + DatabaseContract.Events.LENGTH_IN_MINUTES
                + ", " + DatabaseContract.Events.GENRES
                + ", " + DatabaseContract.Events.PRODUCTION_YEAR
                + ", " + DatabaseContract.Events.RATING
                + ", " + DatabaseContract.Events.LOCAL_RELEASE_DATE
                + ", " + DatabaseContract.Events.EVENT_TYPE
                + ", " + DatabaseContract.Events.SHORT_SYNOPSIS
                + ", " + DatabaseContract.Events.SYNOPSIS
                + ", " + DatabaseContract.Events.EVENT_URL
                + ", " + DatabaseContract.Events.NOW_IN_THEATERS
                + ", " + DatabaseContract.Events.COMING_SOON
                + ") "
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    @Override
    public SQLiteStatement getImagesStatement(SQLiteDatabase sqLiteDatabase) {
        return sqLiteDatabase.compileStatement("INSERT OR IGNORE INTO "
                + DatabaseContract.Images.TABLE_NAME
                + "(" + DatabaseContract.Images.MICRO_IMAGE_PORTRAIT
                + ", " + DatabaseContract.Images.SMALL_IMAGE_PORTRAIT
                + ", " + DatabaseContract.Images.MEDIUM_IMAGE_PORTRAIT
                + ", " + DatabaseContract.Images.LARGE_IMAGE_PORTRAIT
                + ", " + DatabaseContract.Images.SMALL_IMAGE_LANDSCAPE
                + ", " + DatabaseContract.Images.LARGE_IMAGE_LANDSCAPE
                + ", " + DatabaseContract.Images.FK_EVENT_ID
                + ")"
                + " VALUES "
                + "(?, ?, ?, ?, ?, ?, ?)");
    }

    @Override
    public SQLiteStatement getGalleryStatement(SQLiteDatabase sqLiteDatabase) {
        return sqLiteDatabase.compileStatement("INSERT OR IGNORE INTO "
                + DatabaseContract.GalleryImages.TABLE_NAME
                + "(" + DatabaseContract.GalleryImages.THUMBNAIL_LOCATION
                + ", " + DatabaseContract.GalleryImages.LOCATION
                + ", " + DatabaseContract.GalleryImages.FK_EVENT_ID
                + ")"
                + " VALUES "
                + "(?, ?, ?)");
    }

    @Override
    public SQLiteStatement getVideoStatement(SQLiteDatabase sqLiteDatabase) {
        return sqLiteDatabase.compileStatement("INSERT OR IGNORE INTO "
                + DatabaseContract.VideosTable.TABLE_NAME
                + "(" + DatabaseContract.VideosTable.LOCATION
                + ", " + DatabaseContract.VideosTable.MEDIA_RESOURCE_SUB_TYPE
                + ", " + DatabaseContract.VideosTable.MEDIA_RESOURCE_FORMAT
                + ", " + DatabaseContract.GalleryImages.FK_EVENT_ID
                + ")"
                + " VALUES "
                + "(?, ?, ?, ?)");
    }
}
