package com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_DAO.AttandaceInCalandarModel;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by HP on 7/24/2018.
 */
public class SMCalendarAdapter extends BaseAdapter {
    private Context context;

    private java.util.Calendar month;
    public GregorianCalendar pmonth;
    /**
     * calendar instance for previous month for getting complete view
     */
    public GregorianCalendar pmonthmaxset;
    private GregorianCalendar selectedDate;
    int firstDay;
    int maxWeeknumber;
    int maxP;
    int calMaxP;
    int lastWeekDay;
    int leftDays;
    int mnthlength;
    String itemvalue, curentDateString;
    DateFormat df;

    private ArrayList<String> items;
    public static List<String> day_string;
    private View previousView;
    public ArrayList<AttandaceInCalandarModel>  date_collection_arr;

    public SMCalendarAdapter(Context context, GregorianCalendar monthCalendar,
                             ArrayList<AttandaceInCalandarModel> date_collection_arr) {

        this.context=context;
        this.date_collection_arr=date_collection_arr;
        SMCalendarAdapter.day_string = new ArrayList<String>();
        Locale.setDefault(Locale.US);
        month = monthCalendar;
        selectedDate = (GregorianCalendar) monthCalendar.clone();
        this.context = context;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);

        this.items = new ArrayList<String>();
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        curentDateString = df.format(selectedDate.getTime());
        refreshDays();

    }

    public void setItems(ArrayList<String> items) {
        for (int i = 0; i != items.size(); i++) {
            if (items.get(i).length() == 1) {
                items.set(i, "0" + items.get(i));
            }
        }
        this.items = items;
    }

    public int getCount() {
        return day_string.size();
    }

    public Object getItem(int position) {
        return day_string.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new view for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView dayView;
        if (convertView == null) { // if it's not recycled, initialize some
            // attributes
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.cal_item, null);

        }


        dayView = (TextView) v.findViewById(R.id.date);
        String[] separatedTime = day_string.get(position).split("-");


        String gridvalue = separatedTime[2].replaceFirst("^0*", "");
        dayView.setTextColor(Color.BLACK);
        dayView.setClickable(false);
        dayView.setFocusable(false);
        v.setBackgroundColor(Color.parseColor("#ffffff"));
        dayView.setText(gridvalue);

        //if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
        //        else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
        //}


        String date = day_string.get(position);
        if (date.length() == 1) {
            date = "0" + date;
        }
        String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }
        setEventView(v, position,dayView);
        return v;
    }

    /*public View setSelected(View view,int pos) {
        if (previousView != null) {
            previousView.setBackgroundColor(Color.parseColor("#343434"));
        }

        view.setBackgroundColor(Color.CYAN);

        int len=day_string.size();
        if (len>pos) {
            if (day_string.get(pos).equals(curentDateString)) {

            }else{

                previousView = view;

            }

        }
        return view;
    }*/

    public void refreshDays() {
        // clear items
        items.clear();
        day_string.clear();
        Locale.setDefault(Locale.US);
        pmonth = (GregorianCalendar) month.clone();
        // month start day. ie; sun, mon, etc
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        // finding number of weeks in current month.
        maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        // allocating maximum row number for the gridview.
        mnthlength = maxWeeknumber * (7);
        maxP = getMaxP(); // previous month maximum day 31,30....
        calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
        /**
         * Calendar instance for getting a complete gridview including the three
         * month's (previous,current,next) dates.
         */
        pmonthmaxset = (GregorianCalendar) pmonth.clone();
        /**
         * setting the start date as previous month's required date.
         */
        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

        /**
         * filling calendar gridview.
         */
        for (int n = 0; n < mnthlength; n++) {

            itemvalue = df.format(pmonthmaxset.getTime());
            pmonthmaxset.add(GregorianCalendar.DATE, 1);
            day_string.add(itemvalue);

        }
    }

    private int getMaxP() {
        int maxP;
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            pmonth.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return maxP;
    }




   public void setEventView(View v,int pos,TextView txt){
    if(AttandaceInCalandarModel.date_collection_arr!=null) {
        int len = AttandaceInCalandarModel.date_collection_arr.size();
        for (int i = 0; i < len; i++) {
            AttandaceInCalandarModel cal_obj = AttandaceInCalandarModel.date_collection_arr.get(i);
            String date = cal_obj.getDate();
            int len1 = day_string.size();
            if (len1 > pos) {

                if (day_string.get(pos).equals(date)) {
                    v.setBackgroundResource(R.drawable.rounded_calender_item);
                    GradientDrawable drawable = (GradientDrawable) v.getBackground();
                    drawable.setColor(Color.parseColor(cal_obj.getColor()));
                    txt.setTextColor(Color.WHITE);
                }
            }
        }
    }
    }

/*
    public void getPositionList(String date,final Activity act){

        int len=CalendarCollection.date_collection_arr.size();
        for (int i = 0; i < len; i++) {
            CalendarCollection cal_collection=CalendarCollection.date_collection_arr.get(i);
            String event_date=cal_collection.date;
            String event_message=cal_collection.event_message;

            if (date.equals(event_date)) {
               /* Toast.makeText(context, "You have event on this date: "+event_date, Toast.LENGTH_LONG).show();
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Date: "+event_date)
                        .setMessage("Event: "+event_message)
                        .setPositiveButton("OK",new android.content.DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which)
                            {
                                act.finish();
                            }
                        }).show();

                break;
            }}
    }*/

}

