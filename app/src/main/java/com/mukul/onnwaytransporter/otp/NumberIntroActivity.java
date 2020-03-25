package com.mukul.onnwaytransporter.otp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mukul.onnwaytransporter.R;

public class NumberIntroActivity extends AppCompatActivity {

    TextView tvPhoneNumber;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_intro);

        //setting the color of STATUS BAR of activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        tvPhoneNumber = findViewById(R.id.mPhoneNumber);
        cardView = findViewById(R.id.cardView);

        //starting the EnterNumberActivity
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NumberIntroActivity.this, NumberActivity.class);
                startActivity(intent);
            }
        });

        //starting the EnterNumberActivity
        tvPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NumberIntroActivity.this, NumberActivity.class);
                startActivity(intent);
            }
        });
    }
}
