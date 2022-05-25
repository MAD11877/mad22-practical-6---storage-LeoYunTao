package com.example.madpractical;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

public class UserDBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "MADPractical.db";

    public static final String TABLE_USER = "User";

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_DESCRIPTION = "Description";
    public static final String COLUMN_FOLLOWED = "Followed";

    public UserDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT NOT NULL," +
                COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                COLUMN_FOLLOWED + " INTEGER NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(sqLiteDatabase);
    }

    public void addUser(User user) {
        ContentValues contentValues = userToContentValues(user);

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_USER, null, contentValues);
    }

    @NonNull
    private ContentValues userToContentValues(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, user.name);
        contentValues.put(COLUMN_DESCRIPTION, user.description);
        contentValues.put(COLUMN_FOLLOWED, user.followed);
        return contentValues;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            User user = new User();
            user.id = cursor.getInt((int) cursor.getColumnIndex(COLUMN_ID));
            user.name = cursor.getString((int) cursor.getColumnIndex(COLUMN_NAME));
            user.description = cursor.getString((int) cursor.getColumnIndex(COLUMN_DESCRIPTION));
            user.followed =  cursor.getInt((int) cursor.getColumnIndex(COLUMN_FOLLOWED)) != 0;
            users.add(user);
        }

        return users;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USER, userToContentValues(user), COLUMN_ID+"=?", new String[]{Integer.toString(user.id)});
    }

}
