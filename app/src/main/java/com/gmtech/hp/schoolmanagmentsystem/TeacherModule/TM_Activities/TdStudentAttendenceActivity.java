package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AdminModelHelper;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TeacherpanelModel;

public class TdStudentAttendenceActivity extends AppCompatActivity {

    TextView date;
    TextView name;
    TextView status;
    TeacherpanelModel teacherpanelModel;
    SchoolDB schoolDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_listview_item_layout);

        schoolDB=new SchoolDB(this);
        date=(TextView)findViewById(R.id.attendance_date);
        name=(TextView)findViewById(R.id.attendance_name);
        status=(TextView)findViewById(R.id.attendance_status);

        teacherpanelModel=(TeacherpanelModel) getIntent().getSerializableExtra("obj");

        Integer s=schoolDB.TdgetAttendenceOfStudnet(teacherpanelModel, AdminModelHelper.Admin_id);

        date.setText(teacherpanelModel.getDate());
        name.setText(teacherpanelModel.getStudentFirstName()+" "+teacherpanelModel.getStudentLastName());

        if(s==0)//absent
        {
            status.setBackgroundColor(Color.parseColor("#ca1d0d"));
            status.setText("Absent");

        }

        else if(s==1)//present
        {
            status.setBackgroundColor(Color.parseColor("#23b574"));
            status.setText("Present");


        }

        else if(s==2)//leave
        {
            status.setBackgroundColor(Color.parseColor("#CCCC00"));
            status.setText("Leave");


        }

        else
        {
            status.setText("NILL");
            Toast.makeText(this,"Attendence is Not marked", Toast.LENGTH_SHORT).show();
        }
    }
}