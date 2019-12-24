package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.StudentModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

public class AddStudent extends AppCompatActivity implements View.OnClickListener {


    SchoolDB schoolDB;
    StudentModel studentModel;
    EditText firstName;
    EditText lastName;
    EditText city;
    EditText postal;
    EditText email;
    EditText mobileNo;
    EditText userName;
    EditText password;
    Button save;
    Button cancel;
    Spinner spinner, spinner2;

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
        setContentView(R.layout.activity_add_student);
        Intent intent=getIntent();
        school_id=intent.getStringExtra("SchoolId");
        studentModel=new StudentModel();
        schoolDB=new SchoolDB(this);
        spinner = (Spinner) findViewById(R.id.add_student_gender);
        spinner2 = (Spinner) findViewById(R.id.add_student_class);

        firstName=(EditText)findViewById(R.id.add_student_fname);
        lastName=(EditText)findViewById(R.id.add_student_lname);
        city=(EditText)findViewById(R.id.add_student_city);
        postal=(EditText)findViewById(R.id.add_student_postal);
        email=(EditText)findViewById(R.id.add_student_email);
        mobileNo=(EditText)findViewById(R.id.add_student_mobile);
        userName=(EditText)findViewById(R.id.add_student_user_name);
        password=(EditText)findViewById(R.id.add_student_password);
        save=(Button)findViewById(R.id.save);
        cancel=(Button)findViewById(R.id.cancel);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.classes_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:


                firstNameS=firstName.getText().toString();
                lastNameS=lastName.getText().toString();
                cityS=city.getText().toString();
                postalS=postal.getText().toString();
                emailS=email.getText().toString();
                mobileNoS=mobileNo.getText().toString();
                userNameS=userName.getText().toString();
                passwordS=password.getText().toString();
                genderS=spinner.getSelectedItem().toString();

                if(firstNameS.isEmpty() || lastNameS.isEmpty() || cityS.isEmpty() || postalS.isEmpty()
                        || emailS.isEmpty() || mobileNoS.isEmpty() || userNameS.isEmpty() || passwordS.isEmpty())
                {
                    Toast.makeText(getBaseContext(),"please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else if(isValidEmail(emailS)==false)
                {
                    email.setError("Invalid Email");
                }
                else {
                    genderS=spinner.getSelectedItem().toString();
                    String clas_num=spinner2.getSelectedItem().toString();

                    Boolean check=schoolDB.addStudent(school_id,firstNameS,lastNameS,cityS,postalS,emailS,mobileNoS,userNameS,passwordS,genderS,clas_num);
                    if(check==true)
                    {
                        Toast.makeText(getBaseContext(),"Added",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(getBaseContext(),"Not Added",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.cancel:
                finish();

        }
    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
