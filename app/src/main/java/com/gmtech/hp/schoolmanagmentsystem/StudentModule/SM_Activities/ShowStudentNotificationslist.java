package com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.StudentModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_DAO.NotificationModel;

import java.util.ArrayList;
import java.util.List;

public class ShowStudentNotificationslist extends AppCompatActivity {

    SchoolDB schoolDB;
    List<NotificationModel> list;
    StudentModel studentModel;
    ListView listView;
    NotificationModel notificationModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_notifications);
        schoolDB = new SchoolDB(this);
        studentModel = (StudentModel) getIntent().getSerializableExtra("Student");

        list = schoolDB.getAllNotificationsForStudents(studentModel);
        listView = (ListView) findViewById(R.id.listview);

        if(list!=null)
        {
            List<String> a = new ArrayList<>();
            for (int i = 0; i < list.size(); i++){
                a.add(list.get(i).getTitle());
            }

            String[] arr = new String[a.size()];
            arr = a.toArray(arr);

            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
            arrayAdapter.addAll(arr);
            listView.setAdapter(arrayAdapter);

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ShowStudentNotificationslist.this, ShowNotificationDetails.class);
                notificationModel = new NotificationModel();
                notificationModel = list.get(i);
                intent.putExtra("notification", notificationModel);
                startActivity(intent);
            }
        });
    }




}
