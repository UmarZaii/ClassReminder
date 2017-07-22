package com.umarzaii.classreminder.GeneralActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.umarzaii.classreminder.DeptAdminActivity.DeptAdminMainActivity;
import com.umarzaii.classreminder.Handler.DatabaseHandler;
import com.umarzaii.classreminder.R;
import com.umarzaii.classreminder.StudentActivity.StudentMainActivity;

import static com.umarzaii.classreminder.Handler.DatabaseHandler.credentials;
import static com.umarzaii.classreminder.Handler.DatabaseHandler.uniAdminDepartment;
import static com.umarzaii.classreminder.Handler.DatabaseHandler.uniHeadDepartment;
import static com.umarzaii.classreminder.Handler.DatabaseHandler.uniLecturer;
import static com.umarzaii.classreminder.Handler.DatabaseHandler.uniStudent;

public class LaunchActivity extends AppCompatActivity {


    private DatabaseHandler databaseHandler;
    private FirebaseAuth.AuthStateListener fAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_activity_launch);

        databaseHandler = new DatabaseHandler();

        fAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    checkUserLogin();
                } else {
                    startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseHandler.getFirebaseAuth().addAuthStateListener(fAuthListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHandler.getFirebaseAuth().removeAuthStateListener(fAuthListener);
    }

    private void checkUserCredentials(final String userID) {

        databaseHandler.getTblUser(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(credentials)) {
                    getUserRole(databaseHandler.getUserID());
                } else {
                    databaseHandler.getFirebaseAuth().signOut();
                    startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void checkUserLogin() {

        if (databaseHandler.getCurrentUser().isEmailVerified()) {
            checkUserCredentials(databaseHandler.getUserID());
        } else {
            databaseHandler.getFirebaseAuth().signOut();
            startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
            finish();
        }

    }

    private void getUserRole(final String userID) {

        databaseHandler.getTblUserCredentialsUserRole(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(uniAdminDepartment)) {
                    startActivity(new Intent(LaunchActivity.this, DeptAdminMainActivity.class));
                    finish();
                } else if (dataSnapshot.hasChild(uniHeadDepartment)) {
                    Toast.makeText(LaunchActivity.this, "UNIHEAD", Toast.LENGTH_SHORT).show();
                } else if (dataSnapshot.hasChild(uniLecturer)) {
                    Toast.makeText(LaunchActivity.this, "UNILECT", Toast.LENGTH_SHORT).show();
                } else if (dataSnapshot.hasChild(uniStudent)) {
                    startActivity(new Intent(LaunchActivity.this, StudentMainActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
