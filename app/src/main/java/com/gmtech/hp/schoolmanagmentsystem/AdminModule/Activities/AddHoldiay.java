package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.HolidayModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

public class AddHoldiay extends AppCompatActivity implements View.OnClickListener {

    String School_Id;
    EditText editText;
    String curDate="";
    SchoolDB schoolDB;
    HolidayModel holidayModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_holiday_details);
        Intent intent=getIntent();
        schoolDB=new SchoolDB(this);
        School_Id=intent.getStringExtra("School_Id");
        editText=(EditText)findViewById(R.id.title_holiday);
        holidayModel=new HolidayModel();
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                if(editText.getText().toString().isEmpty())
                {
                    Toast.makeText(AddHoldiay.this,"Empty Fields",Toast.LENGTH_SHORT).show();
                }
                  else {
                    if(curDate.isEmpty())
                    {
                        SchoolDB schoolDB=new SchoolDB(AddHoldiay.this);
                        holidayModel.setDate(schoolDB.getDate());
                    }
                    else
                    {
                        holidayModel.setDate(curDate);

                    }
                    holidayModel.setTitle(editText.getText().toString());
                    if(schoolDB.addHoliday(holidayModel, School_Id))
                    {
                        Toast.makeText(AddHoldiay.this,"Holiday Added",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(AddHoldiay.this,"Holiday not",Toast.LENGTH_SHORT).show();
                    }

                    }
                 break;
            case R.id.cancel:
                finish();

        }
    }
}
