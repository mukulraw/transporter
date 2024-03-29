package com.app.onnwaytransporter.driver.profilerelated;

import android.graphics.Color;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.app.onnwaytransporter.driver.DriverMainActivity;
import com.app.onnwaytransporter.driver.drivernetworking.PostDriverData;
import com.app.onnwaytransporter.driver.profilerelated.changephone.ChangePhoneDetails;
import com.app.onnwaytransporter.R;

public class DriverUpdateMobile extends AppCompatActivity {

    private EditText userMobileReqDriver;
    private Button requestBtnDriver;

    private ChangePhoneDetails changePhoneDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_update_mobile);

        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_update_mobile_driver);
        mToolbar.setTitle(getString(R.string.mobile_update_request));
        mToolbar.setNavigationIcon(R.drawable.backimagegray);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Edit Text
        userMobileReqDriver = (EditText) findViewById(R.id.user_mobile_et_driver);

        //Button
        requestBtnDriver = (Button) findViewById(R.id.requestBtnMobile_driver);

        //handlinf activity for button click
        requestBtnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePhoneDetails = new ChangePhoneDetails();
                changePhoneDetails.driverMobile = DriverMainActivity.currenntMobileActive;
                changePhoneDetails.driverChangedPhone = userMobileReqDriver.getText().toString();
                new PostDriverData().doPostChangePhone(DriverUpdateMobile.this, changePhoneDetails);
                finish();
            }
        });
    }
}
