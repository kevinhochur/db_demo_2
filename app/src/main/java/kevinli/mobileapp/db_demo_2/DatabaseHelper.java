package kevinli.mobileapp.db_demo_2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "MY_TRACKS.DB";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_TABLE = "TRACKS";
    static final String TRACK_ID = "_ID";
    static final String TITLE_EN = "Title_en";
    static final String DISTRICT_EN = "District_en";
    static final String ROUTE_EN = "Route_en";
    static final String HOWTOACCESS_EN = "HowToAccess_en";

    // prevent re-creation of the database
    static final String CREATE_DB_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE
            + " (_id INTEGER PRIMARY KEY, "
            + TITLE_EN + " TEXT, "
            + DISTRICT_EN + " TEXT, "
            + ROUTE_EN + " TEXT, "
            + HOWTOACCESS_EN + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //System.out.println("DATABASE NAME: " + DATABASE_NAME + ", " + "DATABASE_VERSION: " + DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB_IF_NOT_EXISTS);
        //System.out.println("CREATE_DB_QUERY: " + CREATE_DB_IF_NOT_EXISTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        System.out.println("Dropped DATABASE_TABLE: " +DATABASE_TABLE);
    }
}
