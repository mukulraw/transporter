package com.app.onnwaytransporter.splash;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.onnwaytransporter.AppSignatureHashHelper;
import com.app.onnwaytransporter.GettingStarted;
import com.app.onnwaytransporter.SelectUserType;
import com.app.onnwaytransporter.SharePreferenceUtils;
import com.app.onnwaytransporter.driver.DriverMainActivity;
import com.app.onnwaytransporter.otp.SharedData;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.app.onnwaytransporter.MainActivity;
import com.app.onnwaytransporter.R;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    SharedData sharedData;
    public static String currentUserType;

    ProgressBar progressBar;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    //taking bad permission at runntime
    String[] permissions = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar=findViewById(R.id.progressBar_cyclic);
        progressBar.setVisibility(View.VISIBLE);

        // Set permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions = new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_IMAGES,
            };
        } else {
            permissions = new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
        }

        AppSignatureHashHelper appSignatureHashHelper = new AppSignatureHashHelper(this);

        // This code requires one time to get Hash keys do comment and share key
        Log.i("SMShash", "HashKey: " + appSignatureHashHelper.getAppSignatures().get(0));

        sharedData = new SharedData(SplashActivity.this);
        //findUserType();

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

                        String userId = SharePreferenceUtils.getInstance().getString("userId");
                        String type = SharePreferenceUtils.getInstance().getString("user");


                        if (userId.length() > 0)
                        {

                            if (type.equals("transporter"))
                            {
                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(intent);
                                finishAffinity();

                            }
                            else if (type.equals("driver"))
                            {
                                Intent intent = new Intent(SplashActivity.this, DriverMainActivity.class);
                                startActivity(intent);
                                finishAffinity();

                            }
                            else
                            {
                                Intent intent = new Intent(SplashActivity.this, SelectUserType.class);
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finishAffinity();

                            }

                        }
                        else
                        {
                            Intent intent = new Intent(SplashActivity.this, GettingStarted.class);
                            startActivity(intent);
                            finishAffinity();
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
