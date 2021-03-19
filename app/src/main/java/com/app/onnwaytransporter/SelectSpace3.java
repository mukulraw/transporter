package com.app.onnwaytransporter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.onnwaytransporter.networking.AppController;
import com.app.onnwaytransporter.placeBidPOJO.placeBidBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SelectSpace3 extends AppCompatActivity {

    private CardView partLoad1, partLoad2, partLoad3, partLoad4, partLoad5, partLoad6, partLoad7, partLoad8;
    private double click1 = 0, click2 = 0, click3 = 0, click4 = 0, click5 = 0, click6 = 0, click7 = 0, click8 = 0;
    private TextView truckTypeDetails, truckCapacity, boxLength, boxWidth, boxArea;
    private TextView selectedArea, remainingArea;
    private Button postPartLoad;

    TextView weight;

    String trucktitle, srcAddress, destAddress, pickUpDate, wei, mid, loadType, amount;
    String length, width, height, desc, tid, passing, id;
    float capcaity, len, wid;

    ProgressBar bar;
    List<String> selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_space3);

        selected = new ArrayList<>();

        tid = getIntent().getStringExtra("tid");
        id = getIntent().getStringExtra("id");
        amount = getIntent().getStringExtra("amount");
        passing = getIntent().getStringExtra("passing");
        trucktitle = getIntent().getStringExtra("trucktitle");
        srcAddress = getIntent().getStringExtra("src");
        destAddress = getIntent().getStringExtra("des");
        pickUpDate = getIntent().getStringExtra("dat");
        wei = getIntent().getStringExtra("wei");
        mid = getIntent().getStringExtra("mid");
        loadType = getIntent().getStringExtra("loa");
        length = getIntent().getStringExtra("len");
        width = getIntent().getStringExtra("wid");
        height = getIntent().getStringExtra("hei");
        desc = getIntent().getStringExtra("desc");
        capcaity = getIntent().getFloatExtra("capcaity", 0);
        len = getIntent().getFloatExtra("length", 0);
        wid = getIntent().getFloatExtra("width", 0);

        bar = findViewById(R.id.progressBar6);
        partLoad1 = findViewById(R.id.part_load_card_1);
        partLoad2 = findViewById(R.id.part_load_card_2);
        partLoad3 = findViewById(R.id.part_load_card_3);
        partLoad4 = findViewById(R.id.part_load_card_4);
        partLoad5 = findViewById(R.id.part_load_card_5);
        partLoad6 = findViewById(R.id.part_load_card_6);
        partLoad7 = findViewById(R.id.part_load_card_7);
        partLoad8 = findViewById(R.id.part_load_card_8);

        weight = findViewById(R.id.textView38);
        truckTypeDetails = findViewById(R.id.truck_type);
        truckCapacity = findViewById(R.id.truck_capacity);
        boxLength = findViewById(R.id.box_length);
        boxWidth = findViewById(R.id.box_width);
        boxArea = findViewById(R.id.box_area);
        selectedArea = findViewById(R.id.selected_area);
        remainingArea = findViewById(R.id.remaining_area);

        postPartLoad = findViewById(R.id.post_part_load_provider_btn);

        double totalArea = 0;
        /*if(SplashActivity.currentUserType.equals("1")) {
            totalArea = getTruckDetails(PartLoadPostTruck.TruckType);
        } else if (SplashActivity.currentUserType.equals("2")) {
            totalArea = getTruckDetails(DriverPostPartLoad.TruckTypeDriver);
        }*/

        weight.setText(wei);

        truckTypeDetails.setText(trucktitle);
        truckCapacity.setText(capcaity + " MT");
        boxLength.setText(len + " ft.");
        boxWidth.setText(wid + " ft.");
        boxArea.setText("" + len * wid);

        totalArea = (len * 4) * (wid * 2);

//        Toast.makeText(SelectTruckSpaceProvider.this, String.valueOf(totalArea), Toast.LENGTH_SHORT).show();
//        Toast.makeText(SelectTruckSpaceProvider.this, boxArea.getText().toString(), Toast.LENGTH_SHORT).show();

        calculateArea(click1 + click2 + click3 + click4 + click5 + click6 + click7 + click8, totalArea);
        final double finalTotalArea = totalArea;
        partLoad1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++click1;
