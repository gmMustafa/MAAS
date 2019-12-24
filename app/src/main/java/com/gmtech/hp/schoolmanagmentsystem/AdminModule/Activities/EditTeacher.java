package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.TeacherModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.Arrays;
import java.util.List;


public class EditTeacher extends AppCompatActivity {

    SchoolDB schoolDB;
    TeacherModel teacherModel;
    EditText firstName;
    EditText lastName;
    EditText city;
    EditText postal;
    EditText email;
    EditText mobileNo;
    EditText userName;
    EditText password;
    android.support.v7.widget.AppCompatSpinner spinner;
    Button update;
    Button cancel;

    String firstNameS;
    String lastNameS;
    String cityS;
    String postalS;
    String emailS;
    String mobileNoS;
    String userNameS;
    String passwordS;
    String genderS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher_details);


        teacherModel=(TeacherModel)getIntent().getSerializableExtra("Teacher");
        spinner=(android.support.v7.widget.AppCompatSpinner)findViewById(R.id.spinner);
        firstName=(EditText)findViewById(R.id.teacher_first_name);
        lastName=(EditText)findViewById(R.id.teacher_last_name);
        city=(EditText)findViewById(R.id.teacher_city);
        postal=(EditText)findViewById(R.id.teacher_postal);
        email=(EditText)findViewById(R.id.teacher_email);
        mobileNo=(EditText)findViewById(R.id.teacher_mobile);
        userName=(EditText)findViewById(R.id.teacher_username);
        password=(EditText)findViewById(R.id.teacher_password);
        update=(Button)findViewById(R.id.save);
        cancel=(Button)findViewById(R.id.cancel);
        update.setText("Update");

        TextView textView=(TextView)findViewById(R.id.toolbar_title);
        textView.setText("Edit Teacher");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        List<String> myOptions = null;
        if (myOptions == null) {
            myOptions = Arrays.asList((getResources().getStringArray(R.array.gender_array)));
        }
        int value = myOptions.indexOf(teacherModel.getGender());
        spinner.setSelection(value);

        firstName.setText(teacherModel.getFirstName());
        lastName.setText(teacherModel.getLastName());
        city.setText(teacherModel.getCity());
        postal.setText(teacherModel.getPostal());
        email.setText(teacherModel.getEmail());
        mobileNo.setText(teacherModel.getMobileNo());
        userName.setText(teacherModel.getUserName());
        password.setText(teacherModel.getPassword());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firstNameS=firstName.getText().toString();
                lastNameS=lastName.getText().toString();
                cityS=city.getText().toString();
                postalS=postal.getText().toString();
                emailS=email.getText().toString();
                mobileNoS=mobileNo.getText().toString();
                userNameS=userName.getText().toString();
                passwordS=password.getText().toString();

                if(firstNameS.isEmpty() || lastNameS.isEmpty() || cityS.isEmpty() || postalS.isEmpty()
                        || emailS.isEmpty() || mobileNoS.isEmpty() || userNameS.isEmpty() || passwordS.isEmpty())
                {
                    Toast.makeText(EditTeacher.this,"please fill all fields",Toast.LENGTH_SHORT).show();
                }
                else {

                    genderS=spinner.getSelectedItem().toString();

                    schoolDB=new SchoolDB(EditTeacher.this);
                    Boolean check=schoolDB.updateTeacher(firstNameS,lastNameS,cityS,postalS,emailS,mobileNoS,userNameS,passwordS,genderS,teacherModel.getUserId());

                    if(check==true)
                    {
                        Toast.makeText(EditTeacher.this,"updated",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(EditTeacher.this,"Update Failure",Toast.LENGTH_SHORT).show();
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
}
