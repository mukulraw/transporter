package com.mukul.onnwaytransporter.OrderStatus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.mukul.onnwaytransporter.MainActivity;
import com.mukul.onnwaytransporter.R;
import com.mukul.onnwaytransporter.networking.Post;
import com.mukul.onnwaytransporter.tablayout.UpComingFragment;

public class AssignTruck extends AppCompatActivity {

    TextInputLayout inputLayoutTruckNo, inputLayoutDriverMobNo;
    EditText truckNumber, driverMobileNumber;
    Button submitDriverDetailsBtn;

    AssignTruckData assignTruckData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_truck);

        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_assign_truck);
        mToolbar.setTitle(getString(R.string.add_truck_info));
        mToolbar.setNavigationIcon(R.drawable.backimagegray);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        inputLayoutTruckNo = findViewById(R.id.input_layout_truck_number);
        inputLayoutDriverMobNo = findViewById(R.id.input_layout_driver_mob_number);

        truckNumber = findViewById(R.id.truck_number_et);
        driverMobileNumber = findViewById(R.id.driver_mobile_number_et);

        submitDriverDetailsBtn = findViewById(R.id.submit_truck_btn);

        submitDriverDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDetails();
                Intent intent = new Intent(AssignTruck.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void submitDetails() {
        if (!validateTruckNumber()){
            return;
        }
        if (!validateMobileNumber()) {
            return;
        }
        assignTruckData = new AssignTruckData();
        assignTruckData.bookingId = UpComingFragment.bId;
        assignTruckData.driverMobNumber = driverMobileNumber.getText().toString();
        assignTruckData.truckNumber = truckNumber.getText().toString();
        new Post().doPostDriverDetails(AssignTruck.this, assignTruckData);
    }

    private boolean validateTruckNumber(){
        if (truckNumber.getText().toString().trim().isEmpty()) {
            inputLayoutTruckNo.setError(getString(R.string.err_truck_number));
            return false;
        } else {
            inputLayoutTruckNo.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateMobileNumber(){
        if (driverMobileNumber.getText().toString().trim().isEmpty()) {
            inputLayoutDriverMobNo.setError(getString(R.string.err_driver_mno));
            return false;
        } else {
            inputLayoutDriverMobNo.setErrorEnabled(false);
        }
        return true;
    }
}
