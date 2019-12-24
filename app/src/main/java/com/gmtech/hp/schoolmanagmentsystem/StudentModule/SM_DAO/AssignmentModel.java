package com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_DAO;

/**
 * Created by HP on 8/7/2018.
 */

public class AssignmentModel {

    Integer Assignment_id;
    Integer Class_id;
    String Title ;
    String Description;
    Integer totla_marks;
    Integer obt_marks;

    public Integer getObt_marks() {
        return obt_marks;
    }

    public void setObt_marks(Integer obt_marks) {
        this.obt_marks = obt_marks;
    }

    String Created_date;
    String DeadLine_date;
    String Created_by;
    Integer Delete_by;
    Integer Flag;
    String  A_type;
    Integer Weightage;
    Integer Subject_id;

    public Integer getAssignment_id() {
        return Assignment_id;
    }

    public void setAssignment_id(Integer assignment_id) {
        Assignment_id = assignment_id;
    }

    public Integer getClass_id() {
        return Class_id;
    }

    public void setClass_id(Integer class_id) {
        Class_id = class_id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getTotla_marks() {
        return totla_marks;
    }

    public void setTotla_marks(Integer totla_marks) {
        this.totla_marks = totla_marks;
    }

    public String getCreated_date() {
        return Created_date;
    }

    public void setCreated_date(String created_date) {
        Created_date = created_date;
    }

    public String getDeadLine_date() {
        return DeadLine_date;
    }

    public void setDeadLine_date(String deadLine_date) {
        DeadLine_date = deadLine_date;
    }

    public String getCreated_by() {
        return Created_by;
    }

    public void setCreated_by(String created_by) {
        Created_by = created_by;
    }

    public Integer getDelete_by() {
        return Delete_by;
    }

    public void setDelete_by(Integer delete_by) {
        Delete_by = delete_by;
    }

    public Integer getFlag() {
        return Flag;
    }

    public void setFlag(Integer flag) {
        Flag = flag;
    }

    public String getA_type() {
        return A_type;
    }

    public void setA_type(String a_type) {
        A_type = a_type;
    }

    public Integer getWeightage() {
        return Weightage;
    }

    public void setWeightage(Integer weightage) {
        Weightage = weightage;
    }

    public Integer getSubject_id() {
        return Subject_id;
    }

    public void setSubject_id(Integer subject_id) {
        Subject_id = subject_id;
    }
}
