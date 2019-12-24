package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.StudentModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.Arrays;
import java.util.List;


public class EditStudent extends AppCompatActivity implements View.OnClickListener{
    TextView textView;
    EditText editText;
    Spinner spinner;
    Spinner spinner2;
    StudentModel studentModel;


    EditText firstName;
    EditText lastName;
    EditText city;
    EditText postal;
    EditText email;
    EditText mobileNo;
    EditText userName;
    EditText password;

    String firstNameS;
    String lastNameS;
    String cityS;
    String postalS;
    String emailS;
    String mobileNoS;
    String userNameS;
    String passwordS;
    String genderS;

    SchoolDB schoolDB;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        Intent intent=getIntent();
         studentModel = (StudentModel) intent.getSerializableExtra("Student");
         schoolDB = new SchoolDB(this);

        textView=(TextView) findViewById(R.id.toolbar_title);
        textView.setText("Edit Details");

        userName=(EditText)findViewById(R.id.add_student_user_name);
        userName.setText(studentModel.getUserName(), TextView.BufferType.EDITABLE);

        password=(EditText)findViewById(R.id.add_student_password);
        password.setInputType(InputType.TYPE_CLASS_TEXT);
        password.setText(studentModel.getPassword(), TextView.BufferType.EDITABLE);


        firstName=(EditText)findViewById(R.id.add_student_fname);
        firstName.setText(studentModel.getfName(), TextView.BufferType.EDITABLE);

        lastName=(EditText)findViewById(R.id.add_student_lname);
        lastName.setText(studentModel.getlName(), TextView.BufferType.EDITABLE);


        spinner=(Spinner)findViewById(R.id.add_student_gender);
        spinner2=(Spinner)findViewById(R.id.add_student_class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        List<String> myOptions = null;
        if (myOptions == null) {
            myOptions = Arrays.asList((getResources().getStringArray(R.array.gender_array)));
        }
        int value = myOptions.indexOf(studentModel.getGender());
        spinner.setSelection(value);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.classes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        myOptions = null;
        if (myOptions == null) {
            myOptions = Arrays.asList((getResources().getStringArray(R.array.classes_array)));
        }
        value = myOptions.indexOf(studentModel.getClass_num().toString());
        spinner2.setSelection(value);

        mobileNo=(EditText)findViewById(R.id.add_student_mobile);
        mobileNo.setText(studentModel.getMobile_no(), TextView.BufferType.EDITABLE);


        city=(EditText)findViewById(R.id.add_student_city);
        city.setText(studentModel.getCity(), TextView.BufferType.EDITABLE);


        postal=(EditText)findViewById(R.id.add_student_postal);
        postal.setText(studentModel.getPostal(), TextView.BufferType.EDITABLE);


        email=(EditText)findViewById(R.id.add_student_email);
        email.setText(studentModel.getEmail(), TextView.BufferType.EDITABLE);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
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
                else {
                    genderS=spinner.getSelectedItem().toString();
                    String clas_num=spinner2.getSelectedItem().toString();

                    Boolean check=schoolDB.updateStudent(studentModel.getSchool_id().toString(),firstNameS,lastNameS,cityS,postalS,emailS,mobileNoS,userNameS,passwordS,genderS,clas_num,
                            studentModel.getUser_id().toString(),studentModel.getS_id().toString());
                    if(check==true)
                    {
                        Toast.makeText(getBaseContext(),"Updated",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(getBaseContext(),"Updated Failure",Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case  R.id.cancel:
                finish();

        }
    }
}
