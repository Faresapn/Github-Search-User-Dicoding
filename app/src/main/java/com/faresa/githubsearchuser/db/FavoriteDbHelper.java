package com.faresa.githubsearchuser.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.faresa.githubsearchuser.db.DbContract.FavoriteColoumn.TABLE_FAVORITE_NAME;


public class FavoriteDbHelper extends SQLiteOpenHelper {
    private static final String FAVORITE_DB_NAME = "favoritedb";
    private static final int FAVORITE_DB_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVORITE = String.format(
            "CREATE TABLE %s" +
                    " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_FAVORITE_NAME,
            DbContract.FavoriteColoumn.ID,
            DbContract.FavoriteColoumn.TITLE,
            DbContract.FavoriteColoumn.IMAGE
    );

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_FAVORITE_NAME);
        onCreate(db);
    }
    public FavoriteDbHelper(Context context){
        super(context, FAVORITE_DB_NAME,null,FAVORITE_DB_VERSION);
    }
}
