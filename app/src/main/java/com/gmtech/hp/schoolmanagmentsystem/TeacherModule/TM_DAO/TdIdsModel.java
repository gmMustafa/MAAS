package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO;

import java.io.Serializable;

/**
 * Created by HP on 8/4/2018.
 */

public class TdIdsModel implements Serializable {

    Integer teacherId;
    Integer schoolId;

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


}
