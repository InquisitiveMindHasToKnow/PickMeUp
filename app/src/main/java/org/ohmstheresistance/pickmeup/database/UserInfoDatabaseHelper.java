package org.ohmstheresistance.pickmeup.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.ohmstheresistance.pickmeup.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class UserInfoDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userInfoDatabase";
    private static final int DATABASE_VERSION = 4;
    private static final String TABLE_USER_INFO = "userInfo";
    private Context context;

    private static UserInfoDatabaseHelper userInfoDatabaseInstance;

    public static synchronized UserInfoDatabaseHelper getInstance(Context context) {
        if (userInfoDatabaseInstance == null) {
            userInfoDatabaseInstance = new UserInfoDatabaseHelper(context.getApplicationContext());
        }
        return userInfoDatabaseInstance;
    }

    public UserInfoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String USER_INFO_TABLE =
                "CREATE TABLE " + TABLE_USER_INFO + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "user_name TEXT NOT NULL"
                        + ")";

        db.execSQL(USER_INFO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
            onCreate(db);
        }
    }

    public void addUserInfo(UserInfo userInfo) {
        SQLiteDatabase db = getWritableDatabase();

        try {
           ContentValues values = new ContentValues();
            values.put("user_name", userInfo.getUserName());

            db.insertOrThrow(TABLE_USER_INFO, null, values);
        } catch (Exception e) {
            Log.e("User Name", "Error while trying to add post to database", e);
        }
    }

    public void deleteUserName(String userName) {
        SQLiteDatabase db = getWritableDatabase();

        try {

            db.delete(TABLE_USER_INFO, "user_name = ?", new String[]{String.valueOf(userName)});
        } catch (Exception e) {
            Log.e("User Name", "Error while trying to delete name", e);
        }
    }

    public void updateUserName(UserInfo updateUserName) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues updatedValues = new ContentValues();
            updatedValues.put("user_name", updateUserName.getUserName());

            db.update(TABLE_USER_INFO, updatedValues,  updateUserName.get_id()+ "=" + 0, null);


        } catch (Exception e) {
            Log.e("User Name", "Error while trying to update name", e);
        }
    }

    public List<UserInfo> getUserInfo() {
        List<UserInfo> userInfo = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER_INFO, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String user_name = (cursor.getString(cursor.getColumnIndex("user_name")));

                    userInfo.add(UserInfo.from(user_name));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("User Info", "Error while trying to get user info from database", e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return userInfo;
    }
}
