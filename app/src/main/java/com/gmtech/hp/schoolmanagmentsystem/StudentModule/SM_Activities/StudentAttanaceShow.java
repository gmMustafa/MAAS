package com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities.AddHoldiay;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities.HolidaysActivities;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.StudentModel;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Fragments.CalanderFragement;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Fragments.listFragment;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_Fragments.SMCalanderFragement;

public class StudentAttanaceShow extends AppCompatActivity {

    public static Integer id=0;
    public static StudentModel studentModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attanace_show);
        Intent intent=getIntent();
        studentModel = (StudentModel) getIntent().getSerializableExtra("Student");
        id=studentModel.getSchool_id();
        Fragment fragment1=new SMCalanderFragement();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
     }
}
