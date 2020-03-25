package com.mukul.onnwaytransporter.driver.profilerelated;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mukul.onnwaytransporter.driver.drivernetworking.PostDriverData;
import com.mukul.onnwaytransporter.driver.profilerelated.DriverOperatedRoutesRecyclerView.SampleOperatedRoutesDriver;
import com.mukul.onnwaytransporter.driver.profilerelated.DriverTruckDetailsRecyclerView.SampleTruckDetails;
import com.mukul.onnwaytransporter.driver.profilerelated.DriverTruckDetailsRecyclerView.TruckDetailsRecyclerAdapter;
import com.mukul.onnwaytransporter.R;
import com.mukul.onnwaytransporter.preferences.SaveSharedPreference;

public class DriverTruckDetails extends AppCompatActivity {
    private FloatingActionButton addTruckDetails;

    Toolbar toolbar;
    RecyclerView recyclerView;
    TruckDetailsRecyclerAdapter truckDetailsRecyclerAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_truck_details);
        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }
        //adding toolbar
        toolbar= (Toolbar) findViewById(R.id.toolbar_truck_details_driver);
        toolbar.setTitle("Truck Details");
        toolbar.setNavigationIcon(R.drawable.backimagegray);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        recyclerView=findViewById(R.id.truck_details_driver_recycler_view);
        swipeRefreshLayout=findViewById(R.id.swipe_refresh_layout_truck_details_driver);


        if(SaveSharedPreference.getCounterPostedStatus((this)).equals("0")) {
            Toast.makeText(this, "hiiiiiii", Toast.LENGTH_SHORT).show();
            new PostDriverData().doDriverDetailsData(DriverTruckDetails.this,recyclerView);
        }
        else {

            if(SampleOperatedRoutesDriver.sampleOperatedRoutes== null) {
                new PostDriverData().doDriverDetailsData(DriverTruckDetails.this,recyclerView);
            }else
            {
                setRecyclerBid();
            }
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new PostDriverData().doDriverDetailsData(DriverTruckDetails.this,recyclerView);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //adding more truck details
        addTruckDetails=(FloatingActionButton) findViewById(R.id.add_truck_details_driver);

        //calling events for clicking on linear layout
        addTruckDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverTruckDetails.this, AddTruckDetails.class);
                startActivity(intent);
            }
        });

    }
    public void setRecyclerBid() {
        truckDetailsRecyclerAdapter = new TruckDetailsRecyclerAdapter(SampleTruckDetails.sampleTruckDetails);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(truckDetailsRecyclerAdapter);
    }

}
