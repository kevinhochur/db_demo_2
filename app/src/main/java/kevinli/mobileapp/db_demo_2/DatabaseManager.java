package kevinli.mobileapp.db_demo_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLDataException;

public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context ctx) {
        context = ctx;
    }

    public DatabaseManager open() throws SQLDataException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String title_en, String district_en, String route_en, String howtoaccess_en) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TITLE_EN, title_en);
        contentValues.put(DatabaseHelper.DISTRICT_EN, district_en);
        contentValues.put(DatabaseHelper.ROUTE_EN, route_en);
        contentValues.put(DatabaseHelper.HOWTOACCESS_EN, howtoaccess_en);
        System.out.println("Ready to Insert " + title_en + " in " + district_en);
        System.out.println("The Table to be inserted is: " + DatabaseHelper.DATABASE_TABLE);
        database.insert(DatabaseHelper.DATABASE_TABLE, null, contentValues);
    }

    public Cursor fetch() {
        //String [] columns = new String[] {DatabaseHelper.USER_ID, DatabaseHelper.USER_NAME, DatabaseHelper.USER_PASSWORD};
        Cursor cursor = database.query(DatabaseHelper.DATABASE_TABLE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            //long idIndex = cursor.getColumnIndex(DatabaseHelper.USER_ID);
            //long usernameIndex = cursor.getColumnIndex(DatabaseHelper.USER_NAME);
            //long userpasswordIndex = cursor.getColumnIndex(DatabaseHelper.USER_PASSWORD);
            //System.out.println("idIndex = " + Long.toString(idIndex) + "usernameIndex = " + Long.toString(usernameIndex) + Long.toString(userpasswordIndex));
            String ID = cursor.getString(0);
            String TITLE_EN = cursor.getString(1);
            String DISTRICT_EN = cursor.getString(2);
            String ROUTE_EN = cursor.getString(3);
            String HOWTOACCESS_EN = cursor.getString(4);
            System.out.println("I have read ID: " + ID
                    + " Title_en: " + TITLE_EN
                    + " District_en: " + DISTRICT_EN
                    + " Route_en: " + ROUTE_EN
                    + " HowToAccess_en: " + HOWTOACCESS_EN);
        }
        cursor.close();
        /*
        if (cursor != null){
            cursor.moveToFirst();
        }
        */
        return cursor;
    }

    /*
    public int update(long _id, String username, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.USER_NAME, username);
        contentValues.put(DatabaseHelper.USER_PASSWORD, password);
        int ret = database.update(DatabaseHelper.DATABASE_TABLE, contentValues, DatabaseHelper.USER_ID + "=" +_id, null);
        return ret;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.DATABASE_TABLE, DatabaseHelper.USER_ID + "=" +_id, null);
        System.out.println(DatabaseHelper.USER_ID + " has been deleted!");
    }
    */
}
