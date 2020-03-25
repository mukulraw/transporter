package com.sumit.myapplication.driver.profilerelated;

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
import android.widget.Toolbar;

import com.sumit.myapplication.driver.DriverMainActivity;
import com.sumit.myapplication.R;

public class DriverNamePhoneActivity extends AppCompatActivity {

    private LinearLayout nameLLDriver, mobileLLDriver;
    private TextView userNameDriver, userPhoneDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_name_phone);

        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_phone_name_driver);
        mToolbar.setTitle(getString(R.string.account_details));
        mToolbar.setNavigationIcon(R.drawable.backimagegray);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Linear Layout
        nameLLDriver = (LinearLayout) findViewById(R.id.change_name_ll_driver);
        mobileLLDriver = (LinearLayout) findViewById(R.id.change_mobile_ll_driver);

        //TextView
        userNameDriver = (TextView) findViewById(R.id.user_name_tv_driver);
        userPhoneDriver = (TextView) findViewById(R.id.user_phone_tv_driver);

        userNameDriver.setText(DriverMainActivity.currentUserName);
        userPhoneDriver.setText("+91 " + DriverMainActivity.currenntMobileActive);

        //calling events for clickking on linear layout
        nameLLDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverNamePhoneActivity.this, DriverUpdateName.class);
                startActivity(intent);
            }
        });

        mobileLLDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverNamePhoneActivity.this, DriverUpdateMobile.class);
                startActivity(intent);
            }
        });
    }
}
