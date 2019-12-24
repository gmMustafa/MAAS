package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.TeacherModel;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

/**
 * Created by HP on 8/3/2018.
 */

public class TeacherAdapter extends BaseAdapter {

    Context context;
    List<TeacherModel> model;
    LayoutInflater inflater;

    TextView name;
    TextView id;
    ImageButton edit;

    TeacherModel teacherModel;


    public TeacherAdapter(Context context, List<TeacherModel> model) {
        this.context = context;
        this.model = model;
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
            view = inflater.inflate(R.layout.teacher_child, viewGroup, false);
        }
        name=(TextView)view.findViewById(R.id.name);
        id=(TextView)view.findViewById(R.id.id);

        name.setText(model.get(i).getFirstName() +" "+model.get(i).getLastName());
        id.setText(model.get(i).getId().toString());

        return view;

    }
}
