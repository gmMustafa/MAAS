package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.TeacherModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;


public class ShowTeacherDetails extends AppCompatActivity implements View.OnClickListener {

    TextView name;
    TextView gender;
    TextView mobileNo;
    TeacherModel model;
    Button courses;
    SchoolDB schoolDB;
    Integer counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_selected);

        schoolDB =new SchoolDB(this);
        model = (TeacherModel) getIntent().getSerializableExtra("model");
        courses = (Button) findViewById(R.id.courses);

        name = (TextView) findViewById(R.id.room_et);
        gender = (TextView) findViewById(R.id.teacher_gender);
        mobileNo = (TextView) findViewById(R.id.add_teacher_mobile);

        name.setText(model.getFirstName() + " " + model.getLastName());
        gender.setText(model.getGender());
        mobileNo.setText(model.getMobileNo());

        courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowTeacherDetails.this, ShowTeacherCourses.class);
                intent.putExtra("Teacher", model);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_edit:
                counter=1;
                Intent intent = new Intent(ShowTeacherDetails.this, EditTeacher.class);
                intent.putExtra("Teacher", model);
                startActivity(intent);
                break;
            case  R.id.deleteTeacher:
                if(schoolDB.deleteTeacher(model)){
                    Toast.makeText(ShowTeacherDetails.this,"Record Deleted",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(ShowTeacherDetails.this,"Failure Deleting Record",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void  onResume() {
        super.onResume();
        if(counter==1) {
            finish();
        }
    }
}
