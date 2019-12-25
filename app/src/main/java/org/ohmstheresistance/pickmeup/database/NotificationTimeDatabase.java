package org.ohmstheresistance.pickmeup.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import org.ohmstheresistance.pickmeup.model.NotificationTime;
import org.ohmstheresistance.pickmeup.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class NotificationTimeDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notificationTimeDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NOTIFICATION_TIME = "notificationTime";
    private Context context;

    private static NotificationTimeDatabase notificationTimeDatabaseInstance;

    public static synchronized NotificationTimeDatabase getInstance(Context context) {
        if (notificationTimeDatabaseInstance == null) {
            notificationTimeDatabaseInstance = new NotificationTimeDatabase(context.getApplicationContext());
        }
        return notificationTimeDatabaseInstance;
    }


    public NotificationTimeDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String NOTIFICATION_TIME_TABLE =
                "CREATE TABLE " + TABLE_NOTIFICATION_TIME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "notification_time TEXT NOT NULL"
                        + ")";

        db.execSQL(NOTIFICATION_TIME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION_TIME);
            onCreate(db);
        }
    }

    public void setUpNotificationTime(NotificationTime notificationTime) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("notification_time", notificationTime.getNotificationTime());

            db.insertOrThrow(TABLE_NOTIFICATION_TIME, null, values);
        } catch (Exception e) {
            Log.e("Notification Time", "Error while trying to add notification time to database", e);
        }
    }

    public void removeNotificationTime(String notificationTime) {
        SQLiteDatabase db = getWritableDatabase();

        try {

            db.delete(TABLE_NOTIFICATION_TIME, "notification_time = ?", new String[]{String.valueOf(notificationTime)});
        } catch (Exception e) {
            Log.e("Notification Time", "Error while trying to remove notification time", e);
        }
    }

    public void updateNotificationTime(NotificationTime notificationTime) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues updatedValues = new ContentValues();
            updatedValues.put("notification_time", notificationTime.getNotificationTime());

            db.update(TABLE_NOTIFICATION_TIME, updatedValues,  notificationTime.get_id()+ "=" + 0, null);


        } catch (Exception e) {
            Log.e("Notification TIme", "Error while trying to update notification time", e);
        }
    }

    public List<NotificationTime> getNotificationTime() {
        List<NotificationTime> notificationTime = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTIFICATION_TIME, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String notification_time = (cursor.getString(cursor.getColumnIndex("notification_time")));

                    notificationTime.add(NotificationTime.from(notification_time));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Notification Time", "Error while trying to get notification time from database", e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return notificationTime;
    }
}
