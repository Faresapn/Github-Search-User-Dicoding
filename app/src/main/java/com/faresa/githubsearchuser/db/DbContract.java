package com.faresa.githubsearchuser.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {
    public static final String AUTH="com.faresa.githubsearchuser";
    public static final String SCHEME="content";
    public static final class FavoriteColoumn implements BaseColumns {
        public static final String TABLE_FAVORITE_NAME = "favorite";

        public static final String ID = "id";
        public static final String TITLE = "login";
        public static final String IMAGE = "avatar_url";
        public static final Uri FAVORITE_URI= new Uri.Builder().scheme(SCHEME).authority(AUTH).appendPath(TABLE_FAVORITE_NAME).build();

    }
    public static String getFavorite(Cursor cursor, String column){
        return cursor.getString(cursor.getColumnIndex(column));
    }
}
