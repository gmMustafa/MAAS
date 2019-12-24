package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AdminModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

public class EditAdmin extends AppCompatActivity {


    SchoolDB schoolDB;
    AdminModel adminModel;
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

    String school_id;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher_details);

        Intent intent=getIntent();
        school_id=intent.getStringExtra("School_id");

        textView=(TextView)findViewById(R.id.toolbar_title);

        textView.setText("Edit Admin");
        adminModel=(AdminModel) getIntent().getSerializableExtra("Admin");
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
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        List<String> myOptions = null;
        if (myOptions == null) {
            myOptions = Arrays.asList((getResources().getStringArray(R.array.gender_array)));
        }
        int value = myOptions.indexOf(adminModel.getGender());
        spinner.setSelection(value);

        firstName.setText(adminModel.getFirstName());
        lastName.setText(adminModel.getLastName());
        city.setText(adminModel.getCity());
        postal.setText(adminModel.getPostalCode());
        email.setText(adminModel.getEmail());
        mobileNo.setText(adminModel.getMobileNo());
        userName.setText(adminModel.getUserName());
        password.setText(adminModel.getPassword());

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
                    Toast.makeText(EditAdmin.this,"please fill all fields",Toast.LENGTH_SHORT).show();
                }
                else {

                    genderS=spinner.getSelectedItem().toString();

                    adminModel.setFirstName(firstNameS);
                    adminModel.setLastName(lastNameS);
                    adminModel.setCity(cityS);
                    adminModel.setPostalCode(postalS);
                    adminModel.setEmail(emailS);
                    adminModel.setMobileNo(mobileNoS);
                    adminModel.setUserName(userNameS);
                    adminModel.setPassword(passwordS);
                    adminModel.setGender(genderS);

                    schoolDB=new SchoolDB(EditAdmin.this);
                    Boolean check=schoolDB.updateAdmin(adminModel,school_id);
                    if(check==true)
                    {
                        Toast.makeText(EditAdmin.this,"updated Successfully ",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(EditAdmin.this,"Not updated",Toast.LENGTH_SHORT).show();
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
