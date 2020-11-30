package com.app.onnwaytransporter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class KYC2 extends AppCompatActivity {

    Button visiting, pan, aadhar, passbook, other;

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

        visiting = findViewById(R.id.visiting);
        pan = findViewById(R.id.pan);
        aadhar = findViewById(R.id.aadhar);
        passbook = findViewById(R.id.passbook);
        other = findViewById(R.id.other);

        visiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KYC2.this, KYC.class);
                intent.putExtra("type", "visiting");
                intent.putExtra("front", "front_visiting");
                intent.putExtra("back", "back_visiting");
                intent.putExtra("title", "Visiting Card");
                startActivity(intent);
            }
        });

        pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KYC2.this, KYC.class);
                intent.putExtra("type", "pan");
                intent.putExtra("front", "front_pan");
                intent.putExtra("back", "back_pan");
                intent.putExtra("title", "PAN Card");
                startActivity(intent);
            }
        });


        aadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KYC2.this, KYC.class);
                intent.putExtra("type", "aadhar");
                intent.putExtra("front", "front_aadhar");
                intent.putExtra("back", "back_aadhar");
                intent.putExtra("title", "Aadhar Card");
                startActivity(intent);
            }
        });

        passbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KYC2.this, KYC.class);
                intent.putExtra("type", "passbook");
                intent.putExtra("front", "front_passbook");
                intent.putExtra("back", "back_passbook");
                intent.putExtra("title", "Bank Passbook");
                startActivity(intent);
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KYC2.this, KYC.class);
                intent.putExtra("type", "other");
                intent.putExtra("front", "front_other");
                intent.putExtra("back", "back_other");
                intent.putExtra("title", "Other Doc");
                startActivity(intent);
            }
        });



    }
}