package com.mukul.onnwaytransporter;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mukul.onnwaytransporter.bidDetailsPOJO.Data;
import com.mukul.onnwaytransporter.bidDetailsPOJO.bidDetailsBean;
import com.mukul.onnwaytransporter.networking.AppController;
import com.mukul.onnwaytransporter.placeBidPOJO.placeBidBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BidDetails2 extends AppCompatActivity {

    TextView orderid , orderdate , truck , source , destination , material , weight, distance;
    Button confirm;
    ProgressBar progress;

    String id;
    EditText amount;

    String sourceLAT = "", sourceLNG = "", destinationLAT = "", destinationLNG = "";

    TextView dimension, equal, quantity, total, phototitle;
    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_details2);

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

        dimension = findViewById(R.id.textView134);
        phototitle = findViewById(R.id.textView140);
        equal = findViewById(R.id.textView135);
        quantity = findViewById(R.id.textView137);
        total = findViewById(R.id.textView139);
        photo = findViewById(R.id.imageView18);
        orderid = findViewById(R.id.textView16);
        confirm = findViewById(R.id.button4);
        amount = findViewById(R.id.amount);
        distance = findViewById(R.id.textView11);

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


                sourceLAT = item.getSourceLAT();
                sourceLNG = item.getSourceLNG();
                destinationLAT = item.getDestinationLAT();
                destinationLNG = item.getDestinationLNG();

                Location startPoint=new Location("Source");
                startPoint.setLatitude(Double.parseDouble(sourceLAT));
                startPoint.setLongitude(Double.parseDouble(sourceLNG));

                Location endPoint=new Location("Destination");
                endPoint.setLatitude(Double.parseDouble(destinationLAT));
                endPoint.setLongitude(Double.parseDouble(destinationLNG));

                distance.setText((startPoint.distanceTo(endPoint) / 1000) + " km");

                dimension.setText(item.getLength() + " X " + item.getWidth() + " X " + item.getHeight());

                if (item.getMaterial_image().length() > 0) {
                    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
                    ImageLoader loader = ImageLoader.getInstance();
                    loader.displayImage(item.getMaterial_image(), photo, options);

                    photo.setVisibility(View.VISIBLE);
                    phototitle.setVisibility(View.VISIBLE);
                } else {
                    photo.setVisibility(View.GONE);
                    phototitle.setVisibility(View.GONE);
                }


                float ll = Float.parseFloat(item.getLength());
                float ww = Float.parseFloat(item.getWidth());
                float hh = Float.parseFloat(item.getHeight());
                float qq = Float.parseFloat(item.getQuantity());

                equal.setText("= " + (ll * ww * hh) + " cu.ft.");
                quantity.setText(item.getQuantity());
                total.setText((ll * ww * hh * qq) + " cu.ft.");

                if (item.getBid().length() > 0)
                {
                    amount.setText(item.getBid());
                    amount.setClickable(false);
                    amount.setFocusable(false);
                    confirm.setVisibility(View.GONE);
                }
                else
                {
                    amount.setClickable(true);
                    amount.setFocusable(true);
                    confirm.setVisibility(View.VISIBLE);
                }

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

                    progress.setVisibility(View.VISIBLE);

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
                                Toast.makeText(BidDetails2.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                finish();

                            }
                            else
                            {
                                Toast.makeText(BidDetails2.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(BidDetails2.this, "Invalid Bid Amount", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
}