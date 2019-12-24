package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gmtech.hp.schoolmanagmentsystem.AdminModule.DAO.StudentModel;
import com.gmtech.hp.schoolmanagmentsystem.DataBase.SchoolDB;
import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_DAO.AssignmentModel;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Adapters.TM_AssignmentAdapter;
import com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_DAO.TeacherpanelModel;

import java.util.List;

public class ShowStudnetReportToTeacher extends AppCompatActivity {


    android.support.v7.widget.Toolbar toolbar;
    TeacherpanelModel teacherpanelModel;
    SchoolDB schoolDB;
    ListView listView;
    TextView textView;
    List<AssignmentModel> model;
    TM_AssignmentAdapter tm_assignmentAdapter;

    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_studnet_report_to_teacher);
        toolbar=( android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);

        teacherpanelModel = (TeacherpanelModel) getIntent().getSerializableExtra("obj");
        schoolDB = new SchoolDB(this);

        String Type = "Assignment";
        model = schoolDB.getAssignments(teacherpanelModel, Type);
        listView = (ListView) findViewById(R.id.assignments);
        textView = (TextView) findViewById(R.id.title_assignments);
        v=(View)findViewById(R.id.view_below_assignments);

        if (model != null && model.size()!=0) {
            tm_assignmentAdapter = new TM_AssignmentAdapter(this, model);
            listView.setAdapter(tm_assignmentAdapter);
            ListUtils.setDynamicHeight(listView);
        } else {
            listView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            v.setVisibility(View.GONE);
        }

        Type = "Quiz";
        model = schoolDB.getAssignments(teacherpanelModel, Type);
        listView = (ListView) findViewById(R.id.quiz);
        textView = (TextView) findViewById(R.id.title_quizzes);
        v=(View)findViewById(R.id.view_below_quiz);
        if (model != null && model.size()!=0) {
            tm_assignmentAdapter = new TM_AssignmentAdapter(this, model);
            listView.setAdapter(tm_assignmentAdapter);
            ListUtils.setDynamicHeight(listView);
        } else {
            listView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            v.setVisibility(View.GONE);
        }

        Type = "Mid";
        model = schoolDB.getAssignments(teacherpanelModel, Type);
        listView = (ListView) findViewById(R.id.mid);
        textView = (TextView) findViewById(R.id.title_mid);
        v=(View)findViewById(R.id.view_below_mid);
        if (model != null && model.size()!=0) {
            tm_assignmentAdapter = new TM_AssignmentAdapter(this, model);
            listView.setAdapter(tm_assignmentAdapter);
            ListUtils.setDynamicHeight(listView);
        } else {
            listView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            v.setVisibility(View.GONE);
        }


        Type = "Final";
        model = schoolDB.getAssignments(teacherpanelModel, Type);
        listView = (ListView) findViewById(R.id.finals);
        textView = (TextView) findViewById(R.id.title_final);

        if (model != null && model.size()!=0) {
            tm_assignmentAdapter = new TM_AssignmentAdapter(this, model);
            listView.setAdapter(tm_assignmentAdapter);
            ListUtils.setDynamicHeight(listView);
        } else {
            listView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
    }



    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }

}
