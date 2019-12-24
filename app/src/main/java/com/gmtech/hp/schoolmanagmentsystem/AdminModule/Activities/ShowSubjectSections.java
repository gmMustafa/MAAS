package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.ClassInfoModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.ArrayList;
import java.util.List;

public class ShowSubjectSections extends AppCompatActivity {

    SchoolDB schoolDB;
    ImageButton imageButton;
    TextView textView;
    ClassInfoModel classInfoModel;
    ListView lv;
    List<ClassInfoModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        schoolDB=new SchoolDB(this);

        final Intent intent=getIntent();
        textView=(TextView)findViewById(R.id.toolbar_title);
        textView.setText("Select Section");
        classInfoModel= (ClassInfoModel) intent.getSerializableExtra("classModel");

        imageButton=(ImageButton)findViewById(R.id.toolbar_image);
        imageButton.setVisibility(View.VISIBLE);


        list=schoolDB.getSectionsList(classInfoModel);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(ShowSubjectSections.this,AddSection.class);
                intent1.putExtra("ClassModel",classInfoModel);
                startActivity(intent1);

            }
        });




        List<String> show=new ArrayList<String>();
        if(list!=null) {
            for (int i = 0; i < list.size(); i++) {
                show.add(list.get(i).getClass_sec());
            }
        }
        else
        {
            Toast.makeText(this,"No Section Found.",Toast.LENGTH_SHORT).show();
        }

        lv = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, show);

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                list = schoolDB.getSectionsList(classInfoModel);
                classInfoModel.setClass_sec(list.get(i).getClass_sec());
                classInfoModel.setClass_info_id(list.get(i).getClass_info_id());
                 Intent intent1=new Intent(ShowSubjectSections.this,ShowClassAssignDetails.class);
                intent1.putExtra("ClassModel",classInfoModel);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = schoolDB.getSectionsList(classInfoModel);
        List<String> show = new ArrayList<String>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                show.add(list.get(i).getClass_sec());
            }
        } else {
            Toast.makeText(this, "No Section Found.", Toast.LENGTH_SHORT).show();
        }
        lv = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, show);
        lv.setAdapter(arrayAdapter);
    }
}
