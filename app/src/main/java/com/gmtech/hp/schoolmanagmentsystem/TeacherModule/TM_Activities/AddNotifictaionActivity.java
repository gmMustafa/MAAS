package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AdminModelHelper;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TeacherpanelModel;

public class AddNotifictaionActivity extends AppCompatActivity {

    TeacherpanelModel teacherpanelModel;
    SchoolDB schoolDB;

    EditText title;
    EditText msg;
    Button save;
    Button show_hide;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_notification);

        TextView textView=(TextView)findViewById(R.id.toolbar_title);
        textView.setText("Add Notification");

        schoolDB=new SchoolDB(this);
        teacherpanelModel=(TeacherpanelModel) getIntent().getSerializableExtra("obj");
        title=(EditText)findViewById(R.id.notification_title);
        msg=(EditText)findViewById(R.id.notification_msg);
        save=(Button)findViewById(R.id.save);
        show_hide=(Button)findViewById(R.id.show_hide);
        teacherpanelModel.setNotificationStatus(0);
        cancel=(Button)findViewById(R.id.cancel);

        teacherpanelModel.setNotificationStatus(1);
        show_hide.setText("Show");

        show_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(show_hide.getText().toString().equals("Show"))
                {
                    teacherpanelModel.setNotificationStatus(0);
                    show_hide.setText("Hide");
                }
                else
                {
                    teacherpanelModel.setNotificationStatus(1);
                    show_hide.setText("Show");
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(title.getText().toString().isEmpty() || msg.getText().toString().isEmpty())
                {
                    Toast.makeText(AddNotifictaionActivity.this,"Please Fill All fields",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    teacherpanelModel.setNotificationTitle(title.getText().toString());
                    teacherpanelModel.setNotificationMsg(msg.getText().toString());
                    Integer id=teacherpanelModel.getNotificationStatus();
                    Boolean check=schoolDB.addNotification(teacherpanelModel, AdminModelHelper.Admin_id);
                    if(check==true){
                        Intent intent=new Intent(AddNotifictaionActivity.this, TdShowNotificationActivity.class);
                        intent.putExtra("obj",teacherpanelModel);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(AddNotifictaionActivity.this,"Error in Inserting", Toast.LENGTH_SHORT).show();
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
