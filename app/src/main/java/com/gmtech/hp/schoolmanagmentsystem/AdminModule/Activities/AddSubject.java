package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.SubjectModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

public class AddSubject extends AppCompatActivity implements View.OnClickListener {

    Spinner spinner;
    EditText editText;
    SchoolDB schoolDB;
    Integer Schhol_id;
    SubjectModel subjectModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject_details);

        Intent intent = getIntent();
        Schhol_id = intent.getIntExtra("School_id", -1);
        subjectModel=new SubjectModel();
        schoolDB=new SchoolDB(this);

        editText=(EditText)findViewById(R.id.room_et);
        spinner = (Spinner) findViewById(R.id.spinner_class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.classes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                    if(editText.getText().toString().isEmpty())
                    {
                        Toast.makeText(AddSubject.this,"Empty Fields",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        subjectModel.setClass_number(Integer.parseInt(spinner.getSelectedItem().toString()));
                        subjectModel.setSubject_name(editText.getText().toString());
                        if(schoolDB.addSubject(subjectModel,Schhol_id))
                        {
                            Toast.makeText(AddSubject.this,"Subject Added",Toast.LENGTH_SHORT).show();
                                finish();
                        }
                        else
                        {
                            Toast.makeText(AddSubject.this,"Failure Adding Subject",Toast.LENGTH_SHORT).show();
                        }
                    }
                break;
            case R.id.cancel:
                finish();

        }
    }
}
