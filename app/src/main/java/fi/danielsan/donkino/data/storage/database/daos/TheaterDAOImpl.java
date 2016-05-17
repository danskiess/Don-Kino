package fi.danielsan.donkino.data.storage.database.daos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import fi.danielsan.donkino.data.api.models.theatres.TheatreArea;
import fi.danielsan.donkino.data.storage.database.DatabaseContract;
import timber.log.Timber;

public class TheaterDAOImpl implements TheaterDAO{

    private final SQLiteOpenHelper sqLiteOpenHelper;

    public TheaterDAOImpl(@NonNull SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

    @Override
    public List<TheatreArea> findAll() {
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseContract.Theaters.TABLE_NAME, null);
            if (cursor.getCount() > 0) {
                List<TheatreArea> theatreAreas = new LinkedList<>();

                int theaterId;
                String theaterName;
                TheatreArea theatreArea;
                while (cursor.moveToNext()) {
                    theaterId = cursor.getInt(cursor.getColumnIndex(DatabaseContract.Theaters.THEATER_ID));
                    theaterName = cursor.getString(cursor.getColumnIndex(DatabaseContract.Theaters.THEATER_NAME));
                    theatreArea = new TheatreArea(theaterId, theaterName);
                    theatreAreas.add(theatreArea);
                }
                return theatreAreas;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            //sqLiteDatabase.close();
        }
        return Collections.emptyList();
    }


    @Override
    public boolean insertTheater(TheatreArea theatreArea) {
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement("INSERT OR IGNORE INTO " + DatabaseContract.Theaters.TABLE_NAME + "("
                + DatabaseContract.Theaters.THEATER_ID + "," + DatabaseContract.Theaters.THEATER_NAME + ")" + " VALUES (?, ?)");
        sqLiteStatement.bindDouble(1, theatreArea.getId());
        sqLiteStatement.bindString(2, theatreArea.getName());
        //sqLiteDatabase.close();
        return sqLiteStatement.executeInsert() > 0;
    }

    @Override
    public boolean updateTheater(TheatreArea theatreArea) {
        return false;
    }

    @Override
    public boolean deleteTheater(TheatreArea theatreArea) {
        return false;
    }
}
