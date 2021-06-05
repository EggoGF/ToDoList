package gngo.com.example.todolist.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public final class DBHelper {
    private static final String LOGTAG = "DBHelper";

    private static final String DATABASE_NAME = "task.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "taskdata";

    // Column Names
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DUE_DATE = "duedate";
    public static final String KEY_ADD_INFO = "addinfo";

    // Column indexes
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_TITLE = 1;
    public static final int COLUMN_DESCRIPTION = 2;
    public static final int COLUMN_DUE_DATE = 3;
    public static final int COLUMN_ADD_INFO = 4;

    private Context context;
    private SQLiteDatabase db;
    private SQLiteStatement insertStmt;

    private static final String INSERT =
            "INSERT INTO " + TABLE_NAME + "(" +
                    KEY_TITLE + "," +
                    KEY_DESCRIPTION + "," +
                    KEY_DUE_DATE + "," +
                    KEY_ADD_INFO + ") values (?,?,?,?)";

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

    public long insert(Task taskinfo){
    // bind values to the pre-compiled SQL statement "inserStmt"
        insertStmt.bindString(COLUMN_TITLE, taskinfo.getTitle());
        insertStmt.bindString(COLUMN_DESCRIPTION, taskinfo.getDescription());
        insertStmt.bindString(COLUMN_DUE_DATE, taskinfo.getDueDate());
        insertStmt.bindString(COLUMN_ADD_INFO, taskinfo.getAddinfo());

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

    // Creates a list of task info retrieved from the sqlite database.
    public List<Task> selectAll(){
        List<Task> list = new ArrayList<>();

        // query takes the following parameters
        // dbName : the table name
        // columnNames: a list of which table columns to return
        // whereClause: filter of selection of data; null selects all data
        // selectionArg: values to fill in the ? if any are in the whereClause
        // group by: Filter specifying how to group rows, null means no grouping
        // having: filter for groups, null means none
        // orderBy: Table columns used to order the data, null means no order.

        // A Cursor provides read-write access to the result set returned by a database query.
        // A Cursor represents the result of the query and points to one row of the query result.
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{KEY_ID, KEY_TITLE, KEY_DESCRIPTION, KEY_DUE_DATE, KEY_ADD_INFO},
                null, null, null, null, null);

        if (cursor.moveToFirst())
        {
            do {
                Task taskInfo = new Task();
                taskInfo.setId(cursor.getLong(COLUMN_ID));
                taskInfo.setTitle(cursor.getString(COLUMN_TITLE));
                taskInfo.setDescription(cursor.getString(COLUMN_DESCRIPTION));
                taskInfo.setDueDate(cursor.getString(COLUMN_DUE_DATE));
                taskInfo.setAddinfo(cursor.getString(COLUMN_ADD_INFO));
                list.add(taskInfo);
            }
            while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        return list;
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
                        KEY_TITLE + " TEXT, " +
                        KEY_DESCRIPTION + " TEXT, " +
                        KEY_DUE_DATE + " TEXT, " +
                        KEY_ADD_INFO + " TEXT);";

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
