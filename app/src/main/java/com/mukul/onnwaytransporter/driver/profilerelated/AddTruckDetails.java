package com.mukul.onnwaytransporter.driver.profilerelated;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mukul.onnwaytransporter.driver.drivernetworking.PostDriverData;
import com.mukul.onnwaytransporter.driver.profilerelated.DriverTruckDetailsRecyclerView.AddTruckDetailsUser;
import com.mukul.onnwaytransporter.MainActivity;
import com.mukul.onnwaytransporter.R;

public class AddTruckDetails extends AppCompatActivity {
    public AddTruckDetailsUser addTruckDetailsUser;
    private LinearLayout truck_ll_driver, container_ll_driver, trailer_ll_driver;

    private TextInputLayout ll_registrationNumber;
    private TextInputLayout ll_driverName;
    private TextInputLayout ll_driverNumber;

    private EditText registrationNumber,driverName, driverNumber;
    private TextView displaySelectedTruck;
    private Button addTruckBtn;
    int flagTruckType=0, flagDriverName, flagDriverNumber, flagRegistrationNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_truck_details);
        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_add_truck_details_driver);
        mToolbar.setTitle("Add Truck Details");
        mToolbar.setNavigationIcon(R.drawable.backimagegray);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //Linear Layout of truck types
        truck_ll_driver = (LinearLayout) findViewById(R.id.truck_ll_driver);
        container_ll_driver = (LinearLayout) findViewById(R.id.container_ll_driver);
        trailer_ll_driver = (LinearLayout) findViewById(R.id.trailer_ll_driver);

        //layout
        ll_registrationNumber=(TextInputLayout)findViewById(R.id.input_driver_truck_reg_no);
        ll_driverName=(TextInputLayout) findViewById(R.id.ll_driver_name);
        ll_driverNumber=(TextInputLayout) findViewById(R.id.ll_input_driver_number);

        //edit text
        registrationNumber=(EditText)findViewById(R.id.input_truck_reg_no);
        flagRegistrationNumber = 1;
        driverName=(EditText)findViewById(R.id.input_driver_name);
        flagDriverName=1;
        driverNumber=(EditText)findViewById(R.id.input_driver_number);
        flagDriverNumber=1;
        //display selected truck
        displaySelectedTruck=(TextView)findViewById(R.id.display_selected_truck_driver);

        //add truck btn
        addTruckBtn=(Button)findViewById(R.id.add_truck_details_btn);
        //handling alertDialog for Truck
        truck_ll_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide keyboard if active
                //hideSoftKeyboard(DriverPostFullLoad.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(AddTruckDetails.this, R.style.MyDialogTheme);
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
                                if(selectedTruck[0] == null){
                                    flagTruckType = 0;
                                    addTruckBtn.setBackground(getResources().getDrawable(R.color.inactive_button));
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
        container_ll_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide keyboard if active
                //hideSoftKeyboard(DriverPostFullLoad.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(AddTruckDetails.this, R.style.MyDialogTheme);
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
                                if(selectedTruck[0] == null){
                                    flagTruckType = 0;
                                    addTruckBtn.setBackground(getResources().getDrawable(R.color.inactive_button));
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
        trailer_ll_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide keyboard if active
                //  hideSoftKeyboard(DriverPostFullLoad.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(AddTruckDetails.this, R.style.MyDialogTheme);
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
                                if(selectedTruck[0] == null){
                                    flagTruckType = 0;
                                    addTruckBtn.setBackground(getResources().getDrawable(R.color.inactive_button));
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
        //handling add truck button
        addTruckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flagTruckType == 1&& submitUserDetails()) {

                    addTruckBtn.setClickable(true);
                    addTruckBtn.setBackground(getResources().getDrawable(R.color.active_button));
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddTruckDetails.this, R.style.MyDialogTheme);
                    builder.setTitle("Are you sure want to Add Truck?")
                            .setMessage("You will not be able to edit the details of truck So make sure before confirming")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

//                                    progressBar.setVisibility(View.VISIBLE);
                                    //Handle the activity on Click of Confirm Button
                                    addTruckDetailsUser = new AddTruckDetailsUser();
                                    addTruckDetailsUser.mobile_no = MainActivity.currenntMobileActive;
                                    addTruckDetailsUser.reg_no = registrationNumber.getText().toString();
                                    addTruckDetailsUser.driverName = driverName.getText().toString();
                                    addTruckDetailsUser.driverNumber = driverNumber.getText().toString();
                                    addTruckDetailsUser.truckType = (String) findVehicleType(displaySelectedTruck.getText().toString());
                                    Toast.makeText(AddTruckDetails.this, addTruckDetailsUser.truckType, Toast.LENGTH_SHORT).show();
                                    new PostDriverData().doPostTruckDetails(AddTruckDetails.this, addTruckDetailsUser);
//                                    Fragment frg = null;
//                                    frg = ((AppCompatActivity)getActivity()).getSupportFragmentManager().findFragmentById(R.id.main_frame);
//                                    final FragmentTransaction ft = ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction();
//                                    ft.detach(frg);
//                                    ft.attach(frg);
//                                    ft.commit();
//                                    flagSource = flagDest = flagSchDate = flagTruckType = 0;
//                                    Intent intent = new Intent(DriverPostFullLoad.this, DriverPostFullLoad.class);
//                                    startActivity(intent);
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


            }
        });
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
    private boolean submitUserDetails() {
        int count = 0;
        if (validateRegistartionNumber()) {
            ++count;
        }

        if (validateDriverName()) {
            ++count;
        }

        if (validateDriverNumber()) {
            ++count;
        }

        if(count == 3){
            Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean validateRegistartionNumber() {
        if (registrationNumber.getText().toString().trim().isEmpty()) {
            ll_registrationNumber.setError(getString(R.string.err_msg_name));
            requestFocus(registrationNumber);
            return false;
        } else {
            ll_registrationNumber.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validateDriverName(){
        if (driverName.getText().toString().trim().isEmpty()) {
            ll_driverName.setError(getString(R.string.err_msg_transport));
            requestFocus(driverName);
            return false;
        } else {
            ll_driverName.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateDriverNumber(){
        if (driverNumber.getText().toString().trim().isEmpty()) {
            ll_driverNumber.setError(getString(R.string.err_msg_city));
            requestFocus(driverNumber);
            return false;
        } else {
            ll_driverNumber.setErrorEnabled(false);
        }

        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
