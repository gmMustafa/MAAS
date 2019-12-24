package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;


import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.MarksModel;

import java.util.ArrayList;
import java.util.List;

public class TdAddMarksAdapter extends BaseAdapter {

    Context context;
    List<MarksModel> models;

    LayoutInflater inflater;

    TextView name;
    EditText marks;
    TextView totalMarks;


    public TdAddMarksAdapter(Context context, List<MarksModel> models) {
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
    public View getView(final int index, View view, ViewGroup viewGroup) {
        if (view == null) {
            inflater = LayoutInflater.from(this.context);
            view = inflater.inflate(R.layout.assignment_listview_item_layout, viewGroup, false);
         }

        name=(TextView)view.findViewById(R.id.name);
        totalMarks=(TextView)view.findViewById(R.id.totalmarks);
        marks=(EditText)view.findViewById(R.id.marks);

        name.setText(models.get(index).getStudentName());
        totalMarks.setText("/"+models.get(index).getTotalMarks());
        marks.setText(models.get(index).getMarks().toString());



        return view;
    }


}
