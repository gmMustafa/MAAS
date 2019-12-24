package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters.TeacherCoursesAdapter;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.TeacherModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

public class ShowTeacherCourses extends AppCompatActivity {

    TeacherModel teacherModel;
    List<TeacherModel> model;
    SchoolDB schoolDB;
    ListView listView;
    public static  TeacherCoursesAdapter teacherCoursesAdapter;
    ImageButton addCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Courses Taken");

        teacherModel = (TeacherModel) getIntent().getSerializableExtra("Teacher");
        addCourse = (ImageButton) findViewById(R.id.toolbar_image);

        schoolDB = new SchoolDB(this);
        model = schoolDB.getTeacherCourses(teacherModel);


        teacherCoursesAdapter = new TeacherCoursesAdapter(this, model, teacherModel);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(teacherCoursesAdapter);

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowTeacherCourses.this, ShowTeacherCoursesToAdd.class);
                intent.putExtra("Teacher", teacherModel);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        model = schoolDB.getTeacherCourses(teacherModel);
        teacherCoursesAdapter = new TeacherCoursesAdapter(this, model, teacherModel);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(teacherCoursesAdapter);
    }

}
