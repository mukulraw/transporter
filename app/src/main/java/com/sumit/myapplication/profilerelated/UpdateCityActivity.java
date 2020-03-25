package com.sumit.myapplication.profilerelated;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.sumit.myapplication.MainActivity;
import com.sumit.myapplication.R;
import com.sumit.myapplication.profilerelated.changeprovidercity.ChangeProviderCityDetails;
import com.sumit.myapplication.networking.Post;

public class UpdateCityActivity extends AppCompatActivity {

    private EditText userCityReq;
    private Button requestBtn;

    private ChangeProviderCityDetails changeProviderCityDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_city);

        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_update_city);
        mToolbar.setTitle(getString(R.string.name_update_request));
        mToolbar.setNavigationIcon(R.drawable.backimagegray);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Edit Text
        userCityReq = (EditText) findViewById(R.id.user_city_et);

        userCityReq.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(userCityReq, InputMethodManager.SHOW_IMPLICIT);

        //Button
        requestBtn = (Button) findViewById(R.id.requestBtnCity);

        //handling activity for button click
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProviderCityDetails = new ChangeProviderCityDetails();
                changeProviderCityDetails.providerCurrentMobile = MainActivity.currenntMobileActive;
                changeProviderCityDetails.providerChangedCity = userCityReq.getText().toString();
                new Post().doPostChangeCity(UpdateCityActivity.this, changeProviderCityDetails);
                finish();
            }
        });
    }
}
