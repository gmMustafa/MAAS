package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Adapters.TdAddMarksAdapter;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.MarksModel;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TeacherpanelModel;

import java.util.ArrayList;
import java.util.List;

public class TdAddMarkdActivity extends AppCompatActivity {


    TeacherpanelModel teacherpanelModel;
    ListView listView;
    List<TeacherpanelModel> model;
    List<MarksModel> listMarksModels;
    MarksModel marksModel;
    SchoolDB schoolDB;
    TdAddMarksAdapter tdAddMarksAdapter;
    ImageButton save;
    android.support.v7.widget.Toolbar toolbar;
    List<Float> tempMarksList;

    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        listMarksModels=new ArrayList<MarksModel>();
        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        save=(ImageButton)findViewById(R.id.toolbar_image);
        teacherpanelModel=(TeacherpanelModel) getIntent().getSerializableExtra("obj");

        save.setImageResource(R.drawable.save);
        toolbar.setBackgroundColor(Color.parseColor("#F39C11"));

        schoolDB=new SchoolDB(this);

        model=schoolDB.TdGetStudentToShow(teacherpanelModel);

        for(i=0;i<model.size();i++)
        {
            marksModel=new MarksModel();
            marksModel.setStudentId(schoolDB.getStudentId(model.get(i).getStudentUserId()));
            Float marks=schoolDB.getMarks(teacherpanelModel.getAssignmentId(),marksModel.getStudentId());
            if(marks==-1)
            {
                marksModel.setMarks((float)0);
            }
            else {
                marksModel.setMarks(marks);
            }
            marksModel.setAssignmentId(teacherpanelModel.getAssignmentId());
            marksModel.setStudentName(model.get(i).getStudentFirstName()+" "+model.get(i).getStudentLastName());
            marksModel.setTotalMarks(teacherpanelModel.getAssignmenttotalMarks());
            listMarksModels.add(marksModel);
        }

        if(i==0)
        {
            Toast.makeText(this,"No Student Found",Toast.LENGTH_SHORT).show();
        }

        else {
            tdAddMarksAdapter=new TdAddMarksAdapter(this,listMarksModels);
            listView=(ListView)findViewById(R.id.listview);
            listView.setAdapter(tdAddMarksAdapter);
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count=listView.getAdapter().getCount();
                Boolean check;

                Float n;
                for(i=0;i<count;i++)
                {
                    EditText editText =listView.getChildAt(i).findViewById(R.id.marks);
                    TextView textView=listView.getChildAt(i).findViewById(R.id.totalmarks);
                    String a=textView.getText().toString();
                    Integer t=Integer.parseInt(a.substring(1));
                    if(editText.getText().toString().isEmpty())
                    {
                        n=Float.valueOf("0");
                    }
                    else
                    {
                        n=Float.parseFloat(editText.getText().toString());
                    }

                    if(n>t)
                    {
                        listMarksModels.get(i).setMarks(Float.parseFloat(t.toString()));

                    }
                    else {
                        listMarksModels.get(i).setMarks(n);
                    }
                    check=schoolDB.updateMarks(listMarksModels.get(i));
                    if(check==false)
                    {
                        check=schoolDB.addMarks(listMarksModels.get(i));
                        if(check==false)
                        {
                            break;
                        }
                    }
                }

                if(i<count)
                {
                    Toast.makeText(TdAddMarkdActivity.this,"Error in uploading",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent=new Intent(TdAddMarkdActivity.this,TdShowAssignmentActivity.class);
                    intent.putExtra("obj",teacherpanelModel);
                    startActivity(intent);

                }



                //Log.e("abc",  editText.getText().toString());

            }
        });



    }


}
