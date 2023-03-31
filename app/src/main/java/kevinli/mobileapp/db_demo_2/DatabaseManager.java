package kevinli.mobileapp.db_demo_2;

import static kevinli.mobileapp.db_demo_2.DatabaseHelper.CREATE_DB_IF_NOT_EXISTS;
import static kevinli.mobileapp.db_demo_2.DatabaseHelper.DATABASE_TABLE;

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

    public void close() { dbHelper.close();
    }

    public void insert(int i, String title_en, String district_en, String route_en, String howtoaccess_en) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TITLE_EN, title_en);
        contentValues.put(DatabaseHelper.DISTRICT_EN, district_en);
        contentValues.put(DatabaseHelper.ROUTE_EN, route_en);
        contentValues.put(DatabaseHelper.HOWTOACCESS_EN, howtoaccess_en);
        if (i == 0) {
            dbHelper.deleteFromTable(database);
            database.replace(DATABASE_TABLE, null, contentValues);
        } else {
            database.replace(DATABASE_TABLE, null, contentValues);
        }
    }

    public Cursor fetch() {
        //database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String ID = cursor.getString(0);
            String TITLE_EN = cursor.getString(1);
            String DISTRICT_EN = cursor.getString(2);
            String ROUTE_EN = cursor.getString(3);
            String HOWTOACCESS_EN = cursor.getString(4);
            trackInfo.addTrack(TITLE_EN, DISTRICT_EN, ROUTE_EN, HOWTOACCESS_EN);
            System.out.println("I have read ID: " + ID
                    + " Title_en: " + TITLE_EN
                    + " District_en: " + DISTRICT_EN
                    + " Route_en: " + ROUTE_EN
                    + " HowToAccess_en: " + HOWTOACCESS_EN);
        }
        cursor.close();
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
