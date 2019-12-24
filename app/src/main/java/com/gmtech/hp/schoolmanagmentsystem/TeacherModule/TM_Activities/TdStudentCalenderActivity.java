package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TeacherpanelModel;

public class TdStudentCalenderActivity extends AppCompatActivity {
    Button show;
    CalendarView calendarView;
    String curDate="";
    TeacherpanelModel teacherpanelModel;
    android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_btn);


        toolbar=( android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#f3bb11"));
        show=(Button)findViewById(R.id.show);
        show.setBackgroundColor(Color.parseColor("#f3bb11"));


        calendarView=(CalendarView)findViewById(R.id.calendarView);
        teacherpanelModel=(TeacherpanelModel) getIntent().getSerializableExtra("obj");

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
                Toast.makeText(getApplicationContext(),date, Toast.LENGTH_SHORT).show();
                curDate =date;
            }
        });


        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(curDate.isEmpty())
                {
                    SchoolDB schoolDB=new SchoolDB(TdStudentCalenderActivity.this);
                    teacherpanelModel.setDate(schoolDB.getDate());
                }
                else
                {
                    teacherpanelModel.setDate(curDate);

                }

                Intent intent=new Intent(TdStudentCalenderActivity.this,TdStudentAttendenceActivity.class);
                intent.putExtra("obj",teacherpanelModel);
                startActivity(intent);
            }
        });

    }

}
