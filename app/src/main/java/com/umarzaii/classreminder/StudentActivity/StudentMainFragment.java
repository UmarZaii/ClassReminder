package com.umarzaii.classreminder.StudentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.umarzaii.classreminder.DeptAdminActivity.DeptAdminAddSubjectFragment;
import com.umarzaii.classreminder.DeptAdminActivity.DeptAdminClassLocationFragment;
import com.umarzaii.classreminder.DeptAdminActivity.DeptAdminMyQRCodeFragment;
import com.umarzaii.classreminder.DeptAdminActivity.DeptAdminScanLectFragment;
import com.umarzaii.classreminder.DeptAdminActivity.DeptAdminUserClassFragment;
import com.umarzaii.classreminder.GeneralActivity.LoginActivity;
import com.umarzaii.classreminder.Handler.DatabaseHandler;
import com.umarzaii.classreminder.Handler.FragmentHandler;
import com.umarzaii.classreminder.R;

public class StudentMainFragment extends Fragment {

    private DatabaseHandler databaseHandler;
    private FragmentHandler fragmentHandler;

    private Button btnLogOut;
    private Button btnMyQRCode;
    private Button btnTimeTable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_fragm_main,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Main Fragment");
        View v = getView();

        databaseHandler = new DatabaseHandler();
        fragmentHandler = new FragmentHandler(getActivity().getSupportFragmentManager());

        btnTimeTable = (Button)v.findViewById(R.id.btnTimeTable);
        btnMyQRCode = (Button)v.findViewById(R.id.btnMyQRCode);
        btnLogOut = (Button)v.findViewById(R.id.btnLogOut);

        btnTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHandler.stackFragment(new StudentTimeFrameDayFragment(),"TimeFrameDay");
            }
        });

        btnMyQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHandler.stackFragment(new StudentMyQRCodeFragment(),"MyQRCode");
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHandler.getFirebaseAuth().signOut();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }

}
