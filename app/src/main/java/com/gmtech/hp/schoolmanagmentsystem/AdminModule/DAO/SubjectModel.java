package com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO;

import java.io.Serializable;

public class SubjectModel implements Serializable {

    String subject_name;
    Integer subject_id;
    Integer class_number;

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public Integer getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Integer subject_id) {
        this.subject_id = subject_id;
    }

    public Integer getClass_number() {
        return class_number;
    }

    public void setClass_number(Integer class_number) {
        this.class_number = class_number;
    }
}
