package com.umarzaii.classreminder.DeptAdminActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.umarzaii.classreminder.Handler.FragmentHandler;
import com.umarzaii.classreminder.R;

public class DeptAdminMainActivity extends AppCompatActivity {

    private FragmentHandler fragmentHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deptadmin_activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentHandler = new FragmentHandler(getSupportFragmentManager());

        fragmentHandler.startFragment(new DeptAdminMainFragment());
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}
