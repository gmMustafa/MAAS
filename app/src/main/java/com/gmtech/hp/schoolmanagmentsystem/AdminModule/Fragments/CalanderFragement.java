package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters.CalendarAdapter;
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
public class CalanderFragement extends Fragment{

    SchoolDB schoolDB;
    public GregorianCalendar cal_month, cal_month_copy;
    private CalendarAdapter cal_adapter;
    private TextView tv_month;
    private static List<HolidayModel> data;
    GregorianCalendar calendar;
    String Month;
    String year;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        HolidaysActivities a=null;
        if (context instanceof HolidaysActivities){
            a=(HolidaysActivities) context;
        }
        schoolDB=new SchoolDB(a);
        HolidayModel.pop(a);
    }


    public CalanderFragement() {
        calendar=(GregorianCalendar)GregorianCalendar.getInstance();
         Month= String.valueOf(calendar.get(GregorianCalendar.MONTH)+1);
         year= String.valueOf(calendar.get(GregorianCalendar.YEAR));

        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_calander, container, false);

        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();

        cal_adapter = new CalendarAdapter(getActivity(), cal_month, HolidayModel.date_collection_arr);



        tv_month = (TextView) rootView.findViewById(R.id.tv_month);
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));

        ImageButton previous = (ImageButton) rootView.findViewById(R.id.ib_prev);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
                getdata();
                CustomAdapter.dataSet.clear();
                CustomAdapter adapter=new CustomAdapter((ArrayList<HolidayModel>) data);
                listFragment.recyclerView.setAdapter(adapter);
                listFragment.adapter.notifyDataSetChanged();
            }
        });

        ImageButton next = (ImageButton) rootView.findViewById(R.id.Ib_next);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();

                getdata();
                CustomAdapter.dataSet.clear();
                CustomAdapter adapter=new CustomAdapter((ArrayList<HolidayModel>) data);
                listFragment.recyclerView.setAdapter(adapter);
                listFragment.adapter.notifyDataSetChanged();
            }
        });

        GridView gridview = (GridView) rootView.findViewById(R.id.gv_calendar);
        gridview.setAdapter(cal_adapter);

        /*gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

               // ((CalendarAdapter) parent.getAdapter()).setSelected(v,position);
                String selectedGridDate = CalendarAdapter.day_string
                        .get(position);

                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*","");
                int gridvalue = Integer.parseInt(gridvalueString);

                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar();
                }
                //((CalendarAdapter) parent.getAdapter()).setSelected(v,position);
              //  ((CalendarAdapter) parent.getAdapter()).getPositionList(selectedGridDate, getActivity());
            }
        });
*/

        return rootView;
    }


    protected void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month
                .getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1),
                    cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
            year=String.valueOf(cal_month.get(GregorianCalendar.YEAR));
            Month=String.valueOf(cal_month.getActualMinimum(GregorianCalendar.MONTH)+1);
        }
        else
            {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) + 1);
            Month=String.valueOf(cal_month.get(GregorianCalendar.MONTH)+1);
        }
    }

    protected void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
            year=String.valueOf(cal_month.get(GregorianCalendar.YEAR));
            Month=String.valueOf(cal_month.getActualMaximum(GregorianCalendar.MONTH)+1);
        } else {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) - 1);
            Month=String.valueOf(cal_month.get(GregorianCalendar.MONTH)+1);
        }
    }

    public void refreshCalendar() {
        cal_adapter.refreshDays();
        cal_adapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
    }

    public void getdata()
    {
        List<HolidayModel> c= (ArrayList<HolidayModel>) schoolDB.getHolidays(HolidaysActivities.s_id);
        data= new ArrayList<HolidayModel>();

        Log.e("Check","Calander "+Month + " "+ year);
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
                    }
                }
            }
        }
    }


}
