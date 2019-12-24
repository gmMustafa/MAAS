package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.TeacherModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;


public class AddTeacher extends AppCompatActivity  {
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
    Button save;
    Button cancel;
    SchoolDB schoolDB;

    String firstNameS;
    String lastNameS;
    String cityS;
    String postalS;
    String emailS;
    String mobileNoS;
    String userNameS;
    String passwordS;
    String genderS;

    String school_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher_details);

        Intent intent=getIntent();
        school_id=intent.getStringExtra("SchoolId");

        teacherModel=new TeacherModel();
        teacherModel.setSchoolId(Integer.parseInt(school_id));
        spinner=(android.support.v7.widget.AppCompatSpinner)findViewById(R.id.spinner);
        firstName=(EditText)findViewById(R.id.teacher_first_name);
        lastName=(EditText)findViewById(R.id.teacher_last_name);
        city=(EditText)findViewById(R.id.teacher_city);
        postal=(EditText)findViewById(R.id.teacher_postal);
        email=(EditText)findViewById(R.id.teacher_email);
        mobileNo=(EditText)findViewById(R.id.teacher_mobile);
        userName=(EditText)findViewById(R.id.teacher_username);
        password=(EditText)findViewById(R.id.teacher_password);
        save=(Button)findViewById(R.id.save);
        cancel=(Button)findViewById(R.id.cancel);

      /*  firstName.setText("hisham");
        lastName.setText("butt");
        city.setText("lahore");
        postal.setText("5400");
        email.setText("hisham@gmail.com");
        mobileNo.setText("03334166913");
        userName.setText("hisham12");
        password.setText("12345"); */


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        save.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(AddTeacher.this,"please fill all fields",Toast.LENGTH_SHORT).show();
                }
                else if(isValidEmail(emailS)==false)
                {
                    email.setError("Invalid Email");
                }
                else {

                    genderS=spinner.getSelectedItem().toString();

                    schoolDB=new SchoolDB(AddTeacher.this);
                    Boolean check=schoolDB.addTeacher(firstNameS,lastNameS,cityS,postalS,emailS,mobileNoS,userNameS,passwordS,genderS,school_id);

                    if(check==true)
                    {
                        Toast.makeText(AddTeacher.this,"Added",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(AddTeacher.this,"Not Added",Toast.LENGTH_SHORT).show();
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
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
