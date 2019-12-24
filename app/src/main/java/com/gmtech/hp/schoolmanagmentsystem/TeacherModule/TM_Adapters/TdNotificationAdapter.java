package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Adapters;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaMetadata;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TeacherpanelModel;

import java.util.List;

public class TdNotificationAdapter extends BaseAdapter {

    Context context;
    List<TeacherpanelModel> models;

    LayoutInflater inflater;

    TextView title;


    public TdNotificationAdapter(Context context, List<TeacherpanelModel> models) {
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
            view = inflater.inflate(R.layout.notification_child, viewGroup, false);
        }
        title=(TextView)view.findViewById(R.id.notifyTV);
        title.setText(models.get(i).getNotificationTitle());


        return view;
    }
}
