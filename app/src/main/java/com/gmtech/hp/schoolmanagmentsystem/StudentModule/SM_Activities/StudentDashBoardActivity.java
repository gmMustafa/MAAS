package com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.StudentModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

public class StudentDashBoardActivity extends AppCompatActivity implements View.OnClickListener{

    StudentModel studentModel;
    SchoolDB schoolDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        studentModel=new StudentModel();
        schoolDB=new SchoolDB(this);
        Intent intent=getIntent() ;
        studentModel=schoolDB.getStudentDetails(intent.getStringExtra("userName"));

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.op_student_attendance:
                Intent intent2=new Intent(StudentDashBoardActivity.this,SM_ShowStudnetCourses.class);
                intent2.putExtra("Student",studentModel);
                intent2.putExtra("Goto","Attandence");
                startActivity(intent2);
                break;

            case R.id.op_student_reports:
                Intent intent1=new Intent(StudentDashBoardActivity.this,SM_ShowStudnetCourses.class);
                intent1.putExtra("Student",studentModel);
                intent1.putExtra("Goto","Report");
                startActivity(intent1);
                break;

            case R.id.op_student_notification:
                intent1=new Intent(StudentDashBoardActivity.this,SM_ShowStudnetCourses.class);
                intent1.putExtra("Student",studentModel);
                intent1.putExtra("Goto","Notifications");
                startActivity(intent1);
                break;
            case R.id.button:
                finish();

        }
    }
}
