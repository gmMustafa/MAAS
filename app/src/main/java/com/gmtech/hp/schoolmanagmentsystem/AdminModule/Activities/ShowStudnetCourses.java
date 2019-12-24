package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters.StudentCoursesAdapter;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.StudentModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

public class ShowStudnetCourses extends AppCompatActivity {

    StudentModel studentModel;
    List<StudentModel> model;
    SchoolDB schoolDB;
    ListView listView;
    StudentCoursesAdapter studentCoursesAdapter;
    ImageButton addCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Courses Taken");

        studentModel = (StudentModel) getIntent().getSerializableExtra("Student");
        addCourse = (ImageButton) findViewById(R.id.toolbar_image);

        schoolDB = new SchoolDB(this);
        model = schoolDB.getStudentCourses(studentModel);


        studentCoursesAdapter = new StudentCoursesAdapter(this, model, studentModel);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(studentCoursesAdapter);

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowStudnetCourses.this, ShowStudnetCoursesToAdd.class);
                intent.putExtra("Student", studentModel);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        model = schoolDB.getStudentCourses(studentModel);
        studentCoursesAdapter = new StudentCoursesAdapter(this, model, studentModel);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(studentCoursesAdapter);
    }
}
