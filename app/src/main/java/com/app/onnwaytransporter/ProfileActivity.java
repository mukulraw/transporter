package com.app.onnwaytransporter;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.iid.FirebaseInstanceId;
import com.app.onnwaytransporter.driver.profilerelated.AddTruckDetails;
import com.app.onnwaytransporter.driver.profilerelated.DriverTruckDetails;
import com.app.onnwaytransporter.profilerelated.NamePhoneActivity;
import com.app.onnwaytransporter.profilerelated.UpdateRoutesActivity;
import com.app.onnwaytransporter.otp.SharedData;
import com.app.onnwaytransporter.splash.SplashActivity;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    SharedData sharedData;

    private TextView userName, userPhone, user_city;
    private TextView transportName;
    private LinearLayout truckDetails, addTruck, addCity, addoperatedRoutes, logout, changeProfile;
    private LinearLayout kycProvider;

    LinearLayout transportNameHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //adding toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
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
        userName = findViewById(R.id.user_name);
        transportNameHeading = findViewById(R.id.transport_name_heading);
        user_city = findViewById(R.id.user_city);
        userPhone = findViewById(R.id.user_phone);
        transportName = findViewById(R.id.transport_name);


        //Image view as button
        changeProfile = findViewById(R.id.change_profile);
        truckDetails = findViewById(R.id.truck_details);
        addTruck = findViewById(R.id.add_truck);
        addCity = findViewById(R.id.change_city);
        addoperatedRoutes = findViewById(R.id.change_route);
        kycProvider = findViewById(R.id.kyc_provider);
        logout = findViewById(R.id.logout);


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

        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfile.class);
                startActivity(intent);
            }
        });

        transportNameHeading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfile.class);
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
                Intent intent = new Intent(ProfileActivity.this, KYC2.class);
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
        if (deletedRow > 0) {
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
