package com.gmtech.hp.schoolmanagmentsystem.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.ClassInfoModel;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AdminModel;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AdminModelHelper;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.AttendenceModel;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.HolidayModel;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.RoomModel;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.SchoolModel;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.StudentModel;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.SubjectModel;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.TeacherModel;
import com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_DAO.AssignmentModel;
import com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_DAO.AttandaceInCalandarModel;
import com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_DAO.NotificationModel;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.MarksModel;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TM_AttendenceForAllModel;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TdIdsModel;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TeacherpanelModel;
import com.gmtech.hp.schoolmanagmentsystem.utils.CommonDataBase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Created by HP on 8/1/2018.
 */

public class SchoolDB {


    private SQLiteDatabase database;
    private Helper dbHelper;
    private Context context;


    public SchoolDB(Context context) {
        dbHelper = new Helper(context);
        this.context = context;
    }

    public String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }


    public Integer getClassId(TeacherpanelModel teacherpanelModel) {
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select " + CommonDataBase.Classes.Class_id.toString() + " From " + CommonDataBase.TableName.Classes.toString() +
                        " Where " + CommonDataBase.Classes.Class_info_id.toString() + " IN ( Select " + CommonDataBase.Classes_info.Class_info_id.toString() +
                        " From " + CommonDataBase.TableName.Classes_info.toString() + " where " + CommonDataBase.Classes_info.Class_sec.toString() + "=? AND " +
                        CommonDataBase.Classes_info.School_id.toString() + "=? And " + CommonDataBase.Classes_info.Subject_id.toString() + "=?);"
                , new String[]{teacherpanelModel.getSection(), teacherpanelModel.getSchoolId().toString(), teacherpanelModel.getSubjectid().toString()});

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return cursor.getInt(0);
        }

        return null;

    }

    public Integer TdgetAttendenceOfStudnet(TeacherpanelModel teacherpanelModel, Integer userId) {

        Integer classId = getClassId(teacherpanelModel);
        //Integer userId = 2; ////useeeerrrrrrrrrrrrrrrrrrrrrrrrrrrrrriddddddddddddddddddddddddd
        database = dbHelper.getWritableDatabase();

        String[] whereArgs = {userId.toString(), getStudentId(teacherpanelModel.getStudentUserId()).toString(), teacherpanelModel.getDate(), classId.toString()};

        Cursor cursor = database.query(CommonDataBase.TableName.Attendance.toString(),
                new String[]{CommonDataBase.Attendance.Active_flag.toString()},
                CommonDataBase.Attendance.User_id.toString() + "=? AND " + CommonDataBase.Attendance.Student_id.toString()
                        + "=? AND " + CommonDataBase.Attendance.a_date.toString() + "=? AND " + CommonDataBase.Attendance.class_id.toString() + "=?"
                , whereArgs, null, null, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return cursor.getInt(0);
        }

        return -1;
    }

    public Integer getAttendenceForAll(Integer studentUserId, TeacherpanelModel teacherpanelModel) {

        Integer stdId = getStudentId(studentUserId);
        Integer classId = getClassId(teacherpanelModel);

        database = dbHelper.getWritableDatabase();

        String[] whereArgs = {stdId.toString(), teacherpanelModel.getDate(), classId.toString()};

        Cursor cursor = database.query(CommonDataBase.TableName.Attendance.toString(),
                new String[]{CommonDataBase.Attendance.Active_flag.toString()},
                CommonDataBase.Attendance.Student_id.toString()
                        + "=? AND " + CommonDataBase.Attendance.a_date.name() + "=? AND " +
                        CommonDataBase.Attendance.class_id.toString() + "=?", whereArgs, null, null, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return cursor.getInt(0);
        }


        return -1;
    }

    public Boolean checkIfAttendenceIsNotMarkedBefore(TeacherpanelModel teacherpanelModel, Integer userId) {

        Integer classId = getClassId(teacherpanelModel);
        //Integer userId = 2; ////useeeerrrrrrrrrrrrrrrrrrrrrrrrrrrrrriddddddddddddddddddddddddd
        database = dbHelper.getWritableDatabase();

        String[] whereArgs = {userId.toString(), teacherpanelModel.getDate(), classId.toString()};

        Cursor cursor = database.query(CommonDataBase.TableName.Attendance.toString(),
                new String[]{CommonDataBase.Attendance.Active_flag.toString()},
                CommonDataBase.Attendance.User_id.toString() + "=? AND " +
                        CommonDataBase.Attendance.a_date.name() + "=? AND " + CommonDataBase.Attendance.class_id + "=?"
                , whereArgs, null, null, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return true;
        }


        return false;

    }

    public Boolean TdSaveAttendence(TM_AttendenceForAllModel attendenceForAllModel, TeacherpanelModel teacherpanelModel, Integer userId) {
        Integer classId = getClassId(teacherpanelModel);
        //Integer userId = 2; ////useeeerrrrrrrrrrrrrrrrrrrrrrrrrrrrrriddddddddddddddddddddddddd
        database = dbHelper.getWritableDatabase();


        String forNull = null;

        ContentValues values = new ContentValues();
        values.put(CommonDataBase.Attendance.Attendance_id.toString(), forNull);
        values.put(CommonDataBase.Attendance.User_id.toString(), userId);
        values.put(CommonDataBase.Attendance.Student_id.toString(), attendenceForAllModel.getStudentId());
        values.put(CommonDataBase.Attendance.a_date.toString(), teacherpanelModel.getDate());
        values.put(CommonDataBase.Attendance.a_time.toString(), getTime());
        values.put(CommonDataBase.Attendance.Active_flag.toString(), attendenceForAllModel.getAttendenceStatus());
        values.put(CommonDataBase.Attendance.class_id.toString(), classId);

        long check = database.insert(CommonDataBase.TableName.Attendance.toString(), null, values);

        if (check > 0) {

            return true;
        } else {
            return false;
        }


    }

    public List<TeacherpanelModel> TdGetNOtificationToShow(TeacherpanelModel teacherpanelModel, Integer userId) {
        List<TeacherpanelModel> All_Data = new ArrayList<TeacherpanelModel>();

        Integer classId = getClassId(teacherpanelModel);
        //Integer userId=2; ////useeeerrrrrrrrrrrrrrrrrrrrrrrrrrrrrriddddddddddddddddddddddddd
        database = dbHelper.getWritableDatabase();

        String[] whereArgs = {classId.toString(), userId.toString()};

        Cursor cursor = database.query(CommonDataBase.TableName.Notifications.toString(),
                new String[]{CommonDataBase.Notifications.Notification_id.toString(),
                        CommonDataBase.Notifications.Notification_title.toString(),
                        CommonDataBase.Notifications.Notification_Message.toString(),
                        CommonDataBase.Notifications.Flag.toString()},
                CommonDataBase.Notifications.Class_id.toString() + "=? AND " +
                        CommonDataBase.Notifications.Created_By.toString() + "=?"
                , whereArgs, null, null, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                TeacherpanelModel model = new TeacherpanelModel();

                model.setNotificationId(cursor.getInt(0));
                model.setNotificationTitle(cursor.getString(1));
                model.setNotificationMsg(cursor.getString(2));
                model.setNotificationStatus(cursor.getInt(3));

                All_Data.add(model);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return All_Data;

    }

    public Boolean addNotification(TeacherpanelModel teacherpanelModel, Integer userId) {
        Integer classId = getClassId(teacherpanelModel);
        //Integer userId=2; ////useeeerrrrrrrrrrrrrrrrrrrrrrrrrrrrrriddddddddddddddddddddddddd
        database = dbHelper.getWritableDatabase();
        String forNull = null;

        ContentValues values = new ContentValues();
        values.put(CommonDataBase.Notifications.Notification_id.toString(), forNull);
        values.put(CommonDataBase.Notifications.Class_id.toString(), classId);
        values.put(CommonDataBase.Notifications.Notification_title.toString(), teacherpanelModel.getNotificationTitle());
        values.put(CommonDataBase.Notifications.Notification_Message.toString(), teacherpanelModel.getNotificationMsg());
        values.put(CommonDataBase.Notifications.Created_Date.toString(), getDate());
        values.put(CommonDataBase.Notifications.Created_By.toString(), userId);
        values.put(CommonDataBase.Notifications.Delete_by.toString(), forNull);
        values.put(CommonDataBase.Notifications.Flag.toString(), teacherpanelModel.getNotificationStatus());

        long insertId = database.insert(CommonDataBase.TableName.Notifications.toString(), null, values);

        if (insertId > 0) {
            return true;
        } else {
            return false;
        }


    }

    public Boolean deleteNotification(Integer notiId) {
        database = dbHelper.getWritableDatabase();

        String[] whereArgs = {notiId.toString()};

        long count = database.delete(CommonDataBase.TableName.Notifications.toString()
                , CommonDataBase.Notifications.Notification_id.toString() + "=?", whereArgs);

        if (count > 0)
            return true;
        else
            return false;


    }

    public Boolean updateFlagOfNotification(Integer notiId, Integer flag) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CommonDataBase.Notifications.Flag.toString(), flag);

        String[] whereArgs = {notiId.toString()};
        long check = database.update(CommonDataBase.TableName.Notifications.toString()
                , values, CommonDataBase.Notifications.Notification_id.toString() + "=?", whereArgs);

        if (check > 0) {
            return true;
        } else {
            return false;
        }

    }

    public List<TeacherpanelModel> getAssignmentsToShow(TeacherpanelModel teacherpanelModel, Integer userId) {
        List<TeacherpanelModel> All_Data = new ArrayList<TeacherpanelModel>();

        Integer classId = getClassId(teacherpanelModel);
        //Integer userId=2; ////useeeerrrrrrrrrrrrrrrrrrrrrrrrrrrrrriddddddddddddddddddddddddd
        database = dbHelper.getWritableDatabase();
        Integer flag = 1;

        String[] whereArgs = {classId.toString(), userId.toString(), teacherpanelModel.getSubjectid().toString(), flag.toString()};


        Cursor cursor = database.query(CommonDataBase.TableName.Assignments.toString(),
                new String[]{CommonDataBase.Assignments.Assignment_id.toString(),
                        CommonDataBase.Assignments.Title.toString(),
                        CommonDataBase.Assignments.Description.toString(),
                        CommonDataBase.Assignments.totla_marks.toString(), CommonDataBase.Assignments.A_type.toString(),
                        CommonDataBase.Assignments.DeadLine_date.toString()},
                CommonDataBase.Assignments.Class_id.toString() + "=? AND " +
                        CommonDataBase.Assignments.Created_by.toString() + "=? AND " + CommonDataBase.Assignments.Subject_id.toString() + "=? AND " +
                        CommonDataBase.Assignments.Flag.toString() + "=?"
                , whereArgs, null, null, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                TeacherpanelModel model = new TeacherpanelModel();

                String d = cursor.getString(5);

                model.setAssignmentId(cursor.getInt(0));
                model.setAssignmentTitle(cursor.getString(1));
                model.setAssassignmentDes(cursor.getString(2));
                model.setAssignmenttotalMarks(cursor.getInt(3));
                model.setAssignmenttype(cursor.getString(4));

                if (d == null) {
                    model.setAssignmentDeadLine("NILL");
                } else {
                    model.setAssignmentDeadLine(cursor.getString(5));
                }

                All_Data.add(model);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return All_Data;

    }

    public Boolean deleteAssignment(TeacherpanelModel teacherpanelModel) {

        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CommonDataBase.Assignments.Flag.toString(), 0);
        values.put(CommonDataBase.Assignments.Delete_by.toString(), 2);

        String[] whereArgs = {teacherpanelModel.getAssignmentId().toString()};
        long check = database.update(CommonDataBase.TableName.Assignments.toString()
                , values, CommonDataBase.Assignments.Assignment_id.toString() + "=?", whereArgs);

        if (check > 0) {
            return true;
        } else {
            return false;
        }


    }

    public Boolean updateAssignment(TeacherpanelModel teacherpanelModel) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CommonDataBase.Assignments.Title.toString(), teacherpanelModel.getAssignmentTitle());
        values.put(CommonDataBase.Assignments.Description.toString(), teacherpanelModel.getAssassignmentDes());
        values.put(CommonDataBase.Assignments.A_type.toString(), teacherpanelModel.getAssignmenttype());
        values.put(CommonDataBase.Assignments.DeadLine_date.toString(), teacherpanelModel.getAssignmentDeadLine());
        values.put(CommonDataBase.Assignments.totla_marks.toString(), teacherpanelModel.getAssignmenttotalMarks());

        String[] whereArgs = {teacherpanelModel.getAssignmentId().toString()};
        long check = database.update(CommonDataBase.TableName.Assignments.toString()
                , values, CommonDataBase.Assignments.Assignment_id.toString() + "=?", whereArgs);

        if (check > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean addAssignment(TeacherpanelModel teacherpanelModel, Integer userId) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String forNull = null;
        Integer classId = getClassId(teacherpanelModel);
        // Integer userId=2; ////useeeerrrrrrrrrrrrrrrrrrrrrrrrrrrrrriddddddddddddddddddddddddd

        values.put(CommonDataBase.Assignments.Assignment_id.toString(), forNull);
        values.put(CommonDataBase.Assignments.Class_id.toString(), classId);
        values.put(CommonDataBase.Assignments.Title.toString(), teacherpanelModel.getAssignmentTitle());
        values.put(CommonDataBase.Assignments.Description.toString(), teacherpanelModel.getAssassignmentDes());
        values.put(CommonDataBase.Assignments.totla_marks.toString(), teacherpanelModel.getAssignmenttotalMarks());
        values.put(CommonDataBase.Assignments.Created_date.toString(), getDate());
        values.put(CommonDataBase.Assignments.DeadLine_date.toString(), teacherpanelModel.getAssignmentDeadLine());
        values.put(CommonDataBase.Assignments.Created_by.toString(), userId);
        values.put(CommonDataBase.Assignments.Delete_by.toString(), forNull);
        values.put(CommonDataBase.Assignments.Flag.toString(), 1);
        values.put(CommonDataBase.Assignments.A_type.toString(), teacherpanelModel.getAssignmenttype());
        values.put(CommonDataBase.Assignments.Subject_id.toString(), teacherpanelModel.getSubjectid());


        long check = database.insert(CommonDataBase.TableName.Assignments.toString(), null, values);

        if (check > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean updateNotification(TeacherpanelModel teacherpanelModel) {
        //Integer classId=getClassId(teacherpanelModel);
        //Integer userId=2; ////useeeerrrrrrrrrrrrrrrrrrrrrrrrrrrrrriddddddddddddddddddddddddd
        database = dbHelper.getWritableDatabase();
        //String forNull=null;

        ContentValues values = new ContentValues();
        values.put(CommonDataBase.Notifications.Notification_title.toString(), teacherpanelModel.getNotificationTitle());
        values.put(CommonDataBase.Notifications.Notification_Message.toString(), teacherpanelModel.getNotificationMsg());
        values.put(CommonDataBase.Notifications.Created_Date.toString(), getDate());
        values.put(CommonDataBase.Notifications.Flag.toString(), teacherpanelModel.getNotificationStatus());

        String[] whereArgs = {teacherpanelModel.getNotificationId().toString()};
        long insertId = database.update(CommonDataBase.TableName.Notifications.toString(), values, CommonDataBase.Notifications.Notification_id.toString() + "=?",
                whereArgs);

        if (insertId > 0) {
            return true;
        } else {
            return false;
        }


    }


    public Boolean TdUpdateSavedAttendence(TM_AttendenceForAllModel attendenceForAllModel, TeacherpanelModel teacherpanelModel, Integer userId) {
        Integer classId = getClassId(teacherpanelModel);
        //Integer userId = 2; ////useeeerrrrrrrrrrrrrrrrrrrrrrrrrrrrrriddddddddddddddddddddddddd
        database = dbHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(CommonDataBase.Attendance.Active_flag.toString(), attendenceForAllModel.getAttendenceStatus());

        String[] whereArgs = {attendenceForAllModel.getStudentId().toString(), teacherpanelModel.getDate(), classId.toString(),userId.toString()};
        long check = database.update(CommonDataBase.TableName.Attendance.toString()
                , values, CommonDataBase.Attendance.Student_id.toString() + "=? AND " +
                        CommonDataBase.Attendance.a_date.name() + "=? AND " +
                        CommonDataBase.Attendance.class_id + "=? AND "+CommonDataBase.Attendance.User_id.toString()+"=?;", whereArgs);

        if (check > 0) {

            return true;
        } else {
            return false;
        }
    }

    public String getTime() {
        Date currentTime = Calendar.getInstance().getTime();
        return currentTime.toString();
    }


    public List<TeacherpanelModel> TdGetStudentToShow(TeacherpanelModel teacherpanelModel) {

        List<TeacherpanelModel> All_Data = new ArrayList<TeacherpanelModel>();

        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + CommonDataBase.Users.First_Name.toString() + "," +
                CommonDataBase.Users.Last_Name.toString() + "," + CommonDataBase.Users.User_id.toString() + "," +
                CommonDataBase.Users.Gender.toString() + "," + CommonDataBase.Users.Mobile_num.toString()
                + " FROM " + CommonDataBase.TableName.Users.toString() +
                " Where " + CommonDataBase.Users.User_id.toString() + " IN( SELECT " + CommonDataBase.Students.User_id.toString()
                + " FROM " + CommonDataBase.TableName.Students.toString() + " Where " + CommonDataBase.Students.Class_num.toString()
                + "=? AND " + CommonDataBase.Students.School_id.toString() + "=? AND " + CommonDataBase.Students.Student_id.toString() +
                " IN ( Select " + CommonDataBase.Student_Subject.Student_Id.toString() + " From " + CommonDataBase.TableName.Student_Subject.toString() +
                " Where " + CommonDataBase.Student_Subject.Class_Id.toString() + " IN ( Select " + CommonDataBase.Classes.Class_id.toString() +
                " From " + CommonDataBase.TableName.Classes.toString() + " Where " + CommonDataBase.Classes.Class_info_id.toString() +
                " IN ( Select " + CommonDataBase.Classes_info.Class_info_id + " From " + CommonDataBase.TableName.Classes_info +
                " Where " + CommonDataBase.Classes_info.Class_sec.toString() + "=? AND " + CommonDataBase.Classes_info.School_id.toString() +
                "=? AND " + CommonDataBase.Classes_info.Subject_id.toString() + "=?))));", new String[]{teacherpanelModel.getClassNumber().toString(),
                teacherpanelModel.getSchoolId().toString(), teacherpanelModel.getSection(), teacherpanelModel.getSchoolId().toString(), teacherpanelModel.getSubjectid().toString()});


        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                TeacherpanelModel model = new TeacherpanelModel();

                model.setStudentFirstName(cursor.getString(0));
                model.setStudentLastName(cursor.getString(1));
                model.setStudentUserId(cursor.getInt(2));
                model.setStudentGender(cursor.getString(3));
                model.setStudentMobileNumber(cursor.getString(4));

                All_Data.add(model);

                cursor.moveToNext();
            }
            cursor.close();
        }

        return All_Data;

    }


    public List<TeacherpanelModel> getsectionOfTeacher(Integer subid) {
        List<TeacherpanelModel> All_Data = new ArrayList<TeacherpanelModel>();
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select " + CommonDataBase.Classes_info.Class_sec.toString() + " From " +
                CommonDataBase.TableName.Classes_info + " Where " + CommonDataBase.Classes_info.Subject_id.toString() +
                "=? AND " + CommonDataBase.Classes_info.Class_info_id.toString() + " IN ( Select " + CommonDataBase.Classes.Class_info_id.toString() +
                " From " + CommonDataBase.TableName.Classes.toString() + " Where " + CommonDataBase.Classes.Class_id.toString() +
                " IN ( Select " + CommonDataBase.Teacher_Subject.Class_Id.toString() + " From " + CommonDataBase.TableName.Teacher_Subject.toString() +
                " Where " + CommonDataBase.Teacher_Subject.User_Id.toString() + "=?));", new String[]{subid.toString(), AdminModelHelper.Admin_id.toString()});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                TeacherpanelModel model = new TeacherpanelModel();

                model.setSection(cursor.getString(0));

                All_Data.add(model);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return All_Data;

    }

    public List<TeacherpanelModel> getsubjectOfTeacher(Integer sclId, Integer classNo) {

        List<TeacherpanelModel> All_Data = new ArrayList<TeacherpanelModel>();
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select " + CommonDataBase.Subjects.Subject_name.toString()
                + " From " + CommonDataBase.TableName.Subjects.toString() + " Where " + CommonDataBase.Subjects.Subject_id.toString() +
                " IN ( Select " + CommonDataBase.Classes_info.Subject_id.toString() + " From " + CommonDataBase.TableName.Classes_info.toString() +
                " Where " + CommonDataBase.Classes_info.Class_info_id.toString() + " IN ( Select " + CommonDataBase.Classes.Class_info_id.toString() +
                " From " + CommonDataBase.TableName.Classes.toString() + " Where " + CommonDataBase.Classes.Class_id.toString()
                + " IN ( Select " + CommonDataBase.Teacher_Subject.Class_Id.toString() + " From " + CommonDataBase.TableName.Teacher_Subject.toString() +
                " Where " + CommonDataBase.Teacher_Subject.User_Id.toString() + "=?))) AND " + CommonDataBase.Subjects.School_Id.toString() + "=? AND " +
                CommonDataBase.Subjects.Class_num + "=?;", new String[]{AdminModelHelper.Admin_id.toString(), sclId.toString(), classNo.toString()});

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                TeacherpanelModel model = new TeacherpanelModel();

                model.setSubjectName(cursor.getString(0));

                All_Data.add(model);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return All_Data;
    }


    public List<TeacherpanelModel> getClassNumberOfTeacher(Integer sclId) {
        List<TeacherpanelModel> All_Data = new ArrayList<TeacherpanelModel>();
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select DISTINCT " + CommonDataBase.Subjects.Class_num.toString()
                + " From " + CommonDataBase.TableName.Subjects.toString() + " Where " + CommonDataBase.Subjects.Subject_id.toString() +
                " IN ( Select " + CommonDataBase.Classes_info.Subject_id.toString() + " From " + CommonDataBase.TableName.Classes_info.toString() +
                " Where " + CommonDataBase.Classes_info.Class_info_id.toString() + " IN ( Select " + CommonDataBase.Classes.Class_info_id.toString() +
                " From " + CommonDataBase.TableName.Classes.toString() + " Where " + CommonDataBase.Classes.Class_id.toString()
                + " IN ( Select " + CommonDataBase.Teacher_Subject.Class_Id.toString() + " From " + CommonDataBase.TableName.Teacher_Subject.toString() +
                " Where " + CommonDataBase.Teacher_Subject.User_Id.toString() + "=?))) AND "
                + CommonDataBase.Subjects.School_Id.toString() + "=?;", new String[]{AdminModelHelper.Admin_id.toString(), sclId.toString()});

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                TeacherpanelModel model = new TeacherpanelModel();


                model.setClassNumber(cursor.getInt(0));

                All_Data.add(model);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return All_Data;

    }

    public List<TdIdsModel> getSchoolAndTeacherId() {

        List<TdIdsModel> All_Data = new ArrayList<TdIdsModel>();
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select " + CommonDataBase.Teachers.School_id.toString() + "," +
                CommonDataBase.Teachers.Teacher_id.toString() + " From " + CommonDataBase.TableName.Teachers.toString() + " Where " +
                CommonDataBase.Teachers.User_id.toString() + "=?", new String[]{AdminModelHelper.Admin_id.toString()});

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                TdIdsModel model = new TdIdsModel();

                model.setSchoolId(cursor.getInt(0));
                model.setTeacherId(cursor.getInt(1));

                All_Data.add(model);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return All_Data;


    }


    public List<Integer> getClassInfoIds(Integer sub_Id, Integer school_id) {
        List<Integer> a = new ArrayList<>();
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select " + CommonDataBase.Classes_info.Class_info_id.toString() + "" +
                " From " + CommonDataBase.TableName.Classes_info.toString() + "  Where " +
                CommonDataBase.Classes_info.Subject_id.toString() + " = ? AND " +
                CommonDataBase.Classes_info.School_id.toString() + " = ? ;", new String[]{sub_Id.toString(),
                school_id.toString()});

        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                a.add(cursor.getInt(0));
                cursor.moveToNext();
            }
            return a;
        }
        return null;
    }

    public List<NotificationModel> getAllNotificationsForStudents(StudentModel studentModel) {

        Integer Sub_Id = GetSubjectIDFromName(studentModel.getCourse(), studentModel.getSchool_id());
        List<Integer> ClassInfolist = getClassInfoIds(Sub_Id, studentModel.getSchool_id());
        if (ClassInfolist != null) {
            List<Integer> ClassIds = getClassesIds(ClassInfolist);
            if (ClassIds != null) {
                Integer c_id_ofStudents = getClassIDofSpecificStudnet(ClassIds);

                if (c_id_ofStudents != null) {
                    List<NotificationModel> list = new ArrayList<>();
                    Cursor cursor = database.rawQuery("Select *" + " From " + CommonDataBase.TableName.Notifications.toString()
                            + " Where " + CommonDataBase.Notifications.Class_id.toString() + " = ? " +
                            "AND " + CommonDataBase.Notifications.Flag.toString() + " !=0", new String[]{c_id_ofStudents.toString()});

                    cursor.moveToFirst();
                    if (cursor.getCount() > 0) {
                        while (!cursor.isAfterLast()) {
                            Integer i = cursor.getInt(6);
                            if (i == 0) {
                                NotificationModel notificationModel = new NotificationModel();
                                notificationModel.setId(cursor.getInt(0));
                                notificationModel.setTitle(cursor.getString(2));
                                notificationModel.setMessage(cursor.getString(3));
                                list.add(notificationModel);
                            }
                            cursor.moveToNext();
                        }
                    }
                    return list;
                }
            }
        }
        return null;
    }

    private Integer getClassIDofSpecificStudnet(List<Integer> classIds) {
        database = dbHelper.getWritableDatabase();
        for (Integer i = 0; i < classIds.size(); i++) {
            Cursor cursor = database.rawQuery("Select " + CommonDataBase.Student_Subject.Student_Id.toString() +
                    " FROM " + CommonDataBase.TableName.Student_Subject.toString() + " Where " + CommonDataBase.Student_Subject.Class_Id.toString() + "" +
                    " =? ", new String[]{classIds.get(i).toString()});

            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                return classIds.get(i);
            }
            cursor.close();
        }
        return null;
    }

    private List<Integer> getClassesIds(List<Integer> classInfolist) {
        database = dbHelper.getWritableDatabase();

        List<Integer> ids = new ArrayList<>();

        for (int i = 0; i < classInfolist.size(); i++) {
            Cursor cursor = database.rawQuery("Select " + CommonDataBase.Classes.Class_id.toString() + "  From " +
                    CommonDataBase.TableName.Classes.toString() + " Where " + CommonDataBase.Classes.Class_info_id.toString() +
                    " = ? ", new String[]{classInfolist.get(i).toString()});

            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                ids.add(cursor.getInt(0));
            }
            cursor.close();
        }
        if (ids.size() > 0) {
            return ids;

        }
        return null;
    }


    public AssignmentModel getSingleAssignmentMarks(Integer Assignemnt_id, Integer Student_id) {
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select * from " + CommonDataBase.TableName.Marks.toString() + " Where " + CommonDataBase.Marks.Assignment_id.toString() + " = ? AND " + CommonDataBase.Marks.Student_Id.toString()
                + " = ? ;", new String[]{Assignemnt_id.toString(), Student_id.toString()});
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            AssignmentModel assignmentModel = new AssignmentModel();
            assignmentModel.setAssignment_id(cursor.getInt(1));
            assignmentModel.setObt_marks(cursor.getInt(2));
            return assignmentModel;
        }
        return null;
    }


    public List<Integer> getAssignmentIds(String type, Integer s_id, Integer teacherId) {
        List<Integer> ids = new ArrayList<Integer>();
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + CommonDataBase.Assignments.Assignment_id.toString() + "" +
                " From " + CommonDataBase.TableName.Assignments.toString() + " Where " + CommonDataBase.Assignments.A_type.toString()
                + " =? AND " + CommonDataBase.Assignments.Subject_id + " =? AND " + CommonDataBase.Assignments.Created_by + " =? AND " + CommonDataBase.Assignments.Flag.toString() + " = 1 ;", new String[]{type, s_id.toString(), teacherId.toString()});

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {

                ids.add(cursor.getInt(0));
                cursor.moveToNext();
            }
            return ids;
        }
        return null;
    }


    public Integer GetSubjectIDFromName(String name, Integer sclId) {
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select " + CommonDataBase.Subjects.Subject_id.toString() +
                " From " + CommonDataBase.TableName.Subjects.toString() + " where " +
                CommonDataBase.Subjects.Subject_name.toString() + "=? And "
                + CommonDataBase.Subjects.School_Id.toString() + "=?;", new String[]{name, sclId.toString()});

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return cursor.getInt(0);
        }

        return 0;
    }

    public Integer geTeaacherUserId(Integer T_ID) {
        database = dbHelper.getWritableDatabase();
        Cursor c = database.rawQuery("SELECT " + CommonDataBase.Teachers.User_id.toString() + " FROM " +
                CommonDataBase.TableName.Teachers.toString() + " Where " + CommonDataBase.Teachers.Teacher_id.toString() + "" +
                " =? ;", new String[]{T_ID.toString()});

        c.moveToFirst();
        if (c.getCount() > 0) {
            return c.getInt(0);
        }
        return -1;

    }

    public List<AssignmentModel> getAssignments(TeacherpanelModel teacherpanelModel, String type) {

        List<AssignmentModel> assignmentModels = new ArrayList<>();
        List<Integer> ids = getAssignmentIds(type, GetSubjectIDFromName(teacherpanelModel.getSubjectName(), teacherpanelModel.getSchoolId()), geTeaacherUserId(teacherpanelModel.getTeacherId()));
        if (ids == null) {
            return null;
        } else {
            database = dbHelper.getWritableDatabase();
            for (int i = 0; i < ids.size(); i++) {
                AssignmentModel assignmentModel = new AssignmentModel();
                assignmentModel = getSingleAssignmentMarks(ids.get(i), getStudentId(teacherpanelModel.getStudentUserId()));
                if (assignmentModel != null) {
                    Cursor cursor = database.rawQuery("Select * From " + CommonDataBase.TableName.Assignments.toString() +
                            " Where " + CommonDataBase.Assignments.Assignment_id.toString() + " = ? ; ", new String[]{ids.get(i).toString()});
                    cursor.moveToFirst();
                    if (cursor.getCount() > 0) {
                        assignmentModel.setAssignment_id(cursor.getInt(0));
                        assignmentModel.setTitle(cursor.getString(2));
                        assignmentModel.setTotla_marks(cursor.getInt(4));
                        assignmentModels.add(assignmentModel);
                    }
                }
            }
            return assignmentModels;
        }
    }


    public List<AssignmentModel> getAssignments(StudentModel studentModel, String type) {

        List<AssignmentModel> assignmentModels = new ArrayList<>();
        List<Integer> ids=null;

        Integer Sub_Id = GetSubjectIDFromName(studentModel.getCourse(), studentModel.getSchool_id());
        List<Integer> ClassInfolist = getClassInfoIds(Sub_Id, studentModel.getSchool_id());
        if (ClassInfolist != null) {
            List<Integer> ClassIds = getClassesIds(ClassInfolist);
            if (ClassIds != null) {
                Integer c_id_ofStudents = getClassIDofSpecificStudnet(ClassIds);
                Integer TeacherID = geTeaacherUserIdFromClassID(c_id_ofStudents);
                ids = getAssignmentIds(type, GetSubjectIDFromName(studentModel.getCourse(), studentModel.getSchool_id()), TeacherID);

            }
        }

        if (ids == null) {
            return null;
        } else {
            database = dbHelper.getWritableDatabase();
            for (int i = 0; i < ids.size(); i++) {
                AssignmentModel assignmentModel = new AssignmentModel();
                assignmentModel = getSingleAssignmentMarks(ids.get(i), studentModel.getS_id());
                if (assignmentModel != null) {
                    Cursor cursor = database.rawQuery("Select * From " + CommonDataBase.TableName.Assignments.toString() +
                            " Where " + CommonDataBase.Assignments.Assignment_id.toString() + " = ? ; ", new String[]{ids.get(i).toString()});
                    cursor.moveToFirst();
                    if (cursor.getCount() > 0) {
                        assignmentModel.setAssignment_id(cursor.getInt(0));
                        assignmentModel.setTitle(cursor.getString(2));
                        assignmentModel.setTotla_marks(cursor.getInt(4));
                        assignmentModels.add(assignmentModel);
                    }
                }
            }
            return assignmentModels;
        }
    }

    private Integer geTeaacherUserIdFromClassID(Integer c_id_ofStudents) {
        database = dbHelper.getWritableDatabase();
        Cursor c = database.rawQuery("Select " + CommonDataBase.Teacher_Subject.User_Id.toString() + " FROM "
                        + CommonDataBase.TableName.Teacher_Subject.toString() + " WHERE " + CommonDataBase.Teacher_Subject.Class_Id.toString() + " =?"
                , new String[]{c_id_ofStudents.toString()});
        c.moveToFirst();

        if (c.getCount() > 0) {
            return c.getInt(0);
        }
        return 0;
    }


    private Integer getTeacherIdForAssignments(Integer classInfoId) {
        database = dbHelper.getWritableDatabase();

        Cursor c = database.rawQuery("Select " + CommonDataBase.Classes.Class_id.toString() + " FROM " +
                        CommonDataBase.TableName.Classes.toString() + " where " + CommonDataBase.Classes.Class_info_id.toString() + " =?",
                new String[]{classInfoId.toString()});
        c.moveToFirst();
        if (c.getCount() > 0) {
            Cursor cursor = database.rawQuery("Select " + CommonDataBase.Teacher_Subject.User_Id.toString() + " FROM "
                    + CommonDataBase.TableName.Teacher_Subject.toString() + " Where " + CommonDataBase.Teacher_Subject.Class_Id.toString() +
                    " = ? ;", new String[]{String.valueOf(c.getInt(0))});
            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                return cursor.getInt(0);
            }
        }
        return 0;
    }


    public Boolean setAttendenceAdmin(Integer studentId, String date, Integer flag) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CommonDataBase.Attendance.Active_flag.toString(), flag);

        String[] whereArgs = {studentId.toString(), date};
        long check = database.update(CommonDataBase.TableName.Attendance.toString()
                , values, CommonDataBase.Attendance.Student_id.toString()
                        + "=? AND " + CommonDataBase.Attendance.a_date.name() + "=?", whereArgs);

        if (check > 0) {
            return true;
        } else {
            return false;
        }
    }


    public Integer getStudentId(Integer studentUserId) {
        database = dbHelper.getWritableDatabase();

        String[] whereArgs = {studentUserId.toString()};

        Cursor cursor = database.query(CommonDataBase.TableName.Students.toString(),
                new String[]{CommonDataBase.Students.Student_id.toString()}, CommonDataBase.Students.User_id.toString()
                        + "=?", whereArgs, null, null, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return cursor.getInt(0);
        }

        return null;
    }

    public Integer getClassId(String section, Integer sclId, Integer subjectId) {
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select " + CommonDataBase.Classes.Class_id.toString() + " From " + CommonDataBase.TableName.Classes.toString() +
                        " Where " + CommonDataBase.Classes.Class_info_id.toString() + " IN ( Select " + CommonDataBase.Classes_info.Class_info_id.toString() +
                        " From " + CommonDataBase.TableName.Classes_info.toString() + " where " + CommonDataBase.Classes_info.Class_sec.toString() + "=? AND " +
                        CommonDataBase.Classes_info.School_id.toString() + "=? And " + CommonDataBase.Classes_info.Subject_id.toString() + "=?);"
                , new String[]{section, sclId.toString(), subjectId.toString()});

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return cursor.getInt(0);
        }

        return null;

    }

    public Integer getAttendenceForAll(Integer studentUserId, String date, String section, Integer sclId, Integer subjectId) {

        Integer stdId = getStudentId(studentUserId);

        Integer classId = getClassId(section, sclId, subjectId);

        database = dbHelper.getWritableDatabase();

        String[] whereArgs = {stdId.toString(), date, classId.toString()};

        Cursor cursor = database.query(CommonDataBase.TableName.Attendance.toString(),
                new String[]{CommonDataBase.Attendance.Active_flag.toString()},
                CommonDataBase.Attendance.Student_id.toString()
                        + "=? AND " + CommonDataBase.Attendance.a_date.name() + "=? AND " + CommonDataBase.Attendance.class_id + "=?"
                , whereArgs, null, null, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return cursor.getInt(0);
        }


        return -1;
    }

    public List<AttendenceModel> getStudentToShow(AttendenceModel attendenceModel) {

        List<AttendenceModel> All_Data = new ArrayList<AttendenceModel>();

        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + CommonDataBase.Users.First_Name.toString() + "," +
                CommonDataBase.Users.Last_Name.toString() + "," + CommonDataBase.Users.User_id.toString()
                + " FROM " + CommonDataBase.TableName.Users.toString() +
                " Where " + CommonDataBase.Users.User_id.toString() + " IN( SELECT " + CommonDataBase.Students.User_id.toString()
                + " FROM " + CommonDataBase.TableName.Students.toString() + " Where " + CommonDataBase.Students.Class_num.toString()
                + "=? AND " + CommonDataBase.Students.School_id.toString() + "=? AND " + CommonDataBase.Students.Student_id.toString() +
                " IN ( Select " + CommonDataBase.Student_Subject.Student_Id.toString() + " From " + CommonDataBase.TableName.Student_Subject.toString() +
                " Where " + CommonDataBase.Student_Subject.Class_Id.toString() + " IN ( Select " + CommonDataBase.Classes.Class_id.toString() +
                " From " + CommonDataBase.TableName.Classes.toString() + " Where " + CommonDataBase.Classes.Class_info_id.toString() +
                " IN ( Select " + CommonDataBase.Classes_info.Class_info_id + " From " + CommonDataBase.TableName.Classes_info +
                " Where " + CommonDataBase.Classes_info.Class_sec.toString() + "=? AND " + CommonDataBase.Classes_info.School_id.toString() +
                "=? AND " + CommonDataBase.Classes_info.Subject_id.toString() + "=?))));", new String[]{attendenceModel.getClassNumber().toString(),
                attendenceModel.getSchoolId().toString(), attendenceModel.getSection(), attendenceModel.getSchoolId().toString(), attendenceModel.getSubjectid().toString()});


        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                AttendenceModel model = new AttendenceModel();

                model.setStudentFirstName(cursor.getString(0));
                model.setStudentLastName(cursor.getString(1));
                model.setStudentUserId(cursor.getInt(2));

                All_Data.add(model);

                cursor.moveToNext();
            }
            cursor.close();
        }

        return All_Data;

    }

    public Integer getSubjectId(String name, Integer sclId, Integer classNo) {
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select " + CommonDataBase.Subjects.Subject_id.toString() +
                " From " + CommonDataBase.TableName.Subjects.toString() + " where "
                + CommonDataBase.Subjects.Class_num.toString() + "=? And " +
                CommonDataBase.Subjects.Subject_name.toString() + "=? And "
                + CommonDataBase.Subjects.School_Id.toString() + "=?;", new String[]{classNo.toString(), name, sclId.toString()});

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return cursor.getInt(0);
        }

        return 0;
    }

    public List<AttendenceModel> getsectionOfSchool(Integer subid) {

        List<AttendenceModel> All_Data = new ArrayList<AttendenceModel>();
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select " + CommonDataBase.Classes_info.Class_sec.toString() + " From " +
                CommonDataBase.TableName.Classes_info + " Where " + CommonDataBase.Classes_info.Subject_id.toString() +
                "=? AND " + CommonDataBase.Classes_info.Class_info_id.toString() + " IN (Select " +
                CommonDataBase.Classes.Class_info_id.toString() + " From " + CommonDataBase.TableName.Classes.toString() + " where " +
                CommonDataBase.Classes.Flag.toString() + "=?);", new String[]{subid.toString(), "1"});

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                AttendenceModel model = new AttendenceModel();

                model.setSection(cursor.getString(0));

                All_Data.add(model);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return All_Data;

    }

    public List<AttendenceModel> getsubjectOfSchool(Integer sclId, Integer classNo) {

        List<AttendenceModel> All_Data = new ArrayList<AttendenceModel>();
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select " + CommonDataBase.Subjects.Subject_name.toString()
                + " From " + CommonDataBase.TableName.Subjects.toString() + " Where " + CommonDataBase.Subjects.Status.toString() + "=? AND "
                + CommonDataBase.Subjects.Subject_id.toString() +
                " IN ( Select " + CommonDataBase.Classes_info.Subject_id.toString() + " From " + CommonDataBase.TableName.Classes_info.toString() +
                ") AND " + CommonDataBase.Subjects.School_Id.toString() + "=? AND " + CommonDataBase.Subjects.Class_num.toString() +
                "=?;", new String[]{"1", sclId.toString(), classNo.toString()});

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                AttendenceModel model = new AttendenceModel();

                model.setSubjectName(cursor.getString(0));

                All_Data.add(model);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return All_Data;
    }

    public List<AttendenceModel> getClassNumberOfSchool(Integer sclId) {
        List<AttendenceModel> All_Data = new ArrayList<AttendenceModel>();
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select DISTINCT " + CommonDataBase.Subjects.Class_num.toString()
                + " From " + CommonDataBase.TableName.Subjects.toString() + " Where " + CommonDataBase.Subjects.Status.toString() + "=? AND " + CommonDataBase.Subjects.Subject_id.toString() +
                " IN ( Select " + CommonDataBase.Classes_info.Subject_id.toString() + " From " + CommonDataBase.TableName.Classes_info.toString() +
                ") AND " + CommonDataBase.Subjects.School_Id.toString() + "=?;", new String[]{"1", sclId.toString()});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                AttendenceModel model = new AttendenceModel();
                model.setClassNumber(cursor.getInt(0));
                All_Data.add(model);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return All_Data;

    }


    public Boolean insert_update_Classes(ClassInfoModel classInfoModel) {
        database = dbHelper.getWritableDatabase();

        if (alreadyAlocated(classInfoModel.getClass_info_id())) {
            ContentValues values = new ContentValues();
            values.put(CommonDataBase.Classes.class_end_time.toString(), classInfoModel.getClass_end_time());
            values.put(CommonDataBase.Classes.class_start_time.toString(), classInfoModel.getClass_start_time());
            values.put(CommonDataBase.Classes.Room_id.toString(), classInfoModel.getRoom_id().toString());

            long c = database.update(CommonDataBase.TableName.Classes.toString(),
                    values, CommonDataBase.Classes.Class_info_id + " =?", new String[]{classInfoModel.getClass_info_id().toString()});
            if (c > 0) {
                return true;
            }
        } else {
            ContentValues values = new ContentValues();
            String a = null;
            Integer roomId = 0;
            values.put(CommonDataBase.Classes.Class_id.toString(), a);
            values.put(CommonDataBase.Classes.Class_info_id.toString(), classInfoModel.getClass_info_id().toString());
            values.put(CommonDataBase.Classes.class_end_time.toString(), classInfoModel.getClass_end_time());
            values.put(CommonDataBase.Classes.class_start_time.toString(), classInfoModel.getClass_start_time());
            values.put(CommonDataBase.Classes.Room_id.toString(), classInfoModel.getRoom_id().toString());
            values.put(CommonDataBase.Classes.Flag.toString(), 1);

            long c = database.insert(CommonDataBase.TableName.Classes.toString(), null, values);
            if (c > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean alreadyAlocated(Integer class_info_id) {
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select " + CommonDataBase.Classes_info.Class_info_id.toString() + " " +
                " From " + CommonDataBase.TableName.Classes.toString() + " Where " + CommonDataBase.Classes_info.Class_info_id.toString() +
                " =?", new String[]{class_info_id.toString()});

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }


    public Boolean addSection(ClassInfoModel classInfoModel) {
        database = dbHelper.getWritableDatabase();

        String id = null;
        ContentValues contentValues = new ContentValues();
        contentValues.put(CommonDataBase.Classes_info.Class_info_id.toString(), id);
        contentValues.put(CommonDataBase.Classes_info.School_id.toString(), classInfoModel.getSchool_id());
        contentValues.put(CommonDataBase.Classes_info.Subject_id.toString(), classInfoModel.getSubject_id());
        contentValues.put(CommonDataBase.Classes_info.Strength.toString(), 0);
        contentValues.put(CommonDataBase.Classes_info.Academic_id.toString(), 1);
        contentValues.put(CommonDataBase.Classes_info.Class_sec.toString(), classInfoModel.getClass_sec());
        long c = database.insert(CommonDataBase.TableName.Classes_info.toString(), null, contentValues);
        if (c > 0) {
            return true;
        }
        return false;
    }


    public Boolean addSchool(SchoolModel schoolModel) {
        database = dbHelper.getWritableDatabase();

        String forNull = null;
        //Integer userId = 1;
        Integer status = 1;
        ContentValues values = new ContentValues();

        values.put(CommonDataBase.School.School_id.toString(), forNull);
        values.put(CommonDataBase.School.School_name.toString(), schoolModel.getSchoolName());
        values.put(CommonDataBase.School.School_address.toString(), schoolModel.getSchoolAdress());
        values.put(CommonDataBase.School.phone_number.toString(), schoolModel.getPhoneNO());
        values.put(CommonDataBase.School.Created_By.toString(), AdminModelHelper.Admin_id);
        values.put(CommonDataBase.School.Delete_by.toString(), forNull);
        values.put(CommonDataBase.School.Status.toString(), status);
        values.put(CommonDataBase.School.Created_Date.toString(), getDate());
        values.put(CommonDataBase.School.Deleted_Date.toString(), forNull);
        values.put(CommonDataBase.School.Allocated_to.toString(), forNull);

        long check = database.insert(CommonDataBase.TableName.School.toString(), null, values);
        if (check > 0) {
            return true;
        } else {
            return false;
        }
    }


    public List<HolidayModel> getHolidays(Integer s_id) {
        database = dbHelper.getWritableDatabase();
        List<HolidayModel> list = new ArrayList<HolidayModel>();
        Cursor c = database.rawQuery("Select  * " +
                " from " + CommonDataBase.TableName.Holidays.toString() + " Where " +
                CommonDataBase.Holidays.School_id + " =?", new String[]{s_id.toString()});

        c.moveToFirst();
        if (c.getCount() > 0) {
            while (!c.isAfterLast()) {
                HolidayModel holidayModel = new HolidayModel();
                holidayModel.setId(c.getInt(0));
                holidayModel.setDate(c.getString(1));
                holidayModel.setTitle(c.getString(2));
                list.add(holidayModel);
                c.moveToNext();
            }
            c.close();
            return list;
        }
        return null;
    }

    public Boolean updateHoliday(HolidayModel holidayModel) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CommonDataBase.Holidays.Holiday_date.toString(), holidayModel.getDate());
        values.put(CommonDataBase.Holidays.Holiday_title.toString(), holidayModel.getTitle());

        String[] whereArgs = {holidayModel.getDate().toString()};
        long check = database.update(CommonDataBase.TableName.Holidays.toString()
                , values, CommonDataBase.Holidays.Holiday_date.toString() + "=?", whereArgs);

        if (check > 0) {
            return true;
        } else {
            return false;
        }

    }

    public Boolean addHoliday(HolidayModel holidayModel, String school_Id) {
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select " + CommonDataBase.Holidays.Holiday_date.toString() + ","
                + CommonDataBase.Holidays.Holiday_id.toString() +
                " From " + CommonDataBase.TableName.Holidays.toString() + " Where " +
                CommonDataBase.Holidays.Holiday_date.toString() + " =? AND " + CommonDataBase.Holidays.School_id.toString() +
                " = ? ;", new String[]{holidayModel.getDate(), school_Id});

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            if (updateHoliday(holidayModel)) {
                return true;
            }
            return false;
        } else {
            String forNull = null;
            Integer userId = AdminModelHelper.Admin_id;
            ContentValues values = new ContentValues();

            values.put(CommonDataBase.Holidays.Holiday_id.toString(), forNull);
            values.put(CommonDataBase.Holidays.Holiday_date.toString(), holidayModel.getDate());
            values.put(CommonDataBase.Holidays.Holiday_title.toString(), holidayModel.getTitle());
            values.put(CommonDataBase.Holidays.Added_by.toString(), userId);
            values.put(CommonDataBase.Holidays.Delete_by.toString(), forNull);
            values.put(CommonDataBase.Holidays.School_id.toString(), school_Id);
            long check = database.insert(CommonDataBase.TableName.Holidays.toString(), null, values);
            if (check > 0) {
                return true;
            } else {
                return false;
            }

        }
    }

    public Integer getAdminId(String school_id) {
        Integer userid = 0;
        database = dbHelper.getWritableDatabase();
        Cursor cursor2;
        cursor2 = database.rawQuery("Select  " + CommonDataBase.School.Allocated_to.toString() +
                " from " + CommonDataBase.TableName.School.toString() + " Where " +
                CommonDataBase.School.School_id + " =?", new String[]{school_id.toString()});

        cursor2.moveToFirst();
        if (cursor2.getCount() > 0) {
            userid = cursor2.getInt(0);
        }
        return userid;
    }

    public Boolean updateAdmin(AdminModel adminModel, String school_id) {
        Integer userid = getAdminId(school_id);
        database = dbHelper.getWritableDatabase();
        Integer activeFlag = 1;
        String forNull = null;
        String role = "Sub Admin";

        if(CheckUsernameAvailable(adminModel.getUserName()))
        {
            Toast.makeText(context," USER NAME TAKEN ",Toast.LENGTH_SHORT).show();
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(CommonDataBase.Users.User_name.toString(), adminModel.getUserName());
        values.put(CommonDataBase.Users.Password.toString(), adminModel.getPassword());
        values.put(CommonDataBase.Users.Role.toString(), role);
        values.put(CommonDataBase.Users.Created_Date.toString(), getDate());
        values.put(CommonDataBase.Users.Deleted_Date.toString(), forNull);
        values.put(CommonDataBase.Users.Activity_flag.toString(), activeFlag);
        values.put(CommonDataBase.Users.Mobile_num.toString(), adminModel.getMobileNo());
        values.put(CommonDataBase.Users.Gender.toString(), adminModel.getGender());
        values.put(CommonDataBase.Users.First_Name.toString(), adminModel.getFirstName());
        values.put(CommonDataBase.Users.Last_Name.toString(), adminModel.getLastName());
        values.put(CommonDataBase.Users.City.toString(), adminModel.getCity());
        values.put(CommonDataBase.Users.Postal_code.toString(), adminModel.getPostalCode());
        values.put(CommonDataBase.Users.Email.toString(), adminModel.getEmail());

        long check = database.update(CommonDataBase.TableName.Users.toString(), values,
                CommonDataBase.Users.User_id.toString() + "=?", new String[]{userid.toString()});
        if (check > 0) {
            return true;
        }
        return false;
    }

    public AdminModel getAdminDetails(String school_id) {
        database = dbHelper.getWritableDatabase();
        Cursor cursor;
        cursor = database.rawQuery("Select * " +
                " from " + CommonDataBase.TableName.Users.toString() + " Where " +
                CommonDataBase.Users.User_id + " =?", new String[]{getAdminId(school_id).toString()});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            AdminModel adminModel = new AdminModel();
            adminModel.setUserName(cursor.getString(1));
            adminModel.setPassword(cursor.getString(2));
            adminModel.setMobileNo(cursor.getString(7));
            adminModel.setFirstName(cursor.getString(9));
            adminModel.setLastName(cursor.getString(10));
            adminModel.setGender(cursor.getString(8));
            adminModel.setCity(cursor.getString(11));
            adminModel.setPostalCode(cursor.getString(12));
            adminModel.setEmail(cursor.getString(13));
            return adminModel;
        }
        return null;
    }

    public Integer checkAllocated(Integer schhol_id) {
        database = dbHelper.getWritableDatabase();
        Cursor cursor;
        cursor = database.rawQuery("Select " + CommonDataBase.School.Allocated_to.toString() +
                " from " + CommonDataBase.TableName.School.toString() + " Where " +
                CommonDataBase.School.School_id + " =?", new String[]{schhol_id.toString()});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            try {
                Integer a = cursor.getInt(0);
                return a;
            } catch (NullPointerException e) {
                return -1;
            }
        }
        return -1;
    }

    public boolean deleteadmin(AdminModel adminModel, String School_id) {
        Integer user_id = getAdminId(School_id);
        database = dbHelper.getWritableDatabase();
        long c1, c2;

        ContentValues contentValues = new ContentValues();
        contentValues.put(CommonDataBase.Users.Activity_flag.toString(), 0);
        contentValues.put(CommonDataBase.Users.Deleted_Date.toString(), getDate());
        c1 = database.update(CommonDataBase.TableName.Users.toString(),
                contentValues, CommonDataBase.Users.User_id.toString() + "=?", new String[]{user_id.toString()});

        ContentValues values = new ContentValues();
        String a = null;
        values.put(CommonDataBase.School.Allocated_to.toString(), a);
        c2 = database.update(CommonDataBase.TableName.School.toString(), values, CommonDataBase.School.Allocated_to + "=" + user_id.toString(), null);
        if (c1 > 0 && c2 > 0) {
            return true;
        }
        return false;
    }


    Boolean CheckUsernameAvailable(String USername)
    {
        database=dbHelper.getWritableDatabase();
        Cursor cursor=database.rawQuery("Select * from "+CommonDataBase.TableName.Users.toString()+" Where "+CommonDataBase.Users.User_name.toString()+"" +
                " = ? ;",new String []{USername});

        cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
            return true;
        }
        return  false;
    }

    public Boolean addAdmin(AdminModel adminModel, String School_id) {
        database = dbHelper.getWritableDatabase();
        Integer userId = 0;
        Integer activeFlag = 1;
        String forNull = null;
        String role = "Sub Admin";

        if(CheckUsernameAvailable(adminModel.getUserName()))
        {
            Toast.makeText(context," USER NAME TAKEN ",Toast.LENGTH_SHORT).show();
            return false;
        }


        ContentValues values = new ContentValues();
        values.put(CommonDataBase.Users.User_id.toString(), forNull);
        values.put(CommonDataBase.Users.User_name.toString(), adminModel.getUserName());
        values.put(CommonDataBase.Users.Password.toString(), adminModel.getPassword());
        values.put(CommonDataBase.Users.Role.toString(), role);
        values.put(CommonDataBase.Users.Created_Date.toString(), getDate());
        values.put(CommonDataBase.Users.Deleted_Date.toString(), forNull);
        values.put(CommonDataBase.Users.Activity_flag.toString(), activeFlag);
        values.put(CommonDataBase.Users.Mobile_num.toString(), adminModel.getMobileNo());
        values.put(CommonDataBase.Users.Gender.toString(), adminModel.getGender());
        values.put(CommonDataBase.Users.First_Name.toString(), adminModel.getFirstName());
        values.put(CommonDataBase.Users.Last_Name.toString(), adminModel.getLastName());
        values.put(CommonDataBase.Users.City.toString(), adminModel.getCity());
        values.put(CommonDataBase.Users.Postal_code.toString(), adminModel.getPostalCode());
        values.put(CommonDataBase.Users.Email.toString(), adminModel.getEmail());

        long check = database.insert(CommonDataBase.TableName.Users.toString(), null, values);
        if (check > 0) {
            Cursor cursor = database.rawQuery("Select " + CommonDataBase.Users.User_id.toString()
                    + " from " + CommonDataBase.TableName.Users.toString(), new String[]{});

            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                userId = cursor.getInt(0);
                cursor.close();
                String[] whereargs = {School_id};

                ContentValues contentValues = new ContentValues();
                contentValues.put(CommonDataBase.School.Allocated_to.toString(), userId.toString());
                long check2 = database.update(CommonDataBase.TableName.School.toString(), contentValues, CommonDataBase.School.School_id.toString()
                        + "=? ", whereargs);

                if (check2 > 0) {
                    return true;
                }
            } else {
                return false;
            }
            return false;
        }
        return false;
    }

    public Boolean updateSubject(SubjectModel subjectModel) {

        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CommonDataBase.Subjects.Subject_name.toString(), subjectModel.getSubject_name());
        values.put(CommonDataBase.Subjects.Class_num.toString(), subjectModel.getClass_number());

        String[] whereArgs = {subjectModel.getSubject_id().toString()};
        long check = database.update(CommonDataBase.TableName.Subjects.toString()
                , values, CommonDataBase.Subjects.Subject_id.toString() + "=?", whereArgs);

        if (check > 0) {
            return true;
        } else {
            return false;
        }

    }

    public Boolean delteSubject(Integer subjectId, Integer userId) {

        database = dbHelper.getWritableDatabase();

        String[] whereArgs = {subjectId.toString()};

        Cursor cursor = database.query(CommonDataBase.TableName.Classes_info.toString(),
                new String[]{CommonDataBase.Classes_info.Class_info_id.toString()},
                CommonDataBase.Classes_info.Subject_id.toString() + "=?", whereArgs, null, null, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return false;
        } else {

            Integer activeFlag = 0;
            ContentValues values = new ContentValues();

            values.put(CommonDataBase.Subjects.Status.toString(), activeFlag);
            values.put(CommonDataBase.Subjects.Delete_by.toString(), userId);
            values.put(CommonDataBase.Subjects.Deleted_Date.toString(), getDate());

            long count = database.update(CommonDataBase.TableName.Subjects.toString(), values
                    , CommonDataBase.Subjects.Subject_id.toString() + "=?", whereArgs);

            if (count > 0) {
                return true;
            } else {
                return false;
            }

        }


    }

    public Boolean addSubject(SubjectModel subjectModel, Integer schhol_id) {

        database = dbHelper.getWritableDatabase();

        Integer activeFlag = 1;
        String forNull = null;
        Integer schoolid = schhol_id;

        ContentValues values = new ContentValues();

        values.put(CommonDataBase.Subjects.Subject_id.toString(), forNull);
        values.put(CommonDataBase.Subjects.Subject_name.toString(), subjectModel.getSubject_name());
        values.put(CommonDataBase.Subjects.Class_num.toString(), subjectModel.getClass_number());
        values.put(CommonDataBase.Subjects.School_Id.toString(), schoolid);
        values.put(CommonDataBase.Subjects.Created_By.toString(), AdminModelHelper.Admin_id.toString());
        values.put(CommonDataBase.Subjects.Delete_by.toString(), forNull);
        values.put(CommonDataBase.Subjects.Created_Date.toString(), getDate());
        values.put(CommonDataBase.Subjects.Deleted_Date.toString(), forNull);
        values.put(CommonDataBase.Subjects.Status.toString(), activeFlag);

        long check = database.insert(CommonDataBase.TableName.Subjects.toString(), null, values);

        if (check > 0) {

            return true;
        } else {
            return false;
        }

    }

    public Boolean updateRoom(Integer roomNo, Integer roomId) {

        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CommonDataBase.Rooms.Room_num.toString(), roomNo);

        String[] whereArgs = {roomId.toString()};
        long check = database.update(CommonDataBase.TableName.Rooms.toString()
                , values, CommonDataBase.Rooms.Room_id.toString() + "=?", whereArgs);

        if (check > 0) {
            return true;
        } else {
            return false;
        }
    }


    public Boolean deleteRoom(Integer roomId) {
        database = dbHelper.getWritableDatabase();

        String[] whereArgs = {roomId.toString()};

        Cursor cursor = database.query(CommonDataBase.TableName.Classes.toString(),
                new String[]{CommonDataBase.Classes.Class_id.toString()},
                CommonDataBase.Classes.Room_id + "=?", whereArgs, null, null, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return false;
        } else {

            Integer activeFlag = 0;
            Integer userId = AdminModelHelper.Admin_id;

            ContentValues values = new ContentValues();

            values.put(CommonDataBase.Rooms.Status.toString(), activeFlag);
            values.put(CommonDataBase.Rooms.Delete_by.toString(), userId);
            values.put(CommonDataBase.Rooms.Deleted_Date.toString(), getDate());

            long count = database.update(CommonDataBase.TableName.Rooms.toString(), values
                    , CommonDataBase.Rooms.Room_id.toString() + "=?", whereArgs);

            if (count > 0) {
                return true;
            } else {
                return false;
            }

        }

    }

    public Boolean addRoom(Integer roomNO, Integer schoolId) {
        database = dbHelper.getWritableDatabase();

        Integer activeFlag = 1;
        String forNull = null;

        ContentValues values = new ContentValues();

        values.put(CommonDataBase.Rooms.Room_id.toString(), forNull);
        values.put(CommonDataBase.Rooms.Room_num.toString(), roomNO);
        values.put(CommonDataBase.Rooms.School_Id.toString(), schoolId);
        values.put(CommonDataBase.Rooms.Created_By.toString(), AdminModelHelper.Admin_id);
        values.put(CommonDataBase.Rooms.Delete_by.toString(), forNull);
        values.put(CommonDataBase.Rooms.Created_Date.toString(), getDate());
        values.put(CommonDataBase.Rooms.Deleted_Date.toString(), forNull);
        values.put(CommonDataBase.Rooms.Status.toString(), activeFlag);

        long check = database.insert(CommonDataBase.TableName.Rooms.toString(), null, values);

        if (check > 0) {
            return true;
        } else {
            return false;
        }

    }

    public Boolean addTeacher(String firstNameS, String lastNameS, String cityS, String postalS,
                              String emailS, String mobileNoS, String userNameS, String passwordS, String genderS,
                              String schoolId) {
        database = dbHelper.getWritableDatabase();
        Integer userId = 0;
        Integer ac = 1;
        String a = null;
        String t = "Teacher";

        if(CheckUsernameAvailable(userNameS))
        {
            Toast.makeText(context," USER NAME TAKEN ",Toast.LENGTH_SHORT).show();
            return false;
        }

        ContentValues values = new ContentValues();

        values.put(CommonDataBase.Users.User_id.toString(), a);
        values.put(CommonDataBase.Users.User_name.toString(), userNameS);
        values.put(CommonDataBase.Users.Password.toString(), passwordS);
        values.put(CommonDataBase.Users.Role.toString(), t);
        values.put(CommonDataBase.Users.Created_Date.toString(), getDate());
        values.put(CommonDataBase.Users.Deleted_Date.toString(), a);
        values.put(CommonDataBase.Users.Activity_flag.toString(), ac);
        values.put(CommonDataBase.Users.Mobile_num.toString(), mobileNoS);
        values.put(CommonDataBase.Users.Gender.toString(), genderS);
        values.put(CommonDataBase.Users.First_Name.toString(), firstNameS);
        values.put(CommonDataBase.Users.Last_Name.toString(), lastNameS);
        values.put(CommonDataBase.Users.City.toString(), cityS);
        values.put(CommonDataBase.Users.Postal_code.toString(), postalS);
        values.put(CommonDataBase.Users.Email.toString(), emailS);


        long check = database.insert(CommonDataBase.TableName.Users.toString(), null, values);

        if (check > 0) {
            Cursor cursor = database.rawQuery("Select " + CommonDataBase.Users.User_id.toString()
                    + " from " + CommonDataBase.TableName.Users.toString(), new String[]{});

            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                userId = cursor.getInt(0);
                cursor.close();
                database.execSQL("INSERT INTO `teachers` (`Teacher_id`, `User_id`, `School_id`,`Created_by`,`Delete_by`)" +
                        "VALUES ( NULL , " + userId + " ," + schoolId + ", " + AdminModelHelper.Admin_id.toString() + " , NULL);");

                return true;
            }
        } else {
            return false;
        }
        return false;
    }


    public Boolean updateTeacher(String firstNameS, String lastNameS, String cityS, String postalS,
                                 String emailS, String mobileNoS, String userNameS, String passwordS, String genderS, Integer userId) {


        if(CheckUsernameAvailable(userNameS))
        {
            Toast.makeText(context," USER NAME TAKEN ",Toast.LENGTH_SHORT).show();
            return false;
        }

        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CommonDataBase.Users.User_name.toString(), userNameS);
        values.put(CommonDataBase.Users.Password.toString(), passwordS);
        values.put(CommonDataBase.Users.Created_Date.toString(), getDate());
        values.put(CommonDataBase.Users.Mobile_num.toString(), mobileNoS);
        values.put(CommonDataBase.Users.Gender.toString(), genderS);
        values.put(CommonDataBase.Users.First_Name.toString(), firstNameS);
        values.put(CommonDataBase.Users.Last_Name.toString(), lastNameS);
        values.put(CommonDataBase.Users.City.toString(), cityS);
        values.put(CommonDataBase.Users.Postal_code.toString(), postalS);
        values.put(CommonDataBase.Users.Email.toString(), emailS);

        String[] whereArgs = {userId.toString()};

        long check = database.update(CommonDataBase.TableName.Users.toString(), values, CommonDataBase.Users.User_id.toString() + "=?", whereArgs);

        if (check > 0) {
            return true;
        } else {
            return false;
        }

    }

    public Boolean updateStudent
            (String school_id, String firstNameS, String lastNameS, String cityS, String postalS, String emailS, String mobileNoS, String userNameS, String passwordS, String genderS, String clas_num
                    , String userId, String std_id) {


        if(CheckUsernameAvailable(userNameS))
        {
            Toast.makeText(context," USER NAME TAKEN ",Toast.LENGTH_SHORT).show();
            return false;
        }

        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CommonDataBase.Users.User_name.toString(), userNameS);
        values.put(CommonDataBase.Users.Password.toString(), passwordS);
        values.put(CommonDataBase.Users.Created_Date.toString(), getDate());
        values.put(CommonDataBase.Users.Mobile_num.toString(), mobileNoS);
        values.put(CommonDataBase.Users.Gender.toString(), genderS);
        values.put(CommonDataBase.Users.First_Name.toString(), firstNameS);
        values.put(CommonDataBase.Users.Last_Name.toString(), lastNameS);
        values.put(CommonDataBase.Users.City.toString(), cityS);
        values.put(CommonDataBase.Users.Postal_code.toString(), postalS);
        values.put(CommonDataBase.Users.Email.toString(), emailS);

        String[] whereArgs = {userId.toString()};

        long check = database.update(CommonDataBase.TableName.Users.toString(), values, CommonDataBase.Users.User_id.toString() + "=?", whereArgs);

        if (check > 0) {
            String[] whereArgs2 = {school_id};
            ContentValues values2 = new ContentValues();
            values2.put(CommonDataBase.Students.Class_num.toString(), clas_num);
            long check2 = database.update(CommonDataBase.TableName.Students.toString(), values2, CommonDataBase.Students.Student_id.toString() + "=?", whereArgs2);
            if (check2 > 0) {
                return true;
            }
        }
        return false;
    }


    public Boolean deleteStudent(StudentModel model) {
        database = dbHelper.getWritableDatabase();
        long c1, c2;
        c1 = database.delete(CommonDataBase.TableName.Students.toString(), CommonDataBase.Students.Student_id.toString() + "=" + model.getS_id().toString()
                , null);
        ContentValues values = new ContentValues();
        values.put(CommonDataBase.Users.Deleted_Date.toString(), getDate());
        values.put(CommonDataBase.Users.Activity_flag.toString(), 0);
        c2 = database.update(CommonDataBase.TableName.Users.toString(), values, CommonDataBase.Users.User_id + "=" + model.getUser_id(), null);
        if (c1 > 0 && c2 > 0) {
            return true;
        }
        return false;
    }

    public Boolean deleteTeacher(TeacherModel model) {
        database = dbHelper.getWritableDatabase();
        long c1, c2;
        c1 = database.delete(CommonDataBase.TableName.Teachers.toString(), CommonDataBase.Teachers.Teacher_id.toString() + "= ?",
                new String[]{model.getId().toString()});

        ContentValues values = new ContentValues();
        values.put(CommonDataBase.Users.Deleted_Date.toString(), getDate());
        values.put(CommonDataBase.Users.Activity_flag.toString(), 0);
        c2 = database.update(CommonDataBase.TableName.Users.toString(), values, CommonDataBase.Users.User_id + "=" + model.getUserId(), null);

        if (c1 > 0 && c2 > 0) {
            return true;
        }
        return false;
    }


    public Boolean addStudent(String school_id, String firstNameS, String lastNameS, String cityS, String postalS, String emailS, String mobileNoS, String userNameS, String passwordS, String genderS, String clas_num) {
        database = dbHelper.getWritableDatabase();
        Integer userId = 0;
        Integer ac = 1;
        String a = null;
        String t = "Student";

        if(CheckUsernameAvailable(userNameS))
        {
            Toast.makeText(context," USER NAME TAKEN ",Toast.LENGTH_SHORT).show();
            return false;
        }


        ContentValues values = new ContentValues();

        values.put(CommonDataBase.Users.User_id.toString(), a);
        values.put(CommonDataBase.Users.User_name.toString(), userNameS);
        values.put(CommonDataBase.Users.Password.toString(), passwordS);
        values.put(CommonDataBase.Users.Role.toString(), t);
        values.put(CommonDataBase.Users.Created_Date.toString(), getDate());
        values.put(CommonDataBase.Users.Deleted_Date.toString(), a);
        values.put(CommonDataBase.Users.Activity_flag.toString(), ac);
        values.put(CommonDataBase.Users.Mobile_num.toString(), mobileNoS);
        values.put(CommonDataBase.Users.Gender.toString(), genderS);
        values.put(CommonDataBase.Users.First_Name.toString(), firstNameS);
        values.put(CommonDataBase.Users.Last_Name.toString(), lastNameS);
        values.put(CommonDataBase.Users.City.toString(), cityS);
        values.put(CommonDataBase.Users.Postal_code.toString(), postalS);
        values.put(CommonDataBase.Users.Email.toString(), emailS);

        //values.put(CommonDataBase.Students.Class_num.toString(),clas_num);

        long check = database.insert(CommonDataBase.TableName.Users.toString(), null, values);

        if (check > 0) {
            Cursor cursor = database.rawQuery("Select " + CommonDataBase.Users.User_id.toString()
                    + " from " + CommonDataBase.TableName.Users.toString(), new String[]{});

            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                userId = cursor.getInt(0);
                cursor.close();
                Integer addmin_id;
                addmin_id = AdminModelHelper.Admin_id;


                ContentValues values1 = new ContentValues();
                values1.put(CommonDataBase.Students.Student_id.toString(), a);
                values1.put(CommonDataBase.Students.User_id.toString(), userId);
                values1.put(CommonDataBase.Students.School_id.toString(), school_id);
                values1.put(CommonDataBase.Students.Class_num.toString(), clas_num);
                values1.put(CommonDataBase.Students.Created_By.toString(), addmin_id);
                values1.put(CommonDataBase.Students.Delete_by.toString(), a);


                long v = database.insert(CommonDataBase.TableName.Students.toString(),
                        null, values1);

                if (v > 0) {
                    return true;
                }
/*                String q="INSERT INTO `students` (`Student_id`, `User_id`, `School_id`, `Class_num`,`Created_by`,`Delete_by`)" +
                        " VALUES (NULL,"+userId+","+Integer.parseInt(school_id)+","+Integer.parseInt(clas_num)+","+addmin_id.toString()+",NULL)";

                Log.e("ccccc",q);
                database.execSQL("INSERT INTO `students` (`Student_id`, `User_id`, `School_id`, `Class_num`,`Created_by`,`Delete_by`)" +
                        " VALUES (NULL,"+userId+","+Integer.parseInt(school_id)+","+Integer.parseInt(clas_num)+","+addmin_id.toString()+",NULL)");
*/
            }
        }

        return false;
    }


    public long deleteCourseForTeacher(TeacherModel teacherModel) {

        Integer classId = 0;
        List<Integer> classList = new ArrayList<Integer>();
        int i = 0;
        long count = 0;

        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select " + CommonDataBase.Classes.Class_id.toString() + " From " + CommonDataBase.TableName.Classes.toString() + " Where " +
                        CommonDataBase.Classes.Class_info_id.toString() + " IN ( Select " + CommonDataBase.Classes_info.Class_info_id.toString() + " From " +
                        CommonDataBase.TableName.Classes_info.toString() + " Where "
                        + CommonDataBase.Classes_info.Subject_id.toString() + " IN ( Select " +
                        CommonDataBase.Subjects.Subject_id.toString() + " From " + CommonDataBase.TableName.Subjects + " Where " +
                        CommonDataBase.Subjects.Subject_name + "=?)" + "AND " + CommonDataBase.Subjects.School_Id + " =?);",
                new String[]{teacherModel.getCourse(), teacherModel.getSchoolId().toString()});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                classId = cursor.getInt(0);
                classList.add(classId);
                cursor.moveToNext();
            }
            cursor.close();

            for (i = 0; i < classList.size(); i++) {
                String[] whereArgs = {teacherModel.getUserId().toString(), classList.get(i).toString()};
                count = database.delete(CommonDataBase.TableName.Teacher_Subject.toString(),
                        CommonDataBase.Teacher_Subject.User_Id.toString() + "=? AND " +
                                CommonDataBase.Teacher_Subject.Class_Id.toString() + "=?", whereArgs);
                if (count > 0) {
                    break;
                }
            }

            if (i < classList.size()) {
                return count;
            }


        }

        return 0;
    }

    public long deleteCourseForStudent(StudentModel studentModel) {

        Integer classId = 0;
        List<Integer> classList = new ArrayList<Integer>();
        int i = 0;
        long count = 0;


        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select " + CommonDataBase.Classes.Class_id.toString() + " From " + CommonDataBase.TableName.Classes.toString() + " Where " +
                        CommonDataBase.Classes.Class_info_id.toString() + " IN ( Select " + CommonDataBase.Classes_info.Class_info_id.toString() + " From " +
                        CommonDataBase.TableName.Classes_info.toString() + " Where " + CommonDataBase.Classes_info.Subject_id.toString() + " IN ( Select " +
                        CommonDataBase.Subjects.Subject_id.toString() + " From " + CommonDataBase.TableName.Subjects + " Where " +
                        CommonDataBase.Subjects.Subject_name + "=?)" + "AND " + CommonDataBase.Subjects.School_Id + " =?);",
                new String[]{studentModel.getCourse(), studentModel.getSchool_id().toString()});

        cursor.moveToFirst();


        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                classId = cursor.getInt(0);
                classList.add(classId);
                cursor.moveToNext();
            }
            cursor.close();

            for (i = 0; i < classList.size(); i++) {
                String[] whereArgs = {studentModel.getS_id().toString(), classList.get(i).toString()};

                count = database.delete(CommonDataBase.TableName.Student_Subject.toString(),
                        CommonDataBase.Student_Subject.Student_Id.toString() + "=? AND " +
                                CommonDataBase.Student_Subject.Class_Id.toString() + "=?", whereArgs);
                if (count > 0) {
                    break;
                }
            }

            if (i < classList.size()) {
                return count;
            }


        }

        return 0;

    }


    public boolean giveSubjectToStudent(StudentModel studentModel) {
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select " + CommonDataBase.Classes.Class_id.toString() + " From " + CommonDataBase.TableName.Classes.toString() + " Where " +
                CommonDataBase.Classes.Class_info_id.toString() + "=?;", new String[]{studentModel.getClassInfoId().toString()});

        Cursor roll = database.rawQuery("Select " + CommonDataBase.Classes_info.Strength.toString() + " from " + CommonDataBase.TableName.Classes_info.toString() + ";", new String[]{});
        roll.moveToFirst();
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            int classId = cursor.getInt(0);
            int roll_no = roll.getInt(0) + 1;
            database.execSQL("INSERT INTO " + CommonDataBase.TableName.Student_Subject.toString() +
                    "(" + CommonDataBase.Student_Subject.Student_Id.toString()
                    + "," + CommonDataBase.Student_Subject.Class_Id.toString() + "," +
                    CommonDataBase.Student_Subject.Roll_num.toString() + ")" + " VALUES (" + studentModel.getS_id().toString() + "," + classId + "," + roll_no + ");");

            ContentValues values = new ContentValues();
            values.put(CommonDataBase.Classes_info.Strength.toString(), roll_no);

            long a = database.update(CommonDataBase.TableName.Classes_info.toString(),
                    values, CommonDataBase.Classes_info.Class_info_id.toString() + " =?",
                    new String[]{studentModel.getClassInfoId().toString()});

            if (a > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean giveSubjectToTeacher(Integer classInfoId, Integer userId) {

        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select " + CommonDataBase.Classes.Class_id.toString() + " From " + CommonDataBase.TableName.Classes.toString() + " Where " +
                CommonDataBase.Classes.Class_info_id.toString() + "=?;", new String[]{classInfoId.toString()});

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            int classId = cursor.getInt(0);

            database.execSQL("INSERT INTO `teacher_subject` (`User_Id`, `Class_Id`) VALUES (" + userId + "," + classId + ");");

            return true;

        }
        return false;
    }

    public List<StudentModel> showSectionOfAvailableCoursesStudent(StudentModel studentModel) {
        List<StudentModel> All_Data = new ArrayList<StudentModel>();

        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT " + CommonDataBase.Classes_info.Class_sec.toString() + "," +
                CommonDataBase.Classes_info.Class_info_id + " From " +
                CommonDataBase.TableName.Classes_info.toString() + " Where " + CommonDataBase.Classes_info.Subject_id.toString()
                + "=? AND  " + CommonDataBase.Classes_info.School_id + " =?;", new String[]{studentModel.getSubject_Id().toString(),
                studentModel.getSchool_id().toString()});

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                StudentModel model = new StudentModel();

                model.setSection(cursor.getString(0));
                model.setClassInfoId(cursor.getInt(1));
                All_Data.add(model);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return All_Data;
    }

    public List<TeacherModel> showSectionOfAvailableCoursesTeacher(TeacherModel teacherModel) {

        List<TeacherModel> All_Data = new ArrayList<TeacherModel>();

        database = dbHelper.getWritableDatabase();

        String q = "SELECT " + CommonDataBase.Classes_info.Class_sec.toString() + "," +
                CommonDataBase.Classes_info.Class_info_id + " From " +
                CommonDataBase.TableName.Classes_info.toString() + " Where " + CommonDataBase.Classes_info.Subject_id.toString()
                + "=? AND  " + CommonDataBase.Classes_info.School_id + " =? ;";


        Cursor cursor = database.rawQuery(q, new String[]{teacherModel.getSubjectId().toString(),
                teacherModel.getSchoolId().toString()});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                Integer Check = getClassId(cursor.getString(0), teacherModel.getSchoolId(), teacherModel.getSubjectId());
                if (Check != null) {
                    if (!checkAssign(Check)) {
                        TeacherModel model = new TeacherModel();
                        model.setSection(cursor.getString(0));
                        model.setClassInfoId(cursor.getInt(1));
                        All_Data.add(model);
                    }
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        return All_Data;
    }

    private boolean checkAssign(Integer check) {

        database = dbHelper.getWritableDatabase();
        String q = "Select Count(" + CommonDataBase.Teacher_Subject.Class_Id.toString() + ") from " + CommonDataBase.TableName.Teacher_Subject.toString() + " " +
                " Where " + CommonDataBase.Teacher_Subject.Class_Id.toString() + " = ?;";
        Cursor cursor = database.rawQuery(q, new String[]{check.toString()});
        cursor.moveToFirst();
        if (cursor.getInt(0) > 0) {
            return true;
        }
        return false;
    }


    public boolean checkIfCourseIsAssignTeacher(String name, Integer subjectId) {

        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT " + CommonDataBase.Subjects.Subject_name.toString() + " From " +
                        CommonDataBase.TableName.Subjects.toString() + " Where " + CommonDataBase.Subjects.Subject_name.toString() + "=? AND " +
                        CommonDataBase.Subjects.Subject_id.toString() + " IN ( Select " +
                        CommonDataBase.Classes_info.Subject_id.toString() + " From " + CommonDataBase.TableName.Classes_info.toString() + " Where "
                        + CommonDataBase.Classes_info.Subject_id + "=?);",
                new String[]{name, subjectId.toString()});


        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;

        }
        return false;
    }

    public Integer getSubjectId(String subject_name, String School_id) {
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + CommonDataBase.Subjects.Subject_id + " FROM " + CommonDataBase.TableName.Subjects.toString()
                + " WHERE " + CommonDataBase.Subjects.School_Id + " = ?  AND " + CommonDataBase.Subjects.Subject_name.toString() + " =?;", new String[]{School_id.toString(), subject_name.toString()});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return cursor.getInt(0);
        }
        return null;
    }

    public List<StudentModel> getStudentCoursesToAdd(StudentModel studentModel) {
        List<StudentModel> All_Data = new ArrayList<StudentModel>();
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + CommonDataBase.Subjects.Subject_name.toString() + ","
                        + CommonDataBase.Subjects.Subject_id.toString()
                        + " FROM " + CommonDataBase.TableName.Subjects.toString() +
                        " Where " + CommonDataBase.Subjects.School_Id.toString() + "=? AND " + CommonDataBase.Subjects.Class_num.toString() + "=? AND " +
                        CommonDataBase.Subjects.Status.toString() + "=? AND " + CommonDataBase.Subjects.Subject_id.toString()
                        + " NOT IN( SELECT " + CommonDataBase.Classes_info.Subject_id.toString()
                        + " FROM " + CommonDataBase.TableName.Classes_info.toString() + " Where " + CommonDataBase.Classes_info.Class_info_id.toString()
                        + " IN( Select " + CommonDataBase.Classes.Class_info_id.toString() + " From "
                        + CommonDataBase.TableName.Classes.toString() + " Where " +
                        CommonDataBase.Classes.Class_id.toString() + " IN( Select " + CommonDataBase.Student_Subject.Class_Id.toString()
                        + " From " + CommonDataBase.TableName.Student_Subject.toString() + " Where " + CommonDataBase.Student_Subject.Student_Id.toString() +
                        "=? ))); ",
                new String[]{studentModel.getSchool_id().toString(), studentModel.getClass_num().toString(), "1", studentModel.getS_id().toString()});


        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                StudentModel model = new StudentModel();
                model.setCourse(cursor.getString(0));
                model.setSubject_Id(cursor.getInt(1));
                All_Data.add(model);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return All_Data;
    }


    public List<TeacherModel> getTeacherCoursesToAdd(TeacherModel teacherModel) {

        List<TeacherModel> All_Data = new ArrayList<TeacherModel>();
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + CommonDataBase.Subjects.Subject_name.toString() + ","
                        + CommonDataBase.Subjects.Class_num.toString() + "," + CommonDataBase.Subjects.Subject_id.toString()
                        + " FROM " + CommonDataBase.TableName.Subjects.toString() +
                        " Where " + CommonDataBase.Subjects.School_Id.toString() + "=? AND " + CommonDataBase.Subjects.Status.toString() + "=? AND "
                        + CommonDataBase.Subjects.Subject_id.toString()
                        + " NOT IN( SELECT " + CommonDataBase.Classes_info.Subject_id.toString()
                        + " FROM " + CommonDataBase.TableName.Classes_info.toString() + " Where " + CommonDataBase.Classes_info.Class_info_id.toString()
                        + " IN( Select " + CommonDataBase.Classes.Class_info_id.toString() + " From "
                        + CommonDataBase.TableName.Classes.toString() + " Where " +
                        CommonDataBase.Classes.Class_id.toString() + " IN( Select " + CommonDataBase.Teacher_Subject.Class_Id.toString()
                        + " From " + CommonDataBase.TableName.Teacher_Subject.toString() + " Where " + CommonDataBase.Teacher_Subject.User_Id.toString() +
                        "=? ))); ",
                new String[]{teacherModel.getSchoolId().toString(), "1", teacherModel.getUserId().toString()});


        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                TeacherModel model = new TeacherModel();

                model.setCourse(cursor.getString(0));
                model.setClass_num(cursor.getInt(1));
                model.setSubjectId(cursor.getInt(2));

                All_Data.add(model);


                cursor.moveToNext();
            }
            cursor.close();
        }
        return All_Data;
    }

    public List<StudentModel> getStudentCourses(StudentModel studentModel) {
        List<StudentModel> All_Data = new ArrayList<StudentModel>();
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT " + CommonDataBase.Subjects.Subject_name.toString()
                        + " FROM " + CommonDataBase.TableName.Subjects.toString() +
                        " Where " + CommonDataBase.Subjects.Subject_id.toString() + " IN( SELECT " + CommonDataBase.Classes_info.Subject_id.toString()
                        + " FROM " + CommonDataBase.TableName.Classes_info.toString() + " Where " + CommonDataBase.Classes_info.Class_info_id.toString()
                        + " IN( Select " + CommonDataBase.Classes.Class_info_id.toString() + " From "
                        + CommonDataBase.TableName.Classes.toString() + " Where " +
                        CommonDataBase.Classes.Class_id.toString() + " IN( Select " + CommonDataBase.Teacher_Subject.Class_Id.toString()
                        + " From " + CommonDataBase.TableName.Student_Subject.toString() + " Where " + CommonDataBase.Student_Subject.Student_Id.toString() +
                        "=? ))) AND " + CommonDataBase.Subjects.School_Id.toString() + "=? AND " + CommonDataBase.Subjects.Class_num.toString() + "=?;",
                new String[]{studentModel.getS_id().toString(), studentModel.getSchool_id().toString(), studentModel.getClass_num().toString()});

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                StudentModel model = new StudentModel();
                model.setCourse(cursor.getString(0));
                All_Data.add(model);
                cursor.moveToNext();
            }
            cursor.close();
            return All_Data;
        }
        return All_Data;
    }


    public List<TeacherModel> getTeacherCourses(TeacherModel teacherModel) {
        List<TeacherModel> All_Data = new ArrayList<TeacherModel>();
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT " + CommonDataBase.Subjects.Subject_name.toString()
                        + " FROM " + CommonDataBase.TableName.Subjects.toString() +
                        " Where " + CommonDataBase.Subjects.Subject_id.toString() + " IN( SELECT " + CommonDataBase.Classes_info.Subject_id.toString()
                        + " FROM " + CommonDataBase.TableName.Classes_info.toString() + " Where " + CommonDataBase.Classes_info.Class_info_id.toString()
                        + " IN( Select " + CommonDataBase.Classes.Class_info_id.toString() + " From "
                        + CommonDataBase.TableName.Classes.toString() + " Where " +
                        CommonDataBase.Classes.Class_id.toString() + " IN( Select " + CommonDataBase.Teacher_Subject.Class_Id.toString()
                        + " From " + CommonDataBase.TableName.Teacher_Subject.toString() + " Where " + CommonDataBase.Teacher_Subject.User_Id.toString() +
                        "=? ))) AND " + CommonDataBase.Subjects.School_Id.toString() + "=?;",
                new String[]{teacherModel.getUserId().toString(), teacherModel.getSchoolId().toString()});

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                TeacherModel model = new TeacherModel();
                model.setCourse(cursor.getString(0));
                All_Data.add(model);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return All_Data;
    }

    public List<TeacherModel> getTeacher(Integer schhol_id) {

        List<TeacherModel> All_Data = new ArrayList<TeacherModel>();

        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + CommonDataBase.Users.First_Name.toString() + "," +
                CommonDataBase.Users.Last_Name.toString() + "," +
                CommonDataBase.Users.Gender.toString() + "," + CommonDataBase.Users.Mobile_num.toString() + ","
                + CommonDataBase.Users.User_id.toString() + "," + CommonDataBase.Users.City.toString() + "," +
                CommonDataBase.Users.Postal_code.toString() + "," +
                CommonDataBase.Users.Email.toString() + "," + CommonDataBase.Users.User_name.toString() + "," +
                CommonDataBase.Users.Password.toString() + " " +
                " FROM " + CommonDataBase.TableName.Users.toString() +
                " Where " + CommonDataBase.Users.User_id.toString() + " IN( SELECT " + CommonDataBase.Teachers.User_id.toString()
                + " FROM " + CommonDataBase.TableName.Teachers.toString() + " Where " + CommonDataBase.Teachers.School_id.toString()
                + "=?);", new String[]{schhol_id.toString()});

        Cursor cursor1 = database.rawQuery("Select " + CommonDataBase.Teachers.Teacher_id.toString() + "," + CommonDataBase.Teachers.School_id + " From " +
                        CommonDataBase.TableName.Teachers.toString() + " Where " + CommonDataBase.Teachers.School_id.toString() + "=?",
                new String[]{schhol_id.toString()});

        cursor.moveToFirst();
        cursor1.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                TeacherModel model = new TeacherModel();

                model.setId(cursor1.getInt(0));
                model.setSchoolId(cursor1.getInt(1));

                model.setFirstName(cursor.getString(0));
                model.setLastName(cursor.getString(1));
                model.setGender(cursor.getString(2));
                model.setMobileNo(cursor.getString(3));
                model.setUserId(cursor.getInt(4));
                model.setCity(cursor.getString(5));
                model.setPostal(cursor.getString(6));
                model.setEmail(cursor.getString(7));
                model.setUserName(cursor.getString(8));
                model.setPassword(cursor.getString(9));
                All_Data.add(model);

                cursor1.moveToNext();
                cursor.moveToNext();
            }
            cursor.close();
            cursor1.close();
        }

        return All_Data;
    }

    public List<StudentModel> getStudents(Integer schoolId) {
        database = dbHelper.getWritableDatabase();
        List<StudentModel> comments = new ArrayList<StudentModel>();

        Cursor cursor = database.rawQuery("SELECT * FROM " + CommonDataBase.TableName.Users.toString() + " where " +
                CommonDataBase.Users.User_id.toString() + " IN (SELECT " +
                CommonDataBase.Students.User_id.toString() + " from " + CommonDataBase.TableName.Students.toString() + " WHERE " +
                CommonDataBase.Students.School_id.toString() + "=?);", new String[]{schoolId.toString()});

        Cursor cursor1 = database.rawQuery(
                "SELECT " + CommonDataBase.Students.Student_id.toString() + "" +
                        "," + CommonDataBase.Students.Class_num.toString() + " FROM " + CommonDataBase.TableName.Students.toString()
                        + " WHERE " + CommonDataBase.Students.School_id.toString() + " = ?;", new String[]{schoolId.toString()});

        cursor.moveToFirst();
        cursor1.moveToFirst();

        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                StudentModel comment = new StudentModel();
                comment.setUser_id(cursor.getInt(0));
                comment.setUserName(cursor.getString(1));
                comment.setPassword(cursor.getString(2));
                comment.setMobile_no(cursor.getString(7));
                comment.setGender(cursor.getString(8));
                comment.setfName(cursor.getString(9));
                comment.setlName(cursor.getString(10));
                comment.setCity(cursor.getString(11));
                comment.setPostal(cursor.getString(12));
                comment.setEmail(cursor.getString(13));
                comment.setSchool_id(schoolId);
                comment.setS_id(cursor1.getInt(0));
                comment.setClass_num(cursor1.getInt(1));
                comments.add(comment);
                cursor.moveToNext();
                cursor1.moveToNext();
            }
        }
        // make sure to close the cursor
        cursor.close();
        cursor1.close();
        return comments;
    }

    public String Schoolname(Integer id) {
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + CommonDataBase.School.School_name + " FROM " + CommonDataBase.TableName.School.toString()
                + " WHERE " + CommonDataBase.School.School_id + " = " + '?' + ";", new String[]{id.toString()});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return cursor.getString(0);
        }
        return null;

    }

    public Integer getSchoolidforSubAdmin(String userName) {
        Integer id = 0;
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + CommonDataBase.Users.User_id + " FROM " + CommonDataBase.TableName.Users.toString()
                + " WHERE " + CommonDataBase.Users.User_name + " = " + '?', new String[]{userName});

        Integer u_id = -1;
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            u_id = cursor.getInt(0);
            cursor.close();
        }
        Cursor cursor1 = database.rawQuery("SELECT " + CommonDataBase.School.School_id + " FROM " + CommonDataBase.TableName.School.toString()
                + " WHERE " + CommonDataBase.School.Allocated_to + " = " + '?' + ";", new String[]{u_id.toString()});
        cursor1.moveToFirst();
        if (cursor1.getCount() > 0) {
            id = cursor1.getInt(0);
            cursor1.close();
            return id;
        }
        return -1;
    }

    public Integer getSchoolid(String School) {
        Integer id = 0;
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + CommonDataBase.School.School_id + " FROM " + CommonDataBase.TableName.School.toString()
                + " WHERE " + CommonDataBase.School.School_name + " = " + '?' + ";", new String[]{School});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            id = cursor.getInt(0);
            cursor.close();
            return id;
        }
        return -1;
    }


    public List<SubjectModel> getClassSubjects(String s, String classnum) {
        List<SubjectModel> All_Data = new ArrayList<SubjectModel>();
        database = dbHelper.getWritableDatabase();
        String q = "SELECT * FROM " + CommonDataBase.TableName.Subjects.toString() + " Where " + CommonDataBase.Subjects.School_Id.toString() + " = ? " +
                "AND " + CommonDataBase.Subjects.Status.toString() + "!=0 AND " + CommonDataBase.Subjects.Class_num.toString()
                + " = ?";
        Cursor cursor = database.rawQuery
                (q, new String[]{s.toString(), classnum.toString()});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                SubjectModel model = new SubjectModel();
                model.setSubject_id(cursor.getInt(0));
                model.setSubject_name(cursor.getString(1));
                model.setClass_number(cursor.getInt(2));
                All_Data.add(model);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return All_Data;
    }

    public List<SubjectModel> getAllSubjects(String s) {
        List<SubjectModel> All_Data = new ArrayList<SubjectModel>();
        database = dbHelper.getWritableDatabase();
        String q = "SELECT * FROM " + CommonDataBase.TableName.Subjects.toString() + " Where " + CommonDataBase.Subjects.School_Id.toString() + " = ? " +
                "AND " + CommonDataBase.Subjects.Status.toString() + "!=0;";
        Cursor cursor = database.rawQuery
                (q, new String[]{s.toString()});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                SubjectModel model = new SubjectModel();
                model.setSubject_id(cursor.getInt(0));
                model.setSubject_name(cursor.getString(1));
                model.setClass_number(cursor.getInt(2));
                All_Data.add(model);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return All_Data;
    }


    public List<ClassInfoModel> getSectionsList(ClassInfoModel classInfoModel) {
        List<ClassInfoModel> c = new ArrayList<ClassInfoModel>();
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT  Class_sec , " + CommonDataBase.Classes_info.Class_info_id.toString() + " from classes_info" +
                " WHERE " + CommonDataBase.Classes_info.Subject_id.toString() + " = ? AND " +
                CommonDataBase.Classes_info.School_id.toString() + " =?", new String[]{classInfoModel.getSubject_id().toString()
                , classInfoModel.getSchool_id().toString()});

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                ClassInfoModel classInfoModelist = new ClassInfoModel();
                classInfoModelist.setClass_sec(cursor.getString(0));
                classInfoModelist.setClass_info_id(cursor.getInt(1));
                c.add(classInfoModelist);
                cursor.moveToNext();
            }
            return c;
        }
        return null;
    }

    public List<RoomModel> getAllRooms(String s_id) {
        List<RoomModel> All_Data = new ArrayList<RoomModel>();
        database = dbHelper.getWritableDatabase();
        String q = "SELECT * FROM " + CommonDataBase.TableName.Rooms.toString() + " Where " + CommonDataBase.Rooms.School_Id.toString() + " = ?  AND  " +
                CommonDataBase.Rooms.Status.toString() + "=1;";
        Cursor cursor = database.rawQuery
                (q, new String[]{s_id.toString()});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                RoomModel model = new RoomModel();
                model.setRoom_id(cursor.getInt(0));
                model.setRoomno(cursor.getInt(1));
                All_Data.add(model);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return All_Data;
    }

    public List<String> getAllSchools() {
        List<String> All_Data = new ArrayList<String>();
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM school;", new String[]{});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                All_Data.add(cursor.getString(1));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return All_Data;
    }

    public String CheckLogin(String Username, String Password) {
        String A;
        database = dbHelper.getReadableDatabase();
        String[] params = {Username, Password};
        String[] req = {CommonDataBase.Users.Role.toString()};

        Cursor cursor = database.rawQuery("SELECT " + CommonDataBase.Users.Role +
                "," + CommonDataBase.Users.User_id + " FROM " + CommonDataBase.TableName.Users.toString()
                + " WHERE " + CommonDataBase.Users.User_name + " = " + '?' + " AND " + CommonDataBase.Users.Password + "" +
                " = ? AND  " + CommonDataBase.Users.Activity_flag.toString() + " !=0 ;", new String[]{params[0], params[1]});
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            AdminModelHelper.Admin_id = cursor.getInt(1);
            A = cursor.getString(cursor.getColumnIndex("Role"));
            cursor.close();
            return A;
        }
        return null;
    }


    public StudentModel getStudentDetails(String userName) {
        database = dbHelper.getWritableDatabase();
        StudentModel studentModel;
        studentModel = new StudentModel();

        Cursor cursor = database.rawQuery("Select * from " + CommonDataBase.TableName.Users.toString() + " " +
                "Where " + CommonDataBase.Users.User_name.toString() + " =?", new String[]{userName});

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            studentModel.setUser_id(cursor.getInt(0));
            studentModel.setUserName(cursor.getString(1));
            studentModel.setPassword(cursor.getString(2));
            studentModel.setMobile_no(cursor.getString(7));
            studentModel.setGender(cursor.getString(8));
            studentModel.setfName(cursor.getString(9));
            studentModel.setlName(cursor.getString(10));
            studentModel.setCity(cursor.getString(11));
            studentModel.setPostal(cursor.getString(12));
            studentModel.setEmail(cursor.getString(13));


            String q = "Select " +
                    CommonDataBase.Students.Student_id.toString() + "," +
                    CommonDataBase.Students.Class_num.toString() + "," +
                    CommonDataBase.Students.School_id.toString() + " From " +
                    CommonDataBase.TableName.Students.toString() + " Where " +
                    CommonDataBase.Students.User_id.toString() + " =? ;";

            Cursor cursor1 = database.rawQuery(q, new String[]{studentModel.getUser_id().toString()});
            cursor1.moveToFirst();

            if (cursor1.getCount() > 0) {
                studentModel.setS_id(cursor1.getInt(0));
                studentModel.setClass_num(cursor1.getInt(1));
                studentModel.setSchool_id(cursor1.getInt(2));

                return studentModel;
            }
        }
        return null;
    }


    /*********************************************************************************************/

    public void DemoDataInsertion2() {
        database = dbHelper.getWritableDatabase();
            database.execSQL("INSERT INTO `users` (`User_id`, `User_name`, `Password`, `Role`, `Created_Date`, `Deleted_Date`, `Activity_flag`, `Mobile_num`, `Gender`, `First_Name`, `Last_Name`, `City`, `Postal_code`, `Email`) VALUES\n" +
                    "(1, 'admin', 'admin', 'Main Admin', '2018-08-01', NULL, 1, '0321-1234567', 'Male', 'Muhammad', 'Ali', 'Lahore', '54000', 'AliAKbar14@gmail.com'),\n" +
                    "(2, 'subadmin', 'subadmin', 'Sub Admin', '2018-08-01', NULL, 1, '0321-8521478', 'Male', 'Qasim', 'Bhatti', 'Lahore', '54000', 'Qasim85@gmail.com'),\n" +
                    "(3, 'teacher1', 'teacher1', 'Teacher', '2018-08-01', NULL, 1, '0321-7896541', 'Male', 'Muhammad', 'Ahmed', 'Lahore', '54000', 'Ahmed14@gmail.com'),\n" +
                    "(4, 'teacher2', 'teacher2', 'Teacher', '2018-08-01', NULL, 1, '0214-8521479', 'Male', 'ALi', 'Khan', 'Lahore', '54000', 'Khan41@gmail.com'),\n" +
                    "(5, 'teacher3', 'teacher3', 'Teacher', '2018-08-01', NULL, 1, '0124-7896541', 'Male', 'Haseeb', 'Ahmed', 'Lahore', '54000', 'Haseeb47@gmail.com'),\n" +
                    "(6, 'teacher4', 'teacher4', 'Teacher', '2018-08-01', NULL, 1, '0322-7894563', 'Male', 'Babar', 'Ali', 'Lahore', '54000', 'Babar85@gmail.com'),\n" +
                    "(7, 'student1', 'student1', 'Student', '2018-08-01', NULL, 1, '0112-7896541', 'Female', 'Alina ', 'Ahmed Khan', 'Lahore', '54000', 'Alina78@gmail.com'),\n" +
                    "(8, 'student2', 'student2', 'Student', '2018-08-01', NULL, 1, '0985-7854128', 'Female', 'Ammara', 'Arshad', 'Lahore', '54000', 'Ammara22@gmail.com'),\n" +
                    "(9, 'student3', 'student3', 'Student', '2018-08-02', NULL, 1, '0214-9632587', 'Male', 'Muhammad Hamza', 'Khalid', 'Lahore', '54800', 'Hamza87@YahooMail.com'),\n" +
                    "(10, 'student4', 'student4', 'Student', '2018-08-01', NULL, 1, '0112-7896541', 'Female', 'Alina1 ', 'Ahmed Khan', 'Lahore', '54000', 'Alina78@gmail.com'),\n" +
                    "(11, 'student5', 'student5', 'Student', '2018-08-01', NULL, 1, '0985-7854128', 'Female', 'Ammara1', 'Arshad', 'Lahore', '54000', 'Ammara22@gmail.com'),\n" +
                    "(12, 'student6', 'student6', 'Student', '2018-08-02', NULL, 1, '0214-9632587', 'Male', 'Muhammad Hamza1', 'Khalid', 'Lahore', '54800', 'Hamza87@YahooMail.com'),\n" +
                    "(13, 'student7', 'student7', 'Student', '2018-08-01', NULL, 1, '0112-7896541', 'Female', 'Alina2 ', 'Ahmed Khan', 'Lahore', '54000', 'Alina78@gmail.com'),\n" +
                    "(14, 'student8', 'student8', 'Student', '2018-08-01', NULL, 1, '0985-7854128', 'Female', 'Ammara2', 'Arshad', 'Lahore', '54000', 'Ammara22@gmail.com'),\n" +
                    "(15, 'student9', 'student9', 'Student', '2018-08-02', NULL, 1, '0214-9632587', 'Male', 'Muhammad Hamza2', 'Khalid', 'Lahore', '54800', 'Hamza87@YahooMail.com'),\n" +
                    "(16, 'student10', 'student10', 'Student', '2018-08-01', NULL, 1, '0985-7854128', 'Female', 'Ammara3', 'Arshad', 'Lahore', '54000', 'Ammara22@gmail.com'),\n" +
                    "(17, 'student11', 'student11', 'Student', '2018-08-02', NULL, 1, '0214-9632587', 'Male', 'Muhammad Hamza4', 'Khalid', 'Lahore', '54800', 'Hamza87@YahooMail.com'),\n" +
                    "(18, 'student12', 'student12', 'Student', '2018-08-02', NULL, 1, '0214-9632587', 'Male', 'Muhammad Hamza5', 'Khalid', 'Lahore', '54800', 'Hamza87@YahooMail.com'),\n" +
                    "(19, 'student13', 'student13', 'Student', '2018-08-01', NULL, 1, '0112-7896541', 'Female', 'Alina3 ', 'Ahmed Khan', 'Lahore', '54000', 'Alina78@gmail.com'),\n" +
                    "(20, 'student14', 'student14', 'Student', '2018-08-01', NULL, 1, '0985-7854128', 'Female', 'Ammara4', 'Arshad', 'Lahore', '54000', 'Ammara22@gmail.com'),\n" +
                    "(21, 'student15', 'student15', 'Student', '2018-08-02', NULL, 1, '0214-9632587', 'Male', 'Muhammad Hamza6', 'Khalid', 'Lahore', '54800', 'Hamza87@YahooMail.com'),\n" +
                    "(22, 'student16', 'student16', 'Student', '2018-08-01', NULL, 1, '0985-7854128', 'Female', 'Ammara5', 'Arshad', 'Lahore', '54000', 'Ammara22@gmail.com'),\n" +
                    "(23, 'student17', 'student17', 'Student', '2018-08-02', NULL, 1, '0214-9632587', 'Male', 'Muhammad Hamza7', 'Khalid', 'Lahore', '54800', 'Hamza87@YahooMail.com'),\n" +
                    "(24, 'student18', 'student18', 'Student', '2018-08-02', NULL, 1, '0214-9632587', 'Male', 'Muhammad Hamza8', 'Khalid', 'Lahore', '54800', 'Hamza87@YahooMail.com'),\n" +
                    "(25, 'student19', 'student19', 'Student', '2018-08-01', NULL, 1, '0985-7854128', 'Female', 'Ammara6', 'Arshad', 'Lahore', '54000', 'Ammara22@gmail.com'),\n" +
                    "(26, 'student20', 'student20', 'Student', '2018-08-02', NULL, 1, '0214-9632587', 'Male', 'Muhammad Hamza9', 'Khalid', 'Lahore', '54800', 'Hamza87@YahooMail.com');");


        database.execSQL("INSERT INTO `rooms` (`Room_id`, `Room_num`, `School_Id`,`Created_By`,`Delete_by`,`Created_Date`,`Deleted_Date`,`Status`) VALUES\n" +
                "(1, 10, 1,1,NULL,\"2018-08-01\",NULL,1),\n" +
                "(2, 1, 1,1,NULL,\"2018-08-01\",NULL,1);");


        database.execSQL("INSERT INTO `school` (`School_id`, `School_name`, `School_address`, `phone_number`, `Created_By`, `Delete_by`,`Created_Date`,`Deleted_Date`,`Status`,`Allocated_to`)\n" +
                " VALUES (1, 'Lahore American School', 'The Lahore American School,15 Upper Mall\n" +
                "         ,Canal Bank,Lahore,54000,Pakistan', '+92 423 576 2406',1,NULL,'2018-08-01',NULL, 1,2);");

        database.execSQL("INSERT INTO `acadamic_year` (`Academic_id`, `From_date`, `till_date`) VALUES (NULL, '2018-08-01', '2019-02-01');");

        database.execSQL("INSERT INTO `subjects` (`Subject_id`, `Subject_name`, `Class_num`, `School_Id`, `Created_By`, `Delete_by`, `Created_Date`, `Deleted_Date`, `Status`) VALUES\n" +
                " (NULL, 'Physics', '10', '1', '1', NULL, '2018-08-09', NULL, '1'), \n" +
                " (NULL, 'Chemistry', '10', '1', '1', NULL, '2018-08-09', NULL, '1'), \n" +
                "(NULL, 'English', '1', '1', '1', NULL, '2018-08-09', NULL, '1'),\n" +
                " (NULL, 'Urdu', '1', '1', '1', NULL, '2018-08-09', NULL, '1');");

        database.execSQL("INSERT INTO `classes_info` (`Class_info_id`, `Class_sec`, `School_id`, `Subject_id`, `Strength`, `Academic_id`) VALUES \n" +
                "(NULL, 'A', '1', '1', '5', '1'),\n" +
                " (NULL, 'B', '1', '2', '5', '1'),\n" +
                " (NULL, 'C', '1', '3', '5', '1'),\n" +
                " (NULL, 'D', '1', '4', '5', '1');");

        database.execSQL(" INSERT INTO `classes` (`Class_id`, `class_start_time`, `class_end_time`, `Room_id`, `Class_info_id`, `Flag`) VALUES \n" +
                " (NULL, '05:13:16', '12:33:28', '1', '1', '1'), \n" +
                " (NULL, '09:14:00', '05:23:00', '1', '2', '1'), \n" +
                " (NULL, '09:12:00', '10:12:00', '2', '3', '1'), \n" +
                " (NULL, '09:10:00', '10:00:00', '2', '4', '1');");

        database.execSQL("INSERT INTO `teacher_subject` (`User_Id`, `Class_Id`) \n" +
                " VALUES ('3', '1'), ('4', '2'), ('5', '3'), ('6', '4');\n");

        database.execSQL("INSERT INTO `teachers` (`Teacher_id`, `User_id`, `School_id`, `Created_By`, `Delete_by`)\n" +
                " VALUES (NULL, '3', '1', '1', NULL), \n" +
                " (NULL, '4', '1', '1', NULL), \n" +
                " (NULL, '5', '1', '1', NULL), \n" +
                " (NULL, '6', '1', '1', NULL);");
        database.execSQL(" INSERT INTO `students` (`Student_id`, `User_id`, `School_id`, `Class_num`, `Created_By`, `Delete_by`) VALUES\n" +
                " (NULL, '7', '1', '1', '1', NULL), (NULL, '8', '1', '1', '1', NULL),\n" +
                " (NULL, '9', '1', '1', '1', NULL), (NULL, '10', '1', '1', '1', NULL),\n" +
                " (NULL, '11', '1', '1', '1', NULL), (NULL, '12', '1', '1', '1', NULL),\n" +
                " (NULL, '13', '1', '1', '1', NULL), (NULL, '14', '1', '1', '1', NULL),\n" +
                " (NULL, '15', '1', '1', '1', NULL), (NULL, '16', '1', '1', '1', NULL),\n" +
                " (NULL, '17', '1', '10', '1', NULL), (NULL, '18', '1', '10', '1', NULL), \n" +
                " (NULL, '19', '1', '10', '1', NULL), (NULL, '20', '1', '10', '1', NULL),\n" +
                " (NULL, '21', '1', '10', '1', NULL), (NULL, '22', '1', '10', '1', NULL), \n" +
                " (NULL, '23', '1', '10', '1', NULL), (NULL, '24', '1', '10', '1', NULL), \n" +
                " (NULL, '25', '1', '10', '1', NULL), (NULL, '26', '1', '10', '1', NULL);");

        database.execSQL(" INSERT INTO `student_subject` (`Student_Id`, `Class_Id`, `Roll_num`) VALUES ('1', '3', '1'), ('2', '3', '2'), ('3', '3', '3'), \n" +
                " ('4', '3', '4'), ('5', '3', '5'), ('6', '4', '1'), ('7', '4', '2'), ('8', '4', '3'), ('9', '4', '4'), ('10', '4', '5'),\n" +
                " ('11', '1', '1'), ('12', '1', '2'), ('13', '1', '3'), ('14', '1', '4'),\n" +
                " ('15', '1', '5'), ('16', '2', '1'), ('17', '2', '2'), ('18', '2', '3'), ('19', '2', '4'), ('20', '2', '5');");

    }

    public void DemoDataInsertion() {
        database = dbHelper.getWritableDatabase();

        database.execSQL("INSERT INTO `users` (`User_id`, `User_name`, `Password`, `Role`, `Created_Date`, `Deleted_Date`, `Activity_flag`, `Mobile_num`, `Gender`, `First_Name`, `Last_Name`, `City`, `Postal_code`, `Email`) VALUES\n" +
                "(1, 'admin', 'admin', 'Main Admin', '2018-08-01', NULL, 1, '0321-1234567', 'Male', 'Muhammad', 'Ali', 'Lahore', '54000', 'AliAKbar14@gmail.com')");

        database.execSQL("INSERT INTO `acadamic_year` (`Academic_id`, `From_date`, `till_date`) VALUES (NULL, '2018-08-01', '2019-02-01');\n");


   /*     database.execSQL("INSERT INTO `school` (`School_id`, `School_name`, `School_address`, `phone_number`, `Created_By`, `Delete_by`,`Created_Date`,`Deleted_Date`,`Status`,`Allocated_to`)\n" +
                " VALUES (1, 'Lahore American School', 'The Lahore American School,15 Upper Mall\\r\\n,Canal Bank,Lahore,54000,Pakistan', '+92 423 576 2406',1,NULL,'2018-08-01',NULL, 1,NULL), \n" +
                " (2,'Govt Secondary School', 'Ghoray Shah Rd, Ghoray Shah Bhogiwal Kot Khawaja Saeed, Lahore, Punjab', '0306 2085527',1, NULL, '2018-08-01',NULL, 1,NULL), \n" +
                " (3, 'The Punjab School', 'Kot Khawaja Saeed, Lahore, Punjab 54000', '0331 4718854',1,NULL,'2018-08-01', NULL, 1,9)," +
                " (4, 'The City School', 'Mughapura, Lahore, Punjab 54000', '0320 4712251',1,NULL,'2018-08-05', NULL, 1,NULL)," +
                " (5, 'The Educators School', 'Township, Lahore, Punjab 54000', '0352 48521454',1,NULL,'2018-08-05', NULL, 1,NULL)");

        database.execSQL("INSERT INTO `users` (`User_id`, `User_name`, `Password`, `Role`, `Created_Date`, `Deleted_Date`, `Activity_flag`, `Mobile_num`, `Gender`, `First_Name`, `Last_Name`, `City`, `Postal_code`, `Email`) VALUES\n" +
                "(1, 'ALI74', 'ALI1234', 'Main Admin', '2018-08-01', NULL, 1, '0321-1234567', 'Male', 'Muhammad', 'Ali', 'Lahore', '54000', 'AliAKbar14@gmail.com'),\n" +
                "(2, 'Qasim85', 'Qasim1234', 'Teacher', '2018-08-01', NULL, 1, '0321-8521478', 'Male', 'Qasim', 'Bhatti', 'Lahore', '54000', 'Qasim85@gmail.com'),\n" +
                "(3, 'Ahmed14', 'Ahmed1234', 'Student', '2018-08-01', NULL, 1, '0321-7896541', 'Male', 'Muhammad', 'Ahmed', 'Lahore', '54000', 'Ahmed14@gmail.com'),\n" +
                "(4, 'Khan41', 'khan1234', 'Student', '2018-08-01', NULL, 1, '0214-8521479', 'Male', 'ALi', 'Khan', 'Lahore', '54000', 'Khan41@gmail.com'),\n" +
                "(5, 'Haseeb47', 'Haseeb1234', 'Student', '2018-08-01', NULL, 1, '0124-7896541', 'Male', 'Haseeb', 'Ahmed', 'Lahore', '54000', 'Haseeb47@gmail.com'),\n" +
                "(6, 'Babar85', 'Babar1234', 'Teacher', '2018-08-01', NULL, 1, '0322-7894563', 'Male', 'Babar', 'Ali', 'Lahore', '54000', 'Babar85@gmail.com'),\n" +
                "(7, 'Alina78', 'Alina1234', 'Teacher', '2018-08-01', NULL, 1, '0112-7896541', 'Female', 'Alina ', 'Ahmed Khan', 'Lahore', '54000', 'Alina78@gmail.com'),\n" +
                "(8, 'Ammara22', 'Ammara1234', 'Teacher', '2018-08-01', NULL, 1, '0985-7854128', 'Female', 'Ammara', 'Arshad', 'Lahore', '54000', 'Ammara22@gmail.com'),\n" +
                "(9, 'Hamza87', 'Hamza1234', 'Sub Admin', '2018-08-02', NULL, 1, '0214-9632587', 'Male', 'Muhammad Hamza', 'Khalid', 'Lahore', '54800', 'Hamza87@YahooMail.com');");

        database.execSQL("INSERT INTO `teachers` (`Teacher_id`, `User_id`, `School_id`,`Created_by`,`Delete_by`) \n" +
                "VALUES (1, 2, 1,1,NULL),\n" +
                " (2, 6, 2,1,NULL), (3, 7, 3,1,NULL), (4, 8, 2,1,NULL);");

        database.execSQL("INSERT INTO `students` (`Student_id`, `User_id`, `School_id`, `Class_num`,`Created_by`,`Delete_by`)\n" +
                " VALUES (1, 3, 1, 1,1,NULL),\n" +
                " (2, 4, 2, 5,1,NULL), \n" +
                " (3, 5, 3, 6,1,NULL);");

        database.execSQL("INSERT INTO `teacher_subject` (`User_Id`, `Class_Id`) VALUES\n" +
                "(2, 1),\n" +
                "(2, 2),\n" +
                "(6, 4),\n" +
                "(7, 6),\n" +
                "(8, 5);");

        database.execSQL("INSERT INTO `student_subject` (`Student_Id`, `Class_Id`,`Roll_num`) VALUES\n" +
                "(1, 1,1),\n" +
                "(1, 2,1),\n" +
                "(2, 5,1),\n" +
                "(2, 4,1),\n" +
                "(3, 6,1);");

        database.execSQL("INSERT INTO `subjects` (`Subject_id`, `Subject_name`, `Class_num`, `School_Id`\n" +
                ",`Created_By`,`Delete_by`,`Created_Date`,`Deleted_Date`,`Status`) VALUES\n" +
                "(1, 'MATH', 1, 1,1,NULL,\"2018-08-01\",NULL,1),\n" +
                "(2, 'ENGLISH', 1, 1,1,NULL,\"2018-08-01\",NULL,1),\n" +
                "(3, 'URDU', 1, 1,1,NULL,\"2018-08-01\",NULL,1),\n" +
                "(4, 'Chemistry', 5, 2,1,NULL,\"2018-08-01\",NULL,1),\n" +
                "(5, 'Bio', 5, 2,1,NULL,\"2018-08-01\",NULL,1),\n" +
                "(6, 'Computer', 6, 3,1,NULL,\"2018-08-01\",NULL,1);");

        database.execSQL("INSERT INTO `rooms` (`Room_id`, `Room_num`, `School_Id`,`Created_By`,`Delete_by`,`Created_Date`,`Deleted_Date`,`Status`) VALUES\n" +
                "(1, 1, 1,1,NULL,\"2018-08-01\",NULL,1),\n" +
                "(2, 2, 1,1,NULL,\"2018-08-01\",NULL,1),\n" +
                "(4, 1, 2,1,NULL,\"2018-08-01\",NULL,1),\n" +
                "(5, 1, 3,1,NULL,\"2018-08-01\",NULL,1);\n");

        database.execSQL("INSERT INTO `classes` (`Class_id`,`class_start_time`,`class_end_time`, `Room_id`, `Class_info_id`, `Flag`) VALUES\n" +
                "(1, '01:10:00','02:10:00', 1, 1, 1),\n" +
                "(2, '04:10:00','05:10:00', 2, 2, 1),\n" +
                "(4, '04:10:00','05:10:00', 1, 4, 1),\n" +
                "(5, '03:10:00','04:10:00', 1, 5, 1),\n" +
                "(6, '01:10:00','02:10:00', 1, 6, 1);");

        database.execSQL("INSERT INTO `classes_info` (`Class_info_id`, `Class_sec`, `School_id`, `Subject_id`,`Strength`,`Academic_id`) VALUES\n" +
                "(1, 'A', 1, 2,1,1),\n" +
                "(2, 'A', 1, 1,1,1),\n" +
                "(4, 'C', 2, 4,1,1),\n" +
                "(5, 'D', 2, 5,1,1),\n" +
                "(6, 'B', 3, 6,1,1);");

        database.execSQL("INSERT INTO `acadamic_year` (`Academic_id`, `From_date`, `till_date`) VALUES (NULL, '2018-08-01', '2019-02-01');\n");

        database.execSQL("INSERT INTO `attendance` (`Attendance_id`, `User_id`, `Student_id`, `a_date`, `a_time`, `Active_flag`, `class_id`)\n" +
                " VALUES (NULL, '2', '1','2018-08-06','03:10:00','0','2')," +
                " (NULL, '8', '2','2018-08-01','02:03:00','1', '5');\n");

        database.execSQL("INSERT INTO `assignments` (`Assignment_id`, `Class_id`, `Title`, " +
                "`Description`, `totla_marks`, `Created_date`, `DeadLine_date`, `Created_by`, `Delete_by`, " +
                "`Flag`, `A_type`, `Subject_id`) VALUES " +
                "(NULL, '2', 'MATHAMATICAL EQUATIONS', 'DO THE TASK ON PAGE 1-20.'," +
                " '100', '2018-08-06', '2018-08-08', '2', NULL, '1', 'Assignment', '1'), " +
                "(NULL, '1', " +
                "'English Tenses', 'Present Past Future Tenses', '20', '2018-08-01', NULL, '2', NULL," +
                " '1', 'Quiz','2');");

        database.execSQL("INSERT INTO `marks` (`Student_Id`, `Assignment_id`, `Marks`) VALUES " +
                "('1', '1', '95')," +
                " ('1', '2', '10');");
        database.execSQL("INSERT INTO `notifications` (`Notification_id`, `Class_id`, `Notification_title`, `Notification_Message`, `Created_Date`, `Created_By`, `Delete_by`, `Flag`) VALUES " +
                "(NULL, '1', 'Meeting', 'PARENT TEACHER MEETING', '2018-08-07', '2', NULL, '1'), " +
                "(NULL, '2', 'EXAMS', 'Prepare For Exams', '2018-08-06', '2', NULL, '1');");
   */
    }

    public List<AttandaceInCalandarModel> getAllAttancdeStatus(Integer id, StudentModel studentModel) {

        database = dbHelper.getWritableDatabase();
        List<AttandaceInCalandarModel> list = new ArrayList<AttandaceInCalandarModel>();
        Cursor c = database.rawQuery("Select  * " +
                " from " + CommonDataBase.TableName.Holidays.toString() + " Where " +
                CommonDataBase.Holidays.School_id + " =?", new String[]{id.toString()});

        c.moveToFirst();
        if (c.getCount() > 0) {
            while (!c.isAfterLast()) {
                AttandaceInCalandarModel AttandaceInCalandarModel = new AttandaceInCalandarModel();
                AttandaceInCalandarModel.setId(c.getInt(0));
                AttandaceInCalandarModel.setDate(c.getString(1));
                AttandaceInCalandarModel.setStatus("Holiday");
                AttandaceInCalandarModel.setColor("#1976d2");
                list.add(AttandaceInCalandarModel);
                c.moveToNext();
            }
            c.close();
        }

            Integer Sub_Id = GetSubjectIDFromName(studentModel.getCourse(), studentModel.getSchool_id());
            List<Integer> ClassInfolist = getClassInfoIds(Sub_Id, studentModel.getSchool_id());
            if (ClassInfolist != null) {
                List<Integer> ClassIds = getClassesIds(ClassInfolist);
                if (ClassIds != null) {
                    Integer c_id_ofStudents = getClassIDofSpecificStudnet(ClassIds);

                    if (c_id_ofStudents != null) {

                        String q = "Select * from " + CommonDataBase.TableName.Attendance.toString() + "" +
                                " Where " + CommonDataBase.Attendance.class_id.toString() + " =? " +
                                " AND " + CommonDataBase.Attendance.Student_id.toString() + " = ?;";

                        Cursor cursor2 = database.rawQuery(q, new String[]{c_id_ofStudents.toString(), studentModel.getS_id().toString()});
                        cursor2.moveToFirst();
                        if (cursor2.getCount() > 0) {
                            while (!cursor2.isAfterLast()) {
                                AttandaceInCalandarModel attandaceInCalandarModel = new AttandaceInCalandarModel();
                                attandaceInCalandarModel.setId(cursor2.getInt(0));
                                attandaceInCalandarModel.setDate(cursor2.getString(3));

                                String St = String.valueOf(cursor2.getInt(5));
                                if (St.equals("1")) {
                                    attandaceInCalandarModel.setStatus("Present");
                                    attandaceInCalandarModel.setColor("#23b574");
                                } else if (St.equals("0")) {
                                    attandaceInCalandarModel.setStatus("Absent");
                                    attandaceInCalandarModel.setColor("#ff206c");
                                }
                                if (St.equals("2")) {
                                    attandaceInCalandarModel.setStatus("Leave");
                                    attandaceInCalandarModel.setColor("#fde319");
                                }
                                list.add(attandaceInCalandarModel);
                                cursor2.moveToNext();
                            }
                        }
                    }
                }
            }

        return list;
    }


    public String getStundetName(Integer S_id) {
        Integer u_id = getUserID(S_id);
        if (u_id != 0) {
            Cursor c = database.rawQuery("SELECT " + CommonDataBase.Users.First_Name.toString() + "," + CommonDataBase.Users.Last_Name.toString() + " FROM " + CommonDataBase.TableName.Users.toString() +
                    " Where " + CommonDataBase.Users.User_id.toString() + " = ? ;", new String[]{u_id.toString()});
            c.moveToFirst();
            if (c.getCount() > 0) {
                return c.getString(0) + " " + c.getString(1);
            }
        }
        return null;
    }

    private Integer getUserID(Integer s_id) {
        database = dbHelper.getWritableDatabase();
        Cursor c = database.rawQuery("SELECT " + CommonDataBase.Students.User_id.toString() + " FROM " + CommonDataBase.TableName.Students.toString() +
                " Where " + CommonDataBase.Students.Student_id.toString() + " = ? ;", new String[]{s_id.toString()});
        c.moveToFirst();
        if (c.getCount() > 0) {
            return c.getInt(0);
        }
        return null;
    }

    public Boolean updateMarks(MarksModel marksModel) {
        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CommonDataBase.Marks.Marks.toString(), marksModel.getMarks());
        String[] whereArgs = {marksModel.getStudentId().toString(), marksModel.getAssignmentId().toString()};

        long check = database.update(CommonDataBase.TableName.Marks.toString()
                , values, CommonDataBase.Marks.Student_Id.toString() + "=? AND " +
                        CommonDataBase.Marks.Assignment_id.toString() + "=?", whereArgs);

        if (check > 0) {
            return true;
        } else {
            return false;
        }


    }

    public Boolean addMarks(MarksModel marksModel) {
        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CommonDataBase.Marks.Student_Id.toString(), marksModel.getStudentId());
        values.put(CommonDataBase.Marks.Assignment_id.toString(), marksModel.getAssignmentId());
        values.put(CommonDataBase.Marks.Marks.toString(), marksModel.getMarks());

        long check = database.insert(CommonDataBase.TableName.Marks.toString(), null, values);

        if (check > 0) {
            return true;
        } else {
            return false;
        }

    }

    public Float getMarks(Integer assignmentId, Integer stdId) {
        database = dbHelper.getWritableDatabase();
        String[] whereArgs = {stdId.toString(), assignmentId.toString()};

        Cursor cursor = database.query(CommonDataBase.TableName.Marks.toString(),
                new String[]{CommonDataBase.Marks.Marks.toString()},
                CommonDataBase.Marks.Student_Id.toString() + "=? AND " +
                        CommonDataBase.Marks.Assignment_id.toString() + "=?"
                , whereArgs, null, null, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return cursor.getFloat(0);
        }
        return (float) -1;

    }


    public boolean conversionToExcel(String date, Integer class_id) {
        Integer count = 0;
        database = dbHelper.getWritableDatabase();
        final Cursor cursor = database.rawQuery("SELECT * FROM " + CommonDataBase.TableName.Attendance.toString()
                + "  Where " + CommonDataBase.Attendance.class_id.toString() + " = ? AND " + CommonDataBase.Attendance.a_date.toString()
                + " = ? ;", new String[]{class_id.toString(), date});

       File sd = new File(context.getExternalFilesDir(null).getAbsolutePath() + "/");
       // File sd = new File( Environment.getExternalStorageDirectory()+"/");
        String csvFile = "myData.xls";

        File directory = new File(sd.getAbsolutePath());
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {
            //file path
            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("Attendance", 0);
            // column and row
            sheet.addCell(new Label(0, 0, "Student_Name"));
            sheet.addCell(new Label(1, 0, "Status"));
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                do {
                    String stauts = "";
                    Integer s = cursor.getInt(cursor.getColumnIndex("Active_flag"));
                    if (s == 0) {
                        stauts = "Absent";
                    } else if (s == 1) {
                        stauts = "Present";
                    } else if (s == 2) {
                        stauts = "Leave";
                    }
                    String name = getStundetName(cursor.getInt(cursor.getColumnIndex("Student_id")));
                    int i = cursor.getPosition() + 1;
                    sheet.addCell(new Label(0, i, name));
                    sheet.addCell(new Label(1, i, stauts));
                } while (cursor.moveToNext());
            }

            //closing cursor
            cursor.close();
            workbook.write();
            workbook.close();
            count = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (count > 0) {
            return true;
        }
        return false;
    }


    public static class Helper extends SQLiteOpenHelper {

        public static String DATABASE_NAME = "SchoolDB";
        public static Integer DATABASE_VERSION = 1;

        public Helper(Context context) {
            super(context,
                    context.getExternalFilesDir(null).getAbsolutePath()
                            + "/" + DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(
                    "Create Table  IF NOT EXISTS Users(\n" +
                            " User_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                            " User_name TEXT NOT NULL,\n" +
                            " Password  TEXT NOT NULL,\n" +
                            " Role  TEXT NOT NULL,\n" +
                            " Created_Date DATE NOT NULL,\n" +
                            " Deleted_Date DATE,\n" +
                            " Activity_flag INTEGER NOT NULL,\n" +
                            " Mobile_num  TEXT NOT NULL,\n" +
                            " Gender  TEXT NOT NULL,\n" +
                            " First_Name  TEXT NOT NULL,\n" +
                            " Last_Name  TEXT NOT NULL,\n" +
                            " City TEXT NOT NULL,\n" +
                            " Postal_code TEXT NOT NULL,\n" +
                            " Email TEXT NOT NULL \n" +
                            ");");

            sqLiteDatabase.execSQL("Create Table IF NOT EXISTS  Teachers(\n" +
                    " Teacher_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    " User_id INTEGER NOT NULL, \n" +
                    " School_id INTEGER NOT NULL,\n" +
                    " Created_By INTEGER NOT NULL,\n" +
                    " Delete_by INTEGER ,\n" +
                    "FOREIGN KEY(Created_By) REFERENCES Users(User_Id),\n" +
                    "FOREIGN KEY(Delete_by) REFERENCES Users(User_Id),\n" +
                    "FOREIGN KEY(User_id) REFERENCES Users(User_id),\n" +
                    "FOREIGN KEY(School_Id) REFERENCES School(School_Id)\n" +
                    ");");

            sqLiteDatabase.execSQL("Create Table IF NOT EXISTS  Students(\n" +
                    " Student_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    " User_id INTEGER NOT NULL, \n" +
                    "School_id INTEGER NOT NULL,\n" +
                    "Class_num INTEGER NOT NULL,\n" +
                    " Created_By INTEGER NOT NULL,\n" +
                    " Delete_by INTEGER,\n" +
                    "FOREIGN KEY(Created_By) REFERENCES Users(User_Id),\n" +
                    "FOREIGN KEY(Delete_by) REFERENCES Users(User_Id),\n" +
                    "FOREIGN KEY(User_id) REFERENCES Users(User_id),\n" +
                    "FOREIGN KEY(School_Id) REFERENCES School(School_Id)" + ");");

            sqLiteDatabase.execSQL("Create Table IF NOT EXISTS Classes (\n" +
                    " Class_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    " class_start_time time NOT NULL,\n" +
                    " class_end_time time NOT NULL,\n" +
                    " Room_id INTEGER NOT NULL, \n" +
                    " Class_info_id INTEGER NOT NULL,\n" +
                    " Flag INTEGER NOT NULL,\n" +
                    " FOREIGN KEY(Room_id) REFERENCES Rooms(Room_id),\n" +
                    " FOREIGN KEY( Class_info_id) REFERENCES Classes_info( Class_info_id)\n" +
                    ");");


            sqLiteDatabase.execSQL("Create Table IF NOT EXISTS Classes_info (\n" +
                    "\tClass_info_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "\tClass_sec TEXT NOT NULL,\n" +
                    "\tSchool_id INTEGER NOT NULL, \n" +
                    "\tSubject_id INTEGER NOT NULL,\n" +
                    "\tStrength INTEGER NOT NULL,\n" +
                    "\tAcademic_id INTEGER NOT NULL,\n" +
                    "\tFOREIGN KEY(School_id) REFERENCES School(School_id),\n" +
                    "\tFOREIGN KEY(Academic_id) REFERENCES Acadamic_year(Academic_id),\n" +
                    "\tFOREIGN KEY(Subject_Id) REFERENCES Subjects(Subject_Id)\n" +
                    ");");

            sqLiteDatabase.execSQL("Create Table IF NOT EXISTS Rooms(\n" +
                    " Room_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    " Room_num INTEGER NOT NULL,\n" +
                    "School_Id INTEGER NOT NULL,\n" +
                    " Created_By INTEGER NOT NULL,\n" +
                    " Delete_by INTEGER,\n" +
                    "  Created_Date DATE NOT NULL,\n" +
                    " Deleted_Date DATE ,\n" +
                    " Status INTEGER NOT NULL,\n" +
                    "FOREIGN KEY(Created_By) REFERENCES Users(User_Id),\n" +
                    "FOREIGN KEY(Delete_by) REFERENCES Users(User_Id),\n" +
                    "FOREIGN KEY(School_Id) REFERENCES School(School_Id)\n" +
                    ");");

            sqLiteDatabase.execSQL("Create Table IF NOT EXISTS Subjects (\n" +
                    "Subject_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                    "Subject_name TEXT NOT NULL,\n" +
                    "Class_num INTEGER NOT NULL,\n" +
                    "School_Id INTEGER NOT NULL,\n" +
                    "Created_By INTEGER NOT NULL,\n" +
                    "Delete_by INTEGER,\n" +
                    "Created_Date DATE NOT NULL,\n" +
                    "Deleted_Date DATE,\n" +
                    "Status INTEGER NOT NULL,\n" +
                    "FOREIGN KEY(Created_By) REFERENCES Users(User_Id),\n" +
                    "FOREIGN KEY(Delete_by) REFERENCES Users(User_Id),\n" +
                    "FOREIGN KEY(School_Id) REFERENCES School(School_Id)\n" +
                    ");");

            sqLiteDatabase.execSQL("Create Table IF NOT EXISTS Student_Subject(\n" +
                    "Student_Id INTEGER NOT NULL ,\n" +
                    "Class_Id INTEGER NOT NULL ,\n" +
                    "Roll_num INTEGER NOT NULL,\n" +
                    "FOREIGN KEY(Class_Id) REFERENCES Classes(Class_Id),\n" +
                    "FOREIGN KEY(Student_Id) REFERENCES Students(Student_Id)\n" +
                    ");");

            sqLiteDatabase.execSQL("Create Table IF NOT EXISTS Teacher_Subject(\n" +
                    "User_Id INTEGER NOT NULL,\n" +
                    "Class_Id INTEGER NOT NULL ,\n" +
                    "FOREIGN KEY(User_Id) REFERENCES Users(User_Id),\n" +
                    "FOREIGN KEY(Class_Id) REFERENCES Classes(Class_Id)\n" +
                    ");\n");

            sqLiteDatabase.execSQL("Create Table IF NOT EXISTS School (\n" +
                    "\tSchool_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "\tSchool_name TEXT NOT NULL,\n" +
                    "\tSchool_address TEXT NOT NULL,\n" +
                    "\tphone_number TEXT NOT NULL,\n" +
                    "\tCreated_By INTEGER NOT NULL,\n" +
                    "\tDelete_by INTEGER ,\n" +
                    "\tCreated_Date DATE NOT NULL,\n" +
                    "\tDeleted_Date DATE,\n" +
                    "\tStatus INTEGER NOT NULL,\n" +
                    "\t Allocated_to INTEGER,\n" +
                    "\tFOREIGN KEY(Allocated_to) REFERENCES Users(User_Id),\n" +
                    "\tFOREIGN KEY(Created_By) REFERENCES Users(User_Id),\n" +
                    "\tFOREIGN KEY(Delete_by) REFERENCES Users(User_Id)\n" +
                    ");");

            sqLiteDatabase.execSQL("Create Table IF NOT EXISTS Attendance(\n" +
                    "\tAttendance_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "\tUser_id INTEGER NOT NULL,\n" +
                    "\tStudent_id INTEGER NOT NULL,\n" +
                    "\ta_date date NOT NULL,\n" +
                    "\ta_time time NOT NULL,\n" +
                    "\tActive_flag INTEGER NOT NULL,\n" +
                    "\tclass_id INTEGER NOT NULL,\n" +
                    "\t \n" +
                    "FOREIGN KEY(class_id) REFERENCES classes(class_id),\n" +
                    "FOREIGN KEY(Student_Id) REFERENCES Students(Student_Id),\n" +
                    "FOREIGN KEY(User_id) REFERENCES Users(User_id)\n" +
                    ");");

            sqLiteDatabase.execSQL("Create Table IF NOT EXISTS Assignments (\n" +
                    "\tAssignment_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                    "\tClass_id INTEGER NOT NULL,\n" +
                    "\tTitle TEXT NOT NULL,\n" +
                    "\tDescription TEXT NOT NULL,\n" +
                    "\ttotla_marks INTEGER NOT NULL,\n" +
                    "\tCreated_date DATE NOT NULL,\n" +
                    "\tDeadLine_date Date,\n" +
                    "\tCreated_by INTEGER NOT NULL,\n" +
                    "\tDelete_by INTEGER,\n" +
                    "\tFlag INTEGER NOT NULL,\n" +
                    "\tA_type TEXT NOT NULL,\n" +
                    "\tSubject_id INTEGER NOT NULL,\n" +
                    "\tFOREIGN KEY(class_id) REFERENCES classes(class_id),\n" +
                    "\tFOREIGN KEY(Subject_Id) REFERENCES Subjects(Subject_Id),\n" +
                    "\tFOREIGN KEY(Created_By) REFERENCES Users(User_Id),\n" +
                    "\tFOREIGN KEY(Delete_by) REFERENCES Users(User_Id)\n" +
                    ");");

            sqLiteDatabase.execSQL("Create Table IF NOT EXISTS Acadamic_year(\n" +
                    "\tAcademic_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
                    "\tFrom_date  Date NOT NULL,\n" +
                    "\ttill_date   Date NOT NULL\n" +
                    ");");

            sqLiteDatabase.execSQL("Create Table IF NOT EXISTS Holidays(\n" +
                    "\tHoliday_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "\tHoliday_date Date NOT NULL,\n" +
                    "\tHoliday_title TEXT NOT NULL,\n" +
                    "\tAdded_by INTEGER NOT NULL,\n" +
                    "\tSchool_id INTEGER NOT NULL,\n" +
                    "\tDelete_by INTEGER,\n" +
                    "\tFOREIGN KEY(Added_by) REFERENCES Users(User_Id),\n" +
                    "\tFOREIGN KEY(Delete_by) REFERENCES Users(User_Id),\n" +
                    "\tFOREIGN KEY(School_id) REFERENCES School(School_id)\n" +
                    ");");

            sqLiteDatabase.execSQL("Create Table IF NOT EXISTS Marks(\n" +
                    "\tStudent_Id INTEGER NOT NULL  ,\n" +
                    "\tAssignment_id INTEGER NOT NULL,\n" +
                    "\tMarks FLOAT NOT NULL,\n" +
                    "\tFOREIGN KEY(Student_Id) REFERENCES Students(Student_Id),\n" +
                    "\tFOREIGN KEY(Assignment_id) REFERENCES Assignments(Assignment_id)\n" +
                    ");");

            sqLiteDatabase.execSQL("Create Table IF NOT EXISTS Notifications (\n" +
                    "\tNotification_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "Class_id INTEGER NOT NULL,\n" +
                    "\tNotification_title TEXT NOT NULL,\n" +
                    "\tNotification_Message TEXT NOT NULL,\n" +
                    "\tCreated_Date Date NOT NULL,\n" +
                    "\tCreated_By INTEGER NOT NULL,\n" +
                    "\tDelete_by INTEGER ,\n" +
                    "\tFlag INTEGER NOT NULL,\n" +
                    "FOREIGN KEY(Created_By) REFERENCES Users(User_Id),\n" +
                    "FOREIGN KEY(Class_Id) REFERENCES Classes(Class_Id),\n" +
                    "FOREIGN KEY(Delete_by) REFERENCES Users(User_Id)\n" +
                    ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("a",
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ", which will destroy all old data");

        }
    }
}
