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

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities.ShowSectionOfSelectedCourseStudent;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.StudentModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

/**
 * Created by HP on 8/3/2018.
 */

public class StudentCoursesAddAdapter extends BaseAdapter {

    Context context;
    List<StudentModel> model;
    LayoutInflater inflater;
    SchoolDB schoolDB;
    StudentModel studentModel;

    TextView courseName;
    ImageButton add;

    public StudentCoursesAddAdapter(Context context, List<StudentModel> model, StudentModel studentModel) {
        this.context = context;
        this.model = model;
        this.studentModel=studentModel;
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
                Integer sub_id=model.get(i).getSubject_Id();

          Boolean check=schoolDB.checkIfCourseIsAssignTeacher(model.get(i).getCourse(),model.get(i).getSubject_Id());
                if(check==true)
                {
                    studentModel.setSubject_Id(sub_id);
                    Intent intent =new Intent(context,ShowSectionOfSelectedCourseStudent.class);
                    intent.putExtra("Student",studentModel);
                    context.startActivity(intent);
                }
                else {
                    Toast.makeText(context,"Subject is not assigned To any class Room yet",Toast.LENGTH_SHORT).show();
                }

            }
        });




        return view;
    }

    public void update(List<StudentModel> model) {
           // this.model=model;
            notifyDataSetChanged();
    }
}
