package com.umarzaii.classreminder.DeptAdminActivity;

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
import com.umarzaii.classreminder.Model.SubjectModel;
import com.umarzaii.classreminder.R;

import java.util.HashMap;
import java.util.Map;

import static com.umarzaii.classreminder.Handler.DatabaseHandler.PSMZAID;

public class DeptAdminAddSubjectFragment extends Fragment {

    private DatabaseHandler databaseHandler;
    private FragmentHandler fragmentHandler;

    private EditText edtSubjectID, edtSubjectName;
    private Button btnAddSubject;

    private String strSubjectID, strSubjectName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.deptadmin_fragm_addsubject,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Add Subject");
        View v = getView();

        databaseHandler = new DatabaseHandler();
        fragmentHandler = new FragmentHandler(getActivity().getSupportFragmentManager());

        edtSubjectID = (EditText)v.findViewById(R.id.edtSubjectID);
        edtSubjectName = (EditText)v.findViewById(R.id.edtSubjectName);
        btnAddSubject = (Button)v.findViewById(R.id.btnAddSubject);

        btnAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strSubjectID = edtSubjectID.getText().toString().trim();
                strSubjectName = edtSubjectName.getText().toString().trim();

                if (inputCheck()) {
                    addSubject();
                }
            }
        });
    }

    private boolean inputCheck() {
        if (TextUtils.isEmpty(strSubjectID)) {
            Toast.makeText(getActivity(), "Please input subject ID", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(strSubjectName)) {
            Toast.makeText(getActivity(), "Please input subject Name", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void addSubject() {
        final Map<String, Object> dataMap = new HashMap<String, Object>();

        SubjectModel subjectModel = new SubjectModel(strSubjectID,strSubjectName);

        dataMap.put(strSubjectID, subjectModel.toMap());
        databaseHandler.getTblUniversitySubject(PSMZAID).updateChildren(dataMap);

        fragmentHandler.popBackStack("AddSubject");
    }

}
