package com.umarzaii.classreminder.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.umarzaii.classreminder.Handler.DatabaseHandler;
import com.umarzaii.classreminder.Handler.FragmentHandler;
import com.umarzaii.classreminder.Model.ClassLocationModel;
import com.umarzaii.classreminder.Model.SubjectModel;
import com.umarzaii.classreminder.Model.UserClassModel;
import com.umarzaii.classreminder.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.umarzaii.classreminder.Handler.DatabaseHandler.PSMZAID;

public class AddTimeFrameFragment extends Fragment {

    private DatabaseHandler databaseHandler;
    private FragmentHandler fragmentHandler;

    private String[] subjectList, anyClassList;
    private ArrayAdapter<String> adpSubj, adpAnyClass;

    private TextView txtAnyClassIDPath, txtDayIDPath, txtTimeFrameHourID;
    private Button btnAddTimeFrame;
    private Spinner spnSubjectIDInfo, spnAnyClassIDInfo;

    private String timeID, timeGap, subjectID, classLocationID, userClassID,
            displayType, dayID, classLocationIDPath, userClassIDPath,
            subjIDSelection, anyClassIDSelection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragm_timeframeadd,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Add TimeFrame");
        View v = getView();

        databaseHandler = new DatabaseHandler();
        fragmentHandler = new FragmentHandler(getActivity().getSupportFragmentManager());

        getBundleValue();

        txtAnyClassIDPath = (TextView) v.findViewById(R.id.txtAnyClassIDPath);
        txtDayIDPath = (TextView) v.findViewById(R.id.txtDayIDPath);
        txtTimeFrameHourID = (TextView) v.findViewById(R.id.txtTimeFrameHourID);
        spnSubjectIDInfo = (Spinner) v.findViewById(R.id.spnSubjectIDInfo);
        spnAnyClassIDInfo = (Spinner) v.findViewById(R.id.spnAnyClassIDInfo);
        btnAddTimeFrame = (Button) v.findViewById(R.id.btnAddTimeFrame);

        setDetailsValue();

        btnAddTimeFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTimeFrameDetails();
            }
        });

    }

    private void getBundleValue() {
        dayID = getArguments().getString("dayID");
        timeID = getArguments().getString("timeID");
        timeGap = getArguments().getString("timeGap");
        subjectID = getArguments().getString("subjectID");
        displayType = getArguments().getString("displayType");

        if (displayType.equals(databaseHandler.tblUserClass)) {
            userClassIDPath = getArguments().getString("userClassID");
            classLocationID = getArguments().getString("classLocationID");
        } else if (displayType.equals(databaseHandler.tblClassLocation)) {
            classLocationIDPath = getArguments().getString("classLocationID");
            userClassID = getArguments().getString("userClassID");
        }
    }

    private void setDetailsValue() {
        if (displayType.equals(databaseHandler.tblUserClass)) {
            txtAnyClassIDPath.setText(userClassIDPath);
        } else if (displayType.equals(databaseHandler.tblClassLocation)) {
            txtAnyClassIDPath.setText(classLocationIDPath);
        }
        txtDayIDPath.setText(dayID);
        txtTimeFrameHourID.setText(timeGap);

        setSpinnerAnyClassIDList();
        setSpinnerSubjectIDList();
    }

    private void setSpinnerSubjectIDList() {
        databaseHandler.getTblUniversitySubject(PSMZAID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getSpinnerList(dataSnapshot,databaseHandler.tblSubject);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        spnSubjectIDInfo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subjIDSelection = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSpinnerAnyClassIDList() {
        DatabaseReference anyClassListDB = null;

        if (displayType.equals(databaseHandler.tblUserClass)) {
            anyClassListDB = databaseHandler.getTblUniversityClassLocation(PSMZAID);
        } else if (displayType.equals(databaseHandler.tblClassLocation)) {
            anyClassListDB = databaseHandler.getTblUniversityUserClass(PSMZAID);
        }

        anyClassListDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getSpinnerList(dataSnapshot,displayType);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        spnAnyClassIDInfo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                anyClassIDSelection = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getSpinnerList(DataSnapshot dataSnapshot, String tblSelection) {
        String anyClassID = "";
        String subjectID = "";

        int anyListTotal = (int) dataSnapshot.getChildrenCount();
        int index = 0;

        anyClassList = new String[anyListTotal];
        subjectList = new String[anyListTotal];

        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            if (tblSelection.equals(databaseHandler.tblSubject)) {
                SubjectModel subjectModel = postSnapshot.getValue(SubjectModel.class);
                subjectID = subjectModel.subjectID;
                subjectList[index] = subjectID;
            } else if (tblSelection.equals(databaseHandler.tblUserClass)) {
                ClassLocationModel classLocationModel = postSnapshot.getValue(ClassLocationModel.class);
                anyClassID = classLocationModel.classLocationID;
                anyClassList[index] = anyClassID;
            } else if (tblSelection.equals(databaseHandler.tblClassLocation)) {
                UserClassModel userClassModel = postSnapshot.getValue(UserClassModel.class);
                anyClassID = userClassModel.userClassID;
                anyClassList[index] = anyClassID;
            }
            index++;
        }

        if (tblSelection.equals(databaseHandler.tblSubject)) {
            adpSubj = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subjectList);
            adpSubj.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnSubjectIDInfo.setAdapter(adpSubj);
        } else {
            adpAnyClass = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, anyClassList);
            adpAnyClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnAnyClassIDInfo.setAdapter(adpAnyClass);
        }
    }

    private void addTimeFrameDetails() {
//        if (displayType.equals(databaseHandler.tblUserClass)) {
//
//        } else if (displayType.equals(databaseHandler.tblClassLocation)) {
//
//        }
//
//        final String strUserIDReg = databaseHandler.getUserID();
//        final Map<String, Object> dataMap = new HashMap<String, Object>();
//
//        UserModel userModel = new UserModel(strUserIDReg,strUserEmailReg,strUserNameReg);
//
//        dataMap.put(strUserIDReg, userModel.toMap());
//        databaseHandler.getTblUser().updateChildren(dataMap);
    }

}
