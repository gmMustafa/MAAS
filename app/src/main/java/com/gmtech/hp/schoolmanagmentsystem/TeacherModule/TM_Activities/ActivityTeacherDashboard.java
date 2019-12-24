package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TdIdsModel;

import java.util.ArrayList;
import java.util.List;

public class ActivityTeacherDashboard extends AppCompatActivity implements View.OnClickListener {


    SchoolDB schoolDB;
    TdIdsModel idsModel;
    List<TdIdsModel> modelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);
        schoolDB=new SchoolDB(this);
        modelList=new ArrayList<TdIdsModel>();
        idsModel=new TdIdsModel();

        modelList=schoolDB.getSchoolAndTeacherId();

        idsModel.setSchoolId(modelList.get(0).getSchoolId());
        idsModel.setTeacherId(modelList.get(0).getTeacherId());

    }

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.op_students:
                Intent intent=new Intent(this,TdStudentActivity.class);
                intent.putExtra("obj",idsModel);
                startActivity(intent);
                break;
            case R.id.op_attendence:
                Intent intent1=new Intent(this,TdAttendenceActivity.class);
                intent1.putExtra("obj",idsModel);
                startActivity(intent1);
                break;
            case R.id.op_backup:
                Intent intentback=new Intent(this,getBackUpActivity.class);
                intentback.putExtra("obj",idsModel);
                startActivity(intentback);
                break;
            case R.id.op_notification:
                Intent intent2=new Intent(this,TdNotificationActivity.class);
                intent2.putExtra("obj",idsModel);
                startActivity(intent2);
                break;
            case R.id.op_assignments:
                Intent intent3=new Intent(this,TdAssignmentActivity.class);
                intent3.putExtra("obj",idsModel);
                startActivity(intent3);
                break;
            case R.id.logout_btn:
                finish();
        }

    }



}
