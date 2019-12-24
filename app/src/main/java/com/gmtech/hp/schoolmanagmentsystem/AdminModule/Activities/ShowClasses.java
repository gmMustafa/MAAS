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

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.ClassInfoModel;
import com.gmtech.hp.schoolmanagmentsystem.R;

public class ShowClasses extends AppCompatActivity {

    ImageButton imageButton;
    TextView textView;
    ClassInfoModel classInfoModel;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);




            final Intent intent=getIntent();

        textView=(TextView)findViewById(R.id.toolbar_title);
        textView.setText("Select Class");
        classInfoModel=new ClassInfoModel();
        classInfoModel.setSchool_id(intent.getIntExtra("School_id",-1));

        imageButton=(ImageButton)findViewById(R.id.toolbar_image);
        imageButton.setVisibility(View.INVISIBLE);

        lv = (ListView) findViewById(R.id.listview);
        final String[] list =getResources().getStringArray(R.array.classes_array);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, list );

        lv.setAdapter(arrayAdapter);
           lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    classInfoModel.setClass_num(Integer.parseInt(list[i]));
                    Intent intent1=new Intent(ShowClasses.this,ShowClassesSubjects.class);
                    intent1.putExtra("classModel",classInfoModel);
                    startActivity(intent1);
                }
            });
    }
}
