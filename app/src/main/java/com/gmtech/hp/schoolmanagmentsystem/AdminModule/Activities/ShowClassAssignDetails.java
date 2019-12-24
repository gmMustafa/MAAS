package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.ClassInfoModel;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.RoomModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShowClassAssignDetails extends AppCompatActivity {

    SchoolDB schoolDB;
    ClassInfoModel classInfoModel;
    EditText editTextroom;
    EditText edittext;
    EditText edittext2;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_class_assign_details);
        final Intent intent=getIntent();
        classInfoModel= (ClassInfoModel) intent.getSerializableExtra("ClassModel");
        schoolDB=new SchoolDB(this);

        spinner=(Spinner)findViewById(R.id.spinner);
        edittext= (EditText) findViewById(R.id.startTimeEdit);
        edittext2=(EditText)findViewById(R.id.endTimeEdit);

        if((schoolDB.alreadyAlocated(classInfoModel.getClass_info_id())))
        {
            Toast.makeText(this,"Already Allocated Edit to change",Toast.LENGTH_SHORT).show();
        }

        List<RoomModel> rooms= schoolDB.getAllRooms(classInfoModel.getSchool_id().toString());
        List<Integer> show=new ArrayList<Integer>();

        if(rooms!=null)
        {
            for(int i=0;i<rooms.size();i++) {
               show.add(rooms.get(i).getRoomno());
            }
        }

        ArrayAdapter adapter2 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, new ArrayList(show));

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);

        edittext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                final String seconds="00";
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ShowClassAssignDetails.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        edittext2.setText( selectedHour + ":" + selectedMinute +":"+seconds);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                final String seconds="00";
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ShowClassAssignDetails.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        edittext.setText( selectedHour + ":" + selectedMinute +":"+seconds);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });



    }



    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.save:

                if(edittext.getText().toString().isEmpty() || edittext2.getText().toString().isEmpty())
                {
                    Toast.makeText(ShowClassAssignDetails.this," Empty Fields",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    classInfoModel.setClass_start_time(edittext.getText().toString());
                    classInfoModel.setClass_end_time(edittext2.getText().toString());
                    classInfoModel.setRoom_id(Integer.parseInt(spinner.getSelectedItem().toString()));
                    Boolean check =schoolDB.insert_update_Classes(classInfoModel);
                    if(check)
                    {
                        Toast.makeText(ShowClassAssignDetails.this," Section Added",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(ShowClassAssignDetails.this," Failure Adding Section",Toast.LENGTH_SHORT).show();

                    }
                }
                break;

            case R.id.cancel:
                finish();
                break;
        }
    }

}
