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

public class DisplayQuotesDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "displayQuotesDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_DISPLAY_QUOTES = "displayQuotes";
    private Context context;

    private static DisplayQuotesDatabase displayQuotesDatabaseInstance;


    public static synchronized DisplayQuotesDatabase getInstance(Context context) {
        if (displayQuotesDatabaseInstance == null) {
            displayQuotesDatabaseInstance = new DisplayQuotesDatabase(context.getApplicationContext());
        }
        return displayQuotesDatabaseInstance;
    }


    public DisplayQuotesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String DISPLAY_QUOTES_TABLE =
                "CREATE TABLE " + TABLE_DISPLAY_QUOTES + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "display_quote TEXT NOT NULL,"
                        + "display_quote_said_by TEXT NOT NULL"
                        + ")";

        db.execSQL(DISPLAY_QUOTES_TABLE);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISPLAY_QUOTES);
            onCreate(db);
        }
    }

    public void addDisplayQuotes(Quotes displayQuotes) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("display_quote", displayQuotes.getQuote());
            values.put("display_quote_said_by", displayQuotes.getSaidby());
            db.insertOrThrow(TABLE_DISPLAY_QUOTES, null, values);
        } catch (Exception e) {
            Log.e("Display Quotes", "Error while trying to add quote to database", e);
        }
    }

    public List<Quotes> getAllQuotes() {
        List<Quotes> displayQuotes = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DISPLAY_QUOTES, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String displayQuote = (cursor.getString(cursor.getColumnIndex("display_quote")));
                    String displayQuoteSaidBy = cursor.getString(cursor.getColumnIndex("display_quote_said_by"));
                    displayQuotes.add(Quotes.from(displayQuote, displayQuoteSaidBy));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Display Quotes", "Error while trying to get favorites from database", e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return displayQuotes;
    }

}
