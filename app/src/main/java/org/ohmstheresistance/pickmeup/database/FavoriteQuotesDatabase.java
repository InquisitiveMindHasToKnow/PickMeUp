package org.ohmstheresistance.pickmeup.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.ohmstheresistance.pickmeup.model.Quotes;

import java.util.ArrayList;
import java.util.List;

public class FavoriteQuotesDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favoriteQuotesDatabase";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_FAVORITES = "favorites";
    private Context context;

    private static FavoriteQuotesDatabase favoriteQuotesDatabase;

    public static synchronized FavoriteQuotesDatabase getInstance(Context context) {
        if (favoriteQuotesDatabase == null) {
            favoriteQuotesDatabase = new FavoriteQuotesDatabase(context.getApplicationContext());
        }
        return favoriteQuotesDatabase;
    }

    public FavoriteQuotesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVORITES_TABLE =
                "CREATE TABLE " + TABLE_FAVORITES + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "favorite_quote TEXT NOT NULL,"
                        + "favorite_quote_said_by TEXT NOT NULL"
                        + ")";

        db.execSQL(CREATE_FAVORITES_TABLE);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
            onCreate(db);
        }
    }

    public void addFavorite(Quotes favorite) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("favorite_quote", favorite.getQuote());
            values.put("favorite_quote_said_by", favorite.getSaidby());
            db.insertOrThrow(TABLE_FAVORITES, null, values);
        } catch (Exception e) {
            Log.e("Favorites", "Error while trying to add post to database", e);
        }
    }

    public boolean isFavorite(String favoriteQuote) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =
                db.rawQuery("SELECT COUNT(1) as count FROM " + TABLE_FAVORITES + " WHERE favorite_quote = ?",
                        new String[] { String.valueOf(favoriteQuote) });

        int count = 0;
        try {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getInt(cursor.getColumnIndex("count"));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Favories", "Error while trying to get posts from database", e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return count != 0;
    }

    public List<Quotes> getFavorites() {
        List<Quotes> favorites = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FAVORITES, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String favoriteQuote = (cursor.getString(cursor.getColumnIndex("favorite_quote")));
                    String favoriteQuoteSaidBy = cursor.getString(cursor.getColumnIndex("favorite_quote_said_by"));
                    favorites.add(Quotes.from(favoriteQuote, favoriteQuoteSaidBy));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Favorites", "Error while trying to get favorites from database", e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return favorites;
    }


    public void deleteFavorite(String favoriteQuoteToDelete) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.delete(TABLE_FAVORITES, "favorite_quote = ?", new String[] { String.valueOf(favoriteQuoteToDelete) });
        } catch (Exception e) {
            Log.e("Favorites", "Error while trying to delete all posts and users", e);
        }
    }

    public void clearFavoriteCountryDatabase() {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_FAVORITES);
        db.close();

    }

}
