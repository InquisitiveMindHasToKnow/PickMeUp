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
    private static final int DATABASE_VERSION = 6;
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


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVORITES_TABLE =
                "CREATE TABLE " + TABLE_CREATED_QUOTES + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "created_quote TEXT NOT NULL,"
                        + "date_created TEXT NOT NULL"
                        + ")";

        db.execSQL(CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREATED_QUOTES);
            onCreate(db);
        }
    }

    public void addCreatedQuote(CreatedQuotes createdQuotes) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("created_quote", createdQuotes.getCreatedQuote());
            values.put("date_created", createdQuotes.getDateCreated());

            db.insertOrThrow(TABLE_CREATED_QUOTES, null, values);
        } catch (Exception e) {
            Log.e("Created Quote", "Error while trying to add post to database", e);
        }
    }


    public List<CreatedQuotes> getCreatedQuotes() {
        List<CreatedQuotes> createdQuotes = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CREATED_QUOTES, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String created_quote = (cursor.getString(cursor.getColumnIndex("created_quote")));
                    String date_created = cursor.getString(cursor.getColumnIndex("date_created"));

                    createdQuotes.add(CreatedQuotes.from(created_quote, date_created));
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

    public void deleteQuote(String quoteCreated) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.delete(TABLE_CREATED_QUOTES, "created_quote = ?", new String[]{String.valueOf(quoteCreated)});
        } catch (Exception e) {
            Log.e("Created Quote", "Error while trying to delete quote", e);
        }
    }

}

