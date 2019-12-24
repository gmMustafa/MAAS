package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

public class AddRoom extends AppCompatActivity implements View.OnClickListener {

    EditText editText;
    SchoolDB schoolDB;
    Integer Schhol_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        editText=(EditText)findViewById(R.id.room_et);

        Intent intent = getIntent();
        Schhol_id = intent.getIntExtra("School_id", -1);
        schoolDB=new SchoolDB(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                if(editText.getText().toString().isEmpty())
                {
                    Toast.makeText(AddRoom.this,"PLease Add a Room",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(schoolDB.addRoom(Integer.parseInt(editText.getText().toString()),Schhol_id))
                    {
                        Toast.makeText(AddRoom.this,"Room Added",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(AddRoom.this,"Failure Adding A room",Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.cancel:
                finish();

        }
    }
}
