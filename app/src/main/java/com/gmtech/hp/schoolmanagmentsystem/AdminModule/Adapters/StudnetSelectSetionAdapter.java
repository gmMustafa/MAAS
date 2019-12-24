package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters;

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
 * Created by HP on 8/3/2018.
 */

public class StudnetSelectSetionAdapter extends BaseAdapter {

    Context context;
    List<StudentModel> studentModel;
    LayoutInflater inflater;

    TextView sec;

    public StudnetSelectSetionAdapter(Context context, List<StudentModel> StudentModel) {
        this.context = context;
        this.studentModel = StudentModel;
    }


    @Override
    public int getCount() {
        return studentModel.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            inflater = LayoutInflater.from(this.context);
            view = inflater.inflate(R.layout.simple_child, viewGroup, false);
        }
        sec=(TextView)view.findViewById(R.id.item_name);
        sec.setText(studentModel.get(i).getSection());
        return view;
    }

    public void update(List<StudentModel> studentModel){
        notifyDataSetChanged();
        this.studentModel=studentModel;
    }
}
