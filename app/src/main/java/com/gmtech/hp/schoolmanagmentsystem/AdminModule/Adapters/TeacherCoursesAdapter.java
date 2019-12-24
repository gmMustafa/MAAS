package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.TeacherModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

/**
 * Created by HP on 8/3/2018.
 */

public class TeacherCoursesAdapter extends BaseAdapter {

    Context context;
    List<TeacherModel> model;
    LayoutInflater inflater;
    TeacherModel teacherModel;

    TextView courseName;
    ImageButton delete;
    SchoolDB schoolDB;


    public TeacherCoursesAdapter(Context context, List<TeacherModel> model, TeacherModel teacherModel) {
        this.context = context;
        this.model = model;
        this.teacherModel = teacherModel;
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
            view = inflater.inflate(R.layout.delete_child, viewGroup, false);
        }

        courseName = (TextView) view.findViewById(R.id.item_name);
        delete = (ImageButton) view.findViewById(R.id.item_delete);



        courseName.setText(model.get(i).getCourse());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,"delete",Toast.LENGTH_SHORT).show();
                teacherModel.setCourse(model.get(i).getCourse());
                teacherModel.setSection(model.get(i).getSection());
                schoolDB = new SchoolDB(context);
                long check = schoolDB.deleteCourseForTeacher(teacherModel);

                if (check <= 0) {
                    Toast.makeText(context, "Delete Unsuccessful", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(context, "Delete Successful", Toast.LENGTH_LONG).show();
                    ((Activity)context).finish();
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
