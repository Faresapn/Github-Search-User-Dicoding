package com.faresa.githubsearchuser.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.faresa.githubsearchuser.db.FavoriteHelper;

import java.util.Objects;

import static com.faresa.githubsearchuser.db.DbContract.AUTH;
import static com.faresa.githubsearchuser.db.DbContract.FavoriteColoumn.FAVORITE_URI;
import static com.faresa.githubsearchuser.db.DbContract.FavoriteColoumn.TABLE_FAVORITE_NAME;

public class MyProvider extends ContentProvider {
    FavoriteHelper favoriteHelper;

    static final int FAVORITE=10;
    static final int FAVORITE_ID=11;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTH,TABLE_FAVORITE_NAME,FAVORITE);
        URI_MATCHER.addURI(AUTH,TABLE_FAVORITE_NAME + "/#",FAVORITE_ID);


    }

    @Override
    public boolean onCreate() {
        favoriteHelper = new FavoriteHelper(getContext());
        favoriteHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (URI_MATCHER.match(uri)){
            case FAVORITE:
                cursor = favoriteHelper.cursorFavoriteGet();
                break;
            case FAVORITE_ID:
                cursor = favoriteHelper.cursorFavoriteGetId(uri.getLastPathSegment());
                break;
            default:
                cursor=null;
                break;
        }
        if (cursor != null){
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;
        Uri contentUri = null;

        switch (URI_MATCHER.match(uri)) {
            case FAVORITE:
                added = favoriteHelper.favoriteInsertProvider(values);
                if (added > 0) {
                    contentUri = ContentUris.withAppendedId(FAVORITE_URI, added);
                }
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return contentUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (URI_MATCHER.match(uri)){
            case FAVORITE_ID:
                deleted = favoriteHelper.favoriteDeleteProvider(uri.getLastPathSegment());
                break;
                default:
                    deleted = 0;
                    break;

        }
        if (deleted > 0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);

        }
        return deleted;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        int updated;
        switch (URI_MATCHER.match(uri)) {
            case FAVORITE_ID:
                updated = favoriteHelper.favoriteUpdateProvider(uri.getLastPathSegment(), values);
                break;

            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
