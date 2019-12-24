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

public class StudentListAdapter extends BaseAdapter {

    List<StudentModel> m_values;
    Context m_context;
    LayoutInflater m_inflator;

    TextView m_Id;
    TextView m_name;
    TextView m_Class;

    public StudentListAdapter(List<StudentModel> values, Context applicationContext) {
        this.m_context = applicationContext;
        this.m_values = values;
        m_inflator = LayoutInflater.from(this.m_context);

    }


    @Override
    public int getCount() {
        return m_values.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = m_inflator.inflate(R.layout.listview_item_layout, parent, false);
        }

        m_Id = (TextView) convertView.findViewById(R.id.rollno);
        m_name = (TextView) convertView.findViewById(R.id.name);
        m_Class = (TextView) convertView.findViewById(R.id.class_number);


        m_Id.setText(m_values.get(position).getS_id().toString());
        m_name.setText(m_values.get(position).getfName()+" "+m_values.get(position).getlName());
        m_Class.setText(m_values.get(position).getClass_num().toString());
        return convertView;
    }

    public void updateResults(List<StudentModel> values) {
        this.m_values = values;
        notifyDataSetChanged();
    }
}
