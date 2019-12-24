package com.gmtech.hp.schoolmanagmentsystem.AdminModule.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Fragments.CalanderFragement;
import com.gmtech.hp.schoolmanagmentsystem.AdminModule.Fragments.listFragment;
import com.gmtech.hp.schoolmanagmentsystem.R;

public class HolidaysActivities extends AppCompatActivity implements View.OnClickListener {

    ImageButton imageButton;
    public static Integer s_id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidays);
        Intent intent=getIntent();
        s_id=intent.getIntExtra("School_id",-1);
        imageButton=(ImageButton)findViewById(R.id.add_holiday);
            imageButton.setOnClickListener(this);
        Fragment fragment1=new CalanderFragement();
        Fragment fragment2=new listFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_list, fragment2).commit();
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(HolidaysActivities.this, AddHoldiay.class);
        intent.putExtra("School_Id",s_id.toString());
            startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment fragment1=new CalanderFragement();
        Fragment fragment2=new listFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_list, fragment2).commit();
    }
}
