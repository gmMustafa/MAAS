package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.SchoolModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

public class AddSchool extends AppCompatActivity implements View.OnClickListener {

    SchoolDB schoolDB;
    SchoolModel schoolModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_school);
        schoolDB = new SchoolDB(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.save:
                schoolModel=new SchoolModel();
                EditText editText = (EditText) findViewById(R.id.school_name);
                schoolModel.setSchoolName(editText.getText().toString());
                EditText editText2 = (EditText) findViewById(R.id.school_address);
                schoolModel.setSchoolAdress(editText2.getText().toString());
                EditText editText3 = (EditText) findViewById(R.id.school_phone_no);
                schoolModel.setPhoneNO(editText3.getText().toString());

                if(editText.getText().toString().isEmpty()
                     || editText2.getText().toString().isEmpty() || editText3.getText().toString().isEmpty())
                {
                    Toast.makeText(this,"Empty Field",Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if( schoolDB.addSchool(schoolModel))
                    {
                        Toast.makeText(this,"School Added",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(this,"Failure Adding School",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.cancel:
                finish();

        }
    }
}
