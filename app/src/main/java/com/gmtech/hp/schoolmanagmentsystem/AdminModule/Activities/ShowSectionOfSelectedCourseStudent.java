package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters.StudnetSelectSetionAdapter;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.StudentModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

public class ShowSectionOfSelectedCourseStudent extends AppCompatActivity implements AdapterView.OnItemClickListener {


    SchoolDB schoolDB;
    ListView listView;
    List<StudentModel> model;
    ImageButton button;
    StudentModel studentModel;
    StudnetSelectSetionAdapter studnetSelectSetionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Available Sections");

        studentModel = (StudentModel) getIntent().getSerializableExtra("Student");
        button = (ImageButton) findViewById(R.id.toolbar_image);
        button.setVisibility(View.GONE);

        schoolDB = new SchoolDB(this);
        model = schoolDB.showSectionOfAvailableCoursesStudent(studentModel);
        studnetSelectSetionAdapter = new StudnetSelectSetionAdapter(this, model);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(studnetSelectSetionAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        studentModel.setSection(model.get(i).getSection());
        studentModel.setClassInfoId(model.get(i).getClassInfoId());
        Boolean check = schoolDB.giveSubjectToStudent(studentModel);
        if (check) {
            Toast.makeText(this, "Course Allocated", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Course Not Allocated", Toast.LENGTH_SHORT).show();
        }
    }


}
