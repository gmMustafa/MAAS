package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Adapters.TdStudentAdapter;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TeacherpanelModel;

import java.util.List;

public class TdStudentShowActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TeacherpanelModel teacherpanelModel;
    ListView listView;
    List<TeacherpanelModel> model;
    SchoolDB schoolDB;
    TdStudentAdapter tdStudentAdapter;
    ImageButton imageButton;
    android.support.v7.widget.Toolbar toolbar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        imageButton=(ImageButton)findViewById(R.id.toolbar_image);
        teacherpanelModel=(TeacherpanelModel) getIntent().getSerializableExtra("obj");

        textView=(TextView)findViewById(R.id.toolbar_title);
        textView.setText("Student List");
        toolbar.setBackgroundColor(Color.parseColor("#f3bb11"));


        imageButton.setVisibility(View.GONE);

        schoolDB=new SchoolDB(this);
        model=schoolDB.TdGetStudentToShow(teacherpanelModel);

        tdStudentAdapter=new TdStudentAdapter(this,model);
        listView=(ListView)findViewById(R.id.listview);
        listView.setAdapter(tdStudentAdapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        teacherpanelModel.setStudentFirstName(model.get(i).getStudentFirstName());
        teacherpanelModel.setStudentLastName(model.get(i).getStudentLastName());
        teacherpanelModel.setStudentUserId(model.get(i).getStudentUserId());
        teacherpanelModel.setStudentMobileNumber(model.get(i).getStudentMobileNumber());
        teacherpanelModel.setStudentGender(model.get(i).getStudentGender());
        Intent intent =new Intent(TdStudentShowActivity.this,TdShowStudentDetailsActivity.class);
        intent.putExtra("obj",teacherpanelModel);
        startActivity(intent);
    }
}

