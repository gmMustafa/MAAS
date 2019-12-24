package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.TeacherModel;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

/**
 * Created by HP on 8/3/2018.
 */

public class TeacherSelectSetionAdapter extends BaseAdapter {

    Context context;
    List<TeacherModel> model;
    LayoutInflater inflater;

    TextView sec;

    public TeacherSelectSetionAdapter(Context context, List<TeacherModel> model) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            inflater = LayoutInflater.from(this.context);
            view = inflater.inflate(R.layout.simple_child, viewGroup, false);
        }

        sec=(TextView)view.findViewById(R.id.item_name);
        sec.setText(model.get(i).getSection());
        return view;
    }

    public void update(List<TeacherModel> model){
        notifyDataSetChanged();
        this.model=model;
    }
}
