package com.sumit.myapplication.providerpartload;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sumit.myapplication.driver.DriverMainActivity;
import com.sumit.myapplication.driver.postload.DriverPostPartLoad;
import com.sumit.myapplication.MainActivity;
import com.sumit.myapplication.R;
import com.sumit.myapplication.networking.Post;
import com.sumit.myapplication.splash.SplashActivity;
import com.sumit.myapplication.tablayout.PartLoadPostTruck;

public class SelectTruckSpaceProvider extends AppCompatActivity {

    private CardView partLoad1, partLoad2, partLoad3, partLoad4, partLoad5, partLoad6, partLoad7, partLoad8;
    private double click1 = 0, click2 = 0, click3 = 0, click4 = 0, click5 = 0, click6 = 0, click7 = 0, click8 = 0;
    private TextView truckTypeDetails, truckCapacity, boxLength, boxWidth, boxArea;
    private TextView selectedArea, remainingArea;
    private Button postPartLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_truck_space_provider);

        partLoad1 = findViewById(R.id.part_load_card_1);
        partLoad2 = findViewById(R.id.part_load_card_2);
        partLoad3 = findViewById(R.id.part_load_card_3);
        partLoad4 = findViewById(R.id.part_load_card_4);
        partLoad5 = findViewById(R.id.part_load_card_5);
        partLoad6 = findViewById(R.id.part_load_card_6);
        partLoad7 = findViewById(R.id.part_load_card_7);
        partLoad8 = findViewById(R.id.part_load_card_8);

        truckTypeDetails = findViewById(R.id.truck_type);
        truckCapacity = findViewById(R.id.truck_capacity);
        boxLength = findViewById(R.id.box_length);
        boxWidth = findViewById(R.id.box_width);
        boxArea = findViewById(R.id.box_area);
        selectedArea = findViewById(R.id.selected_area);
        remainingArea = findViewById(R.id.remaining_area);

        postPartLoad = findViewById(R.id.post_part_load_provider_btn);

        double totalArea = 0;
        if(SplashActivity.currentUserType.equals("1")) {
            totalArea = getTruckDetails(PartLoadPostTruck.TruckType);
        } else if (SplashActivity.currentUserType.equals("2")) {
            totalArea = getTruckDetails(DriverPostPartLoad.TruckTypeDriver);
        }

//        Toast.makeText(SelectTruckSpaceProvider.this, String.valueOf(totalArea), Toast.LENGTH_SHORT).show();
//        Toast.makeText(SelectTruckSpaceProvider.this, boxArea.getText().toString(), Toast.LENGTH_SHORT).show();

        calculateArea(click1 + click2 + click3 + click4 + click5 + click6 + click7 + click8, totalArea);
        final double finalTotalArea = totalArea;
        partLoad1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++click1;
