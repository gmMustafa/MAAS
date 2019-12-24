package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters.CustomAdapter;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities.HolidaysActivities;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.HolidayModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class listFragment extends Fragment {


    SchoolDB schoolDB;
    public static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static RecyclerView recyclerView;
    private static ArrayList<HolidayModel> data;

    GregorianCalendar calendar;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        HolidaysActivities a=null;
        if (context instanceof HolidaysActivities){
            a=(HolidaysActivities) context;
        }
        schoolDB=new SchoolDB(a);
    }

    public listFragment() {
        // Required empty public constructor
        calendar= (GregorianCalendar) GregorianCalendar.getInstance();
    }

    public void getdata()
    {
        List<HolidayModel> c= (ArrayList<HolidayModel>) schoolDB.getHolidays(HolidaysActivities.s_id);
        data= new ArrayList<HolidayModel>();

        String Month= String.valueOf(calendar.get(GregorianCalendar.MONTH)+1);
        String year= String.valueOf(calendar.get(GregorianCalendar.YEAR));

        Log.e("Check","List "+Month + " "+ year);
        if(Integer.parseInt(Month)<=9)
        {
            Month="0"+Month;
        }

        if(c!=null)
        {
            for(int i=0;i<c.size();i++)
            {
                if(year.equals(c.get(i).getDate().substring(0,4))) {
                    if (Month.equals(c.get(i).getDate().substring(5, 7))) {
                        data.add (new HolidayModel(c.get(i).getDate().substring(8),c.get(i).getTitle(),HolidayModel.color));
                        Log.e("sda",data.get(i).date);
                    }
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_view, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
      recyclerView.setLayoutManager(layoutManager);
       // recyclerView.setLayoutManager(new MyLinearLayoutManager(getActivity(),1,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getdata();
        adapter = new CustomAdapter(data);
        recyclerView.setAdapter(adapter);
       return rootView;
    }

}
