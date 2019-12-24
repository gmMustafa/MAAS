package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.SubjectModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.Arrays;
import java.util.List;

public class EditSubject extends AppCompatActivity implements View.OnClickListener{


    EditText editText;
    Spinner spinner;

    SchoolDB schoolDB;
    SubjectModel subjectModel;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject_details);
        Intent intent=getIntent();
        subjectModel= (SubjectModel) intent.getSerializableExtra("model");
        schoolDB=new SchoolDB(this);
        editText=(EditText) findViewById(R.id.room_et);
        editText.setText(subjectModel.getSubject_name().toString(), TextView.BufferType.EDITABLE);
        save=(Button)findViewById(R.id.save);
        save.setText("Update");
        TextView   textView=(TextView)findViewById(R.id.toolbar_title);
        textView.setText("Edit Subject");

        spinner=(Spinner)findViewById(R.id.spinner_class);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.classes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        List<String> myOptions = null;
        if (myOptions == null) {
            myOptions = Arrays.asList((getResources().getStringArray(R.array.classes_array)));
        }
        int value = myOptions.indexOf(subjectModel.getClass_number().toString());
        spinner.setSelection(value);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                if(editText.getText().toString().isEmpty())
                {
                    Toast.makeText(EditSubject.this,"PLease Enter a Subject",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    subjectModel.setClass_number(Integer.parseInt(spinner.getSelectedItem().toString()));
                    subjectModel.setSubject_name(editText.getText().toString());
                    if(schoolDB.updateSubject(subjectModel))
                    {
                        Toast.makeText(EditSubject.this,"Subject Updated",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(EditSubject.this,"Update Failure",Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.cancel:
                finish();

        }
    }
}
