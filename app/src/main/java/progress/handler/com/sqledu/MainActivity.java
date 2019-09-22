package progress.handler.com.sqledu;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    // Declare all the Buttons and Edit controll List View
    ListView studentLitView ;
    EditText userName , age ;

    StudentUtility studentUtility ;   // This will be a wrapper class on our data base class core class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Use find view by id
        // add list. to it..

        userName = (EditText) findViewById(R.id.username);
        age      = (EditText) findViewById(R.id.age);

        Button insertButton = (Button) findViewById(R.id.insertButton);
        Button readButton = (Button) findViewById(R.id.readButton);
        Button updateButton = (Button) findViewById(R.id.updateButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButtton);

        studentLitView = (ListView) findViewById(R.id.studentListView);


        insertButton.setOnClickListener(this);
        readButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

        studentUtility = new StudentUtility(this);
        studentUtility.open();

    }

    @Override
    public void onClick(View view) {

        switch( view.getId()) {

            case R.id.insertButton :
                InsertRecords();
                break;

            case R.id.readButton :
               // ReadRecordsByToast();
               ReadRecords();  // display the rec in the list view...

                break;

            case R.id.updateButton :
                UpdateRecords();
                break;

            case R.id.deleteButtton :
               // DeleteRecords();
               DeleteAllrecords();
                break;





        }

    }

    private void DeleteAllrecords() {

        studentUtility.deleteAllrecords();
    }

    private void DeleteRecords() {

       // studentUtility.deleteRecordsByStudent(new Student("Sathya","36"));

        studentUtility.deleteRecordsByStudent( new Student(userName.getText().toString(),age.getText().toString())  );

    }

    private void UpdateRecords() {


        studentUtility.UpdateRecords( new Student(userName.getText().toString(),age.getText().toString())  );

    }

    private void ReadRecordsByToast() {

        studentUtility.listStudentsByToast();
    }

    private void ReadRecords() {

        Cursor cursor = studentUtility.listStudents();

        if( cursor != null) {

            String[] from = new String[]{ StudentUtility.COLUMN_NAME,studentUtility.COLUMN_AGE};
            int[] to = new int[]{android.R.id.text1,android.R.id.text2};

            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_2,cursor,from,to,0);

            studentLitView.setAdapter(simpleCursorAdapter);

        }
    }

    private void InsertRecords() {

        Toast.makeText(this, "We are in Insert records...", Toast.LENGTH_SHORT).show();
        studentUtility.addStudent( new Student(userName.getText().toString(),age.getText().toString())  );


    }




}
