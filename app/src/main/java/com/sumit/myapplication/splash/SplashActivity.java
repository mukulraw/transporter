package com.sumit.myapplication.splash;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sumit.myapplication.driver.DriverMainActivity;
import com.sumit.myapplication.otp.NumberIntroActivity;
import com.sumit.myapplication.otp.SharedData;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.sumit.myapplication.MainActivity;
import com.sumit.myapplication.R;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    SharedData sharedData;
    public static String currentUserType;

    ProgressBar progressBar;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    //taking bad permission at runntime
    String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar=findViewById(R.id.progressBar_cyclic);
        progressBar.setVisibility(View.VISIBLE);

        sharedData = new SharedData(SplashActivity.this);
        findUserType();
        Toast.makeText(SplashActivity.this, currentUserType, Toast.LENGTH_LONG).show();

        String rationale = "Please provide location permission so that you can ...";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");

        Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.
                //handler
       new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {

                        Cursor cursor = sharedData.getAllData();
                        if(cursor.getCount() == 0) {
                            Intent intent = new Intent(SplashActivity.this, NumberIntroActivity.class);
                            startActivity(intent);
                            finish();
                        } else{
                            while (cursor.moveToNext()){
                                if (cursor.getString(2).equals("1")){
                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    return;
                                }else {
                                    Intent intent = new Intent(SplashActivity.this, DriverMainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    return;
                                }
                            }
                        }
                    }
                }, 1000);
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                Toast.makeText(context, "Why you denied permissions", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void findUserType() {
        Cursor cursor = sharedData.getAllData();
        if(cursor.getCount() == 0) {
            return;
        }

//        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext()) {
//            buffer.append("Name" + cursor.getString(0));
            currentUserType = cursor.getString(2);
        }
    }
}
