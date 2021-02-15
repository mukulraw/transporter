package com.app.onnwaytransporter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class SelectUserType extends AppCompatActivity {

    CardView truckProvider;
    CardView truckDriver;

    public static String userType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_type);

        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        truckProvider = (CardView) findViewById(R.id.truckProvider);
        truckDriver = (CardView) findViewById(R.id.truckDriver);

        //setting onclicklistener for truckprovider
        truckProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectUserType.this, Profile.class);
                intent.putExtra("user" , "transporter");
                startActivity(intent);
            }
        });

        //setting onclicklistener for truckdriver
        truckDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectUserType.this, Profile2.class);
                intent.putExtra("user" , "driver");
                startActivity(intent);
            }
        });
    }

}
