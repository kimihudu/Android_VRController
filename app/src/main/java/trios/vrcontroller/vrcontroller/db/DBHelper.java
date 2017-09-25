package trios.vrcontroller.vrcontroller.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import trios.vrcontroller.vrcontroller.model.GeoInfo;
import trios.vrcontroller.vrcontroller.model.User;

/**
 * Created by kimiboo on 2017-09-20.
 */

public class DBHelper extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    private static final String TABLE_USER  = "user";
    private static final String TABLE_GEO   = "geo";

    // User Table Columns names
    private static final String COLUMN_USER_ID          = "user_id";
    private static final String COLUMN_USER_NAME        = "user_name";
    private static final String COLUMN_USER_EMAIL       = "user_email";
    private static final String COLUMN_USER_PASSWORD    = "user_password";
    private static final String COLUMN_USER_GEO_INFO    = "user_geo_info";

    // GEO Table Columns names
    private static final String COLUMN_GEO_ID   = "geo_id";
    private static final String COLUMN_GEO_LONG = "geo_long";
    private static final String COLUMN_GEO_LAT  = "geo_lat";


    // create table sql query

    private String CREATE_USER_TABLE = "CREATE TABLE "  + TABLE_USER            + "("
                                                        + COLUMN_USER_ID        + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                        + COLUMN_USER_NAME      + " TEXT,"
                                                        + COLUMN_USER_EMAIL     + " TEXT,"
                                                        + COLUMN_USER_PASSWORD  + " TEXT,"
                                                        + COLUMN_USER_GEO_INFO   + "TEXT"
                                                                                + ")";

    private String CREATE_GEO_TABLE = "CREATE TABLE "   + TABLE_GEO         + "("
                                                        + COLUMN_GEO_ID     + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                        + COLUMN_GEO_LONG   + " TEXT,"
                                                        + COLUMN_GEO_LAT    + " TEXT"
                                                                            + ")";

    // drop table sql query
    private String DROP_USER_TABLE  = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_GEO_TABLE   = "DROP TABLE IF EXISTS " + TABLE_GEO;

    // delete table sql query
    private String DELETE_USER_TABLE  = "DELETE FROM " + TABLE_USER;
    private String DELETE_GEO_TABLE   = "DELETE FROM " + TABLE_GEO;

    /**
     * Constructor
     *
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_GEO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_GEO_TABLE);

        // Create tables again
        onCreate(db);

    }

//    TODO: USER table section
    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_GEO_INFO, user.getGeoInfo());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_GEO_INFO
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password,user_geo_info FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setGeoInfo(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_GEO_INFO))));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_GEO_INFO, user.getGeoInfo());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

//    TODO: GEO table section

    /**
     * This method is to create geo record
     *
     * @param geo
     */
    public void addGEO(GeoInfo geo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_GEO_LAT, geo.getLatitube());
        values.put(COLUMN_GEO_LONG,geo.getLongitube());

        // Inserting Row
        db.insert(TABLE_GEO, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of geo records
     *
     * @return list
     */
    public List<GeoInfo> getAllGEO() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_GEO_ID,
                COLUMN_GEO_LONG,
                COLUMN_GEO_LAT
        };
        // sorting orders
        String sortOrder =
                COLUMN_GEO_ID + " ASC";
        List<GeoInfo> geoList = new ArrayList<GeoInfo>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT geo_id,geo_long,geo_lat FROM geo ORDER BY geo_id;
         */
        Cursor cursor = db.query(TABLE_GEO, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                GeoInfo _geo = new GeoInfo();
                _geo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_GEO_ID))));
                _geo.setLatitube(cursor.getString(cursor.getColumnIndex(COLUMN_GEO_LAT)));
                _geo.setLongitube(cursor.getString(cursor.getColumnIndex(COLUMN_GEO_LONG)));

                // Adding user record to list
                geoList.add(_geo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return geo list
        return geoList;
    }

    public void deleteTable(String tableName){

        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + tableName);
        Log.i("DELETE TABLE",tableName);
    }

}