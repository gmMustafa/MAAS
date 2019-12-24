package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters.SubjectListAdapter;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.SubjectModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

public class ShowSubjectList extends AppCompatActivity implements  View.OnClickListener{

    public SchoolDB schoolDB;
    ListView listView;
    TextView textView;
    List<SubjectModel> list;
    Integer s_id;
    SubjectListAdapter subjectListAdapter;
    SubjectModel subjectModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent=getIntent();
        s_id=intent.getIntExtra("School_id",-1);

        schoolDB=new SchoolDB(this);
        list=schoolDB.getAllSubjects(s_id.toString());
        listView=(ListView) findViewById(R.id.listview);
        textView=(TextView)findViewById(R.id.toolbar_title);
        textView.setText("Subjects");


        if(list.size()==0)
        {
            Toast.makeText(this,"No Subject Found",Toast.LENGTH_SHORT).show();
        }
        else
        {
             subjectListAdapter=new SubjectListAdapter(list,this);
            listView.setAdapter(subjectListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    subjectModel=new SubjectModel();
                    subjectModel.setSubject_id(list.get(i).getSubject_id());
                    subjectModel.setSubject_name(list.get(i).getSubject_name());
                    subjectModel.setClass_number(list.get(i).getClass_number());
                    Intent intent1=new Intent(ShowSubjectList.this,ShowSubjectDetails.class);
                    intent1.putExtra("model",subjectModel);
                    intent1.putExtra("School_id",s_id);
                    startActivity(intent1);}
            });
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_image:
                Intent intent=new Intent(ShowSubjectList.this,AddSubject.class);
                intent.putExtra("School_id",s_id);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        list=schoolDB.getAllSubjects(s_id.toString());
        subjectListAdapter=new SubjectListAdapter(list,this);
        listView.setAdapter(subjectListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                subjectModel=new SubjectModel();
                subjectModel.setSubject_id(list.get(i).getSubject_id());
                subjectModel.setSubject_name(list.get(i).getSubject_name());
                subjectModel.setClass_number(list.get(i).getClass_number());
                Intent intent1=new Intent(ShowSubjectList.this,ShowSubjectDetails.class);
                intent1.putExtra("model",subjectModel);
                intent1.putExtra("School_id",s_id);
                startActivity(intent1);}
        });
    }

}
