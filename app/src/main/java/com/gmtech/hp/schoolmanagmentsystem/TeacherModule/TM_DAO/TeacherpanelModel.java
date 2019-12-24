package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO;

import java.io.Serializable;

import javax.sql.StatementEvent;

/**
 * Created by HP on 8/4/2018.
 */

public class TeacherpanelModel implements Serializable {

    Integer classNumber;
    String Section;

    Integer teacherId;
    Integer schoolId;

    Integer subjectid;
    String SubjectName;

    String notificationMsg;
    String notificationTitle;
    Integer notificationId;
    Integer notificationStatus;

    String studentFirstName;
    String studentLastName;
    Integer studentUserId;
    String studentMobileNumber;
    String StudentGender;

    String date;

    Integer attendenceStatus;

    Integer assignmentId;
    String assignmentTitle;
    String assassignmentDes;
    Integer assignmenttotalMarks;
    String assignmenttype;
    String assignmentDeadLine;

    public String getAssignmentDeadLine() {
        return assignmentDeadLine;
    }

    public void setAssignmentDeadLine(String assignmentDeadLine) {
        this.assignmentDeadLine = assignmentDeadLine;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public String getAssassignmentDes() {
        return assassignmentDes;
    }

    public void setAssassignmentDes(String assassignmentDes) {
        this.assassignmentDes = assassignmentDes;
    }

    public Integer getAssignmenttotalMarks() {
        return assignmenttotalMarks;
    }

    public void setAssignmenttotalMarks(Integer assignmenttotalMarks) {
        this.assignmenttotalMarks = assignmenttotalMarks;
    }

    public String getAssignmenttype() {
        return assignmenttype;
    }

    public void setAssignmenttype(String assignmenttype) {
        this.assignmenttype = assignmenttype;
    }

    public Integer getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(Integer notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStudentMobileNumber() {
        return studentMobileNumber;
    }

    public void setStudentMobileNumber(String studentMobileNumber) {
        this.studentMobileNumber = studentMobileNumber;
    }

    public String getStudentGender() {
        return StudentGender;
    }

    public void setStudentGender(String studentGender) {
        StudentGender = studentGender;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public Integer getAttendenceStatus() {
        return attendenceStatus;
    }

    public void setAttendenceStatus(Integer attendenceStatus) {
        this.attendenceStatus = attendenceStatus;
    }

    public Integer getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(Integer studentUserId) {
        this.studentUserId = studentUserId;
    }

    public String getNotificationMsg() {
        return notificationMsg;
    }

    public void setNotificationMsg(String notificationMsg) {
        this.notificationMsg = notificationMsg;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }



    public Integer getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(Integer classNumber) {
        this.classNumber = classNumber;
    }


    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }
}
