package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AttendenceModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.ArrayList;
import java.util.List;

public class AttandenceSelectionActivity extends AppCompatActivity {

    TextView selectClass;
    TextView selectSubject;
    TextView selectSection;

    android.support.v7.widget.AppCompatSpinner spinnerClass;
    android.support.v7.widget.AppCompatSpinner spinnerSubject;
    android.support.v7.widget.AppCompatSpinner spinnerSection;
    List<AttendenceModel> modelList;
    SchoolDB schoolDB;
    AttendenceModel attendenceModel;
    int i=0;
    ArrayList<String> arrayClass;
    ArrayList<String> arraySubject;
    ArrayList<String> arraySection;
    Button show;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_select_class_section);

        attendenceModel=new AttendenceModel();
        modelList=new ArrayList<AttendenceModel>();
        schoolDB=new SchoolDB(this);
        selectClass=(TextView)findViewById(R.id.select_class_tv);
        selectSubject=(TextView)findViewById(R.id.select_subject_tv);
        selectSection=(TextView)findViewById(R.id.select_section_tv);
        spinnerClass=(android.support.v7.widget.AppCompatSpinner)findViewById(R.id.select_class_spinner);
        spinnerSubject=(android.support.v7.widget.AppCompatSpinner)findViewById(R.id.select_subject_spinner);
        spinnerSection=(android.support.v7.widget.AppCompatSpinner)findViewById(R.id.select_section_spinner);
        show=(Button)findViewById(R.id.show);
        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#f85f50"));
        show.setBackgroundColor(Color.parseColor("#f85f50"));

        Intent intent = getIntent();
        attendenceModel.setSchoolId(intent.getIntExtra("School_id", -1));


        modelList=schoolDB.getClassNumberOfSchool(attendenceModel.getSchoolId());

        arrayClass=new ArrayList<String>();
        for(i=0;i<modelList.size();i++)
        {
            arrayClass.add(modelList.get(i).getClassNumber().toString());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayClass);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClass.setAdapter(spinnerAdapter);


        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Integer clsssNo=Integer.parseInt(spinnerClass.getSelectedItem().toString());
                attendenceModel.setClassNumber(clsssNo);
                modelList=schoolDB.getsubjectOfSchool(attendenceModel.getSchoolId(),clsssNo);
                arraySubject=new ArrayList<String>();
                for(i=0;i<modelList.size();i++)
                {
                    arraySubject.add(modelList.get(i).getSubjectName());
                }

                ArrayAdapter<String> spinnerAdapter =
                        new ArrayAdapter<String>(AttandenceSelectionActivity.this, android.R.layout.simple_spinner_item, arraySubject);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSubject.setAdapter(spinnerAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String subname=spinnerSubject.getSelectedItem().toString();

                Integer subId=schoolDB.getSubjectId(subname,attendenceModel.getSchoolId(),attendenceModel.getClassNumber());

                attendenceModel.setSubjectName(subname);
                attendenceModel.setSubjectid(subId);
                modelList=schoolDB.getsectionOfSchool(attendenceModel.getSubjectid());

                arraySection=new ArrayList<String>();
                for(i=0;i<modelList.size();i++)
                {

                    arraySection.add(modelList.get(i).getSection().toString());
                }

                ArrayAdapter<String> spinnerAdapter1 =
                        new ArrayAdapter<String>(AttandenceSelectionActivity.this, android.R.layout.simple_spinner_item, arraySection);
                spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSection.setAdapter(spinnerAdapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String subSec=spinnerSection.getSelectedItem().toString();
                attendenceModel.setSection(subSec);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AttandenceSelectionActivity.this,AttendenceCalenderActivity.class);
                intent.putExtra("obj",attendenceModel);
                startActivity(intent);
            }
        });



    }
}
