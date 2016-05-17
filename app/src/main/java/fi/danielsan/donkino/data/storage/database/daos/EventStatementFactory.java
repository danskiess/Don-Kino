package fi.danielsan.donkino.data.storage.database.daos;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public interface EventStatementFactory {
    SQLiteStatement getEventStatement(SQLiteDatabase sqLiteDatabase);
    SQLiteStatement getImagesStatement(SQLiteDatabase sqLiteDatabase);
    SQLiteStatement getGalleryStatement(SQLiteDatabase sqLiteDatabase);
    SQLiteStatement getVideoStatement(SQLiteDatabase sqLiteDatabase);
}
