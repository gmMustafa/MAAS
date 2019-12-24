package com.gmtech.hp.schoolmanagmentsystem.StartUpPackage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities.AdminDashboard;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities.SchoolListActivity;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_Activities.StudentDashBoardActivity;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Activities.ActivityTeacherDashboard;

public class LoginActivity extends AppCompatActivity {

    public static Context context;
    SchoolDB schoolDB;
    ImageView imageView;
    TextView textView;
    LinearLayout linearLayout;
    android.support.v7.widget.Toolbar toolbar;
    Button button;

    Integer counter=0;

    SharedPreferences sharedPreferences;
    EditText userName ;
    EditText passWord ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_login);
        schoolDB = new SchoolDB(this);


        final Intent intent=getIntent();
        final String oldRole=intent.getStringExtra("Role");

        userName = (EditText) findViewById(R.id.login_username);
        passWord = (EditText) findViewById(R.id.login_password);

   // if(intent.getBooleanExtra("check",false)==false) {
        sharedPreferences = getSharedPreferences("file", MODE_PRIVATE);
        if (sharedPreferences.getInt("DemoDataInsertion", -1) == 1) {
            counter = 1;
        }

        if (counter == 0) {
            sharedPreferences = getSharedPreferences("file", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("DemoDataInsertion", 1);
           // schoolDB.DemoDataInsertion();
           schoolDB.DemoDataInsertion2();
            editor.commit();
        }
    //}

        imageView = (ImageView) findViewById(R.id.login_user_img);
        textView = (TextView) findViewById(R.id.login_user_name);
        linearLayout = (LinearLayout) findViewById(R.id.login_user_panel);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        button = (Button) findViewById(R.id.login_btn);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (userName.getText().toString().isEmpty() || passWord.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), "Invalid Entries", Toast.LENGTH_SHORT).show();
                } else {
                    //sqLiteDatabase.getALL();
                    String Role = schoolDB.CheckLogin(userName.getText().toString(), passWord.getText().toString());
                    if (Role == null) {
                        Toast.makeText(getBaseContext(), "Invalid Details", Toast.LENGTH_SHORT).show();
                    } else if (Role.equals("Main Admin") && oldRole.equals("Admin")) {
                        Intent intent1 = new Intent(LoginActivity.this, SchoolListActivity.class);
                        startActivity(intent1);
                    } else if (Role.equals("Sub Admin") && oldRole.equals("Admin"))  {
                        Integer id = schoolDB.getSchoolidforSubAdmin(userName.getText().toString());
                        Intent intent1 = new Intent(LoginActivity.this, AdminDashboard.class);
                        intent1.putExtra("Role", "Sub Admin");
                        intent1.putExtra("School_id", id);
                        intent1.putExtra("School_name", schoolDB.Schoolname(id));
                        startActivity(intent1);
                    } else if (Role.equals("Teacher")  && oldRole.equals("Teacher")) {
                        Intent intent1 = new Intent(LoginActivity.this, ActivityTeacherDashboard.class);
                        startActivity(intent1);
                    } else if (Role.equals("Student") && oldRole.equals("Student")) {
                        Intent intent1 = new Intent(LoginActivity.this, StudentDashBoardActivity.class);
                        intent1.putExtra("userName",userName.getText().toString());
                        startActivity(intent1);
                    } else {
                        Toast.makeText(getBaseContext(), "Invalid Entries", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        switch (oldRole) {
            case "Admin":
                textView.setText("Admin");
                imageView.setImageResource(R.drawable.admin_avatar);
                linearLayout.setBackgroundColor(getResources().getColor(R.color.panelAdminColor));
                toolbar.setBackgroundColor(getResources().getColor(R.color.panelAdminColor));
                button.setBackgroundColor(getResources().getColor(R.color.panelAdminColor));
                break;
            case "Student":
                textView.setText("Student");
                imageView.setImageResource(R.drawable.student_avatar);
                linearLayout.setBackgroundColor(getResources().getColor(R.color.panelStudentColor));
                toolbar.setBackgroundColor(getResources().getColor(R.color.panelStudentColor));
                button.setBackgroundColor(getResources().getColor(R.color.panelStudentColor));
                break;
            case "Teacher":
                textView.setText("Teacher");
                imageView.setImageResource(R.drawable.teacher_avatar);
                linearLayout.setBackgroundColor(getResources().getColor(R.color.panelTeacherColor));
                toolbar.setBackgroundColor(getResources().getColor(R.color.panelTeacherColor));
                button.setBackgroundColor(getResources().getColor(R.color.panelTeacherColor));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
         userName.setText("");
        passWord.setText("");
    }
}
