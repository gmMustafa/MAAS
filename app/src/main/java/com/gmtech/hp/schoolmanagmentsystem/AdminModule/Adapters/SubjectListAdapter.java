package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.SubjectModel;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

/**
 * Created by HP on 8/3/2018.
 */

public class SubjectListAdapter extends BaseAdapter {

    List<SubjectModel> m_values;
    Context m_context;
    LayoutInflater inflator;

    TextView m_Subject_name;



    public SubjectListAdapter(List<SubjectModel> m_values, Context m_context) {
        this.m_values = m_values;
        this.m_context = m_context;
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            inflator = LayoutInflater.from(this.m_context);
            view = inflator.inflate(R.layout.simple_child, viewGroup, false);
        }
        m_Subject_name = (TextView) view.findViewById(R.id.item_name);
        m_Subject_name.setText(m_values.get(i).getSubject_name().toString());
        return view;
    }
}
