package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AttendenceForAllModel;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

public class AttendenceAdapter extends BaseAdapter {

    Context context;
    List<AttendenceForAllModel> models;

    LayoutInflater inflater;

    TextView date;
    TextView name;
    TextView status;

    public AttendenceAdapter(Context context, List<AttendenceForAllModel> models) {
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
            view = inflater.inflate(R.layout.attendance_listview_item_layout, viewGroup, false);
         }
        date=(TextView)view.findViewById(R.id.attendance_date);
        name=(TextView)view.findViewById(R.id.attendance_name);
        status=(TextView)view.findViewById(R.id.attendance_status);
        date.setBackgroundColor(Color.parseColor("#f85f50"));
        name.setText(models.get(i).getFirstName()+" "+models.get(i).getLastName());
        date.setText(models.get(i).getDate());
       // status.setText(models.get(i).getAttendenceStatus().toString());

        if(models.get(i).getAttendenceStatus()==0)//absent
        {
            status.setBackgroundColor(Color.parseColor("#ca1d0d"));
            status.setText("Absent");

        }

        else if(models.get(i).getAttendenceStatus()==1)//present
        {
            status.setBackgroundColor(Color.parseColor("#23b574"));
            status.setText("Present");


        }

        else if(models.get(i).getAttendenceStatus()==2)//leave
        {
            status.setBackgroundColor(Color.parseColor("#CCCC00"));
            status.setText("Leave");


        }

        return view;
    }
}
