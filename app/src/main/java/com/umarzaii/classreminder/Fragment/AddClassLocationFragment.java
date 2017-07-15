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

import com.umarzaii.classreminder.Handler.DatabaseHandler;
import com.umarzaii.classreminder.Handler.FragmentHandler;
import com.umarzaii.classreminder.Model.ClassLocationModel;
import com.umarzaii.classreminder.Model.TimeFrameModel;
import com.umarzaii.classreminder.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.umarzaii.classreminder.Handler.DatabaseHandler.PSMZAID;

public class AddClassLocationFragment extends Fragment {

    private DatabaseHandler databaseHandler;
    private FragmentHandler fragmentHandler;

    private EditText edtClassLocationID;
    private Button btnAddClassLocation;

    private String strClassLocationID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragm_addclasslocation,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Add Class Location");
        View v = getView();

        databaseHandler = new DatabaseHandler();
        fragmentHandler = new FragmentHandler(getActivity().getSupportFragmentManager());

        edtClassLocationID = (EditText)v.findViewById(R.id.edtClassLocationID);
        btnAddClassLocation = (Button)v.findViewById(R.id.btnAddClassLocation);

        btnAddClassLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strClassLocationID = edtClassLocationID.getText().toString().trim();

                if (inputCheck()) {
                    addClassLocation();
                }
            }
        });
    }

    private boolean inputCheck() {
        if(TextUtils.isEmpty(strClassLocationID)) {
            Toast.makeText(getActivity(), "Please class location ID", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private void addClassLocation() {
        final Map<String, Object> classLocationDataMap = new HashMap<String, Object>();
        final Map<String, Object> timeFrameDataMap = new HashMap<String, Object>();

        ClassLocationModel classLocationModel = new ClassLocationModel(strClassLocationID);
        TimeFrameModel timeFrameModel = new TimeFrameModel();

        classLocationDataMap.put(strClassLocationID, classLocationModel.detailsToMap());
        databaseHandler.getTblUniversityClassLocation(PSMZAID).updateChildren(classLocationDataMap);

        timeFrameDataMap.put("timeFrame", timeFrameModel.timeFrameInitLoop(TimeFrameModel.strClassLocation));
        databaseHandler.getTblUniversityClassLocation(PSMZAID,strClassLocationID).updateChildren(timeFrameDataMap);

        fragmentHandler.popBackStack("AddClassLocation");
    }

}
