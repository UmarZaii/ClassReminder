package com.umarzaii.classreminder.GeneralActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.umarzaii.classreminder.Handler.DatabaseHandler;
import com.umarzaii.classreminder.Model.CourseModel;
import com.umarzaii.classreminder.Model.UserClassModel;
import com.umarzaii.classreminder.Model.UserModel;
import com.umarzaii.classreminder.R;
import com.umarzaii.classreminder.StudentActivity.StudentMainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.umarzaii.classreminder.Handler.DatabaseHandler.PSMZAID;
import static com.umarzaii.classreminder.Handler.DatabaseHandler.credentials;
import static com.umarzaii.classreminder.Handler.DatabaseHandler.studentList;
import static com.umarzaii.classreminder.Handler.DatabaseHandler.uniStudent;

public class CredentialsAddActivity extends AppCompatActivity {

    private DatabaseHandler databaseHandler;

    private ArrayAdapter adpCourserID;
    private ArrayAdapter adpUserClassID;
    private ArrayList<String> courseIDList;
    private ArrayList<String> userClassIDList;

    private EditText edtStudentID;
    private Spinner spnCourseID, spnUserClassID;
    private Button btnAddStudentCredentials;

    private String strStudentID;
    private String strCourseIDSelection;
    private String strUserClassIDSelection;

    private Boolean boolCourseID = true;
    private Boolean boolUserClassID = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_activity_credentialsadd);

        databaseHandler = new DatabaseHandler();

        edtStudentID = (EditText)findViewById(R.id.edtStudentID);
        spnCourseID = (Spinner)findViewById(R.id.spnCourseID);
        spnUserClassID = (Spinner)findViewById(R.id.spnUserClassID);
        btnAddStudentCredentials = (Button) findViewById(R.id.btnAddStudentCredentials);

        getCourseIDList();
        getUserClassIDList();

        btnAddStudentCredentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strStudentID = edtStudentID.getText().toString().trim();

                if (inputCheck()) {
                    addCredentials();
                }
            }
        });
    }

    private void getCourseIDList() {
        databaseHandler.getTblUniversityCourse(PSMZAID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (boolCourseID) {
                    getSpinnerCourseID(dataSnapshot);
                    boolCourseID = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        spnCourseID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strCourseIDSelection = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getSpinnerCourseID(DataSnapshot dataSnapshot) {
        courseIDList = new ArrayList<String>();
        userClassIDList = new ArrayList<String>();

        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            UserClassModel userClassModel = postSnapshot.getValue(UserClassModel.class);
            String userClassID = userClassModel.userClassID;
            userClassIDList.add(userClassID);

            CourseModel courseModel = postSnapshot.getValue(CourseModel.class);
            String courseID = courseModel.courseID;
            courseIDList.add(courseID);
        }

        adpCourserID = new ArrayAdapter<String>(CredentialsAddActivity.this, android.R.layout.simple_spinner_item, courseIDList);
        adpCourserID.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCourseID.setAdapter(adpCourserID);
    }

    private void getUserClassIDList() {
        databaseHandler.getTblUniversityCourse(PSMZAID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (boolUserClassID) {
                    getSpinnerUserClassID(dataSnapshot);
                    boolUserClassID = false;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        spnUserClassID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strUserClassIDSelection = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getSpinnerUserClassID(DataSnapshot dataSnapshot) {
        userClassIDList = new ArrayList<String>();

        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            UserClassModel userClassModel = postSnapshot.getValue(UserClassModel.class);
            String userClassID = userClassModel.userClassID;
            userClassIDList.add(userClassID);
        }

        adpUserClassID = new ArrayAdapter<String>(CredentialsAddActivity.this, android.R.layout.simple_spinner_item, userClassIDList);
        adpUserClassID.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnUserClassID.setAdapter(adpUserClassID);
    }

    private boolean inputCheck() {
        if(TextUtils.isEmpty(strStudentID) || strStudentID == null) {
            Toast.makeText(this, "Please input your studentID", Toast.LENGTH_LONG).show();
            return false;
        } else if(TextUtils.isEmpty(strCourseIDSelection) || strCourseIDSelection == null) {
            Toast.makeText(this, "Please select your course", Toast.LENGTH_LONG).show();
            return false;
        } else if(TextUtils.isEmpty(strUserClassIDSelection) || strUserClassIDSelection == null) {
            Toast.makeText(this, "Please select your class", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private void addCredentials() {
        String userID = databaseHandler.getUserID();

        final Map<String, Object> dataMapUser = new HashMap<String, Object>();
        final Map<String, Object> dataMapCourseLect = new HashMap<String, Object>();

        UserModel userModel = new UserModel(userID,PSMZAID,uniStudent,strStudentID,strCourseIDSelection,strUserClassIDSelection);

        dataMapUser.put(credentials, userModel.credentialsStudentToMap());
        databaseHandler.getTblUser(userID).updateChildren(dataMapUser);

        dataMapCourseLect.put(studentList, userModel.userIDToMap());
        databaseHandler.getTblUniversityUserClass(PSMZAID,strUserClassIDSelection).updateChildren(dataMapCourseLect);

        Intent intent = new Intent(getApplicationContext(), StudentMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
