package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AdminModel;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AdminModelHelper;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Adapters.TdNotificationAdapter;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TeacherpanelModel;

import java.util.List;

public class TdShowNotificationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TeacherpanelModel teacherpanelModel;
    ListView listView;
    List<TeacherpanelModel> model;
    SchoolDB schoolDB;
    TdNotificationAdapter tdNotificationAdapter;
    ImageButton imageButton;
    android.support.v7.widget.Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        imageButton=(ImageButton)findViewById(R.id.toolbar_image);
        teacherpanelModel=(TeacherpanelModel) getIntent().getSerializableExtra("obj");

        toolbar.setBackgroundColor(Color.parseColor("#F39C11"));

        schoolDB=new SchoolDB(this);
        model=schoolDB.TdGetNOtificationToShow(teacherpanelModel, AdminModelHelper.Admin_id);

        tdNotificationAdapter=new TdNotificationAdapter(this,model);
        listView=(ListView)findViewById(R.id.listview);
        listView.setAdapter(tdNotificationAdapter);
        listView.setOnItemClickListener(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(TdShowNotificationActivity.this,AddNotifictaionActivity.class);
                intent.putExtra("obj",teacherpanelModel);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        teacherpanelModel.setNotificationId(model.get(i).getNotificationId());
        teacherpanelModel.setNotificationTitle(model.get(i).getNotificationTitle());
        teacherpanelModel.setNotificationMsg(model.get(i).getNotificationMsg());
        teacherpanelModel.setNotificationStatus(model.get(i).getNotificationStatus());
        Intent intent=new Intent(TdShowNotificationActivity.this,TdShowNotificationDetailsActivity.class);
        intent.putExtra("obj",teacherpanelModel);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
            model=schoolDB.TdGetNOtificationToShow(teacherpanelModel,AdminModelHelper.Admin_id);
            tdNotificationAdapter=new TdNotificationAdapter(this,model);
            listView.setAdapter(tdNotificationAdapter);

    }
}
