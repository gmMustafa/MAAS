package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO;

import java.io.Serializable;

public class TM_AttendenceForAllModel implements Serializable {

    Integer attendenceStatus;
    Integer studentId;
    String firstName;
    String lastName;
    String date;

    public Integer getAttendenceStatus() {
        return attendenceStatus;
    }

    public void setAttendenceStatus(Integer attendenceStatus) {
        this.attendenceStatus = attendenceStatus;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
