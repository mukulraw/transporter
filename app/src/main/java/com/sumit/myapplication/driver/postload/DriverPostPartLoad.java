package com.sumit.myapplication.driver.postload;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.sumit.myapplication.providerpartload.PartLoadMaterialDetails;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.sumit.myapplication.PostTruckDataDetails;
import com.sumit.myapplication.R;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DriverPostPartLoad extends AppCompatActivity {
    private LinearLayout truck_ll, container_ll, trailer_ll;
    private LinearLayout scheduleDate_ll;
    private TextView displaySelectedTruck, displayScheduleDate;
    private TextView sourceLoc, destLoc;

    private DatePickerDialog picker;

    private Button postTruckBtn;

    private ImageView getSourceLocDotIv, getSourceLocSearchIv, getDestinationLocDotIv, getDestinationLocSearchIv;

    private CardView getSourceLoc, getDestinationLoc, getScheduleDate;

    int AUTOCOMPLETE_REQUEST_CODE = 1;
    int locationIdentification = 0;
    public PostTruckDataDetails postTruckDataDetails;

    int flagSource = 0, flagDest = 0, flagTruckType = 0, flagSchDate = 0;

    public static String SourceAddDriver, DestAddDriver, TruckTypeDriver, schDateDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_post_part_load);
        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_post_part_load_driver);
        mToolbar.setTitle(getString(R.string.post_part_load_driver));
        mToolbar.setNavigationIcon(R.drawable.backimagegray);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final Calendar mCalendar = Calendar.getInstance();

        //Linear Layout of truck types
        truck_ll = (LinearLayout) findViewById(R.id.truck_ll);
        container_ll = (LinearLayout) findViewById(R.id.container_ll);
        trailer_ll = (LinearLayout) findViewById(R.id.trailer_ll);

        //selected truck textview
        displaySelectedTruck = (TextView) findViewById(R.id.display_selected_truck);

        //selected Date Textview
        displayScheduleDate = (TextView) findViewById(R.id.schedule_date);

        //textview
        sourceLoc = (TextView) findViewById(R.id.source_tv);
        destLoc = (TextView) findViewById(R.id.dest_tv);

        //Linear Layout of scheduled Date
        scheduleDate_ll = (LinearLayout) findViewById(R.id.schedule_date_ll);

        //cardview
        getSourceLoc = (CardView) findViewById(R.id.get_source_location);
        getDestinationLoc = (CardView) findViewById(R.id.get_drop_location);
        getScheduleDate = (CardView) findViewById(R.id.get_schedule_date);

        //Imageview
        getSourceLocDotIv = (ImageView) findViewById(R.id.get_source_location_dot_iv);
        getSourceLocSearchIv = (ImageView) findViewById(R.id.get_source_location_search_iv);
        getDestinationLocDotIv = (ImageView) findViewById(R.id.get_drop_location_dot_iv);
        getDestinationLocSearchIv = (ImageView) findViewById(R.id.get_drop_location_search_iv);

        //Post Truck Button
        postTruckBtn = (Button) findViewById(R.id.post_truck_next_btn_driver);


        //get source location
        getSourceLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationIdentification = 1;
                getLocation();
            }
        });
        getSourceLocSearchIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationIdentification = 1;
                getLocation();
            }
        });
        getSourceLocDotIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationIdentification = 1;
                getLocation();
            }
        });
        sourceLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationIdentification = 1;
                getLocation();
            }
        });


        //get destination location
        getDestinationLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationIdentification = 2;
                getLocation();
            }
        });
        getDestinationLocDotIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationIdentification = 2;
                getLocation();
            }
        });
        getDestinationLocSearchIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationIdentification = 2;
                getLocation();
            }
        });
        destLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationIdentification = 2;
                getLocation();
            }
        });



        //handling alertDialog for Truck
        truck_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide keyboard if active
                //hideSoftKeyboard(DriverPostFullLoad.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(DriverPostPartLoad.this, R.style.MyDialogTheme);
                final String[] singleChoiceItems = getResources().getStringArray(R.array.truck_type);
                int itemSelected = -1; // no item selected
                final String[] selectedTruck = new String[1]; // selected item will be stored in this array
                builder.setTitle("Select the type of Truck")
                        .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            //when the item is clicked in the alertDialog
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                selectedTruck[0] = singleChoiceItems[selectedIndex];
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            //after pressing the Ok button
                            public void onClick(DialogInterface dialog, int which) {
                                flagTruckType = 1;
                                if(flagSchDate + flagDest + flagSource + flagTruckType == 4){
                                    postTruckBtn.setBackground(getResources().getDrawable(R.color.active_button));
                                }
                                Toast.makeText(DriverPostPartLoad.this, selectedTruck[0], Toast.LENGTH_LONG).show();
                                if(selectedTruck[0] == null){
                                    flagTruckType = 0;
                                    postTruckBtn.setBackground(getResources().getDrawable(R.color.inactive_button));
                                }
                                displaySelectedTruck.setText("Selected Truck : " + selectedTruck[0]); // change the textview of displaySelectedText
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        //handling alertDialog for Container
        container_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide keyboard if active
                //hideSoftKeyboard(DriverPostFullLoad.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(DriverPostPartLoad.this, R.style.MyDialogTheme);
                final String[] singleChoiceItems = getResources().getStringArray(R.array.container_type);
                int itemSelected = -1; // no item selected
                final String[] selectedTruck = new String[1]; // selected item will be stored in this array
                builder.setTitle("Select the type of Container")
                        .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            //when the item is clicked in the alertDialog
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                selectedTruck[0] = singleChoiceItems[selectedIndex];
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            //after pressing the Ok button
                            public void onClick(DialogInterface dialog, int which) {
                                flagTruckType = 1;
                                if(flagSchDate + flagDest + flagSource + flagTruckType == 4){
                                    postTruckBtn.setBackground(getResources().getDrawable(R.color.active_button));
                                }
                                Toast.makeText(DriverPostPartLoad.this, selectedTruck[0], Toast.LENGTH_LONG).show();
                                if(selectedTruck[0] == null){
                                    flagTruckType = 0;
                                    postTruckBtn.setBackground(getResources().getDrawable(R.color.inactive_button));
                                }
                                displaySelectedTruck.setText("Selected Container : " + selectedTruck[0]); // change the textview of displaySelectedText
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        //handling alertDialog for Trailer
        trailer_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide keyboard if active
                //  hideSoftKeyboard(DriverPostFullLoad.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(DriverPostPartLoad.this, R.style.MyDialogTheme);
                final String[] singleChoiceItems = getResources().getStringArray(R.array.trailer_type);
                int itemSelected = -1;// no item selected
                final String[] selectedTruck = new String[1];// selected item will be stored in this array
                builder.setTitle("Select the type of Trailer")
                        .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            //when the item is clicked in the alertDialog
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                selectedTruck[0] = singleChoiceItems[selectedIndex];
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            //after pressing the Ok button
                            public void onClick(DialogInterface dialog, int which) {
                                flagTruckType = 1;
                                if(flagSchDate + flagDest + flagSource + flagTruckType == 4){
                                    postTruckBtn.setBackground(getResources().getDrawable(R.color.active_button));
                                }
                                Toast.makeText(DriverPostPartLoad.this, selectedTruck[0], Toast.LENGTH_LONG).show();
                                if(selectedTruck[0] == null){
                                    flagTruckType = 0;
                                    postTruckBtn.setBackground(getResources().getDrawable(R.color.inactive_button));
                                }
                                displaySelectedTruck.setText("Selected Trailer : " + selectedTruck[0]);// change the textview of displaySelectedText
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });


        //handling date picker for schedule date
        scheduleDate_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog

                picker = new DatePickerDialog(DriverPostPartLoad.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override

                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                flagSchDate = 1;
                                if(flagSchDate + flagDest + flagSource + flagTruckType == 4){
                                    postTruckBtn.setBackground(getResources().getDrawable(R.color.active_button));
                                }
                                String dateFormat = (String) setDateFormat(dayOfMonth, (monthOfYear + 1), year);
                                displayScheduleDate.setText(dateFormat);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                //Toast.makeText(getContext(), ""+System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
                picker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000+(1000*60*60*24*4));
                picker.show();

            }
        });
        displayScheduleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(DriverPostPartLoad.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                flagSchDate = 1;
                                if(flagSchDate + flagDest + flagSource + flagTruckType == 4){
                                    postTruckBtn.setBackground(getResources().getDrawable(R.color.active_button));
                                }
                                String dateFormat = (String) setDateFormat(dayOfMonth, (monthOfYear + 1), year);
                                displayScheduleDate.setText(dateFormat);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                //Toast.makeText(getContext(), ""+System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
                picker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000+(1000*60*60*24*4));
                picker.show();
            }
        });


        //handeling Alert dialog for Post Trck Button
        postTruckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flagSource == 1 && flagDest == 1 && flagSchDate == 1 && flagTruckType == 1){

                    postTruckBtn.setClickable(true);
                    SourceAddDriver = sourceLoc.getText().toString();
                    DestAddDriver = destLoc.getText().toString();
                    TruckTypeDriver = findVehicleType(displaySelectedTruck.getText().toString());
                    schDateDriver = displayScheduleDate.getText().toString();

                    Intent intent = new Intent(DriverPostPartLoad.this, PartLoadMaterialDetails.class);
                    startActivity(intent);
                }
                if (flagSource == 0) {

                    Toast.makeText(DriverPostPartLoad.this, "Please select Start Location", Toast.LENGTH_LONG).show();

                } else if (flagDest == 0) {

                    Toast.makeText(DriverPostPartLoad.this, "Please select Drop Location", Toast.LENGTH_LONG).show();

                } else if (flagTruckType == 0) {

                    Toast.makeText(DriverPostPartLoad.this, "Please select Truck Type", Toast.LENGTH_LONG).show();

                } else if (flagSchDate == 0) {

                    Toast.makeText(DriverPostPartLoad.this, "Please select Schedule Date", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private void getLocation() {

        Places.initialize(DriverPostPartLoad.this, "AIzaSyDD5e-SJP_E8SDLOHYz79IR79pVy6YQOgg");

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(DriverPostPartLoad.this);

        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .setCountry("ind")
                .setTypeFilter(TypeFilter.CITIES)
                .build(DriverPostPartLoad.this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(locationIdentification == 1) {
            if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    flagSource = 1;
                    if(flagSchDate + flagDest + flagSource + flagTruckType == 4){
                        postTruckBtn.setBackground(getResources().getDrawable(R.color.active_button));
                    }
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    sourceLoc.setText(place.getName());
                    Toast.makeText(DriverPostPartLoad.this,place.toString(), Toast.LENGTH_SHORT).show();
                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    // TODO: Handle the error.
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Toast.makeText(DriverPostPartLoad.this,status.toString(),Toast.LENGTH_SHORT).show();
                } else if (resultCode == RESULT_CANCELED) {
                    // The user canceled the operation.
                }
            }
        }
        if(locationIdentification == 2) {

            if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    flagDest = 1;
                    if(flagSchDate + flagDest + flagSource + flagTruckType == 4){
                        postTruckBtn.setBackground(getResources().getDrawable(R.color.active_button));
                    }
                    destLoc.setText(place.getName());
                    Toast.makeText(DriverPostPartLoad.this,place.toString(), Toast.LENGTH_SHORT).show();
                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    // TODO: Handle the error.
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Toast.makeText(DriverPostPartLoad.this,status.toString(),Toast.LENGTH_SHORT).show();
                } else if (resultCode == RESULT_CANCELED) {
                    // The user canceled the operation.
                }
            }

        }
    }

    //method to hind Keyboard if active
    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
        );
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),0);
    }

    private String setDateFormat(int day, int month, int year) {
        String finalDate = "";

        if(month == 1){
            finalDate = day + " January " + year;
        } else if (month == 2) {
            finalDate = day + " February " + year;
        } else if (month == 3) {
            finalDate = day + " March " + year;
        } else if (month == 4) {
            finalDate = day + " April " + year;
        } else if (month == 5) {
            finalDate = day + " May " + year;
        } else if (month == 6) {
            finalDate = day + " June " + year;
        } else if (month == 7) {
            finalDate = day + " July " + year;
        } else if (month == 8) {
            finalDate = day + " August " + year;
        } else if (month == 9) {
            finalDate = day + " September" + year;
        } else if(month == 10) {
            finalDate = day + " October " + year;
        } else if (month == 11) {
            finalDate = day + " November " + year;
        } else if (month == 12) {
            finalDate = day + " December " + year;
        }
        return finalDate;
    }

    private String findVehicleType(String truckType){
        String truckTypeNumber = "";
        if(truckType.equals("Selected Truck : 3.5 MT/14'x6'x6'")){
            truckTypeNumber = "11";
        } else if (truckType.equals("Selected Truck : 4.5 MT/17'x6'x6'")) {
            truckTypeNumber = "12";
        } else if (truckType.equals("Selected Truck : 7.5 MT/19'x6'x6'")) {
            truckTypeNumber = "13";
        } else if (truckType.equals("Selected Truck : 9 MT/18'x7'x7'")) {
            truckTypeNumber = "14";
        } else if (truckType.equals("Selected Truck : 15 MT/22'x7'x7'")) {
            truckTypeNumber = "15";
        } else if (truckType.equals("Selected Truck : 20 MT/24'x7'x7'")) {
            truckTypeNumber = "16";
        } else if (truckType.equals("Selected Container : 6.5 MT/20'x8'x8'")) {
            truckTypeNumber = "31";
        } else if (truckType.equals("Selected Container : 8 MT/24'x8'x8'")) {
            truckTypeNumber = "32";
        } else if (truckType.equals("Selected Container : 14 MT/32'x8'x10'")) {
            truckTypeNumber = "33";
        } else if (truckType.equals("Selected Trailer : 20 MT/40'x8'x8'")) {
            truckTypeNumber = "51";
        } else if (truckType.equals("Selected Trailer : 20 MT/40'x8'x7'")) {
            truckTypeNumber = "52";
        } else if (truckType.equals("Selected Trailer : 25 MT/40'x8'x8'")) {
            truckTypeNumber = "53";
        } else if (truckType.equals("Selected Trailer : 25 MT/40'x8'x7'")) {
            truckTypeNumber = "54";
        } else if (truckType.equals("Selected Trailer : 32 MT/40'x8'x7'")) {
            truckTypeNumber = "55";
        } else if (truckType.equals("Selected Trailer : 32 MT/40'x8'x8'")) {
            truckTypeNumber = "56";
        }
        return truckTypeNumber;
    }
}