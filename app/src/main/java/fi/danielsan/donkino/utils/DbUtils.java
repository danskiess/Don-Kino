package fi.danielsan.donkino.utils;

import android.database.Cursor;

//https://github.com/bdonalds/LoginViewsMVP/blob/master/app/src/main/java/com/uele/examples/loginviewsmvp/util/DbUtils.java
public class DbUtils {

    public static final int BOOLEAN_FALSE = 0;
    public static final int BOOLEAN_TRUE = 1;

    public static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    public static boolean getBoolean(Cursor cursor, String columnName) {
        return getInt(cursor, columnName) == BOOLEAN_TRUE;
    }

    public static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }

    public static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

    private DbUtils() {
        throw new AssertionError("No instances.");
    }
}