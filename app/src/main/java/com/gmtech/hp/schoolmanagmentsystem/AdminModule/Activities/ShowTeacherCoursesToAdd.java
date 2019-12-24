package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters.TeacherCoursesAddAdapter;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.TeacherModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

public class ShowTeacherCoursesToAdd extends AppCompatActivity {

    TextView textView;
    TeacherModel teacherModel;
    List<TeacherModel> model;
    SchoolDB schoolDB;
    ListView listView;
    TeacherCoursesAddAdapter teacherCoursesAddAdapter;
    ImageButton addCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Available Courses");

        teacherModel = (TeacherModel) getIntent().getSerializableExtra("Teacher");
        addCourse = (ImageButton) findViewById(R.id.toolbar_image);
        addCourse.setVisibility(View.GONE);

        schoolDB = new SchoolDB(this);
        model = schoolDB.getTeacherCoursesToAdd(teacherModel);

        teacherCoursesAddAdapter = new TeacherCoursesAddAdapter(this, model, teacherModel);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(teacherCoursesAddAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        model = schoolDB.getTeacherCoursesToAdd(teacherModel);
        teacherCoursesAddAdapter = new TeacherCoursesAddAdapter(this, model, teacherModel);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(teacherCoursesAddAdapter);

    }
}
