package gngo.com.example.zoolist.ui.main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public final class DBHelper {
    private static final String LOGTAG = "DBHelper";

    private static final String DATABASE_NAME = "animal.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "animaldata";

    // Column Names
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_TYPE = "type";

    // Column indexes
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_NAME = 1;
    public static final int COLUMN_LOCATION = 2;
    public static final int COLUMN_TYPE = 3;

    private Context context;
    private SQLiteDatabase db;
    private SQLiteStatement insertStmt;

    private static final String INSERT =
            "INSERT INTO " + TABLE_NAME + "(" +
                    KEY_NAME + "," +
                    KEY_LOCATION + "," +
                    KEY_TYPE + ") values (?,?,?)";

    public DBHelper (Context context) throws Exception{
        this.context = context;
        try {
            OpenHelper openHelper = new OpenHelper(this.context);
                // Open a database for reading and writing
                db =openHelper.getWritableDatabase();
                // compile a sqlite insert statement into re-usable statement object.
                insertStmt =db.compileStatement(INSERT);
            } catch(Exception e){
                Log.e(LOGTAG, " DBHelper constructor: could not get database " + e);
                throw (e);
            }
        }

        public long insert(Animal animalinfo){
        // bind values to the pre-compiled SQL statement "inserStmt"
            insertStmt.bindString(COLUMN_NAME, animalinfo.getName());
            insertStmt.bindString(COLUMN_LOCATION, animalinfo.getLocation());
            insertStmt.bindString(COLUMN_TYPE, animalinfo.getType());

            long value =-1;
            try{
                // Execute the sqlite statement.
                value = insertStmt.executeInsert();
            } catch (Exception e){
                Log.e(LOGTAG, " executeInsert problem: " + e);
            }
            Log.d (LOGTAG, "value=" + value);
            return value;
        }

        public void deleteAll(){
        db.delete(TABLE_NAME, null, null);
        }

        // delete a row in the database
    public boolean deleteRecord(long rowId){
        return db.delete(TABLE_NAME, KEY_ID + "=" + rowId, null) > 0;
    }

    

    // Helper class for DB creation/update
    // SQLiteOpenHelper provides getReadableDatabase() and getWriteableDatabase() methods
    // to get access to an SQLiteDatabase object; either in read or write mode.
    // A key defined as INTEGER PRIMARY KEY will autoincrement.

    private static class OpenHelper extends SQLiteOpenHelper{
        private static final String LOGTAG = "OpenHelper";

        private static final String CREATE_TABLE =
                "CREATE TABLE " +
                        TABLE_NAME +
                        "(" + KEY_ID + " INTEGER PRIMARY KEY, " +
                        KEY_NAME + " TEXT, " +
                        KEY_LOCATION + " TEXT, " +
                        KEY_TYPE + " TEXT);";

        OpenHelper (Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        // Creates the tables.
        // This function is only run once or after every Clear Data
        @Override
        public void onCreate (SQLiteDatabase db){
            Log.d(LOGTAG, "onCreate");
            try{
                db.execSQL(CREATE_TABLE);
            } catch (Exception e){
                Log.e(LOGTAG, " onCreate: Could not create SQL database: " + e);
            }
        }

        // Called if the database version is increased in your application code.
        // This method updating an existing database schema or dropping the existing database
        // and recreating it via the onCreate() method.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.w(LOGTAG,"Upgrading database, this will drop tables and recreate.");
            try{
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
                onCreate(db);
            } catch (Exception e){
                Log.e(LOGTAG, " onUpgrade: Could not update SQL database: " + e);
            }

            // Technique to add a column rather than recreate the tables.
            // String upgradeQuery_ADD_AREA =
            // "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + KEY_AREA + " TEXT ";
            // if(oldVersion <2){
            // db.execSQL(upgradeQuery_ADD_AREA);
        }
    }



}
