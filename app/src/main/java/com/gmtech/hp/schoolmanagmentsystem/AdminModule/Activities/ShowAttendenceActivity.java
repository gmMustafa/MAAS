package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters.AttendenceAdapter;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AttendenceForAllModel;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AttendenceModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.ArrayList;
import java.util.List;

public class ShowAttendenceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    AttendenceModel attendenceModel;
    ListView listView;
    List<AttendenceModel> model;
    List<AttendenceForAllModel> attendenceForAllModels;
    AttendenceForAllModel attendence;
    SchoolDB schoolDB;
    AttendenceAdapter attendenceAdapter;
    ImageButton imageButton;
    int i=0;
    Integer status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        attendenceForAllModels=new ArrayList<AttendenceForAllModel>();
        imageButton=(ImageButton)findViewById(R.id.toolbar_image);
        attendenceModel=(AttendenceModel) getIntent().getSerializableExtra("obj");

        imageButton.setVisibility(View.GONE);

        schoolDB=new SchoolDB(this);
        model=schoolDB.getStudentToShow(attendenceModel);

        for(i=0;i<model.size();i++)
        {
            attendence=new AttendenceForAllModel();
            status=schoolDB.getAttendenceForAll(model.get(i).getStudentUserId(),attendenceModel.getDate(),attendenceModel.getSection(),attendenceModel.getSchoolId(),attendenceModel.getSubjectid());

            if(status!=-1)
            {
                attendence.setFirstName(model.get(i).getStudentFirstName());
                attendence.setLastName(model.get(i).getStudentLastName());
                attendence.setAttendenceStatus(status);
                attendence.setStudentId(schoolDB.getStudentId(model.get(i).getStudentUserId()));
                attendence.setDate(attendenceModel.getDate());

                attendenceForAllModels.add(attendence);
            }

        }

        if(i>0 && attendenceForAllModels.size()>0)
        {
            attendenceAdapter=new AttendenceAdapter(this,attendenceForAllModels);
            listView=(ListView)findViewById(R.id.listview);
            listView.setAdapter(attendenceAdapter);
            listView.setOnItemClickListener(this);
        }

        else {
            Toast.makeText(this,"No Attendance Found",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        Boolean check;
        String status=((TextView)view.findViewById(R.id.attendance_status)).getText().toString();

        if(status.equals("Present"))
        {
            check=schoolDB.setAttendenceAdmin(attendenceForAllModels.get(i).getStudentId(),attendenceForAllModels.get(i).getDate(),0);

            if(check==true)
            {
                ((TextView)view.findViewById(R.id.attendance_status)).setText("Absent");
                ((TextView)view.findViewById(R.id.attendance_status)).setBackgroundColor(Color.parseColor("#ca1d0d"));
            }

        }

        else if (status.equals("Absent")){

            check=schoolDB.setAttendenceAdmin(attendenceForAllModels.get(i).getStudentId(),attendenceForAllModels.get(i).getDate(),2);

            if(check==true) {
                ((TextView) view.findViewById(R.id.attendance_status)).setText("Leave");
                ((TextView) view.findViewById(R.id.attendance_status)).setBackgroundColor(Color.parseColor("#CCCC00"));
            }
        }

        else if (status.equals("Leave")){

            check=schoolDB.setAttendenceAdmin(attendenceForAllModels.get(i).getStudentId(),attendenceForAllModels.get(i).getDate(),1);

            if(check==true) {
                ((TextView) view.findViewById(R.id.attendance_status)).setText("Present");
                ((TextView) view.findViewById(R.id.attendance_status)).setBackgroundColor(Color.parseColor("#23b574"));
            }
        }


    }
}
