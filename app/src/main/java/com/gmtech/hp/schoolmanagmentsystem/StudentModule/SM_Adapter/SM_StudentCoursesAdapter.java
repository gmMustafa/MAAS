package com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.StudentModel;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

/**
 * Created by HP on 8/7/2018.
 */

public class SM_StudentCoursesAdapter extends BaseAdapter{

    Context context;
    List<StudentModel> model;
    LayoutInflater inflater;
    StudentModel studentModel;
    TextView courseName;



    public SM_StudentCoursesAdapter(Context context, List<StudentModel> model, StudentModel studentModel) {
        this.context = context;
        this.model = model;
        this.studentModel = studentModel;
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
            view = inflater.inflate(R.layout.simple_child, viewGroup, false);
        }
        courseName = (TextView) view.findViewById(R.id.item_name);
        courseName.setText(model.get(i).getCourse());
        return view;
    }
}
