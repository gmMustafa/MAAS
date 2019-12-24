package com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_DAO.NotificationModel;

public class ShowNotificationDetails extends AppCompatActivity {
    NotificationModel notificationModel;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notification_details);
            Intent intent=getIntent();
        notificationModel= (NotificationModel) intent.getSerializableExtra("notification");
        textView=(TextView)findViewById(R.id.title_show_tv);
        textView.setText(notificationModel.getTitle());

        textView=(TextView)findViewById(R.id.msg_show_tv);
        textView.setText(notificationModel.getMessage());
    }
}
