package com.shivam.stickyheader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "usersManager.db";

    // Contacts table name
    private static final String TABLE_USERS= "users";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FNAME = "name";
    private static final String KEY_DATE = "date";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USERS="CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID +" INTEGER PRIMARY KEY," +
                KEY_FNAME +" TEXT," + KEY_DATE +" TEXT " + ")";
        db.execSQL(CREATE_TABLE_USERS);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    //Insert values to the table contacts
    public void addUsers(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values=new ContentValues();

        values.put(KEY_FNAME, user.getName());
        values.put(KEY_DATE, user.getDate());




        db.insert(TABLE_USERS, null, values);
        db.close();
    }


    /**
     *Getting All Contacts
     **/

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS+ " ORDER BY "+ KEY_DATE+" DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setDate(cursor.getString(2));
                // Adding user to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        // return user list
        return userList;
    }


    /**
     *Updating single user
     **/
    public int updateUsers(User user, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_FNAME, user.getName());
        values.put(KEY_DATE, user.getDate());


        // updating row
         return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    /**
     *Deleting single User
     **/
    public void deleteUser(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + " = ?",
                new String[] { String.valueOf(Id) });
        db.close();
    }


}
