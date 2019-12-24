package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.ClassInfoModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

public class AddSection extends AppCompatActivity {

    SchoolDB schoolDB;
    ClassInfoModel classInfoModel;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_section);
        editText=(EditText)findViewById(R.id.room_et);
        final Intent intent=getIntent();
        classInfoModel= (ClassInfoModel) intent.getSerializableExtra("ClassModel");
        schoolDB=new SchoolDB(this);
    }

    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.save:

                    if(editText.getText().toString().isEmpty())
                    {
                        Toast.makeText(AddSection.this," Empty Fields",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        classInfoModel.setClass_sec(editText.getText().toString());
                        Boolean check =schoolDB.addSection(classInfoModel);
                            if(check)
                            {
                                Toast.makeText(AddSection.this," Section Added",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                            {
                                Toast.makeText(AddSection.this," Failure Adding Section",Toast.LENGTH_SHORT).show();

                            }
                    }
                break;

            case R.id.cancel:
                finish();
                break;
        }
    }
}
