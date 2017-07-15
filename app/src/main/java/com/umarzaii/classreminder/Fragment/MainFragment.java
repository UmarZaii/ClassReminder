package com.umarzaii.classreminder.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.umarzaii.classreminder.Activity.LoginActivity;
import com.umarzaii.classreminder.Handler.DatabaseHandler;
import com.umarzaii.classreminder.Handler.FragmentHandler;
import com.umarzaii.classreminder.R;

public class MainFragment extends Fragment {

    private DatabaseHandler databaseHandler;
    private FragmentHandler fragmentHandler;

    private Button btnLogOut, btnMyQRCode, btnGoToAddClassLocation, btnGoToAddSubject, btnGoToUserClass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragm_main,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Main Fragment");
        View v = getView();

        databaseHandler = new DatabaseHandler();
        fragmentHandler = new FragmentHandler(getActivity().getSupportFragmentManager());

        btnGoToAddClassLocation = (Button)v.findViewById(R.id.btnGoToAddClassLocation);
        btnGoToAddSubject = (Button)v.findViewById(R.id.btnGoToAddSubject);
        btnGoToUserClass = (Button)v.findViewById(R.id.btnGoToUserClass);
        btnMyQRCode = (Button)v.findViewById(R.id.btnMyQRCode);
        btnLogOut = (Button)v.findViewById(R.id.btnLogOut);

        btnGoToAddClassLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHandler.stackFragment(new AddClassLocationFragment(),"AddClassLocation");
            }
        });

        btnGoToAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHandler.stackFragment(new AddSubjectFragment(),"AddSubject");
            }
        });

        btnGoToUserClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHandler.stackFragment(new UserClassFragment(),"UserClass");
            }
        });

        btnMyQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHandler.stackFragment(new MyQRCodeFragment(),"MyQRCode");
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
