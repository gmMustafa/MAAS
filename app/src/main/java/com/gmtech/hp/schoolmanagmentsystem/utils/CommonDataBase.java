package com.gmtech.hp.schoolmanagmentsystem.utils;

/**
 * Created by HP on 8/2/2018.
 */

public class CommonDataBase {

    public enum TableName{

        Users, Teachers, Students, Classes, Classes_info, Rooms, Subjects, Student_Subject,
        Teacher_Subject, School, Attendance, Assignments, Acadamic_year, Holidays, Marks,
        Notifications;
    }

    public enum Users{
        User_id, User_name, Password, Role, Created_Date, Deleted_Date, Activity_flag, Mobile_num,
        Gender, First_Name, Last_Name,City, Postal_code, Email;
    }

    public enum Teachers{
        Teacher_id,User_id,School_id,Created_By,Delete_by;
    }

    public enum Students{
        Student_id,User_id,School_id,Class_num,Created_By,Delete_by;
    }

    public enum Classes{
        Class_id,class_start_time,class_end_time,Room_id,Class_info_id,Flag;
    }

    public enum Classes_info{
        Class_info_id,Class_sec,School_id,Subject_id,Strength,Academic_id;
    }

    public enum Rooms{
        Room_id,Room_num,School_Id,Created_By,Delete_by,Created_Date,Deleted_Date,Status;
    }

    public enum Subjects{
        Subject_id,Subject_name,Class_num,School_Id,Created_By,Delete_by,Created_Date,Deleted_Date,
        Status;
    }

    public enum Student_Subject{
        Student_Id,Class_Id,Roll_num;
    }

    public enum Teacher_Subject{
        User_Id,Class_Id;
    }

    public enum School{
        School_id,School_name,School_address,phone_number,Created_By,Delete_by,Created_Date,Deleted_Date,
        Allocated_to,Status;
    }

    public enum Attendance{
        Attendance_id,User_id,Student_id,a_date,a_time,Active_flag,class_id;
    }

    public enum Assignments{
        Assignment_id,Class_id,Title,Description,totla_marks,Created_date,DeadLine_date,Created_by,
        Delete_by,Flag,A_type,Weightage,Subject_id;
    }

    public enum Acadamic_year{
        Academic_id,From_date,Till_date;
    }

    public enum Holidays{
        Holiday_id,Holiday_date,Holiday_title,Added_by,Delete_by,School_id;
    }

    public enum Marks{
        Student_Id,Assignment_id,Marks;
    }

    public enum Notifications{
        Notification_id,Class_id,Notification_title,Notification_Message,Created_Date,Created_By,
        Delete_by,Flag;
    }


}
