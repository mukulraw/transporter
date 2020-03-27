package com.mukul.onnwaytransporter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mukul.onnwaytransporter.bidDetailsPOJO.Data;
import com.mukul.onnwaytransporter.bidDetailsPOJO.bidDetailsBean;
import com.mukul.onnwaytransporter.networking.AppController;
import com.mukul.onnwaytransporter.placeBidPOJO.placeBidBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BidDetails extends AppCompatActivity {

    TextView orderid , orderdate , truck , source , destination , material , weight;
    Button confirm;
    ProgressBar progress;

    String id;
    EditText amount;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_details);

        id = getIntent().getStringExtra("id");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Bid Details");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        orderid = findViewById(R.id.textView16);
        confirm = findViewById(R.id.button4);
        amount = findViewById(R.id.amount);

        orderdate = findViewById(R.id.textView17);
        truck = findViewById(R.id.textView19);
        source = findViewById(R.id.textView20);
        destination = findViewById(R.id.textView21);
        material = findViewById(R.id.textView23);
        weight = findViewById(R.id.textView25);

        progress = findViewById(R.id.progressBar);

        progress.setVisibility(View.VISIBLE);


        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        final Call<bidDetailsBean> call = cr.getBidDetails(SharePreferenceUtils.getInstance().getString("userId") , id);

        call.enqueue(new Callback<bidDetailsBean>() {
            @Override
            public void onResponse(Call<bidDetailsBean> call, Response<bidDetailsBean> response) {

                Data item = response.body().getData();
                truck.setText(item.getTruckType());
                source.setText(item.getSource());
                destination.setText(item.getDestination());
                material.setText(item.getMaterial());
                weight.setText(item.getWeight());

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<bidDetailsBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a = amount.getText().toString();

                if (a.length() > 0)
                {
                    AppController b = (AppController) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                    Call<placeBidBean> call1 = cr.placeBid(SharePreferenceUtils.getInstance().getString("userId") , id , a);

                    call1.enqueue(new Callback<placeBidBean>() {
                        @Override
                        public void onResponse(Call<placeBidBean> call, Response<placeBidBean> response) {

                            if (response.body().getStatus().equals("1"))
                            {
                                Toast.makeText(BidDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                finish();

                            }
                            else
                            {
                                Toast.makeText(BidDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<placeBidBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });
                }
                else
                {
                    Toast.makeText(BidDetails.this, "Invalid Bid Amount", Toast.LENGTH_SHORT).show();
                }



            }
        });


    }
}