//              Toast.makeText(SelectTruckSpaceProvider.this, String.valueOf(click1), Toast.LENGTH_SHORT).show();
                if (click1 % 2 == 0) {
                    click1 = 0;
                    partLoad1.setCardBackgroundColor(Color.parseColor("#EDEDED"));
                    selected.remove("1");
                } else {
                    partLoad1.setCardBackgroundColor(Color.parseColor("#e11f26"));
                    selected.add("1");
                }
                calculateArea(click1 + click2 + click3 + click4 + click5 + click6 + click7 + click8, finalTotalArea);

            }
        });

        final double finalTotalArea1 = totalArea;
        partLoad2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++click2;
//                Toast.makeText(SelectTruckSpaceProvider.this, String.valueOf(click2), Toast.LENGTH_SHORT).show();
//                Toast.makeText(SelectTruckSpaceProvider.this, String.valueOf(click1), Toast.LENGTH_SHORT).show();
                if (click2 % 2 == 0) {
                    click2 = 0;
                    partLoad2.setCardBackgroundColor(Color.parseColor("#EDEDED"));
                    selected.remove("2");
                } else {
                    partLoad2.setCardBackgroundColor(Color.parseColor("#e11f26"));
                    selected.add("2");
                }
                calculateArea(click1 + click2 + click3 + click4 + click5 + click6 + click7 + click8, finalTotalArea1);

            }
        });

        final double finalTotalArea2 = totalArea;
        partLoad3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++click3;
//                Toast.makeText(SelectTruckSpaceProvider.this, String.valueOf(click3), Toast.LENGTH_SHORT).show();
//                Toast.makeText(SelectTruckSpaceProvider.this, String.valueOf(click1), Toast.LENGTH_SHORT).show();
                if (click3 % 2 == 0) {
                    click3 = 0;
                    partLoad3.setCardBackgroundColor(Color.parseColor("#EDEDED"));
                    selected.remove("3");
                } else {
                    partLoad3.setCardBackgroundColor(Color.parseColor("#e11f26"));
                    selected.add("3");
                }
                calculateArea(click1 + click2 + click3 + click4 + click5 + click6 + click7 + click8, finalTotalArea2);

            }
        });

        final double finalTotalArea3 = totalArea;
        partLoad4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++click4;
//                Toast.makeText(SelectTruckSpaceProvider.this, String.valueOf(click4), Toast.LENGTH_SHORT).show();
//                Toast.makeText(SelectTruckSpaceProvider.this, String.valueOf(click1), Toast.LENGTH_SHORT).show();
                if (click4 % 2 == 0) {
                    click4 = 0;
                    partLoad4.setCardBackgroundColor(Color.parseColor("#EDEDED"));
                    selected.remove("4");
                } else {
                    partLoad4.setCardBackgroundColor(Color.parseColor("#e11f26"));
                    selected.add("4");
                }
                calculateArea(click1 + click2 + click3 + click4 + click5 + click6 + click7 + click8, finalTotalArea3);

            }
        });

        final double finalTotalArea4 = totalArea;
        partLoad5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++click5;
//                Toast.makeText(SelectTruckSpaceProvider.this, String.valueOf(click5), Toast.LENGTH_SHORT).show();
//                Toast.makeText(SelectTruckSpaceProvider.this, String.valueOf(click1), Toast.LENGTH_SHORT).show();
                if (click5 % 2 == 0) {
                    click5 = 0;
                    partLoad5.setCardBackgroundColor(Color.parseColor("#EDEDED"));
                    selected.remove("5");
                } else {
                    partLoad5.setCardBackgroundColor(Color.parseColor("#e11f26"));
                    selected.add("5");
                }
                calculateArea(click1 + click2 + click3 + click4 + click5 + click6 + click7 + click8, finalTotalArea4);

            }
        });

        final double finalTotalArea5 = totalArea;
        partLoad6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++click6;
