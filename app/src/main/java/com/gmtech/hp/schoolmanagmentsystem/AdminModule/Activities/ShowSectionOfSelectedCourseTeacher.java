package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters.TeacherSelectSetionAdapter;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.TeacherModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

public class ShowSectionOfSelectedCourseTeacher extends AppCompatActivity implements AdapterView.OnItemClickListener {


    SchoolDB schoolDB;
    ListView listView;
    List<TeacherModel> model;
    ImageButton button;
    TeacherModel teacherModel;
    TeacherSelectSetionAdapter teacherSelectSetionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Available Sections");

        teacherModel = (TeacherModel) getIntent().getSerializableExtra("Teacher");
        button = (ImageButton) findViewById(R.id.toolbar_image);
        button.setVisibility(View.GONE);

        schoolDB = new SchoolDB(this);
        model = schoolDB.showSectionOfAvailableCoursesTeacher(teacherModel);

        teacherSelectSetionAdapter = new TeacherSelectSetionAdapter(this, model);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(teacherSelectSetionAdapter);
        listView.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        teacherModel.setSection(model.get(i).getSection());
        teacherModel.setClassInfoId(model.get(i).getClassInfoId());

        Boolean check = schoolDB.giveSubjectToTeacher(teacherModel.getClassInfoId(), teacherModel.getUserId());
        if (check) {
            Toast.makeText(this, "Course Allocated", Toast.LENGTH_SHORT).show();
                    finish();
        } else {
            Toast.makeText(this, "Course Not Allocated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
