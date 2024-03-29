package com.app.onnwaytransporter.profilerelated;

import android.content.Intent;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.app.onnwaytransporter.R;
import com.app.onnwaytransporter.SharePreferenceUtils;

public class NamePhoneActivity extends AppCompatActivity {

    private LinearLayout nameLL, mobileLL;
    private TextView userName, userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_phone);


        //adding toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar_phone_name);
        mToolbar.setTitle(getString(R.string.account_details));
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Linear Layout
        nameLL = (LinearLayout) findViewById(R.id.change_name_ll);
        mobileLL = (LinearLayout) findViewById(R.id.change_mobile_ll);

        //TextView
        userName = (TextView) findViewById(R.id.user_name_tv);
        userPhone = (TextView) findViewById(R.id.user_phone_tv);

        userPhone.setText("Ph. - " + SharePreferenceUtils.getInstance().getString("phone"));
        userName.setText(SharePreferenceUtils.getInstance().getString("name"));

        //calling events for clickking on linear layout
        nameLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NamePhoneActivity.this, UpdateNameActivity.class);
                startActivity(intent);
            }
        });

        mobileLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NamePhoneActivity.this, UpdateMobileActivity.class);
                startActivity(intent);
            }
        });
    }
}
