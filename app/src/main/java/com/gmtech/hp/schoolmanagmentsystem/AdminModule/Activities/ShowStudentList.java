package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters.StudentListAdapter;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.StudentModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

public class ShowStudentList extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    SchoolDB schoolDB;
    TextView textView;
    List<StudentModel> list;
    Integer Schhol_id;

    StudentListAdapter studentListAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Students");

        Intent intent = getIntent();
        Schhol_id = intent.getIntExtra("School_id", -1);
        schoolDB = new SchoolDB(this);
        listView = (ListView) findViewById(R.id.listview);
        list = schoolDB.getStudents(Schhol_id);
         studentListAdapter = new StudentListAdapter(list, this);
        listView.setAdapter(studentListAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(ShowStudentList.this, AddStudent.class);
        intent.putExtra("SchoolId",Schhol_id.toString());
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("1234","clicked");
        Intent intent = new Intent(ShowStudentList.this, ShowStudentDetails.class);
        StudentModel model = list.get(i);
        intent.putExtra("Student", model);
        startActivity(intent);

    }

    @Override
    public void onResume() {
        super.onResume();
        list = schoolDB.getStudents(Schhol_id);
        studentListAdapter = new StudentListAdapter(list, this);
        listView.setAdapter(studentListAdapter);
    }
}
