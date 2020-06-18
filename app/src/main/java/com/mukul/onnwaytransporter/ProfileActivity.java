package com.mukul.onnwaytransporter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mukul.onnwaytransporter.driver.profilerelated.AddTruckDetails;
import com.mukul.onnwaytransporter.driver.profilerelated.DriverTruckDetails;
import com.mukul.onnwaytransporter.profilerelated.KycProviderActivity;
import com.mukul.onnwaytransporter.profilerelated.NamePhoneActivity;
import com.mukul.onnwaytransporter.profilerelated.UpdateCityActivity;
import com.mukul.onnwaytransporter.profilerelated.UpdateRoutesActivity;
import com.mukul.onnwaytransporter.otp.SharedData;
import com.mukul.onnwaytransporter.splash.SplashActivity;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    SharedData sharedData;

    private TextView userName, userPhone , user_city;
    private TextView transportName;
    private LinearLayout truckDetails, addTruck, addCity, addoperatedRoutes, logout, changeProfile;
    private LinearLayout kycProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Profile");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sharedData = new SharedData(ProfileActivity.this);

        //textview
        userName = (TextView) findViewById(R.id.user_name);
        user_city = (TextView) findViewById(R.id.user_city);
        userPhone = (TextView) findViewById(R.id.user_phone);
        transportName = (TextView) findViewById(R.id.transport_name);




        //Image view as button
        changeProfile = (LinearLayout) findViewById(R.id.change_profile);
        truckDetails = (LinearLayout) findViewById(R.id.truck_details);
        addTruck = (LinearLayout) findViewById(R.id.add_truck);
        addCity = (LinearLayout) findViewById(R.id.change_city);
        addoperatedRoutes = (LinearLayout) findViewById(R.id.change_route);
        kycProvider=(LinearLayout)findViewById(R.id.kyc_provider);
        logout = (LinearLayout) findViewById(R.id.logout);



        //change profile i.e. name and mobile
        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, NamePhoneActivity.class);
                startActivity(intent);
            }
        });

        //truck details
        truckDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, DriverTruckDetails.class);
                startActivity(intent);
            }
        });

        //add truck
        addTruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, AddTruckDetails.class);
                startActivity(intent);
            }
        });

        //add city
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UpdateCityActivity.class);
                startActivity(intent);
            }
        });

        //add operated routes
        addoperatedRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(DriverProfileActivity.this,"hello",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ProfileActivity.this, UpdateRoutesActivity.class);
               startActivity(intent);
            }
        });
        //kyc
        kycProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, KycProviderActivity.class);
                startActivity(intent);

            }
        });

        //logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FirebaseInstanceId.getInstance().deleteInstanceId();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


                SharePreferenceUtils.getInstance().deletePref();
                startActivity(new Intent(ProfileActivity.this, SplashActivity.class));
                finishAffinity();
            }
        });
    }
    public void deleteData() {
        Integer deletedRow = sharedData.deleteData(MainActivity.currenntMobileActive);
        if(deletedRow > 0){
            //deleted
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        userPhone.setText("Ph. - " + SharePreferenceUtils.getInstance().getString("phone"));
        userName.setText(SharePreferenceUtils.getInstance().getString("name"));
        transportName.setText(SharePreferenceUtils.getInstance().getString("email"));
        user_city.setText(SharePreferenceUtils.getInstance().getString("city"));
    }
}
