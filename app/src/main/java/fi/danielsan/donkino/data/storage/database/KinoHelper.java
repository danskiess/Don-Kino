package fi.danielsan.donkino.data.storage.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import timber.log.Timber;

public class KinoHelper extends SQLiteOpenHelper {

    public KinoHelper(@NonNull Context context){
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Timber.i("KinoHelper onCreate");
        db.execSQL(DatabaseContract.Theaters.CREATE_TABLE);
        db.execSQL(DatabaseContract.Events.CREATE_TABLE);
        db.execSQL(DatabaseContract.Images.CREATE_TABLE);
        db.execSQL(DatabaseContract.GalleryImages.CREATE_TABLE);
        db.execSQL(DatabaseContract.VideosTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Timber.i("KinoHelper onUpgrade");
        db.execSQL(DatabaseContract.Theaters.DELETE_TABLE);
        db.execSQL(DatabaseContract.Events.DELETE_TABLE);
        db.execSQL(DatabaseContract.Images.DELETE_TABLE);
        db.execSQL(DatabaseContract.GalleryImages.DELETE_TABLE);
        db.execSQL(DatabaseContract.VideosTable.DELETE_TABLE);
    }
}
