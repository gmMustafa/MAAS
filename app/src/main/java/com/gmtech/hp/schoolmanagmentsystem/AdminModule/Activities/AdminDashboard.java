package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.StartUpPackage.LoginActivity;
import com.gmtech.hp.schoolmanagmentsystem.StartUpPackage.SelectionLogin;

public class AdminDashboard extends AppCompatActivity implements View.OnClickListener {

    SchoolDB schoolDB;
    Integer Schhol_id;
    TextView textView;
    LinearLayout layout;
    Integer counter=0;
    String Role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        textView = (TextView) findViewById(R.id.toolbar_title);
        final Intent intent = getIntent();

         Role = intent.getStringExtra("Role");
        if (Role.equals("Main Admin")) {
            schoolDB =new SchoolDB(this);
            Schhol_id = intent.getIntExtra("School_id", -1);

            Integer v=schoolDB.checkAllocated(Schhol_id);
            if(v==0)
            {

                 layout = (LinearLayout) findViewById(R.id.admin_db_row_Four);
                layout.setVisibility(View.VISIBLE);
                TextView textView=(TextView)findViewById(R.id.admin_cc);
                textView.setText(" Add Admin ");
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1=new Intent(AdminDashboard.this,AddAdmin.class);
                        intent1.putExtra("School_id", Schhol_id.toString());
                        counter=1;
                        startActivity(intent1);
                    }
                });

            }
            else
            {
                 layout = (LinearLayout) findViewById(R.id.admin_db_row_Four);
                layout.setVisibility(View.VISIBLE);
                TextView textView=(TextView)findViewById(R.id.admin_cc);
                textView.setText(" Admin ");
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1=new Intent(AdminDashboard.this,ShowAdminDetails.class);
                        intent1.putExtra("School_id",Schhol_id.toString() );
                        counter=1;
                        startActivity(intent1);
                    }
                });
            }//check assign
            textView.setText(intent.getStringExtra("School_name"));
        } else if (Role.equals("Sub Admin")) {
             layout = (LinearLayout) findViewById(R.id.admin_db_row_Four);
            layout.setVisibility(View.GONE);
            Schhol_id = intent.getIntExtra("School_id", -1);
            textView.setText(intent.getStringExtra("School_name"));
        }

    }

    @Override
    public void onClick(View view) {
        Intent go;
        switch (view.getId()) {

            case R.id.op_students:
                go = new Intent(AdminDashboard.this, ShowStudentList.class);
                go.putExtra("School_id", Schhol_id);
                startActivity(go);
                break;
            case R.id.op_teachers:
                go = new Intent(AdminDashboard.this, ShowTeachersList.class);
                go.putExtra("School_id", Schhol_id);
                startActivity(go);
                break;
            case R.id.op_Room:
                go = new Intent(AdminDashboard.this, ShowRoomList.class);
                go.putExtra("School_id", Schhol_id);
                startActivity(go);
                break;
            case R.id.op_attendance:
                go=new Intent(this,AttandenceSelectionActivity.class);
                go.putExtra("School_id", Schhol_id);
                startActivity(go);
                break;
            case R.id.op_subjects:
                go = new Intent(AdminDashboard.this, ShowSubjectList.class);
                go.putExtra("School_id", Schhol_id);
                startActivity(go);
                break;
            case R.id.op_class_room:
                go = new Intent(AdminDashboard.this, ShowClasses.class);
                go.putExtra("School_id", Schhol_id);
                startActivity(go);

                break;
            case R.id.op_add_holiday:
                go = new Intent(AdminDashboard.this, HolidaysActivities.class);
                go.putExtra("School_id", Schhol_id);
                startActivity(go);
                break;
            case R.id.button2:
                if (Role.equals("Main Admin")) {
                        Intent intent=new Intent(AdminDashboard.this,SelectionLogin.class);
                        startActivity(intent);
                            ((Activity)SchoolListActivity.context).finish();
                           // ((Activity)SelectionLogin.context).finish();
                            ((Activity) LoginActivity.context).finish();
                    finish();
                }else{finish();}

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(counter==1)
        {
            finish();
        }
    }

}
