package com.mukul.onnwaytransporter.driver.profilerelated;

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

import com.mukul.onnwaytransporter.driver.DriverMainActivity;
import com.mukul.onnwaytransporter.driver.profilerelated.uploadkycimage.KycMainPage;
import com.mukul.onnwaytransporter.R;
import com.mukul.onnwaytransporter.otp.SharedData;
import com.mukul.onnwaytransporter.splash.SplashActivity;

public class DriverProfileActivity extends AppCompatActivity {

    SharedData sharedData;

    private TextView userName, userPhone;
    private TextView transportName;
    private LinearLayout truckDetails, addTruck, addCity, addoperatedRoutes, logout, changeProfile,addKycDriver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.driver_profile_toolbar);
        mToolbar.setTitle(getString(R.string.my_account));
        mToolbar.setNavigationIcon(R.drawable.backimage);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sharedData = new SharedData(DriverProfileActivity.this);

        //textview
        userName = (TextView) findViewById(R.id.user_name_driver);
        userPhone = (TextView) findViewById(R.id.user_phone_driver);
        transportName = (TextView) findViewById(R.id.transport_name_driver);

        userName.setText(DriverMainActivity.currentUserName);
        userPhone.setText("+91 " + DriverMainActivity.currenntMobileActive);

        //Image view as button
        changeProfile = (LinearLayout) findViewById(R.id.change_profile_driver);
        truckDetails = (LinearLayout) findViewById(R.id.truck_details_driver);
        addTruck = (LinearLayout) findViewById(R.id.add_truck_driver);
        addCity = (LinearLayout) findViewById(R.id.change_city_driver);
        addoperatedRoutes = (LinearLayout) findViewById(R.id.change_route_driver);
        addKycDriver=(LinearLayout)findViewById(R.id.kyc_driver);
        logout = (LinearLayout) findViewById(R.id.logout_driver);

        //change profile i.e. name and mobile
        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverProfileActivity.this, DriverNamePhoneActivity.class);
                startActivity(intent);
            }
        });

        //truck details
        truckDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverProfileActivity.this, DriverTruckDetails.class);
                startActivity(intent);

            }
        });

        //add truck
        addTruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverProfileActivity.this, AddTruckDetails.class);
                startActivity(intent);
            }
        });

        //add city
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverProfileActivity.this, DriverUpdateCity.class);
                startActivity(intent);
            }
        });

        //add operated routes
        addoperatedRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(DriverProfileActivity.this,"hello",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DriverProfileActivity.this, DriverOperatedRoutesActivity.class);
                startActivity(intent);
            }
        });
        //add  rkyc details
        addKycDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(DriverProfileActivity.this,"hello",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DriverProfileActivity.this, KycMainPage.class);
                startActivity(intent);
            }
        });

        //logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                startActivity(new Intent(DriverProfileActivity.this, SplashActivity.class));
                finish();
            }
        });
    }
    public void deleteData() {
        Integer deletedRow = sharedData.deleteData(DriverMainActivity.currenntMobileActive);
        if(deletedRow > 0){
            //deleted
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
        }
    }
}
