package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Adapters.RoomListAdapter;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.RoomModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;

import java.util.List;

public class ShowRoomList extends AppCompatActivity implements View.OnClickListener {

    public SchoolDB schoolDB;
    ListView listView;
    TextView textView;
    Integer s_id;
    RoomListAdapter roomListAdapter;
    List<RoomModel> list;

    RoomModel roomModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
         s_id = intent.getIntExtra("School_id", -1);

        schoolDB = new SchoolDB(this);
        list = schoolDB.getAllRooms(s_id.toString());
        listView = (ListView) findViewById(R.id.listview);
        textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("Rooms");


        if (list.size() == 0) {
            Toast.makeText(this, "No Room Found", Toast.LENGTH_SHORT).show();
        } else {
            roomListAdapter = new RoomListAdapter(list, this);
            listView.setAdapter(roomListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    roomModel=new RoomModel();
                    roomModel.setRoomno(list.get(i).getRoomno());
                    roomModel.setRoom_id(list.get(i).getRoom_id());

                    Intent intent1=new Intent(ShowRoomList.this,ShowRoomDetails.class);
                    intent1.putExtra("model",roomModel);
                    intent1.putExtra("School_id",s_id);
                    startActivity(intent1);
                }
            });

        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_image:
                Intent intent = new Intent(ShowRoomList.this, AddRoom.class);
                intent.putExtra("School_id",s_id);
                startActivity(intent);
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = schoolDB.getAllRooms(s_id.toString());
        roomListAdapter = new RoomListAdapter(list, this);
        listView.setAdapter(roomListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                roomModel=new RoomModel();
                roomModel.setRoomno(list.get(i).getRoomno());
                roomModel.setRoom_id(list.get(i).getRoom_id());

                Intent intent1=new Intent(ShowRoomList.this,ShowRoomDetails.class);
                intent1.putExtra("model",roomModel);
                intent1.putExtra("School_id",s_id);
                startActivity(intent1);
            }
        });
    }
}
