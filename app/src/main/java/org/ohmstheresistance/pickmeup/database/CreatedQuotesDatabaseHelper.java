package org.ohmstheresistance.pickmeup.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.ohmstheresistance.pickmeup.model.CreatedQuotes;

import java.util.ArrayList;
import java.util.List;

public class CreatedQuotesDatabaseHelper extends SQLiteOpenHelper {


        private static final String DATABASE_NAME = "createdQuotesDatabase";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_CREATED_QUOTES = "createdQuotes";
        private Context context;

        private static CreatedQuotesDatabaseHelper quoteDatabaseInstance;

        public static synchronized CreatedQuotesDatabaseHelper getInstance(Context context) {
            if (quoteDatabaseInstance == null) {
                quoteDatabaseInstance = new CreatedQuotesDatabaseHelper(context.getApplicationContext());
            }
            return quoteDatabaseInstance;
        }

        public CreatedQuotesDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }


        @Override public void onCreate(SQLiteDatabase db) {
            String CREATE_FAVORITES_TABLE =
                    "CREATE TABLE " + TABLE_CREATED_QUOTES + "("
                            + "created_quote TEXT PRIMARY KEY,"
                            + "date_created TEXT NOT NULL"
                            + ")";

            db.execSQL(CREATE_FAVORITES_TABLE);
        }

        @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion != newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREATED_QUOTES);
                onCreate(db);
            }
        }

        public void addCreatedQuote(CreatedQuotes createdQuotes) {
            SQLiteDatabase db = getWritableDatabase();

            try {
                ContentValues values = new ContentValues();
                values.put("createdQuote", createdQuotes.getCreatedQuote());
                values.put("dateCreated", createdQuotes.getDateCreated());

                db.insertOrThrow(TABLE_CREATED_QUOTES, null, values);
            } catch (Exception e) {
                Log.e("Created Quote", "Error while trying to add post to database", e);
            }
        }

        public boolean isFavorite(String createdQuote) {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor =
                    db.rawQuery("SELECT COUNT(1) as count FROM " + TABLE_CREATED_QUOTES + " WHERE created_quote = ?",
                            new String[] { String.valueOf(createdQuote) });

            int count = 0;
            try {
                if (cursor.moveToFirst()) {
                    do {
                        count = cursor.getInt(cursor.getColumnIndex("count"));
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                Log.e("Created Quote", "Error while trying to get posts from database", e);
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }

            return count != 0;
        }

        public List<CreatedQuotes> getCreatedQuotes() {
            List<CreatedQuotes> createdQuotes = new ArrayList<>();

            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CREATED_QUOTES, null);
            try {
                if (cursor.moveToFirst()) {
                    do {
                        String quote = (cursor.getString(cursor.getColumnIndex("created_quote")));
                        String date = cursor.getString(cursor.getColumnIndex("date_created"));

                        createdQuotes.add(CreatedQuotes.from(quote, date));
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                Log.e("Created Quote", "Error while trying to get created quotes from database", e);
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
            return createdQuotes;
        }

    public void deleteFavorite(String createdQuote) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.delete(TABLE_CREATED_QUOTES, "createdQuote = ?", new String[] { String.valueOf(createdQuote) });
        } catch (Exception e) {
            Log.e("Created Quote", "Error while trying to delete quote", e);
        }
    }

    }

