package com.mukul.onnwaytransporter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.mukul.onnwaytransporter.networking.AppController;
import com.mukul.onnwaytransporter.truckDetailsPOJO.Data;
import com.mukul.onnwaytransporter.truckDetailsPOJO.truckDetailsBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TruckDetails extends AppCompatActivity {

    TextView orderid , orderdate , truck , source , destination , material , weight , date , status , loadtype;
    Button confirm , request;
    ProgressBar progress;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_details);

        id = getIntent().getStringExtra("id");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Truck Details");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        orderid = findViewById(R.id.textView16);
        confirm = findViewById(R.id.button);
        request = findViewById(R.id.button4);
        orderdate = findViewById(R.id.textView17);
        truck = findViewById(R.id.textView19);
        source = findViewById(R.id.textView20);
        destination = findViewById(R.id.textView21);
        material = findViewById(R.id.textView23);
        weight = findViewById(R.id.textView25);
        date = findViewById(R.id.textView81);
        status = findViewById(R.id.textView83);
        loadtype = findViewById(R.id.textView85);
        progress = findViewById(R.id.progressBar);
        progress.setVisibility(View.VISIBLE);


        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<truckDetailsBean> call = cr.getTruckDetails(id);

        call.enqueue(new Callback<truckDetailsBean>() {
            @Override
            public void onResponse(Call<truckDetailsBean> call, Response<truckDetailsBean> response) {

                Data item = response.body().getData();
                truck.setText(item.getTruckType());
                source.setText(item.getSource());
                destination.setText(item.getDestination());
                material.setText(item.getLoadPassing());
                weight.setText(item.getWeight());
                date.setText(item.getSchedule());
                status.setText(item.getStatus());
                loadtype.setText(item.getLaodType());



                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<truckDetailsBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }
}
