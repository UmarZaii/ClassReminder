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
import com.umarzaii.classreminder.Handler.DatabaseHandler;
import com.umarzaii.classreminder.Model.UserModel;
import com.umarzaii.classreminder.R;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private DatabaseHandler databaseHandler;

    private EditText edtUserNameReg, edtUserEmailReg, edtUserPassReg;
    private Button btnSignUp;
    private ProgressDialog progressDialog;

    private String strUserNameReg, strUserEmailReg, strUserPassReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_activity_signup);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);

        databaseHandler = new DatabaseHandler();

        edtUserNameReg = (EditText)findViewById(R.id.edtUserNameReg);
        edtUserEmailReg = (EditText)findViewById(R.id.edtUserEmailReg);
        edtUserPassReg = (EditText)findViewById(R.id.edtUserPassReg);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUserNameReg = edtUserNameReg.getText().toString().trim();
                strUserEmailReg = edtUserEmailReg.getText().toString().trim();
                strUserPassReg = edtUserPassReg.getText().toString().trim();

                if (inputCheck()) {
                    signUp();
                }
            }
        });

    }

    private boolean inputCheck() {
        if(TextUtils.isEmpty(strUserNameReg)) {
            Toast.makeText(SignUpActivity.this, "Please input your user name", Toast.LENGTH_LONG).show();
            return false;
        } else if (TextUtils.isEmpty(strUserEmailReg)){
            Toast.makeText(SignUpActivity.this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(strUserPassReg)){
            Toast.makeText(SignUpActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void signUp() {

        progressDialog.setMessage("Signing Up, Please Wait...");
        progressDialog.show();

        databaseHandler.getFirebaseAuth().createUserWithEmailAndPassword(strUserEmailReg,strUserPassReg).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){

                    progressDialog.dismiss();
                    Log.d("Unsuccessfull", task.getException().toString());
                    Toast.makeText(SignUpActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();

                } else {

                    final String strUserIDReg = databaseHandler.getUserID();
                    final Map<String, Object> dataMap = new HashMap<String, Object>();

                    UserModel userModel = new UserModel(strUserIDReg,strUserEmailReg,strUserNameReg);

                    dataMap.put(strUserIDReg, userModel.detailsToMap());
                    databaseHandler.getTblUser().updateChildren(dataMap);

                    databaseHandler.getCurrentUser().sendEmailVerification();

                    Intent intent = new Intent(SignUpActivity.this, ActivationActivity.class);
                    intent.putExtra("userEmail", strUserEmailReg);
                    intent.putExtra("userPass", strUserPassReg);
                    startActivity(intent);

                    finish();
                    progressDialog.dismiss();

                }
            }
        });

    }

}
