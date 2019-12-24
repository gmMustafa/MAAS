package com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO;

import android.content.Context;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities.HolidaysActivities;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;

import java.util.ArrayList;

/**
 * Created by HP on 7/24/2018.
 */

public class HolidayModel {
    Integer id;
    public String date = "";
    public String title = "";
    public static String color = "#23b574";

    public HolidayModel() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static ArrayList<HolidayModel> date_collection_arr;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public HolidayModel(String date, String title, String color) {
        this.date = date;
        this.title = title;
        this.color = color;
    }


    public static void pop(Context context) {
        SchoolDB schoolDB = new SchoolDB(context);
        date_collection_arr = (ArrayList<HolidayModel>) schoolDB.getHolidays(HolidaysActivities.s_id);
    }
}