package com.umarzaii.classreminder.LecturerActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umarzaii.classreminder.Handler.DatabaseHandler;
import com.umarzaii.classreminder.Handler.FragmentHandler;
import com.umarzaii.classreminder.R;

public class LecturerTimeFrameDetailsFragment extends Fragment {

    private DatabaseHandler databaseHandler;
    private FragmentHandler fragmentHandler;

    private TextView txtUserClassID, txtDayIDPath, txtTimeFrameHourID, txtSubjectIDInfo, txtClassLocationID;

    private String dayID;
    private String timeID;
    private String timeGap;
    private String subjectID;
    private String classLocationID;
    private String userClassID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lecturer_fragm_timeframedetails,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("TimeFrame");
        View v = getView();

        databaseHandler = new DatabaseHandler();
        fragmentHandler = new FragmentHandler(getActivity().getSupportFragmentManager());

        getBundleValue();

        txtUserClassID = (TextView) v.findViewById(R.id.txtUserClassID);
        txtDayIDPath = (TextView) v.findViewById(R.id.txtDayIDPath);
        txtTimeFrameHourID = (TextView) v.findViewById(R.id.txtTimeFrameHourID);
        txtSubjectIDInfo = (TextView) v.findViewById(R.id.txtSubjectIDInfo);
        txtClassLocationID = (TextView) v.findViewById(R.id.txtClassLocationID);

        setDetailsValue();

    }

    private void getBundleValue() {
        dayID = getArguments().getString("dayID");
        timeID = getArguments().getString("timeID");
        timeGap = getArguments().getString("timeGap");
        subjectID = getArguments().getString("subjectID");

        userClassID = getArguments().getString("userClassID");
        classLocationID = getArguments().getString("classLocationID");
    }

    private void setDetailsValue() {
        txtDayIDPath.setText(dayID);
        txtTimeFrameHourID.setText(timeGap);
        txtSubjectIDInfo.setText(subjectID);

        txtUserClassID.setText(userClassID);
        txtClassLocationID.setText(classLocationID);
    }

}
