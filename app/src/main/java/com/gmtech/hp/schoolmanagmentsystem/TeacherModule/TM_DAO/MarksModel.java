package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO;

import java.io.Serializable;

public class MarksModel implements Serializable {

    String studentName;
    Integer studentId;
    Integer AssignmentId;
    Float marks;
    Integer totalMarks;

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getAssignmentId() {
        return AssignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        AssignmentId = assignmentId;
    }

    public Float getMarks() {
        return marks;
    }

    public void setMarks(Float marks) {
        this.marks = marks;
    }
}
