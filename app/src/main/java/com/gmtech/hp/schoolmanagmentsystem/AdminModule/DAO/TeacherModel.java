package com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO;

import java.io.Serializable;

/**
 * Created by HP on 8/3/2018.
 */

public class TeacherModel implements Serializable {

    public String firstName;
    public String lastName;
    public  Integer id;
    public String gender;
    public String mobileNo;
    public String course;
    public Integer userId;
    public Integer schoolId;
    public Integer class_num;
    public Integer subjectId;
    public String section;
    public Integer classInfoId;
    public String city;
    public String postal;
    public String email;
    public String userName;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String password;

    public Integer getSchool_id() {
        return School_id;
    }

    public void setSchool_id(Integer school_id) {
        School_id = school_id;
    }

    Integer School_id;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public  Integer getId() {
        return id;
    }

    public  void setId(Integer id) {
        this.id = id;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getClass_num() {
        return class_num;
    }

    public void setClass_num(Integer class_num) {
        this.class_num = class_num;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }


    public Integer getClassInfoId() {
        return classInfoId;
    }

    public void setClassInfoId(Integer classInfoId) {
        this.classInfoId = classInfoId;
    }

}