//              Toast.makeText(SelectTruckSpaceProvider.this, String.valueOf(click1), Toast.LENGTH_SHORT).show();
                if(click1 % 2 == 0) {
                    click1 = 0;
                    partLoad1.setCardBackgroundColor(Color.parseColor("#EDEDED"));
                } else {
                    partLoad1.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
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
                if(click2 % 2 == 0) {
                    click2 = 0;
                    partLoad2.setCardBackgroundColor(Color.parseColor("#EDEDED"));
                } else {
                    partLoad2.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
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
                if(click3 % 2 == 0) {
                    click3 = 0;
                    partLoad3.setCardBackgroundColor(Color.parseColor("#EDEDED"));
                } else {
                    partLoad3.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
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
                if(click4 % 2 == 0) {
                    click4 = 0;
                    partLoad4.setCardBackgroundColor(Color.parseColor("#EDEDED"));
                } else {
                    partLoad4.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
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
                if(click5 % 2 == 0) {
                    click5 = 0;
                    partLoad5.setCardBackgroundColor(Color.parseColor("#EDEDED"));
                } else {
                    partLoad5.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
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
                if(click6 % 2 == 0) {
                    click6 = 0;
                    partLoad6.setCardBackgroundColor(Color.parseColor("#EDEDED"));
                } else {
                    partLoad6.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
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
                if(click7 % 2 == 0) {
                    click7 = 0;
                    partLoad7.setCardBackgroundColor(Color.parseColor("#EDEDED"));
                } else {
                    partLoad7.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
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
                if(click8 % 2 == 0) {
                    click8 = 0;
                    partLoad8.setCardBackgroundColor(Color.parseColor("#EDEDED"));
                } else {
                    partLoad8.setCardBackgroundColor(Color.parseColor("#A0A0A0"));
                }
                calculateArea(click1 + click2 + click3 + click4 + click5 + click6 + click7 + click8, finalTotalArea7);

            }
        });

        postPartLoad.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPartLoadData();
            }
        });
    }

    private void submitPartLoadData () {
        PartLoadDataDetails partLoadDataDetails = new PartLoadDataDetails();
        if(SplashActivity.currentUserType.equals("1")) {
            partLoadDataDetails.part_mobile_no = MainActivity.currenntMobileActive;
            partLoadDataDetails.part_user_type = "1";
            partLoadDataDetails.part_source_add = PartLoadPostTruck.SourceAdd;
            partLoadDataDetails.part_dest_add = PartLoadPostTruck.DestAdd;
            partLoadDataDetails.part_vehicle_type = PartLoadPostTruck.TruckType;
            partLoadDataDetails.part_sch_date = PartLoadPostTruck.schDate;

        } else if (SplashActivity.currentUserType.equals("2")) {
            partLoadDataDetails.part_mobile_no = DriverMainActivity.currenntMobileActive;
            partLoadDataDetails.part_user_type = "2";
            partLoadDataDetails.part_source_add = DriverPostPartLoad.SourceAddDriver;
            partLoadDataDetails.part_dest_add = DriverPostPartLoad.DestAddDriver;
            partLoadDataDetails.part_vehicle_type = DriverPostPartLoad.TruckTypeDriver;
            partLoadDataDetails.part_sch_date = DriverPostPartLoad.schDateDriver;
        }

        partLoadDataDetails.part_material_type = PartLoadMaterialDetails.materialType;
        partLoadDataDetails.part_rem_payload = PartLoadMaterialDetails.remPayLoad;
        partLoadDataDetails.part_rem_payload_unit = "kg";
        partLoadDataDetails.part_rem_len = PartLoadMaterialDetails.remLen;
        partLoadDataDetails.part_rem_len_unit = "m";
        partLoadDataDetails.part_rem_wid = PartLoadMaterialDetails.remWid;
        partLoadDataDetails.part_rem_wid_unit = "m";
        partLoadDataDetails.part_rem_hei = PartLoadMaterialDetails.remHei;
        partLoadDataDetails.part_rem_hei_unit = "m";
        if(click1 % 2 == 0) {
            partLoadDataDetails.part_box1 = "0";
        } else {
            partLoadDataDetails.part_box1 = "1";
        }

        if(click2 % 2 == 0) {
            partLoadDataDetails.part_box2 = "0";
        } else {
            partLoadDataDetails.part_box2 = "1";
        }

        if(click3 % 2 == 0) {
            partLoadDataDetails.part_box3 = "0";
        } else {
            partLoadDataDetails.part_box3 = "1";
        }

        if(click4 % 2 == 0) {
            partLoadDataDetails.part_box4 = "0";
        } else {
            partLoadDataDetails.part_box4 = "1";
        }

        if(click5 % 2 == 0) {
            partLoadDataDetails.part_box5 = "0";
        } else {
            partLoadDataDetails.part_box5 = "1";
        }

        if(click6 % 2 == 0) {
            partLoadDataDetails.part_box6 = "0";
        } else {
            partLoadDataDetails.part_box6 = "1";
        }

        if(click7 % 2 == 0) {
            partLoadDataDetails.part_box7 = "0";
        } else {
            partLoadDataDetails.part_box7 = "1";
        }

        if(click8 % 2 == 0) {
            partLoadDataDetails.part_box8 = "0";
        } else {
            partLoadDataDetails.part_box8 = "1";
        }

        new Post().doPostProviderPartLoad(SelectTruckSpaceProvider.this, partLoadDataDetails);
        finish();
    }
    private double getTruckDetails(String truckType) {
        if(truckType.equals("11")){
            truckTypeDetails.setText("Truck of 14' x 6' x 6'");
            truckCapacity.setText("3.5 MT");
            boxLength.setText("3.5 Foot");
            boxWidth.setText("3 Foot");
            boxArea.setText("10.5");
            return 84;

        } else if (truckType.equals("12")) {
            truckTypeDetails.setText("Truck of 17' x 6' x 6'");
            truckCapacity.setText("4.5 MT");
            boxLength.setText("4.25 Foot");
            boxWidth.setText("3 Foot");
            boxArea.setText("12.75");
            return 102;

        } else if (truckType.equals("13")) {
            truckTypeDetails.setText("Truck of 19' x 6' x 6'");
            truckCapacity.setText("7.5 MT");
            boxLength.setText("4.75 Foot");
            boxWidth.setText("3 Foot");
            boxArea.setText("14.25");
            return 114;

        } else if (truckType.equals("14")) {
            truckTypeDetails.setText("Truck of 18' x 7' x 7'");
            truckCapacity.setText("9 MT");
            boxLength.setText("4.5 Foot");
            boxWidth.setText("3.5 Foot");
            boxArea.setText("15.75");
            return 126;

        } else if (truckType.equals("15")) {
            truckTypeDetails.setText("Truck of 22' x 7' x 7'");
            truckCapacity.setText("15 MT");
            boxLength.setText("5.5 Foot");
            boxWidth.setText("3.5 Foot");
            boxArea.setText("19.25");
            return 154;

        } else if (truckType.equals("16")) {
            truckTypeDetails.setText("Truck of 24' x 7' x 7'");
            truckCapacity.setText("20 MT");
            boxLength.setText("6 Foot");
            boxWidth.setText("3.5 Foot");
            boxArea.setText("21");
            return 168;

        } else if (truckType.equals("31")) {
            truckTypeDetails.setText("Container of 20' x 8' x 8'");
            truckCapacity.setText("6.5 MT");
            boxLength.setText("5 Foot");
            boxWidth.setText("4 Foot");
            boxArea.setText("20");
            return 160;

        } else if (truckType.equals("32")) {
            truckTypeDetails.setText("Container of 24' x 8' x 8'");
            truckCapacity.setText("8 MT");
            boxLength.setText("6 Foot");
            boxWidth.setText("4 Foot");
            boxArea.setText("24");
            return 192;

        } else if (truckType.equals("33")) {
            truckTypeDetails.setText("Container of 32' x 8' x 10'");
            truckCapacity.setText("14 MT");
            boxLength.setText("8 Foot");
            boxWidth.setText("4 Foot");
            boxArea.setText("32");
            return 256;

        } else if (truckType.equals("51")) {
            truckTypeDetails.setText("Trailer of 40' x 8' x 8'");
            truckCapacity.setText("20 MT");
            boxLength.setText("10 Foot");
            boxWidth.setText("4 Foot");
            boxArea.setText("40");
            return 320;

        } else if (truckType.equals("52")) {
            truckTypeDetails.setText("Trailer of 40' x 8' x 7'");
            truckCapacity.setText("20 MT");
            boxLength.setText("10 Foot");
            boxWidth.setText("4 Foot");
            boxArea.setText("40");
            return 320;

        } else if (truckType.equals("53")) {
            truckTypeDetails.setText("Trailer of 40' x 8' x 8'");
            truckCapacity.setText("25 MT");
            boxLength.setText("10 Foot");
            boxWidth.setText("4 Foot");
            boxArea.setText("40");
            return 320;

        } else if (truckType.equals("54")) {
            truckTypeDetails.setText("Trailer of 40' x 8' x 7'");
            truckCapacity.setText("25 MT");
            boxLength.setText("10 Foot");
            boxWidth.setText("4 Foot");
            boxArea.setText("40");
            return 320;

        } else if (truckType.equals("55")) {
            truckTypeDetails.setText("Trailer of 40' x 8' x 7'");
            truckCapacity.setText("32 MT");
            boxLength.setText("10 Foot");
            boxWidth.setText("4 Foot");
            boxArea.setText("40");
            return 320;

        } else if (truckType.equals("56")) {
            truckTypeDetails.setText("Trailer of 40' x 8' x 8'");
            truckCapacity.setText("32 MT");
            boxLength.setText("10 Foot");
            boxWidth.setText("4 Foot");
            boxArea.setText("40");
            return 320;

        }
        return 0;
    }

    public void calculateArea(double selBox, double totArea) {
        double getArea = Double.parseDouble(boxArea.getText().toString());
        double areaSel = selBox * getArea;
        selectedArea.setText(String.valueOf(areaSel));
        double remArea = totArea - areaSel;
        remainingArea.setText(String.valueOf(remArea));
    }
}
