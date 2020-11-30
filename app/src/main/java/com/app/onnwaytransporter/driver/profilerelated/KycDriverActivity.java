package com.app.onnwaytransporter.driver.profilerelated;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toolbar;

import com.app.onnwaytransporter.driver.profilerelated.uploadkycimage.UploadImageActivity;
import com.app.onnwaytransporter.R;

public class KycDriverActivity extends AppCompatActivity {

    private Button aadharBtn, drivingBtn, registrationBtn;
    public static int cardType = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc_driver);
        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_kyc_driver);
        mToolbar.setTitle("KYC");
        mToolbar.setNavigationIcon(R.drawable.backimagegray);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        aadharBtn = findViewById(R.id.aadhar_card_btn);
        drivingBtn = findViewById(R.id.driving_licence_btn);
        registrationBtn = findViewById(R.id.registration_card_btn);

        aadharBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardType = 1;
                Intent intent = new Intent(KycDriverActivity.this, UploadImageActivity.class);
                startActivity(intent);
            }
        });

        drivingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardType = 2;
                Intent intent = new Intent(KycDriverActivity.this, UploadImageActivity.class);
                startActivity(intent);
            }
        });

        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardType = 3;
                Intent intent = new Intent(KycDriverActivity.this, UploadImageActivity.class);
                startActivity(intent);
            }
        });

    }

}
