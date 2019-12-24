package com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.StudentModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_Adapter.SM_StudentCoursesAdapter;

import java.util.List;

public class SM_ShowStudnetCourses extends AppCompatActivity {

    StudentModel studentModel;
    List<StudentModel> model;
    SchoolDB schoolDB;
    ListView listView;
    SM_StudentCoursesAdapter studentCoursesAdapter;
    String ActivityGo="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sm__show_studnet_courses);
        Intent check=getIntent();
        ActivityGo=check.getStringExtra("Goto");


        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Courses Taken");

        studentModel = (StudentModel) getIntent().getSerializableExtra("Student");

        schoolDB = new SchoolDB(this);
        model = schoolDB.getStudentCourses(studentModel);
        studentCoursesAdapter = new SM_StudentCoursesAdapter(this, model, studentModel);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(studentCoursesAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(SM_ShowStudnetCourses.this,model.get(i).getCourse(),Toast.LENGTH_SHORT).show();
                studentModel.setCourse(model.get(i).getCourse());
                if(ActivityGo.equals("Report"))
                {
                    Intent intent=new Intent(SM_ShowStudnetCourses.this,SM_ShowReport.class);
                    intent.putExtra("Student",studentModel);
                    startActivity(intent);
                }
                else if(ActivityGo.equals("Notifications"))
                {
                    Intent intent=new Intent(SM_ShowStudnetCourses.this,ShowStudentNotificationslist.class);
                    intent.putExtra("Student",studentModel);
                    startActivity(intent);
                }
                else if(ActivityGo.equals("Attandence"))
                {
                    Intent intent=new Intent(SM_ShowStudnetCourses.this,StudentAttanaceShow.class);
                    intent.putExtra("Student",studentModel);
                    startActivity(intent);
                }
            }
        });
    }
}
