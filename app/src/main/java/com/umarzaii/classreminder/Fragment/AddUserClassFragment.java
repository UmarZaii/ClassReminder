package com.umarzaii.classreminder.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.umarzaii.classreminder.Handler.DatabaseHandler;
import com.umarzaii.classreminder.Handler.FragmentHandler;
import com.umarzaii.classreminder.Model.TimeFrameModel;
import com.umarzaii.classreminder.Model.UserClassModel;
import com.umarzaii.classreminder.R;

import java.util.HashMap;
import java.util.Map;

import static com.umarzaii.classreminder.Handler.DatabaseHandler.PSMZAID;
import static com.umarzaii.classreminder.Handler.DatabaseHandler.courseID;

public class AddUserClassFragment extends Fragment {

    private DatabaseHandler databaseHandler;
    private FragmentHandler fragmentHandler;

    private EditText edtUserClassIDReg;
    private Button btnAddUserClass;

    private String strUserClassIDReg, strUserCourseID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragm_adduserclass,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Add User Class");
        View v = getView();

        databaseHandler = new DatabaseHandler();
        fragmentHandler = new FragmentHandler(getActivity().getSupportFragmentManager());

        edtUserClassIDReg = (EditText)v.findViewById(R.id.edtUserClassIDReg);
        btnAddUserClass = (Button)v.findViewById(R.id.btnAddUserClass);

        String userID = databaseHandler.getUserID();
        getUserCourseID(userID);

        btnAddUserClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUserClassIDReg = edtUserClassIDReg.getText().toString().trim();

                if (inputCheck()) {
                    addUserClass();
                }
            }
        });
    }

    private boolean inputCheck() {
        if (TextUtils.isEmpty(strUserClassIDReg)) {
            Toast.makeText(getActivity(), "Please input user class id", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void getUserCourseID(String userID) {
        databaseHandler.getTblUserCredentials(userID).child(courseID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                strUserCourseID = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addUserClass() {
        final Map<String, Object> dataMap = new HashMap<String, Object>();
        final Map<String, Object> timeFrameDataMap = new HashMap<String, Object>();

        UserClassModel userClassModel = new UserClassModel(strUserClassIDReg);
        TimeFrameModel timeFrameModel = new TimeFrameModel();

        dataMap.put(strUserClassIDReg, userClassModel.toMap());
        databaseHandler.getTblUniversityUserClass(PSMZAID).updateChildren(dataMap);
        databaseHandler.getTblUniversityCourseClassList(PSMZAID,strUserCourseID).updateChildren(dataMap);

        timeFrameDataMap.put(databaseHandler.timeFrameDay, timeFrameModel.timeFrameInitLoop(TimeFrameModel.strUserClass));
        databaseHandler.getTblUniversityUserClass(PSMZAID,strUserClassIDReg).updateChildren(timeFrameDataMap);

        fragmentHandler.popBackStack("AddUserClass");
    }

}
