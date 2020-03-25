package com.sumit.myapplication;

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

import com.sumit.myapplication.driver.profilerelated.AddTruckDetails;
import com.sumit.myapplication.driver.profilerelated.DriverTruckDetails;
import com.sumit.myapplication.profilerelated.KycProviderActivity;
import com.sumit.myapplication.profilerelated.NamePhoneActivity;
import com.sumit.myapplication.profilerelated.UpdateCityActivity;
import com.sumit.myapplication.profilerelated.UpdateRoutesActivity;
import com.sumit.myapplication.otp.SharedData;
import com.sumit.myapplication.splash.SplashActivity;

public class ProfileActivity extends AppCompatActivity {

    SharedData sharedData;

    private TextView userName, userPhone;
    private TextView transportName;
    private LinearLayout truckDetails, addTruck, addCity, addoperatedRoutes, logout, changeProfile;
    private LinearLayout kycProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.my_account));
        mToolbar.setNavigationIcon(R.drawable.backimage);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sharedData = new SharedData(ProfileActivity.this);

        //textview
        userName = (TextView) findViewById(R.id.user_name);
        userPhone = (TextView) findViewById(R.id.user_phone);
        transportName = (TextView) findViewById(R.id.transport_name);

        userName.setText(MainActivity.currentUserName);
        userPhone.setText("+91 " + MainActivity.currenntMobileActive);

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
                deleteData();
                startActivity(new Intent(ProfileActivity.this, SplashActivity.class));
                finish();
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
}
