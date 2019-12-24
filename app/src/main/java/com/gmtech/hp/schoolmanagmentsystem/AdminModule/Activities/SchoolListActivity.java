package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

public class SchoolListActivity extends AppCompatActivity implements View.OnClickListener {

    public static Context context;
    public SchoolDB schoolDB;
    ListView listView;
    TextView textView;
    List<String> list;
     ArrayAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        schoolDB = new SchoolDB(this);
        list = schoolDB.getAllSchools();

        listView = (ListView) findViewById(R.id.listview);
        textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Schools");


        if (list.size() == 0) {
            Toast.makeText(this, "No School Found", Toast.LENGTH_SHORT).show();
        } else {
           adapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(SchoolListActivity.this, AdminDashboard.class);
                    intent.putExtra("Role", "Main Admin");
                    Integer id = schoolDB.getSchoolid(list.get(i));
                    intent.putExtra("School_id", id);
                    intent.putExtra("School_name", schoolDB.Schoolname(id));
                    startActivity(intent);
                }
            });
        }
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(SchoolListActivity.this, AddSchool.class);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        list = schoolDB.getAllSchools();
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SchoolListActivity.this, AdminDashboard.class);
                intent.putExtra("Role", "Main Admin");
                Integer id = schoolDB.getSchoolid(list.get(i));
                intent.putExtra("School_id", id);
                intent.putExtra("School_name", schoolDB.Schoolname(id));
                startActivity(intent);
            }
        });
    }
}
