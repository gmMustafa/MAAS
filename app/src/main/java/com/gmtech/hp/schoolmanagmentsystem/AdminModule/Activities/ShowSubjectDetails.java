package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AdminModel;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AdminModelHelper;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.SubjectModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

public class ShowSubjectDetails extends AppCompatActivity implements View.OnClickListener{
    TextView textView;
    SchoolDB schoolDB;
    Integer Schhol_id;
    SubjectModel subjectModel;
    Integer    counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_subject_details);
        Intent intent=getIntent();
        Schhol_id = intent.getIntExtra("School_id", -1);
        subjectModel= (SubjectModel) intent.getSerializableExtra("model");
        schoolDB=new SchoolDB(this);

        textView=(TextView)findViewById(R.id.title_holiday);
        textView.setText(subjectModel.getSubject_name());

        textView=(TextView)findViewById(R.id.room_et);
        textView.setText(subjectModel.getClass_number().toString());

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.toolbar_edit:

                Intent intent=new Intent(ShowSubjectDetails.this,EditSubject.class);
                intent.putExtra("model",subjectModel);
                intent.putExtra("School_id",Schhol_id);
                counter=1;
                startActivity(intent);
                break;

            case R.id.deletesubject:
                if(schoolDB.delteSubject(subjectModel.getSubject_id(), AdminModelHelper.Admin_id)){
                    Toast.makeText(ShowSubjectDetails.this,"Record Deleted",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(ShowSubjectDetails.this,"Failure Deleting Record",Toast.LENGTH_SHORT).show();
                }

                finish();
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(counter==1)
        {finish();}
    }
}
