package com.gmtech.hp.schoolmanagmentsystem.StartUpPackage;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SelectionLogin extends AppCompatActivity implements View.OnClickListener {

    SchoolDB schoolDB;
    Boolean check;
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        context=this;
        schoolDB=new SchoolDB(this);
        check=false;
        if (Build.VERSION.SDK_INT >= 23)
            getPermission();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(SelectionLogin.this, LoginActivity.class);
        switch (view.getId()) {
            case R.id.syr_admin_avatar:
                intent.putExtra("Role", "Admin");
                intent.putExtra("check",check);
                startActivity(intent);
                break;
            case R.id.syr_student_avatar:
                intent.putExtra("Role", "Student");
                intent.putExtra("check",check);
                startActivity(intent);
                break;
            case R.id.syr_teacher_avatar:
                intent.putExtra("Role", "Teacher");
                intent.putExtra("check",check);
                startActivity(intent);
         /*   //case R.id.demo:
              //  check=true;
                //clearApplicationData();
                //schoolDB.DemoDataInsertion2();
                Toast.makeText(SelectionLogin.this,"DEMO DATA INSERTED",Toast.LENGTH_SHORT).show();
                //break;

            case R.id.clear:
                check=true;
               // clearApplicationData();
                //schoolDB.DemoDataInsertion();
                Toast.makeText(SelectionLogin.this,"DATA CLEARED",Toast.LENGTH_SHORT).show();
                break;*/
        }
    }


    public void clearApplicationData() {
       // File cache = getCacheDir();

        File appDir = new File(String.valueOf(this.getFilesDir()));//(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                }
            }
        }
    }

    public  boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


    private void getPermission() {
        String[] params = null;
        String writeExternalStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String readExternalStorage = Manifest.permission.READ_EXTERNAL_STORAGE;

        int hasWriteExternalStoragePermission = ActivityCompat.checkSelfPermission(this, writeExternalStorage);
        int hasReadExternalStoragePermission = ActivityCompat.checkSelfPermission(this, readExternalStorage);
        List<String> permissions = new ArrayList<String>();

        if (hasWriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED)
            permissions.add(writeExternalStorage);
        if (hasReadExternalStoragePermission != PackageManager.PERMISSION_GRANTED)
            permissions.add(readExternalStorage);

        if (!permissions.isEmpty()) {
            params = permissions.toArray(new String[permissions.size()]);
        }
        if (params != null && params.length > 0) {
            ActivityCompat.requestPermissions(SelectionLogin.this,
                    params,
                    100);
        }
    }

}

