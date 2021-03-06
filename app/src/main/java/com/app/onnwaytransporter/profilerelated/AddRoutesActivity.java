package com.app.onnwaytransporter.profilerelated;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.app.onnwaytransporter.MainActivity;
import com.app.onnwaytransporter.R;
import com.app.onnwaytransporter.profilerelated.OperatedRoutesSampleUser.AddRoutesdataDetailsProvider;
import com.app.onnwaytransporter.networking.Post;

import java.util.Arrays;
import java.util.List;

public class AddRoutesActivity extends AppCompatActivity {

    private LinearLayout sourceLocLL, destLocLL;
    private TextView sourceLocTv, destLocTv;

    private Button addRouteProvider;

    int flagSource = 0, flagDest = 0;

    public AddRoutesdataDetailsProvider addRoutesdataDetailsProvider;

    private int locationIdentification = 0;
    private int AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routes);


        Toolbar mToolbar = findViewById(R.id.toolbar_add_route);
        mToolbar.setTitle("Add Route");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        addRouteProvider=(Button)findViewById(R.id.addRouteBtnProvider);

        sourceLocLL = (LinearLayout) findViewById(R.id.source_ll);
        destLocLL = (LinearLayout) findViewById(R.id.destination_ll);

        sourceLocTv = (TextView) findViewById(R.id.source_location_tv);
        destLocTv = (TextView) findViewById(R.id.drop_location_tv);

        sourceLocLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationIdentification = 1;
                getLocation();
            }
        });

        destLocLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationIdentification = 2;
                getLocation();
            }
        });
        //handeling Alert dialog for Add route Button
        addRouteProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flagSource == 1 && flagDest == 1) {

                    addRouteProvider.setClickable(true);

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddRoutesActivity.this, R.style.MyDialogTheme);
                    builder.setTitle("Are you sure want to add new route?")
                            .setMessage("You  can delete the added route whenever you want")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

//                                    progressBar.setVisibility(View.VISIBLE);
                                    //Handle the activity on Click of Confirm Button


                                    addRoutesdataDetailsProvider= new AddRoutesdataDetailsProvider();
                                    addRoutesdataDetailsProvider.mobile_no_provider = MainActivity.currenntMobileActive;
                                    //addRoutesDataDetails.type = "2";
                                    addRoutesdataDetailsProvider.source_provider = sourceLocTv.getText().toString();
                                    addRoutesdataDetailsProvider.destination_provider= destLocTv.getText().toString();


                                    new Post().doAddRoutesProvider(AddRoutesActivity.this, addRoutesdataDetailsProvider);
                                    finish();
                                   // startActivity(getIntent());


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

                    Toast.makeText(AddRoutesActivity.this, "Please select Start Location", Toast.LENGTH_LONG).show();

                } else if (flagDest == 0) {

                    Toast.makeText(AddRoutesActivity.this, "Please select Drop Location", Toast.LENGTH_LONG).show();

                }
            }

        });
    }

    private void getLocation() {

        Places.initialize(AddRoutesActivity.this, "AIzaSyDg928l41AL20avLOGqYVVHHYHyNTM3DMY");

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(AddRoutesActivity.this);

        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .setCountry("IN")
                .setTypeFilter(TypeFilter.CITIES)
                .build(AddRoutesActivity.this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (locationIdentification == 1) {
            if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    flagSource = 1;
//                    if(flagSchDate + flagDest + flagSource + flagTruckType == 4){
//                        postTruckBtn.setBackground(getResources().getDrawable(R.color.active_button));
//                    }
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    sourceLocTv.setText(place.getName());

                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    // TODO: Handle the error.
                    Status status = Autocomplete.getStatusFromIntent(data);

                } else if (resultCode == RESULT_CANCELED) {
                    // The user canceled the operation.
                }
            }
        }
        if (locationIdentification == 2) {

            if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    flagDest = 1;
                    Place place = Autocomplete.getPlaceFromIntent(data);

//                    if(flagSchDate + flagDest + flagSource + flagTruckType == 4){
//                        postTruckBtn.setBackground(getResources().getDrawable(R.color.active_button));
//                    }
                    destLocTv.setText(place.getName());

                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    // TODO: Handle the error.
                    Status status = Autocomplete.getStatusFromIntent(data);

                } else if (resultCode == RESULT_CANCELED) {
                    // The user canceled the operation.
                }
            }

        }
    }
}
