package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AdminModel;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AdminModelHelper;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TeacherpanelModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddAssignmentActivity extends AppCompatActivity {
    TeacherpanelModel teacherpanelModel;
    SchoolDB schoolDB;
    EditText title;
    EditText des;
    EditText totalMarks;
    EditText deadline;
    TextView deadline_tv;
    Spinner type;
    Button save;
    Button cancel;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_assignment_detail);

        TextView textView=(TextView)findViewById(R.id.toolbar_title);
        textView.setText("Add Assignment");

        myCalendar = Calendar.getInstance();
        schoolDB=new SchoolDB(this);
        teacherpanelModel=(TeacherpanelModel) getIntent().getSerializableExtra("obj");
        title=(EditText)findViewById(R.id.title_show_tv);
        des=(EditText)findViewById(R.id.description_show_tv);
        totalMarks=(EditText)findViewById(R.id.total_marks_show_tv);
        deadline=(EditText)findViewById(R.id.deadline_show_tv);
        type=(Spinner) findViewById(R.id.spinner_type);
        save=(Button) findViewById(R.id.save);
        cancel=(Button)findViewById(R.id.cancel);
        deadline_tv=(TextView)findViewById(R.id.deadline_tv);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.assignment_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        deadline.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddAssignmentActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String t=type.getSelectedItem().toString();
                if(t.equals("Assignment")){
                    deadline.setVisibility(View.VISIBLE);
                    deadline_tv.setVisibility(View.VISIBLE);
                }
                else {
                    deadline.setVisibility(View.INVISIBLE);
                    deadline_tv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(type.getSelectedItem().toString().equals("Assignment"))
                {

                    if(title.getText().toString().isEmpty() || des.getText().toString().isEmpty()
                            || totalMarks.getText().toString().isEmpty()|| deadline.getText().toString().isEmpty())
                    {
                        Toast.makeText(AddAssignmentActivity.this,"Please Fill All fields",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        teacherpanelModel.setAssignmentTitle(title.getText().toString());
                        teacherpanelModel.setAssassignmentDes(des.getText().toString());
                        teacherpanelModel.setAssignmenttotalMarks(Integer.parseInt(totalMarks.getText().toString()));
                        teacherpanelModel.setAssignmentDeadLine(deadline.getText().toString());
                        teacherpanelModel.setAssignmenttype(type.getSelectedItem().toString());

                        Boolean check=schoolDB.addAssignment(teacherpanelModel, AdminModelHelper.Admin_id);
                        if(check==true){
                            Intent intent=new Intent(AddAssignmentActivity.this, TdShowAssignmentActivity.class);
                            intent.putExtra("obj",teacherpanelModel);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(AddAssignmentActivity.this,"Error in Updating",Toast.LENGTH_SHORT).show();

                        }
                    }


                }

                else {

                    if(title.getText().toString().isEmpty() || des.getText().toString().isEmpty()
                            || totalMarks.getText().toString().isEmpty())
                    {
                        Toast.makeText(AddAssignmentActivity.this,"Please Fill All fields",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        teacherpanelModel.setAssignmentTitle(title.getText().toString());
                        teacherpanelModel.setAssassignmentDes(des.getText().toString());
                        teacherpanelModel.setAssignmenttotalMarks(Integer.parseInt(totalMarks.getText().toString()));
                        teacherpanelModel.setAssignmentDeadLine("NILL");
                        teacherpanelModel.setAssignmenttype(type.getSelectedItem().toString());

                        Boolean check=schoolDB.addAssignment(teacherpanelModel,AdminModelHelper.Admin_id);
                        if(check==true){
                            Intent intent=new Intent(AddAssignmentActivity.this, TdShowAssignmentActivity.class);
                            intent.putExtra("obj",teacherpanelModel);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(AddAssignmentActivity.this,"Error in Updating",Toast.LENGTH_SHORT).show();

                        }
                    }


                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        deadline.setText(sdf.format(myCalendar.getTime()));
    }


}
