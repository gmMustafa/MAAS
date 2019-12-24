package com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_DAO;

import android.content.Context;

import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_Activities.StudentAttanaceShow;

import java.util.ArrayList;

/**
 * Created by HP on 7/24/2018.
 */

public class AttandaceInCalandarModel {
    Integer id;
    public String date = "";
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String status = "";
    public  String color = "";

    public AttandaceInCalandarModel() {

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


    public static ArrayList<AttandaceInCalandarModel> date_collection_arr;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public AttandaceInCalandarModel(String date, String status, String color) {
        this.date = date;
        this.status = status;
        this.color = color;
    }


    public static void pop(Context context) {
        SchoolDB schoolDB = new SchoolDB(context);
        date_collection_arr = (ArrayList<AttandaceInCalandarModel>)  schoolDB.getAllAttancdeStatus(StudentAttanaceShow.id, StudentAttanaceShow.studentModel);
       }
}
