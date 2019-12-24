package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.RoomModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

public class EditRoom extends AppCompatActivity implements View.OnClickListener{

    EditText editText;
    SchoolDB schoolDB;
    RoomModel roomModel;
    Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        editText=(findViewById(R.id.room_et));
        Intent intent=getIntent();
        schoolDB=new SchoolDB(this);
        roomModel= (RoomModel) intent.getSerializableExtra("model");
        editText.setText(roomModel.getRoomno().toString(), TextView.BufferType.EDITABLE);
        save=(Button)findViewById(R.id.save);
        save.setText("Update");
        TextView   textView=(TextView)findViewById(R.id.toolbar_title);
        textView.setText("Edit Room");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                if(editText.getText().toString().isEmpty())
                {
                    Toast.makeText(EditRoom.this,"PLease Enter a Room",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(schoolDB.updateRoom(Integer.parseInt(editText.getText().toString()),roomModel.getRoom_id()))
                    {
                        Toast.makeText(EditRoom.this,"Room Updated",Toast.LENGTH_SHORT).show();
                        ((Activity)ShowRoomDetails.context).finish();
                            finish();

                    }
                    else
                    {
                        Toast.makeText(EditRoom.this,"Failure updating Room",Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.cancel:
                finish();

        }
    }






}
