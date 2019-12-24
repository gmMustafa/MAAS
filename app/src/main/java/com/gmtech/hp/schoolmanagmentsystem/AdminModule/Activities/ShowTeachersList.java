package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters.TeacherAdapter;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.TeacherModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

public class ShowTeachersList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView textView;
    ListView listView;
    List<TeacherModel> model;
    SchoolDB schoolDB;
    TeacherAdapter teacherAdapter;
    ImageButton add;

    Integer Schhol_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        add = (ImageButton) findViewById(R.id.toolbar_image);
        textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Teachers");

        Intent intent = getIntent();
        Schhol_id = intent.getIntExtra("School_id", -1);

        schoolDB = new SchoolDB(this);
        model = schoolDB.getTeacher(Schhol_id);

        teacherAdapter = new TeacherAdapter(ShowTeachersList.this, model);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(teacherAdapter);
        listView.setOnItemClickListener(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowTeachersList.this, AddTeacher.class);
                intent.putExtra("SchoolId", Schhol_id.toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(ShowTeachersList.this, ShowTeacherDetails.class);
        model.get(i).setSchoolId(Schhol_id);
        intent.putExtra("model", model.get(i));
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        model = schoolDB.getTeacher(Schhol_id);
        teacherAdapter = new TeacherAdapter(ShowTeachersList.this, model);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(teacherAdapter);
    }
}
