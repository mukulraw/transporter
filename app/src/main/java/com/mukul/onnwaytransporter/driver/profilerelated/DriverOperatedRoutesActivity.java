package com.mukul.onnwaytransporter.driver.profilerelated;

import android.content.Context;
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
import com.mukul.onnwaytransporter.driver.profilerelated.DriverOperatedRoutesRecyclerView.DriverOperatedRoutesRecyclerAdapter;
import com.mukul.onnwaytransporter.driver.profilerelated.DriverOperatedRoutesRecyclerView.SampleOperatedRoutesDriver;
import com.mukul.onnwaytransporter.R;
import com.mukul.onnwaytransporter.preferences.SaveSharedPreference;

public class DriverOperatedRoutesActivity extends AppCompatActivity {
    String routes[]={"Bhopal-Mumbai","Mumbai-Bikaner","Delhi-Haldia","Madras-Kolkata","Kota-Varanasi"};
    Integer[] imageId = {
            R.drawable.delete,
            R.drawable.delete,
            R.drawable.delete,
            R.drawable.delete,
            R.drawable.delete};

    Toolbar toolbar;
    RecyclerView recyclerView;
    DriverOperatedRoutesRecyclerAdapter driverOperatedRoutesRecyclerAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    Context context;
    private FloatingActionButton addRoute_driver;


    public DriverOperatedRoutesActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_operated_routes);

        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }
        //adding toolbar
        toolbar= (Toolbar) findViewById(R.id.toolbar_update_routes_driver);
        toolbar.setTitle("Operated Routes");
        toolbar.setNavigationIcon(R.drawable.backimagegray);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        recyclerView=findViewById(R.id.recyclerview_id_driver);
        swipeRefreshLayout=findViewById(R.id.swipe_refresh_layout_operated_routes);


        if(SaveSharedPreference.getCounterPostedStatus((this)).equals("0")) {
            Toast.makeText(this, "hiiiiiii", Toast.LENGTH_SHORT).show();
            new PostDriverData().doOperatedRoutes(DriverOperatedRoutesActivity.this,recyclerView);
        }
        else {

            if(SampleOperatedRoutesDriver.sampleOperatedRoutes== null) {
                new PostDriverData().doOperatedRoutes(DriverOperatedRoutesActivity.this,recyclerView);
            }else
            {
                setRecyclerBid();
            }
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new PostDriverData().doOperatedRoutes(DriverOperatedRoutesActivity.this,recyclerView);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //adding more routes
        addRoute_driver=(FloatingActionButton) findViewById(R.id.addNewRoute_Btn);

        //calling events for clicking on linear layout
        addRoute_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverOperatedRoutesActivity.this, DriverAddRoutesActivity.class);
                startActivity(intent);
            }
        });

    }
    public void setRecyclerBid() {
        driverOperatedRoutesRecyclerAdapter = new DriverOperatedRoutesRecyclerAdapter(SampleOperatedRoutesDriver.sampleOperatedRoutes,context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(driverOperatedRoutesRecyclerAdapter);
    }
}