//                Toast.makeText(SelectTruckSpaceProvider.this, String.valueOf(click6), Toast.LENGTH_SHORT).show();
//                Toast.makeText(SelectTruckSpaceProvider.this, String.valueOf(click1), Toast.LENGTH_SHORT).show();
                if (click6 % 2 == 0) {
                    click6 = 0;
                    partLoad6.setCardBackgroundColor(Color.parseColor("#EDEDED"));
                    selected.remove("6");
                } else {
                    partLoad6.setCardBackgroundColor(Color.parseColor("#e11f26"));
                    selected.add("6");
                }
                calculateArea(click1 + click2 + click3 + click4 + click5 + click6 + click7 + click8, finalTotalArea5);

            }
        });

        final double finalTotalArea6 = totalArea;
        partLoad7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++click7;
//                Toast.makeText(SelectTruckSpaceProvider.this, String.valueOf(click7), Toast.LENGTH_SHORT).show();
//                Toast.makeText(SelectTruckSpaceProvider.this, String.valueOf(click1), Toast.LENGTH_SHORT).show();
                if (click7 % 2 == 0) {
                    click7 = 0;
                    partLoad7.setCardBackgroundColor(Color.parseColor("#EDEDED"));
                    selected.remove("7");
                } else {
                    partLoad7.setCardBackgroundColor(Color.parseColor("#e11f26"));
                    selected.add("7");
                }
                calculateArea(click1 + click2 + click3 + click4 + click5 + click6 + click7 + click8, finalTotalArea6);

            }
        });

        final double finalTotalArea7 = totalArea;
        partLoad8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++click8;
//                Toast.makeText(SelectTruckSpaceProvider.this, String.valueOf(click8), Toast.LENGTH_SHORT).show();
//                Toast.makeText(SelectTruckSpaceProvider.this, String.valueOf(click1), Toast.LENGTH_SHORT).show();
                if (click8 % 2 == 0) {
                    click8 = 0;
                    partLoad8.setCardBackgroundColor(Color.parseColor("#EDEDED"));
                    selected.remove("8");
                } else {
                    partLoad8.setCardBackgroundColor(Color.parseColor("#e11f26"));
                    selected.add("8");
                }
                calculateArea(click1 + click2 + click3 + click4 + click5 + click6 + click7 + click8, finalTotalArea7);

            }
        });


        postPartLoad.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        bar.setVisibility(View.VISIBLE);

                        AppController b = (AppController) getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                        Call<placeBidBean> call = cr.placeBid(
                                SharePreferenceUtils.getInstance().getString("userId"),
                                id,
                                amount,
                                loadType,
                                srcAddress,
                                destAddress,
                                tid,
                                pickUpDate,
                                wei,
                                passing,
                                desc,
                                length,
                                width,
                                height,
                                "",
                                mid,
                                truckTypeDetails.getText().toString(),
                                truckCapacity.getText().toString(),
                                boxLength.getText().toString(),
                                boxWidth.getText().toString(),
                                boxArea.getText().toString(),
                                selectedArea.getText().toString(),
                                remainingArea.getText().toString(),
                                android.text.TextUtils.join(",", selected)
                        );

                        call.enqueue(new Callback<placeBidBean>() {
                            @Override
                            public void onResponse(Call<placeBidBean> call, Response<placeBidBean> response) {

                                if (response.body().getStatus().equals("1")) {
                                    Toast.makeText(SelectSpace3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(SelectSpace3.this, MainActivity.class);
                                    startActivity(intent);
                                    finishAffinity();

                                }
                                Toast.makeText(SelectSpace3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                bar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(Call<placeBidBean> call, Throwable t) {
                                bar.setVisibility(View.GONE);
                            }
                        });


                    }
                });

    }

    public void calculateArea(double selBox, double totArea) {
        double getArea = Double.parseDouble(boxArea.getText().toString());
        double areaSel = selBox * getArea;
        selectedArea.setText(String.valueOf(areaSel));
        double remArea = totArea - areaSel;
        remainingArea.setText(String.valueOf(remArea));
    }

}