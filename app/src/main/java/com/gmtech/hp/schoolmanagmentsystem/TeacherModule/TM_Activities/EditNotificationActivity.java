package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TeacherpanelModel;

public class EditNotificationActivity extends AppCompatActivity {

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
        textView.setText("Edit Notification");

        schoolDB=new SchoolDB(this);
        teacherpanelModel=(TeacherpanelModel) getIntent().getSerializableExtra("obj");
        title=(EditText)findViewById(R.id.notification_title);
        msg=(EditText)findViewById(R.id.notification_msg);
        save=(Button)findViewById(R.id.save);
        show_hide=(Button)findViewById(R.id.show_hide);
        cancel=(Button)findViewById(R.id.cancel);
        title.setText(teacherpanelModel.getNotificationTitle());
        msg.setText(teacherpanelModel.getNotificationMsg());

        if(teacherpanelModel.getNotificationStatus()==0)
        {
            show_hide.setText("Hide");
        }
        else {
            show_hide.setText("Show");

        }


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
                    Toast.makeText(EditNotificationActivity.this,"Please Fill All fields",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    teacherpanelModel.setNotificationTitle(title.getText().toString());
                    teacherpanelModel.setNotificationMsg(msg.getText().toString());
                    Boolean check=schoolDB.updateNotification(teacherpanelModel);
                    if(check==true){
                        Intent intent=new Intent(EditNotificationActivity.this, TdShowNotificationActivity.class);
                        intent.putExtra("obj",teacherpanelModel);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(EditNotificationActivity.this,"Error in Updating",Toast.LENGTH_SHORT).show();

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
