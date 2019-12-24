package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.StudentModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

public class ShowStudentDetails extends AppCompatActivity implements View.OnClickListener {
    TextView textView;
    Intent intent;
    StudentModel model;
    Button button;
    SchoolDB schoolDB;

    Integer counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        intent = getIntent();

        model = (StudentModel) intent.getSerializableExtra("Student");
        schoolDB=new SchoolDB(this);

        textView = (TextView) findViewById(R.id.student_details_name_show_tv);
        textView.setText(model.getfName() + " " + model.getlName());

        textView = (TextView) findViewById(R.id.student_details_gender_show_tv);
        textView.setText(model.getGender());

        textView = (TextView) findViewById(R.id.student_details_class_show_tv);
        textView.setText(model.getClass_num().toString());

        textView = (TextView) findViewById(R.id.student_details_mobile_show_tv);
        textView.setText(model.getMobile_no());

        button = (Button) findViewById(R.id.courses_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ShowStudentDetails.this, ShowStudnetCourses.class);
                intent2.putExtra("Student", model);
                startActivity(intent2);

            }
        });
    }


    @Override
    public void onClick(View view) {
        Intent intent2;
        switch (view.getId()) {
            case R.id.student_details_edit_btn:
                counter=1;
                intent2 = new Intent(ShowStudentDetails.this, EditStudent.class);
                intent2.putExtra("Student", model);
                startActivity(intent2);
                break;
            case  R.id.deleteStudent:
                if(schoolDB.deleteStudent(model)){
                    Toast.makeText(ShowStudentDetails.this,"Record Deleted",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(ShowStudentDetails.this,"Failure Deleting Record Due To Already Allocation ",Toast.LENGTH_SHORT).show();

                }

                break;
        }
    }

    @Override
    public void  onResume() {
        super.onResume();
        if(counter==1)
        {finish();}
    }
}
