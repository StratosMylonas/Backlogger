package com.stratos.backlogger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Backlogger.db";

    public static final String TABLE_VIDEO_GAMES = "videoGames";
    public static final String TABLE_TV_SERIES = "tvSeries";
    public static final String TABLE_MOVIES = "movies";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_VIDEO_GAMES + " (id integer primary key autoincrement, name text, genre text, platform text, yearOfRelease integer, durationHours integer)");
        db.execSQL("create table " + TABLE_TV_SERIES + " (id integer primary key autoincrement, name text, genre text, yearsRunning text, amountOfEpisodes integer)");
        db.execSQL("create table " + TABLE_MOVIES + " (id integer primary key autoincrement, name text, genre text, yearOfRealease integer, durationMinutes integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_VIDEO_GAMES);
        db.execSQL("drop table if exists " + TABLE_TV_SERIES);
        db.execSQL("drop table if exists " + TABLE_MOVIES);
        onCreate(db);
    }

    public boolean insertVideoGames(VideoGame videoGame){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", videoGame.getTitle());
        contentValues.put("genre", videoGame.getGenre());
        contentValues.put("platform", videoGame.getPlatform());
        contentValues.put("yearOfRelease", videoGame.getYearOfRelease());
        contentValues.put("durationHours", videoGame.getDurationHours());

        long result = db.insert(TABLE_VIDEO_GAMES, null, contentValues);
        return result != -1;
    }

    public Cursor getAllVideoGames() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_VIDEO_GAMES, null);
    }
}
