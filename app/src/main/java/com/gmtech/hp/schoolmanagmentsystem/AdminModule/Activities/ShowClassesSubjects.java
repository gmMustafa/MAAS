package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters.SubjectListAdapter;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.ClassInfoModel;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.SubjectModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

public class ShowClassesSubjects extends AppCompatActivity {
    SchoolDB schoolDB;
    ImageButton imageButton;
    TextView textView;
    ClassInfoModel classInfoModel;
    List<SubjectModel> Info_list;
    ListView lv;

    SubjectListAdapter subjectListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        schoolDB=new SchoolDB(this);
        final Intent intent=getIntent();
        textView=(TextView)findViewById(R.id.toolbar_title);
        textView.setText("Select Subject");
        classInfoModel= (ClassInfoModel) intent.getSerializableExtra("classModel");
        Info_list=schoolDB.getClassSubjects(classInfoModel.getSchool_id().toString(),classInfoModel.getClass_num().toString());
        imageButton=(ImageButton)findViewById(R.id.toolbar_image);
        imageButton.setVisibility(View.INVISIBLE);
        lv=(ListView) findViewById(R.id.listview);
        subjectListAdapter=new SubjectListAdapter(Info_list,this);
        lv.setAdapter(subjectListAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                classInfoModel.setSubject_id(Info_list.get(i).getSubject_id());
                classInfoModel.setClass_num(Info_list.get(i).getClass_number());
                Intent intent1=new Intent(ShowClassesSubjects.this,ShowSubjectSections.class);
                intent1.putExtra("classModel",classInfoModel);
                startActivity(intent1);
            }
        });
    }
}
