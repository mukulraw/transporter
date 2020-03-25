package com.mukul.onnwaytransporter.profilerelated;

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

import com.mukul.onnwaytransporter.MainActivity;
import com.mukul.onnwaytransporter.R;
import com.mukul.onnwaytransporter.profilerelated.changeprovidermobile.ChangeProviderMobileDetails;
import com.mukul.onnwaytransporter.networking.Post;

public class UpdateMobileActivity extends AppCompatActivity {

    private EditText userMobileReq;
    private Button requestBtn;

    private ChangeProviderMobileDetails changeProviderMobileDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mobile);

        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }


        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_update_mobile);
        mToolbar.setTitle(getString(R.string.mobile_update_request));
        mToolbar.setNavigationIcon(R.drawable.backimagegray);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Edit Text
        userMobileReq = (EditText) findViewById(R.id.user_mobile_et);

        userMobileReq.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(userMobileReq, InputMethodManager.SHOW_IMPLICIT);

        //Button
        requestBtn = (Button) findViewById(R.id.requestBtnMobile);

        //handlinf activity for button click
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProviderMobileDetails = new ChangeProviderMobileDetails();
                changeProviderMobileDetails.providerCurrentMobile = MainActivity.currenntMobileActive;
                changeProviderMobileDetails.providerChangedMobile = userMobileReq.getText().toString();
                new Post().doPostChangePhone(UpdateMobileActivity.this, changeProviderMobileDetails);
                finish();
            }
        });
    }
}
