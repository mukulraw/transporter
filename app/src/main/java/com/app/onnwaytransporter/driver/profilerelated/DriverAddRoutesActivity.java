package com.app.onnwaytransporter.driver.profilerelated;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.app.onnwaytransporter.driver.DriverMainActivity;
import com.app.onnwaytransporter.driver.drivernetworking.PostDriverData;
import com.app.onnwaytransporter.driver.profilerelated.DriverOperatedRoutesRecyclerView.AddRoutesDataDetails;
import com.app.onnwaytransporter.R;

import java.util.Arrays;
import java.util.List;

public class DriverAddRoutesActivity extends AppCompatActivity {

    private LinearLayout sourceLocLLDriver, destLocLLDriver;
    private TextView sourceLocTvDriver, destLocTvDriver;

    private Button addRouteDriver;

    int flagSource = 0, flagDest = 0;

    public AddRoutesDataDetails addRoutesDataDetails;
    private int locationIdentification = 0;
    private int AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_add_routes);

        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_add_route_driver);
        mToolbar.setTitle(getString(R.string.add_route));
        mToolbar.setNavigationIcon(R.drawable.backimagegray);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        sourceLocLLDriver = (LinearLayout) findViewById(R.id.source_ll_driver);
        destLocLLDriver = (LinearLayout) findViewById(R.id.destination_ll_driver);

        sourceLocTvDriver = (TextView) findViewById(R.id.source_location_tv_driver);
        destLocTvDriver = (TextView) findViewById(R.id.drop_location_tv_driver);

        addRouteDriver = (Button) findViewById(R.id.addRouteBtn_driver);

        sourceLocLLDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationIdentification = 1;
                getLocation();
            }
        });

        destLocLLDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationIdentification = 2;
                getLocation();
            }
        });
        //handeling Alert dialog for Post Trck Button
        addRouteDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flagSource == 1 && flagDest == 1) {

                    addRouteDriver.setClickable(true);

                    AlertDialog.Builder builder = new AlertDialog.Builder(DriverAddRoutesActivity.this, R.style.MyDialogTheme);
                    builder.setTitle("Are you sure want to add new route?")
                            .setMessage("You delete the added route whenever you want")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

//                                    progressBar.setVisibility(View.VISIBLE);
                                    //Handle the activity on Click of Confirm Button


                                    addRoutesDataDetails = new AddRoutesDataDetails();
                                    addRoutesDataDetails.mobile_no = DriverMainActivity.currenntMobileActive;
                                    //addRoutesDataDetails.type = "2";
                                    addRoutesDataDetails.source = sourceLocTvDriver.getText().toString();
                                    addRoutesDataDetails.destination = destLocTvDriver.getText().toString();

                                    Toast.makeText(DriverAddRoutesActivity.this, addRoutesDataDetails.source, Toast.LENGTH_SHORT).show();
                                    new PostDriverData().doAddRoutesDriver(DriverAddRoutesActivity.this, addRoutesDataDetails);
                                    finish();
                                    startActivity(getIntent());

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Handle the activity on Click of Cancel Button
                                }
                            })
                            .show();
                }
                if (flagSource == 0) {

                    Toast.makeText(DriverAddRoutesActivity.this, "Please select Start Location", Toast.LENGTH_LONG).show();

                } else if (flagDest == 0) {

                    Toast.makeText(DriverAddRoutesActivity.this, "Please select Drop Location", Toast.LENGTH_LONG).show();

                }
            }

        });
    }
            private void getLocation() {

                Places.initialize(DriverAddRoutesActivity.this, "AIzaSyDD5e-SJP_E8SDLOHYz79IR79pVy6YQOgg");

                // Create a new Places client instance.
                PlacesClient placesClient = Places.createClient(DriverAddRoutesActivity.this);

                // Set the fields to specify which types of place data to return.
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountry("ind")
                        .setTypeFilter(TypeFilter.CITIES)
                        .build(DriverAddRoutesActivity.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }

            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {

                if (locationIdentification == 1) {
                    if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
                        if (resultCode == RESULT_OK) {
                            flagSource = 1;
//                    if(flagSchDate + flagDest + flagSource + flagTruckType == 4){
//                        postTruckBtn.setBackground(getResources().getDrawable(R.color.active_button));
//                    }
                            Place place = Autocomplete.getPlaceFromIntent(data);
                            sourceLocTvDriver.setText(place.getName());
                            Toast.makeText(DriverAddRoutesActivity.this, place.toString(), Toast.LENGTH_SHORT).show();
                        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                            // TODO: Handle the error.
                            Status status = Autocomplete.getStatusFromIntent(data);
                            Toast.makeText(DriverAddRoutesActivity.this, status.toString(), Toast.LENGTH_SHORT).show();
                        } else if (resultCode == RESULT_CANCELED) {
                            // The user canceled the operation.
                        }
                    }
                }
                if (locationIdentification == 2) {

                    if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
                        if (resultCode == RESULT_OK) {
                            Place place = Autocomplete.getPlaceFromIntent(data);
                          flagDest = 1;
//                    if(flagSchDate + flagDest + flagSource + flagTruckType == 4){
//                        postTruckBtn.setBackground(getResources().getDrawable(R.color.active_button));
//                    }
                            destLocTvDriver.setText(place.getName());
                            Toast.makeText(DriverAddRoutesActivity.this, place.toString(), Toast.LENGTH_SHORT).show();
                        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                            // TODO: Handle the error.
                            Status status = Autocomplete.getStatusFromIntent(data);
                            Toast.makeText(DriverAddRoutesActivity.this, status.toString(), Toast.LENGTH_SHORT).show();
                        } else if (resultCode == RESULT_CANCELED) {
                            // The user canceled the operation.
                        }
                    }
                }
            }
        }
