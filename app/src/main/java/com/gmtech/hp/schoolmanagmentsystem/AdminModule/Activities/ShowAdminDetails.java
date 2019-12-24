package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AdminModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

public class ShowAdminDetails extends AppCompatActivity implements View.OnClickListener {

    TextView name;
    TextView gender;
    TextView mobileNo;
    AdminModel adminModel;
    Button courses;
    SchoolDB schoolDB;
    Integer counter=0;
    String school_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_selected);
        TextView   textView=(TextView)findViewById(R.id.toolbar_title);
        textView.setText("Admin Details");

        Intent intent=getIntent();
        school_id=intent.getStringExtra("School_id");

        schoolDB =new SchoolDB(this);
        adminModel=schoolDB.getAdminDetails(school_id);
        //adminModel = (AdminModel) getIntent().getSerializableExtra("model");
        courses = (Button) findViewById(R.id.courses);

        name = (TextView) findViewById(R.id.room_et);
        gender = (TextView) findViewById(R.id.teacher_gender);
        mobileNo = (TextView) findViewById(R.id.add_teacher_mobile);

        name.setText(adminModel.getFirstName() + " " + adminModel.getLastName());
        gender.setText(adminModel.getGender());
        mobileNo.setText(adminModel.getMobileNo());
        courses.setVisibility(View.INVISIBLE);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_edit:
                counter=1;
                Intent intent = new Intent(ShowAdminDetails.this, EditAdmin.class);
                intent.putExtra("Admin", adminModel);
                intent.putExtra("School_id", school_id);
                startActivity(intent);
                break;
            case  R.id.deleteTeacher:
                if(schoolDB.deleteadmin(adminModel,school_id)){
                    Toast.makeText(ShowAdminDetails.this,"Admin Deleted",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(ShowAdminDetails.this,"Record Not Deleted",Toast.LENGTH_SHORT).show();
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
