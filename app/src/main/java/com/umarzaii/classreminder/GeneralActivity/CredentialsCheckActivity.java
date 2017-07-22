package com.umarzaii.classreminder.GeneralActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.umarzaii.classreminder.Handler.DatabaseHandler;
import com.umarzaii.classreminder.R;

public class CredentialsCheckActivity extends AppCompatActivity {

    private DatabaseHandler databaseHandler;

    private Button btnContAsLect, btnContAsStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_activity_credentialscheck);

        databaseHandler = new DatabaseHandler();

        btnContAsLect = (Button)findViewById(R.id.btnContAsLect);
        btnContAsStudent = (Button)findViewById(R.id.btnContAsStudent);

        btnContAsLect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CredentialsCheckActivity.this, QRCodeActivity.class));
            }
        });

        btnContAsStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CredentialsCheckActivity.this, CredentialsAddActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        databaseHandler.getFirebaseAuth().signOut();
        super.onBackPressed();
    }

}
