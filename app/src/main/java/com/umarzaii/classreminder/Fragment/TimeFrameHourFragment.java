package com.umarzaii.classreminder.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.umarzaii.classreminder.Handler.DatabaseHandler;
import com.umarzaii.classreminder.Handler.FragmentHandler;
import com.umarzaii.classreminder.Handler.RVDividerItemHandler;
import com.umarzaii.classreminder.Model.TimeFrameModel;
import com.umarzaii.classreminder.R;

import static com.umarzaii.classreminder.Handler.DatabaseHandler.PSMZAID;

public class TimeFrameHourFragment extends Fragment {

    private DatabaseHandler databaseHandler;
    private FragmentHandler fragmentHandler;

    private DatabaseReference dbClassPicker;
    private RecyclerView rvTimeFrameHour;

    private String dayID, classLocationID, userClassID, displayType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragm_timeframehour,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("TimeFrame Hour");
        View v = getView();

        databaseHandler = new DatabaseHandler();
        fragmentHandler = new FragmentHandler(getActivity().getSupportFragmentManager());

        rvTimeFrameHour = (RecyclerView)v.findViewById(R.id.rvTimeFrameHour);

        dayID = getArguments().getString("dayID");
        displayType = getArguments().getString("displayType");
        if (displayType.equals(databaseHandler.tblUserClass)) {
            userClassID = getArguments().getString("userClassID");
            dbClassPicker = databaseHandler.getTblUniversityUserClassTimeFrame(PSMZAID,userClassID);
        } else if (displayType.equals(databaseHandler.tblClassLocation)) {
            classLocationID = getArguments().getString("classLocationID");
            dbClassPicker = databaseHandler.getTblUniversityClassLocationTimeFrame(PSMZAID,classLocationID);
        }

        rvTimeFrameHour.setHasFixedSize(true);
        rvTimeFrameHour.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTimeFrameHour.addItemDecoration(new RVDividerItemHandler(getActivity()));
        rvTimeFrameHour.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<TimeFrameModel,TimeFrameHourViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<TimeFrameModel, TimeFrameHourViewHolder>(

                TimeFrameModel.class,
                R.layout.rvitem_timeframehourrow,
                TimeFrameHourViewHolder.class,
                dbClassPicker.child(dayID)

        ) {
            @Override
            protected void populateViewHolder(TimeFrameHourViewHolder viewHolder, TimeFrameModel model, int position) {

                String timeGap = model.getTimeGap();
                String subjectID = model.getSubjectID();
                String classLocationID = model.getClassLocationID();
                String userClassID = model.getUserClassID();

                viewHolder.setTimeGap(timeGap);
                viewHolder.setSubjectID(subjectID);
                if (displayType.equals(databaseHandler.tblUserClass)) {
                    viewHolder.setAnyClassID(classLocationID);
                } else if (displayType.equals(databaseHandler.tblClassLocation)) {
                    viewHolder.setAnyClassID(userClassID);
                }

            }
        };

        rvTimeFrameHour.setAdapter(firebaseRecyclerAdapter);
    }

    public static class TimeFrameHourViewHolder extends RecyclerView.ViewHolder {
        View fView;

        public TimeFrameHourViewHolder(View itemView) {
            super(itemView);
            fView = itemView;
        }

        public void setTimeGap(String timeGap) {
            TextView txtTimeGap = (TextView)fView.findViewById(R.id.txtTimeGap);
            txtTimeGap.setText(timeGap);
        }

        public void setAnyClassID(String anyClassID) {
            TextView txtAnyClassID = (TextView)fView.findViewById(R.id.txtAnyClassID);
            txtAnyClassID.setText(anyClassID);
        }

        public void setSubjectID(String subjectID) {
            TextView txtSubjectID = (TextView)fView.findViewById(R.id.txtSubjectID);
            txtSubjectID.setText(subjectID);
        }
    }

}
