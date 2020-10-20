package com.mukul.onnwaytransporter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class KYC2 extends AppCompatActivity {

    Button aadharcard, driving, registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_y_c2);

        Toolbar mToolbar = findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("KYC");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        aadharcard = findViewById(R.id.aadhar_card_btn);
        driving = findViewById(R.id.aadhar_card_btn2);
        registration = findViewById(R.id.aadhar_card_btn3);

        aadharcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KYC2.this, KYC.class);
                startActivity(intent);
            }
        });

        driving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KYC2.this, KYC3.class);
                startActivity(intent);
            }
        });


        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KYC2.this, KYC4.class);
                startActivity(intent);
            }
        });


    }
}