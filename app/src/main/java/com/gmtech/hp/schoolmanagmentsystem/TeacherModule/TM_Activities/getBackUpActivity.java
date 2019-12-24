package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gmtech.hp.schoolmanagmentsystem.BuildConfig;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TdIdsModel;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TeacherpanelModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class getBackUpActivity extends AppCompatActivity {

    TextView selectClass;
    TextView selectSubject;
    TextView selectSection;
    android.support.v7.widget.AppCompatSpinner spinnerClass;
    android.support.v7.widget.AppCompatSpinner spinnerSubject;
    android.support.v7.widget.AppCompatSpinner spinnerSection;
    List<TeacherpanelModel> modelList;
    SchoolDB schoolDB;
    TeacherpanelModel teacherpanelModel;;
    TdIdsModel idsModel;
    int i=0;
    ArrayList<String> arrayClass;
    ArrayList<String> arraySubject;
    ArrayList<String> arraySection;

    Button show;
    Calendar myCalendar;
    EditText date_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_back_up);

        date_et=(EditText)findViewById(R.id.editText);
        myCalendar = Calendar.getInstance();
        idsModel=(TdIdsModel) getIntent().getSerializableExtra("obj");
        teacherpanelModel=new TeacherpanelModel();
        modelList=new ArrayList<TeacherpanelModel>();
        schoolDB=new SchoolDB(this);
        selectClass=(TextView)findViewById(R.id.select_class_tv);
        selectSubject=(TextView)findViewById(R.id.select_subject_tv);
        selectSection=(TextView)findViewById(R.id.select_section_tv);
        spinnerClass=(android.support.v7.widget.AppCompatSpinner)findViewById(R.id.select_class_spinner);
        spinnerSubject=(android.support.v7.widget.AppCompatSpinner)findViewById(R.id.select_subject_spinner);
        spinnerSection=(android.support.v7.widget.AppCompatSpinner)findViewById(R.id.select_section_spinner);
        show=(Button)findViewById(R.id.show);

        teacherpanelModel.setSchoolId(idsModel.getSchoolId());
        teacherpanelModel.setTeacherId(idsModel.getTeacherId());

        Integer sid=teacherpanelModel.getSchoolId();
        Integer tid=teacherpanelModel.getTeacherId();

        modelList=schoolDB.getClassNumberOfTeacher(teacherpanelModel.getSchoolId());

        arrayClass=new ArrayList<String>();
        for(i=0;i<modelList.size();i++)
        {

            arrayClass.add(modelList.get(i).getClassNumber().toString());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayClass);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClass.setAdapter(spinnerAdapter);


        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Integer clsssNo=Integer.parseInt(spinnerClass.getSelectedItem().toString());
                teacherpanelModel.setClassNumber(clsssNo);
                modelList=schoolDB.getsubjectOfTeacher(teacherpanelModel.getSchoolId(),clsssNo);
                arraySubject=new ArrayList<String>();
                for(i=0;i<modelList.size();i++)
                {
                    arraySubject.add(modelList.get(i).getSubjectName());
                }

                ArrayAdapter<String> spinnerAdapter =
                        new ArrayAdapter<String>(getBackUpActivity.this, android.R.layout.simple_spinner_item, arraySubject);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSubject.setAdapter(spinnerAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String subname=spinnerSubject.getSelectedItem().toString();

                Integer subId=schoolDB.getSubjectId(subname,teacherpanelModel.getSchoolId(),teacherpanelModel.getClassNumber());

                teacherpanelModel.setSubjectName(subname);
                teacherpanelModel.setSubjectid(subId);
                modelList=schoolDB.getsectionOfTeacher(teacherpanelModel.getSubjectid());

                arraySection=new ArrayList<String>();
                for(i=0;i<modelList.size();i++)
                {

                    arraySection.add(modelList.get(i).getSection().toString());
                }

                ArrayAdapter<String> spinnerAdapter1 =
                        new ArrayAdapter<String>(getBackUpActivity.this, android.R.layout.simple_spinner_item, arraySection);
                spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSection.setAdapter(spinnerAdapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String subSec=spinnerSection.getSelectedItem().toString();
                teacherpanelModel.setSection(subSec);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(date_et.getText().toString().isEmpty()){
                        Toast.makeText(getBackUpActivity.this," INVALID ENTRIES ",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        ExlConverison t=new ExlConverison(getBackUpActivity.this);
                        t.execute();
                    }
                }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        date_et.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getBackUpActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date_et.setText(sdf.format(myCalendar.getTime()));
    }











    private class ExlConverison extends AsyncTask<Void, Void, String> {
        private ProgressDialog dialog;

        public ExlConverison(getBackUpActivity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Exporting EXCEL FILE...");
            dialog.show();
        }

        protected String doInBackground(Void... args) {

            try {
                    Integer c_id=schoolDB.getClassId(teacherpanelModel);
                if(schoolDB.conversionToExcel(date_et.getText().toString(),c_id))
                {
                    return "Completed";
                }
                else
                {
                    return  "FAILURE";
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        return  "Exl";
        }

        protected void onPostExecute(String result) {
            // do UI work here
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if(result.equals("Completed")){
                Toast.makeText(getBackUpActivity.this, "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();
               /* Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("application/excel");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                File filetoshare = new File(path);
                Uri uri = Uri.fromFile(filetoshare);
                share.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(share, "Share Data!"));
                */

                Intent intent = new Intent(Intent.ACTION_SEND);
                String path = getExternalFilesDir(null).getAbsolutePath() + "/myData.xls";
                Uri uri = FileProvider.getUriForFile(getBackUpActivity.this, "com.gmtech.hp.schoolmanagmentsystem.fileprovider", new File(path));
                intent.setDataAndType(uri, "application/excel");
                PackageManager pm = getPackageManager();
                if (intent.resolveActivity(pm) != null) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(intent);

                }
            }
        }
    }
}
