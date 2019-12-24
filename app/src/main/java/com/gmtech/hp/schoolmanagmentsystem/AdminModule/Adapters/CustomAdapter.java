package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.HolidayModel;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.ArrayList;

/**
 * Created by HP on 7/24/2018.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    public  interface helper{
        void setData(ArrayList<HolidayModel> data);
    }

    public static ArrayList<HolidayModel> dataSet;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewEvent;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewEvent = (TextView) itemView.findViewById(R.id.textViewEvent);
        }
    }

    public CustomAdapter(ArrayList<HolidayModel> data) {
        this.dataSet = data;
      }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        if(dataSet!=null)
        {
            TextView textViewName = holder.textViewName;
            TextView textViewEvent = holder.textViewEvent;
            textViewName.setText(dataSet.get(listPosition).getDate());
            textViewEvent.setText(dataSet.get(listPosition).getTitle());
            ((CardView)holder.itemView).setCardBackgroundColor(Color.parseColor(HolidayModel.color));
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setDataSet(ArrayList<HolidayModel> data)
    {
        dataSet = data;
    }


    void setData(ArrayList<HolidayModel> data) {
        setDataSet(data);
    }
}