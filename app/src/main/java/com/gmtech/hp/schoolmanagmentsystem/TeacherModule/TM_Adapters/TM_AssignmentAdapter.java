package com.gmtech.hp.schoolmanagmentsystem.TeacherModule.TM_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gmtech.hp.schoolmanagmentsystem.R;
import com.gmtech.hp.schoolmanagmentsystem.StudentModule.SM_DAO.AssignmentModel;

import java.util.List;

/**
 * Created by HP on 8/8/2018.
 */

public class TM_AssignmentAdapter extends BaseAdapter {

    Context context;
    List<AssignmentModel> model;
    LayoutInflater inflater;
    TextView title;
    TextView obt;
    TextView total;



    public TM_AssignmentAdapter(Context context, List<AssignmentModel> model) {
        this.context = context;
        this.model = model;
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            inflater = LayoutInflater.from(this.context);
            view = inflater.inflate(R.layout.simple_assignment_view, viewGroup, false);
        }
        title = (TextView) view.findViewById(R.id.title);
        obt=(TextView)view.findViewById(R.id.o_marks);
        total=(TextView)view.findViewById(R.id.t_marks);

        title.setText(model.get(i).getTitle());
        total.setText(model.get(i).getTotla_marks().toString());
        obt.setText(model.get(i).getObt_marks().toString());
        return view;
    }
}
