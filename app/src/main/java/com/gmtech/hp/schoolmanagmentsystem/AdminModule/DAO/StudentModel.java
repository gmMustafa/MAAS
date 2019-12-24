package com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO;

import java.io.Serializable;

/**
 * Created by HP on 8/3/2018.
 */

public class StudentModel implements Serializable{
    Integer s_id;
    Integer user_id;
    Integer class_num;
    String fName;
    String lName;

    String Mobile_no;
    String Password;
    String UserName;
    String gender;

    String city;
    String Email;
    String Postal;


    public String course;
    Integer Subject_Id;
    Integer School_id;

    public Integer classInfoId;

    public Integer getClassInfoId() {
        return classInfoId;
    }

    public void setClassInfoId(Integer classInfoId) {
        this.classInfoId = classInfoId;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    String Section;

    public Integer getSubject_Id() {
        return Subject_Id;
    }

    public void setSubject_Id(Integer subject_Id) {
        Subject_Id = subject_Id;
    }

    public Integer getSchool_id() {
        return School_id;
    }

    public void setSchool_id(Integer school_id) {
        School_id = school_id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPostal() {
        return Postal;
    }

    public void setPostal(String postal) {
        Postal = postal;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public String getMobile_no() {
        return Mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        Mobile_no = mobile_no;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getS_id() {
        return s_id;
    }

    public void setS_id(Integer s_id) {
        this.s_id = s_id;
    }

    public Integer getClass_num() {
        return class_num;
    }

    public void setClass_num(Integer class_num) {
        this.class_num = class_num;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }


}
