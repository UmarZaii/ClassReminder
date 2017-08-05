package com.umarzaii.classreminder.DeptAdminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.umarzaii.classreminder.GeneralActivity.LoginActivity;
import com.umarzaii.classreminder.Handler.DatabaseHandler;
import com.umarzaii.classreminder.Handler.FragmentHandler;
import com.umarzaii.classreminder.R;

public class DeptAdminMainFragment extends Fragment {

    private DatabaseHandler databaseHandler;
    private FragmentHandler fragmentHandler;

    private Button btnGoToScanLect;
    private Button btnLogOut;
    private Button btnMyQRCode;
    private Button btnGoToAddClassLocation;
    private Button btnGoToAddSubject;
    private Button btnGoToUserClass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.deptadmin_fragm_main,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("DEPT ADMIN");
        View v = getView();

        databaseHandler = new DatabaseHandler();
        fragmentHandler = new FragmentHandler(getActivity().getSupportFragmentManager());

        btnGoToScanLect = (Button)v.findViewById(R.id.btnGoToScanLect);
        btnGoToAddClassLocation = (Button)v.findViewById(R.id.btnGoToClassLocation);
        btnGoToAddSubject = (Button)v.findViewById(R.id.btnGoToAddSubject);
        btnGoToUserClass = (Button)v.findViewById(R.id.btnGoToUserClass);
        btnMyQRCode = (Button)v.findViewById(R.id.btnMyQRCode);
        btnLogOut = (Button)v.findViewById(R.id.btnLogOut);

        btnGoToScanLect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHandler.stackFragment(new DeptAdminScanLectFragment(),"ScanLect");
            }
        });

        btnGoToAddClassLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHandler.stackFragment(new DeptAdminClassLocationFragment(),"ClassLocation");
            }
        });

        btnGoToAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHandler.stackFragment(new DeptAdminAddSubjectFragment(),"AddSubject");
            }
        });

        btnGoToUserClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHandler.stackFragment(new DeptAdminUserClassFragment(),"UserClass");
            }
        });

        btnMyQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHandler.stackFragment(new DeptAdminMyQRCodeFragment(),"MyQRCode");
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
