package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TeacherpanelModel;

public class TdShowAssignmentDetailsActivity extends AppCompatActivity {


    TeacherpanelModel teacherpanelModel;
    SchoolDB schoolDB;
    TextView title;
    TextView des;
    TextView totalMarks;
    TextView deadline;
    TextView type;
    Button mark;
    Button delete;
    ImageView edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_assignment_detail);

        TextView textView=(TextView)findViewById(R.id.toolbar_title);
        textView.setText("Assignment Details");


        schoolDB=new SchoolDB(this);
        teacherpanelModel=(TeacherpanelModel) getIntent().getSerializableExtra("obj");
        title=(TextView)findViewById(R.id.title_show_tv);
        des=(TextView)findViewById(R.id.description_show_tv);
        totalMarks=(TextView)findViewById(R.id.total_marks_show_tv);
        deadline=(TextView)findViewById(R.id.deadline_show_tv);
        type=(TextView)findViewById(R.id.type_show_tv);
        mark=(Button) findViewById(R.id.mark);
        delete=(Button)findViewById(R.id.delete);
        edit=(ImageView)findViewById(R.id.toolbar_image);

        title.setText(teacherpanelModel.getAssignmentTitle());
        des.setText(teacherpanelModel.getAssassignmentDes());
        totalMarks.setText(teacherpanelModel.getAssignmenttotalMarks().toString());
        deadline.setText(teacherpanelModel.getAssignmentDeadLine());
        type.setText(teacherpanelModel.getAssignmenttype());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean check=schoolDB.deleteAssignment(teacherpanelModel);
                if(check==true)
                {
                    Intent intent=new Intent(TdShowAssignmentDetailsActivity.this, TdShowAssignmentActivity.class);
                    intent.putExtra("obj",teacherpanelModel);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(TdShowAssignmentDetailsActivity.this,"Error in deleting", Toast.LENGTH_SHORT).show();
                }

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TdShowAssignmentDetailsActivity.this, EditAssignmentActivity.class);
                intent.putExtra("obj",teacherpanelModel);
                startActivity(intent);
            }
        });

        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TdShowAssignmentDetailsActivity.this, TdAddMarkdActivity.class);
                intent.putExtra("obj",teacherpanelModel);
                startActivity(intent);
            }
        });

    }
}
