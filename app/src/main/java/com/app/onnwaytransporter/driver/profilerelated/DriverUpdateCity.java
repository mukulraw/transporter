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
import com.app.onnwaytransporter.driver.profilerelated.changecity.ChangeCityDetails;
import com.app.onnwaytransporter.R;

public class DriverUpdateCity extends AppCompatActivity {

    private EditText userCityReqDriver;
    private Button requestBtnDriver;

    private ChangeCityDetails changeCityDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_update_city);

        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_update_city_driver);
        mToolbar.setTitle("City Change Request");
        mToolbar.setNavigationIcon(R.drawable.backimagegray);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Edit Text
        userCityReqDriver = (EditText) findViewById(R.id.user_city_et_driver);

        //Button
        requestBtnDriver = (Button) findViewById(R.id.requestBtnCity_driver);

        //handling activity for button click
        requestBtnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCityDetails = new ChangeCityDetails();
                changeCityDetails.driverMobile = DriverMainActivity.currenntMobileActive;
                changeCityDetails.driverChangedCity = userCityReqDriver.getText().toString();
                new PostDriverData().doPostChangeCity(DriverUpdateCity.this, changeCityDetails);
                finish();
            }
        });
    }
}
