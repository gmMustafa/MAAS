package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AttendenceModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

public class AttendenceCalenderActivity extends AppCompatActivity {

    AttendenceModel attendenceModel;
    Button show;
    CalendarView calendarView;
    String curDate="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender1);


        show=(Button)findViewById(R.id.show);
        calendarView=(CalendarView)findViewById(R.id.calendarView);
        attendenceModel=(AttendenceModel) getIntent().getSerializableExtra("obj");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {int d = dayOfMonth;
                String date=String.valueOf(year);
                if((month+1)<=9)
                {date+="-0"+String.valueOf((month+1));}
                else
                {date+="-"+String.valueOf((month+1));}
                if(d<=9)
                {date+="-0"+String.valueOf(d);}
                else
                {date+="-"+String.valueOf(d);}
                Toast.makeText(getApplicationContext(),date,Toast.LENGTH_SHORT).show();
                curDate =date;
            }
        });


        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(curDate.isEmpty())
                {
                    SchoolDB schoolDB=new SchoolDB(AttendenceCalenderActivity.this);
                    attendenceModel.setDate(schoolDB.getDate());
                }
                else
                {
                    attendenceModel.setDate(curDate);

                }

                Intent intent=new Intent(AttendenceCalenderActivity.this,ShowAttendenceActivity.class);
                intent.putExtra("obj",attendenceModel);
                startActivity(intent);
            }
        });


    }
}
