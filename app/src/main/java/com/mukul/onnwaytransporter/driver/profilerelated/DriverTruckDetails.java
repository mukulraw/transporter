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
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mukul.onnwaytransporter.AllApiIneterface;
import com.mukul.onnwaytransporter.SharePreferenceUtils;
import com.mukul.onnwaytransporter.driver.drivernetworking.PostDriverData;
import com.mukul.onnwaytransporter.driver.profilerelated.DriverOperatedRoutesRecyclerView.SampleOperatedRoutesDriver;
import com.mukul.onnwaytransporter.driver.profilerelated.DriverTruckDetailsRecyclerView.SampleTruckDetails;
import com.mukul.onnwaytransporter.driver.profilerelated.DriverTruckDetailsRecyclerView.TruckDetailsRecyclerAdapter;
import com.mukul.onnwaytransporter.R;
import com.mukul.onnwaytransporter.myTrucksPOJO.myTrucksBean;
import com.mukul.onnwaytransporter.networking.AppController;
import com.mukul.onnwaytransporter.preferences.SaveSharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class DriverTruckDetails extends AppCompatActivity {
    private FloatingActionButton addTruckDetails;

    Toolbar toolbar;
    RecyclerView recyclerView;
    TruckDetailsRecyclerAdapter truckDetailsRecyclerAdapter;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_truck_details);
        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969

        toolbar = findViewById(R.id.toolbar_truck_details_driver);
        toolbar.setTitle("Truck Details");
        toolbar.setNavigationIcon(R.drawable.ic_next_back);
        toolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progress = findViewById(R.id.progressBar3);
        recyclerView = findViewById(R.id.truck_details_driver_recycler_view);


        //adding more truck details
        addTruckDetails = findViewById(R.id.add_truck_details_driver);

        //calling events for clicking on linear layout
        addTruckDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverTruckDetails.this, AddTruckDetails.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<myTrucksBean> call = cr.mytrucksprovider(SharePreferenceUtils.getInstance().getString("userId"));

        call.enqueue(new Callback<myTrucksBean>() {
            @Override
            public void onResponse(Call<myTrucksBean> call, Response<myTrucksBean> response) {

                truckDetailsRecyclerAdapter = new TruckDetailsRecyclerAdapter(response.body().getData(), DriverTruckDetails.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DriverTruckDetails.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(truckDetailsRecyclerAdapter);

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<myTrucksBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }


}
