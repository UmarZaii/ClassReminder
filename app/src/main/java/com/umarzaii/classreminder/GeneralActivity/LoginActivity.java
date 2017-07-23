package com.umarzaii.classreminder.GeneralActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.umarzaii.classreminder.DeptAdminActivity.DeptAdminMainActivity;
import com.umarzaii.classreminder.Handler.DatabaseHandler;
import com.umarzaii.classreminder.R;
import com.umarzaii.classreminder.StudentActivity.StudentMainActivity;

import java.util.concurrent.Semaphore;

import static com.umarzaii.classreminder.Handler.DatabaseHandler.credentials;
import static com.umarzaii.classreminder.Handler.DatabaseHandler.uniAdminDepartment;
import static com.umarzaii.classreminder.Handler.DatabaseHandler.uniHeadDepartment;
import static com.umarzaii.classreminder.Handler.DatabaseHandler.uniLecturer;
import static com.umarzaii.classreminder.Handler.DatabaseHandler.uniStudent;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHandler databaseHandler;

    private EditText edtUserEmailLogin, edtUserPassLogin;
    private Button btnLogin, btnGoToSignUp;
    private ProgressDialog progressDialog;

    private String strUserEmail;
    private String strUserPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);

        databaseHandler = new DatabaseHandler();

        edtUserEmailLogin = (EditText)findViewById(R.id.edtUserEmailLogin);
        edtUserPassLogin = (EditText)findViewById(R.id.edtUserPassLogin);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnGoToSignUp = (Button)findViewById(R.id.btnGoToSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUserEmail = edtUserEmailLogin.getText().toString().trim();
                strUserPass = edtUserPassLogin.getText().toString().trim();

                if (inputCheck()) {
                    userLogin();
                }
            }
        });

        btnGoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

    }

    private void checkUserCredentials(final String userID) {
        databaseHandler.getTblUser(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(credentials)) {
                    getUserRole(databaseHandler.getUserID());
                } else {
                    startActivity(new Intent(LoginActivity.this, CredentialsCheckActivity.class));
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private boolean inputCheck() {

        if(TextUtils.isEmpty(strUserEmail) || strUserEmail == null) {
            Toast.makeText(this, "Please input your email", Toast.LENGTH_LONG).show();
            return false;
        } else if(TextUtils.isEmpty(strUserPass) || strUserPass == null) {
            Toast.makeText(this, "Please input your password", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }

    }

    private void userLogin() {

        progressDialog.setMessage("LogIn, Please Wait...");
        progressDialog.show();

        databaseHandler.getFirebaseAuth().signInWithEmailAndPassword(strUserEmail,strUserPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    Log.d("Error", task.getException().toString());
                } else if (!databaseHandler.getCurrentUser().isEmailVerified()) {
                    Intent intent = new Intent(LoginActivity.this, ActivationActivity.class);
                    intent.putExtra("userEmail", strUserEmail);
                    intent.putExtra("userPass", strUserPass);
                    startActivity(intent);
                    progressDialog.dismiss();
                } else {
                    checkUserCredentials(databaseHandler.getUserID());
                }
            }
        });

    }

    private void getUserRole(final String userID) {

        databaseHandler.getTblUserCredentialsUserRole(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(uniAdminDepartment)) {
                    startActivity(new Intent(LoginActivity.this, DeptAdminMainActivity.class));
                    finish();
                    progressDialog.dismiss();
                } else if (dataSnapshot.hasChild(uniHeadDepartment)) {
                    Toast.makeText(LoginActivity.this, "UNIHEAD Not Available", Toast.LENGTH_SHORT).show();
                } else if (dataSnapshot.hasChild(uniLecturer)) {
                    startActivity(new Intent(LoginActivity.this, DeptAdminMainActivity.class));
                    finish();
                    progressDialog.dismiss();
                    //change activity
                } else if (dataSnapshot.hasChild(uniStudent)) {
                    startActivity(new Intent(LoginActivity.this, StudentMainActivity.class));
                    finish();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}