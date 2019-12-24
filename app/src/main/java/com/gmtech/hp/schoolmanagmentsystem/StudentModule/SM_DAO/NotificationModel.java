package com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_DAO;

import android.content.Intent;

import java.io.Serializable;

/**
 * Created by HP on 8/7/2018.
 */

public class NotificationModel implements Serializable{
    Integer id;
    String title;
    String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
