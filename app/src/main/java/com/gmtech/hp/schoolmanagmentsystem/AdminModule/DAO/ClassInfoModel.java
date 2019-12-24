package com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO;

import java.io.Serializable;

/**
 * Created by HP on 8/6/2018.
 */

public class ClassInfoModel implements Serializable{
    Integer class_num;

    public Integer getClass_num() {
        return class_num;
    }

    public void setClass_num(Integer class_num) {
        this.class_num = class_num;
    }

    public Integer Class_info_id;
    public Integer School_id;
    public  Integer Subject_id;
    public Integer Strength;
    public Integer Academic_id;

    public Integer getClass_info_id() {
        return Class_info_id;
    }

    public void setClass_info_id(Integer class_info_id) {
        Class_info_id = class_info_id;
    }

    public Integer getSchool_id() {
        return School_id;
    }

    public void setSchool_id(Integer school_id) {
        School_id = school_id;
    }

    public Integer getSubject_id() {
        return Subject_id;
    }

    public void setSubject_id(Integer subject_id) {
        Subject_id = subject_id;
    }

    public Integer getStrength() {
        return Strength;
    }

    public void setStrength(Integer strength) {
        Strength = strength;
    }

    public Integer getAcademic_id() {
        return Academic_id;
    }

    public void setAcademic_id(Integer academic_id) {
        Academic_id = academic_id;
    }

    public Integer getClass_id() {
        return Class_id;
    }

    public void setClass_id(Integer class_id) {
        Class_id = class_id;
    }

    public Integer getRoom_id() {
        return Room_id;
    }

    public void setRoom_id(Integer room_id) {
        Room_id = room_id;
    }

    public String getClass_sec() {
        return Class_sec;
    }

    public void setClass_sec(String class_sec) {
        Class_sec = class_sec;
    }

    public String getClass_start_time() {
        return class_start_time;
    }

    public void setClass_start_time(String class_start_time) {
        this.class_start_time = class_start_time;
    }

    public String getClass_end_time() {
        return class_end_time;
    }

    public void setClass_end_time(String class_end_time) {
        this.class_end_time = class_end_time;
    }

   public Integer Class_id;
    public Integer Room_id;
    public String  Class_sec,
            class_start_time,
    class_end_time;
}
