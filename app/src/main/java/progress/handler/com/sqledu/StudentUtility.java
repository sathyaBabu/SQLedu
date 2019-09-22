package progress.handler.com.sqledu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by malsneha on 18/02/18.
 */



// sqlite CIPHER


//  StudentUtility happens to be a wrapper to our core DB Class

    //       Students.db
    //       DB_VERSION    = 1  to 2
    //   Table name Student
    //   ID         Name      Age      Address
    //   1          Hebbar    18
    //   2          Deepak    19
    //   3          Nisha     20



public class StudentUtility {

    public static final String DB_NAME     = "students.db";
    public static final String TABLE_NAME  = "students";
    public static final String COLUMN_ID   =  "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE  =  "age";
    public static final int DB_VERSION     =  1 ;


    private static  final String TABLE_CREATE_QUERY =

            "create table "+TABLE_NAME+"("+COLUMN_ID+" integer primary key autoincrement,"+COLUMN_NAME +" text,"+
                    COLUMN_AGE +  " text);";

    private MainActivity mainActivity ;
    private Context      context ;  // Old style

    private StudentDatabaseUtility databaseUtility;
    private SQLiteDatabase         sqLiteDatabase ;

    public StudentUtility(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
        databaseUtility = new StudentDatabaseUtility( mainActivity );  // This handle points to all the CRUD oprations of our DB


    }

    public void open() {

        sqLiteDatabase = databaseUtility.getWritableDatabase();
    }



    // Core DB class
    private class StudentDatabaseUtility extends SQLiteOpenHelper {

        public StudentDatabaseUtility(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(TABLE_CREATE_QUERY);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

            Log.d("tag","Updating data base from olde ver "+oldVersion+"to "+newVersion+"This will destroy the old data base");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS");
            onCreate(sqLiteDatabase);

        }
    }



    ////////////////////////////////  Lets incorporate Business logic here....



    public void addStudent(Student student) {

        ContentValues contentValues = new ContentValues(); // key value pairs

        contentValues.put(COLUMN_NAME,student.name);
        contentValues.put(COLUMN_AGE,student.age);

        long id = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        if( id != -1 ) {
            Toast.makeText(mainActivity, "Insert Success.."+id, Toast.LENGTH_SHORT).show();
        }


    }

      //////////////////////////  Reading the records via TOAST




    public void listStudentsByToast() {

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,null,null,null,null,null,null);

        if( cursor  != null && cursor.moveToFirst()) {
            do {

                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String age = cursor.getString(cursor.getColumnIndex(COLUMN_AGE));

                Toast.makeText(mainActivity, "Name : "+name+" Age : "+age, Toast.LENGTH_SHORT).show();




            }while (cursor.moveToNext());
        } else
        {
            Toast.makeText(mainActivity, "Record not found!!!", Toast.LENGTH_SHORT).show();
        }

        if( cursor != null) {

            cursor.close();

        }

    }

    ///////////////////////////////////  Delete student by name

    public void deleteRecordsByStudent(Student student) {

       int numRowsDeleted =  sqLiteDatabase.delete(TABLE_NAME,COLUMN_NAME+"=?",new String[]{student.name});

        Toast.makeText(mainActivity, " Rows Deleted "+numRowsDeleted, Toast.LENGTH_SHORT).show();
    }




    /////////////////////////////////////




    ///////////////////////////////////////////////  delete all records : delete TABLE

    public void deleteAllrecords() {

        sqLiteDatabase.delete(TABLE_NAME,null,null);
        Toast.makeText(mainActivity, "Entire table is deleted...", Toast.LENGTH_SHORT).show();
    }


    ///////////////////////////  Update records


    public void UpdateRecords(Student student) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_AGE,student.age);


       int numOfRowsUpdated =  sqLiteDatabase.update(TABLE_NAME,contentValues,COLUMN_NAME+"=?",new String[]{student.name});

        Toast.makeText(mainActivity, "Updated : "+numOfRowsUpdated, Toast.LENGTH_SHORT).show();

    }


    ///////////////

    public Cursor listStudents() {

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,null,null,null,null,null,null);
        return  cursor;
    }


    //////////////////////////////////////////////////////////////////////////////


}
