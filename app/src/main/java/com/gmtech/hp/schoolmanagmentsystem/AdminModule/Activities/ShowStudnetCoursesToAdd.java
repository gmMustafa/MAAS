package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters.StudentCoursesAddAdapter;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.StudentModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

public class ShowStudnetCoursesToAdd extends AppCompatActivity {

    TextView textView;
    StudentModel studentModel;
    List<StudentModel> model;
    SchoolDB schoolDB;
    ListView listView;
    StudentCoursesAddAdapter studentCoursesAddAdapter;
    ImageButton addCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Available Courses");

        studentModel = (StudentModel) getIntent().getSerializableExtra("Student");
        addCourse = (ImageButton) findViewById(R.id.toolbar_image);
        addCourse.setVisibility(View.GONE);

        schoolDB = new SchoolDB(this);
        model = schoolDB.getStudentCoursesToAdd(studentModel);

        studentCoursesAddAdapter = new StudentCoursesAddAdapter(this, model, studentModel);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(studentCoursesAddAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        model = schoolDB.getStudentCoursesToAdd(studentModel);
        studentCoursesAddAdapter = new StudentCoursesAddAdapter(this, model, studentModel);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(studentCoursesAddAdapter);

    }
}
