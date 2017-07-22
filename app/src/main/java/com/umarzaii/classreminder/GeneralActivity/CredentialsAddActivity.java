package com.umarzaii.classreminder.GeneralActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.umarzaii.classreminder.Handler.DatabaseHandler;
import com.umarzaii.classreminder.R;

public class CredentialsAddActivity extends AppCompatActivity {

    private DatabaseHandler databaseHandler;

    private EditText edtStudentID;
    private Spinner spnCourseID, spnUserClassID;
    private Button btnAddStudentCredentials;

    private String strStudentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_activity_credentialsadd);

        databaseHandler = new DatabaseHandler();

        edtStudentID = (EditText)findViewById(R.id.edtStudentID);
        spnCourseID = (Spinner)findViewById(R.id.spnCourseID);
        spnUserClassID = (Spinner)findViewById(R.id.spnUserClassID);
        btnAddStudentCredentials = (Button) findViewById(R.id.btnAddStudentCredentials);

//        btnAddStudentCredentials.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                strStudentID = edtStudentID.getText().toString().trim();
//
//                if (inputCheck()) {
//
//                }
//            }
//        });
    }

//    private void getCourseIDList() {
//
//        databaseHandler.getTblUniversityCourse(PSMZA).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }

//    private boolean inputCheck() {
//        if(TextUtils.isEmpty(strStudentID) || strStudentID == null) {
//            Toast.makeText(this, "Please input your studentID", Toast.LENGTH_LONG).show();
//            return false;
//        } else if(TextUtils.isEmpty(strUserPass) || strUserPass == null) {
//            Toast.makeText(this, "Please input your password", Toast.LENGTH_LONG).show();
//            return false;
//        } else {
//            return true;
//        }
//    }

}
