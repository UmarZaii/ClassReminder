package com.umarzaii.classreminder.GeneralActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.umarzaii.classreminder.DeptAdminActivity.DeptAdminMainActivity;
import com.umarzaii.classreminder.Handler.DatabaseHandler;
import com.umarzaii.classreminder.Handler.FragmentHandler;
import com.umarzaii.classreminder.R;

import static com.umarzaii.classreminder.Handler.DatabaseHandler.uniAdminDepartment;
import static com.umarzaii.classreminder.Handler.DatabaseHandler.uniHeadDepartment;
import static com.umarzaii.classreminder.Handler.DatabaseHandler.uniLecturer;
import static com.umarzaii.classreminder.Handler.DatabaseHandler.uniStudent;

public class QRCodeActivity extends AppCompatActivity {

    private DatabaseHandler databaseHandler;
    private FragmentHandler fragmentHandler;

    private ImageView imgQRCode;
    private TextView txtUserID;

    private String strMyQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_activity_qrcode);

        databaseHandler = new DatabaseHandler();
        fragmentHandler = new FragmentHandler(this.getSupportFragmentManager());

        imgQRCode = (ImageView)findViewById(R.id.imgQrCode);
        txtUserID = (TextView)findViewById(R.id.txtUserID);

        strMyQRCode = databaseHandler.getUserID();
        txtUserID.setText(strMyQRCode);

        getMyQRCode();
        getUserRole(databaseHandler.getUserID());
    }

    private void getMyQRCode() {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(strMyQRCode, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgQRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void getUserRole(String userID) {

        databaseHandler.getTblUserCredentialsUserRole(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.hasChild(uniAdminDepartment)) {
//                    Intent intent = new Intent(getApplicationContext(), DeptAdminMainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    finish();
//                } else
                if (dataSnapshot.hasChild(uniHeadDepartment)) {

                } else if (dataSnapshot.hasChild(uniLecturer)) {
                    Intent intent = new Intent(getApplicationContext(), DeptAdminMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
