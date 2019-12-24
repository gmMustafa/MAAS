package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TeacherpanelModel;

public class TdShowStudentDetailsActivity extends AppCompatActivity {
    TeacherpanelModel teacherpanelModel;
    TextView name;
    TextView gender;
    TextView classNumber;
    TextView mobileNo;
    ImageView imageView;
    Button report;
    Button attendence;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_student_details);

        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#f3bb11"));
        teacherpanelModel=(TeacherpanelModel) getIntent().getSerializableExtra("obj");
        name=(TextView)findViewById(R.id.student_details_name_show_tv);
        gender=(TextView)findViewById(R.id.student_details_gender_show_tv);
        classNumber=(TextView)findViewById(R.id.student_details_class_show_tv);
        mobileNo=(TextView)findViewById(R.id.student_details_mobile_show_tv);
        imageView=(ImageView) findViewById(R.id.student_details_edit_btn);
        report=(Button)findViewById(R.id.courses);
        attendence=(Button)findViewById(R.id.attendence);
        report.setText("Report");

        report.setBackgroundColor(Color.parseColor("#F39C11"));
        attendence.setBackgroundColor(Color.parseColor("#F39C11"));
        toolbar.setBackgroundColor(Color.parseColor("#F39C11"));


        imageView.setVisibility(View.GONE);

        name.setText(teacherpanelModel.getStudentFirstName()+" "+teacherpanelModel.getStudentLastName());
        gender.setText(teacherpanelModel.getStudentGender());
        classNumber.setText(teacherpanelModel.getClassNumber().toString());
        mobileNo.setText(teacherpanelModel.getStudentMobileNumber());

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(TdShowStudentDetailsActivity.this,ShowStudnetReportToTeacher.class);
                intent.putExtra("obj",teacherpanelModel);
                startActivity(intent);

            }
        });

        attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(TdShowStudentDetailsActivity.this,TdStudentCalenderActivity.class);
                intent.putExtra("obj",teacherpanelModel);
                startActivity(intent);

            }
        });


    }
}
