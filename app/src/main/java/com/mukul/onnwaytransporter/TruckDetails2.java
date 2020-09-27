package com.mukul.onnwaytransporter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mukul.onnwaytransporter.networking.AppController;
import com.mukul.onnwaytransporter.truckDetailsPOJO.Data;
import com.mukul.onnwaytransporter.truckDetailsPOJO.truckDetailsBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TruckDetails2 extends AppCompatActivity {

    TextView orderid, orderdate, truck, source, destination, material, weight, date, status, loadtype, mat, wei, mattitle, weititle;
    Button confirm, request;
    ProgressBar progress;
    String id;

    private CardView partLoad1, partLoad2, partLoad3, partLoad4, partLoad5, partLoad6, partLoad7, partLoad8;
    private double click1 = 0, click2 = 0, click3 = 0, click4 = 0, click5 = 0, click6 = 0, click7 = 0, click8 = 0;
    private TextView truckTypeDetails, truckCapacity, boxLength, boxWidth, boxArea;
    private TextView selectedArea, remainingArea;
    String trucktitle, srcAddress, destAddress, pickUpDate, mid, loadType;
    String length, width, height, desc, tid, passing;
    float capcaity, len, wid;
    List<String> selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_details2);

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

        orderid = findViewById(R.id.textView16);
        mattitle = findViewById(R.id.textView39);
        weititle = findViewById(R.id.textView40);
        mat = findViewById(R.id.textView41);
        wei = findViewById(R.id.textView42);
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


        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(TruckDetails2.this)
                        .setTitle("Cancel Truck")
                        .setMessage("Are you sure you want to cancel this truck?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int which) {

                                AppController b = (AppController) getApplicationContext();

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(b.baseurl)
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                final AllApiIneterface cr = retrofit.create(AllApiIneterface.class);
                                progress.setVisibility(View.VISIBLE);

                                Call<truckDetailsBean> call = cr.cancel_posted_truck(id);
                                call.enqueue(new Callback<truckDetailsBean>() {
                                    @Override
                                    public void onResponse(Call<truckDetailsBean> call, Response<truckDetailsBean> response) {

                                        if (response.body().getStatus().equals("1")) {
                                            Toast.makeText(TruckDetails2.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            onResume();
                                        } else {
                                            Toast.makeText(TruckDetails2.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }


                                        progress.setVisibility(View.GONE);

                                    }

                                    @Override
                                    public void onFailure(Call<truckDetailsBean> call, Throwable t) {
                                        progress.setVisibility(View.GONE);
                                    }
                                });


                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

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
                mat.setText(item.getMaterial());
                wei.setText(item.getWeight());


                if (item.getSelected().contains("1")) {
                    partLoad1.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
                }
                if (item.getSelected().contains("2")) {
                    partLoad2.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
                }
                if (item.getSelected().contains("3")) {
                    partLoad3.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
                }
                if (item.getSelected().contains("4")) {
                    partLoad4.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
                }
                if (item.getSelected().contains("5")) {
                    partLoad5.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
                }
                if (item.getSelected().contains("6")) {
                    partLoad6.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
                }
                if (item.getSelected().contains("7")) {
                    partLoad7.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
                }
                if (item.getSelected().contains("8")) {
                    partLoad8.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
                }


                truckTypeDetails.setText(item.getTruckTypeDetails());
                truckCapacity.setText(item.getTruckCapacity());
                boxLength.setText(item.getBoxLength());
                boxWidth.setText(item.getBoxWidth());
                boxArea.setText(item.getBoxArea() + " sq. ft.");
                selectedArea.setText(item.getSelectedArea() + " sq. ft.");
                remainingArea.setText(item.getRemainingArea() + " sq. ft.");

                if (item.getStatus().equals("cancelled")) {
                    request.setVisibility(View.GONE);
                } else {
                    request.setVisibility(View.VISIBLE);
                }

                if (item.getWeight().length() > 0) {
                    wei.setVisibility(View.VISIBLE);
                    weititle.setVisibility(View.VISIBLE);
                    //mat.setVisibility(View.VISIBLE);
                    //mattitle.setVisibility(View.VISIBLE);
                } else {
                    wei.setVisibility(View.GONE);
                    weititle.setVisibility(View.GONE);
                    mat.setVisibility(View.GONE);
                    mattitle.setVisibility(View.GONE);
                }


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<truckDetailsBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

}