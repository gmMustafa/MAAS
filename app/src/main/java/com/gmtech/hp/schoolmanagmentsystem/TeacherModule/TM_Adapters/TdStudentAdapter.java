package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TeacherpanelModel;

import java.util.List;

public class TdStudentAdapter extends BaseAdapter {

    Context context;
    List<TeacherpanelModel> models;

    LayoutInflater inflater;

    TextView name;
    TextView id;

    public TdStudentAdapter(Context context, List<TeacherpanelModel> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
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
            view = inflater.inflate(R.layout.teacher_child, viewGroup, false);

        }

        name=(TextView)view.findViewById(R.id.name);
        id=(TextView)view.findViewById(R.id.id);

        name.setText(models.get(i).getStudentFirstName()+" "+models.get(i).getStudentLastName());
        id.setText(models.get(i).getStudentUserId().toString());

        return view;
    }
}
