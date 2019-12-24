package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TeacherpanelModel;

public class TdShowNotificationDetailsActivity extends AppCompatActivity {


    Button show_hide;
    TextView title;
    TextView msg;
    TeacherpanelModel teacherpanelModel;
    ImageView edit;
    SchoolDB schoolDB;
    Button delete;
    Boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_notification);

        TextView textView=(TextView)findViewById(R.id.toolbar_title);
        textView.setText("Notification Details");

        schoolDB=new SchoolDB(this);
        teacherpanelModel=(TeacherpanelModel) getIntent().getSerializableExtra("obj");
        show_hide=(Button)findViewById(R.id.show_hide);
        title=(TextView)findViewById(R.id.title_show_tv);
        msg=(TextView)findViewById(R.id.msg_show_tv);
        edit=(ImageView) findViewById(R.id.student_details_edit_btn);
        delete=(Button)findViewById(R.id.delete);



        if(teacherpanelModel.getNotificationStatus()==0)
        {
            show_hide.setText("Hide");
        }
        else {
            show_hide.setText("Show");

        }

        title.setText(teacherpanelModel.getNotificationTitle());
        msg.setText(teacherpanelModel.getNotificationMsg());


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TdShowNotificationDetailsActivity.this, EditNotificationActivity.class);
                intent.putExtra("obj",teacherpanelModel);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check=schoolDB.deleteNotification(teacherpanelModel.getNotificationId());
                if(check==true)
                {
                    Intent intent=new Intent(TdShowNotificationDetailsActivity.this, TdShowNotificationActivity.class);
                    intent.putExtra("obj",teacherpanelModel);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(TdShowNotificationDetailsActivity.this,"Error in deleting",Toast.LENGTH_SHORT).show();
                }

            }
        });

        show_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(show_hide.getText().toString().equals("Show"))
                {
                    teacherpanelModel.setNotificationStatus(0);
                    show_hide.setText("Hide");
                    schoolDB.updateFlagOfNotification(teacherpanelModel.getNotificationId(),teacherpanelModel.getNotificationStatus());
                }
                else
                {
                    teacherpanelModel.setNotificationStatus(1);
                    show_hide.setText("Show");
                    check=schoolDB.updateFlagOfNotification(teacherpanelModel.getNotificationId(),teacherpanelModel.getNotificationStatus());

                    if(check==false)
                    {
                        Toast.makeText(TdShowNotificationDetailsActivity.this,"failed",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(TdShowNotificationDetailsActivity.this, TdShowNotificationActivity.class);
        intent.putExtra("obj",teacherpanelModel);
        startActivity(intent);

    }
}
