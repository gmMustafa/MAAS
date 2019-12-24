package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities.ShowSectionOfSelectedCourseTeacher;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.TeacherModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

/**
 * Created by HP on 8/3/2018.
 */

public class TeacherCoursesAddAdapter extends BaseAdapter {

    Context context;
    List<TeacherModel> model;
    LayoutInflater inflater;
    SchoolDB schoolDB;
    TeacherModel teacherModel;

    TextView courseName;
    ImageButton add;

    public TeacherCoursesAddAdapter(Context context, List<TeacherModel> model,TeacherModel teacherModel) {
        this.context = context;
        this.model = model;
        this.teacherModel=teacherModel;
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            inflater = LayoutInflater.from(this.context);
            view = inflater.inflate(R.layout.add_child, viewGroup, false);
        }


        courseName=(TextView)view.findViewById(R.id.item_name);
        add=(ImageButton)view.findViewById(R.id.item_add);
        courseName.setText(model.get(i).getCourse());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                schoolDB=new SchoolDB(context);
                Integer sub_id=schoolDB.getSubjectId(model.get(i).getCourse(),teacherModel.getSchoolId().toString());
               model.get(i).setSubjectId(sub_id);
                Boolean check=schoolDB.checkIfCourseIsAssignTeacher(model.get(i).getCourse(),model.get(i).getSubjectId());
                if(check==true)
                {
                    teacherModel.setCourse(model.get(i).getCourse());
                    teacherModel.setSubjectId(model.get(i).getSubjectId());
                    teacherModel.setClass_num(model.get(i).getClass_num());

                    Intent intent =new Intent(context,ShowSectionOfSelectedCourseTeacher.class);
                    intent.putExtra("Teacher",teacherModel);
                    context.startActivity(intent);
                }
                else {
                    Toast.makeText(context,"Subject is not assigned To any class Room yet",Toast.LENGTH_SHORT).show();
                }

            }
        });




        return view;
    }

    public void update(List<TeacherModel> model) {
           // this.model=model;
            notifyDataSetChanged();
    }
}
