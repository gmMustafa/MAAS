package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.RoomModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

public class ShowRoomDetails extends AppCompatActivity {

    TextView textView;
    SchoolDB schoolDB;
    Integer Schhol_id;
    RoomModel roomModel;
    public static  Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_room_details);
        Intent intent=getIntent();
        context=this;
        Schhol_id = intent.getIntExtra("School_id", -1);
        roomModel= (RoomModel) intent.getSerializableExtra("model");
        schoolDB=new SchoolDB(this);

        textView=(TextView)findViewById(R.id.room_et);
        textView.setText(roomModel.getRoomno().toString());
    }


    public void onClick(View view) {
       switch (view.getId())
       {
           case R.id.toolbar_edit:
               Intent intent=new Intent(ShowRoomDetails.this,EditRoom.class);
               intent.putExtra("model",roomModel);
               intent.putExtra("School_id",Schhol_id);
                startActivity(intent);
               break;

           case R.id.deleteRoom:
               if(schoolDB.deleteRoom(roomModel.getRoom_id())){Toast.makeText(ShowRoomDetails.this,"Record Deleted",Toast.LENGTH_SHORT).show();finish();} else {
                   Toast.makeText(ShowRoomDetails.this,"Record Not Deleted Due To its Current Allocation",Toast.LENGTH_SHORT).show();}
               break;
       }

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        finish();
//    }finish
}
