package com.mukul.onnwaytransporter.driver.profilerelated;

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

import com.mukul.onnwaytransporter.driver.DriverMainActivity;
import com.mukul.onnwaytransporter.driver.drivernetworking.PostDriverData;
import com.mukul.onnwaytransporter.driver.profilerelated.changename.ChangeNameDetails;
import com.mukul.onnwaytransporter.R;

public class DriverUpdateName extends AppCompatActivity {

    private EditText userNameReqDriver;
    private Button requestBtnDriver;

    private ChangeNameDetails changeNameDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_update_name);

        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_update_name_driver);
        mToolbar.setTitle(getString(R.string.name_update_request));
        mToolbar.setNavigationIcon(R.drawable.backimagegray);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Edit Text
        userNameReqDriver = (EditText) findViewById(R.id.user_name_et_driver);

        //Button
        requestBtnDriver = (Button) findViewById(R.id.requestBtnName_driver);

        //handlinf activity for button click
        requestBtnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeNameDetails = new ChangeNameDetails();
                changeNameDetails.driverMobile = DriverMainActivity.currenntMobileActive;
                changeNameDetails.driverChangedName = userNameReqDriver.getText().toString();
                new PostDriverData().doPostChangeName(DriverUpdateName.this, changeNameDetails);
                finish();
            }
        });
    }
}
