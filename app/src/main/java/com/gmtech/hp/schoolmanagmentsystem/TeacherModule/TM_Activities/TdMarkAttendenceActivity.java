package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AdminModelHelper;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Adapters.TdAttendenceAdapter;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TM_AttendenceForAllModel;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TeacherpanelModel;

import java.util.ArrayList;
import java.util.List;

public class TdMarkAttendenceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TeacherpanelModel teacherpanelModel;
    ListView listView;
    List<TeacherpanelModel> model;
    List<TM_AttendenceForAllModel> TM_AttendenceForAllModels;
    TM_AttendenceForAllModel attendence;
    SchoolDB schoolDB;
    TdAttendenceAdapter attendenceAdapter;
    ImageButton save;
    int i=0;
    Integer status;
    android.support.v7.widget.Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        teacherpanelModel=(TeacherpanelModel) getIntent().getSerializableExtra("obj");
        TM_AttendenceForAllModels=new ArrayList<TM_AttendenceForAllModel>();
        save=(ImageButton)findViewById(R.id.toolbar_image);

        toolbar.setBackgroundColor(Color.parseColor("#f3bb11"));
        save.setImageResource(R.drawable.save);

        schoolDB=new SchoolDB(this);
        model=schoolDB.TdGetStudentToShow(teacherpanelModel);

        if(model.size()>0)
        {
            for(i=0;i<model.size();i++)
            {
                attendence=new TM_AttendenceForAllModel();
                status=schoolDB.getAttendenceForAll(model.get(i).getStudentUserId(),teacherpanelModel);
                attendence.setFirstName(model.get(i).getStudentFirstName());
                attendence.setLastName(model.get(i).getStudentLastName());
                attendence.setAttendenceStatus(status);
                attendence.setStudentId(schoolDB.getStudentId(model.get(i).getStudentUserId()));
                attendence.setDate(teacherpanelModel.getDate());
                TM_AttendenceForAllModels.add(attendence);

            }


            attendenceAdapter=new TdAttendenceAdapter(this,TM_AttendenceForAllModels);
            listView=(ListView)findViewById(R.id.listview);
            listView.setAdapter(attendenceAdapter);
            listView.setOnItemClickListener(this);


        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(i=0;i<TM_AttendenceForAllModels.size();i++)
                {
                    if(TM_AttendenceForAllModels.get(i).getAttendenceStatus()==-1)
                    {
                        break;
                    }
                }
                if(i<TM_AttendenceForAllModels.size())
                {
                    Toast.makeText(TdMarkAttendenceActivity.this,"Please Mark Attendance of all students",Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean check=false;

                    check=schoolDB.checkIfAttendenceIsNotMarkedBefore(teacherpanelModel, AdminModelHelper.Admin_id);

                    if(check==false)
                    {
                        for(i=0;i<TM_AttendenceForAllModels.size();i++)
                        {
                            check=schoolDB.TdSaveAttendence(TM_AttendenceForAllModels.get(i),teacherpanelModel,AdminModelHelper.Admin_id);
                            if(check==false)
                                break;
                        }

                        if(check==false)
                        {
                            Toast.makeText(TdMarkAttendenceActivity.this,"Error saving Attendance",Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Intent intent=new Intent(TdMarkAttendenceActivity.this,ActivityTeacherDashboard.class);
                            startActivity(intent);
                        }
                    }
                    else
                    {
                        for(i=0;i<TM_AttendenceForAllModels.size();i++)
                        {
                            check=schoolDB.TdUpdateSavedAttendence(TM_AttendenceForAllModels.get(i),teacherpanelModel,AdminModelHelper.Admin_id);
                            if(check==false)
                            {
                                check=schoolDB.TdSaveAttendence(TM_AttendenceForAllModels.get(i),teacherpanelModel,AdminModelHelper.Admin_id);
                                if(check==false)
                                    break;
                            }
                        }

                        if(check==false)
                        {
                            Toast.makeText(TdMarkAttendenceActivity.this,"Error saving Attendance",Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Intent intent=new Intent(TdMarkAttendenceActivity.this,ActivityTeacherDashboard.class);
                            startActivity(intent);
                        }
                    }




                }


            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Boolean check;
        String status=((TextView)view.findViewById(R.id.attendance_status)).getText().toString();

        if(status.equals("Present"))
        {

            ((TextView)view.findViewById(R.id.attendance_status)).setText("Absent");
            ((TextView)view.findViewById(R.id.attendance_status)).setBackgroundColor(Color.parseColor("#ca1d0d"));
            TM_AttendenceForAllModels.get(i).setAttendenceStatus(0);
        }

        else if (status.equals("Absent")){


            ((TextView) view.findViewById(R.id.attendance_status)).setText("Leave");
            ((TextView) view.findViewById(R.id.attendance_status)).setBackgroundColor(Color.parseColor("#CCCC00"));
            TM_AttendenceForAllModels.get(i).setAttendenceStatus(2);

        }

        else if (status.equals("Leave")){

            ((TextView) view.findViewById(R.id.attendance_status)).setText("Present");
            ((TextView) view.findViewById(R.id.attendance_status)).setBackgroundColor(Color.parseColor("#23b574"));
            TM_AttendenceForAllModels.get(i).setAttendenceStatus(1);

        }

        else if(status.equals("NILL"))
        {
            ((TextView) view.findViewById(R.id.attendance_status)).setText("Present");
            ((TextView) view.findViewById(R.id.attendance_status)).setBackgroundColor(Color.parseColor("#23b574"));
            TM_AttendenceForAllModels.get(i).setAttendenceStatus(1);
        }


    }
}
