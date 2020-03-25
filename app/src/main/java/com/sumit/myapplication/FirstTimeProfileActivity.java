package com.sumit.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.sumit.myapplication.driver.DriverMainActivity;
import com.sumit.myapplication.networking.Post;
import com.sumit.myapplication.otp.NumberActivity;
import com.sumit.myapplication.otp.SharedData;

import java.util.Arrays;
import java.util.List;

public class FirstTimeProfileActivity extends AppCompatActivity {

    SharedData sharedData;

    ProviderData providerData;

    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutTransport;
    private TextInputLayout inputLayoutCity;

    private EditText userName;
    private EditText transportName;
    private TextView cityName;
    private int AUTOCOMPLETE_REQUEST_CODE = 1;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_profile);

        sharedData = new SharedData(FirstTimeProfileActivity.this);
        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }


        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutTransport = (TextInputLayout) findViewById(R.id.input_layout_transport);
        inputLayoutCity = (TextInputLayout) findViewById(R.id.input_layout_city);

        userName = (EditText) findViewById(R.id.input_name);
        transportName = (EditText) findViewById(R.id.input_transport);
        cityName = (TextView) findViewById(R.id.input_city);
        cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
        nextBtn = (Button) findViewById(R.id.nextBtn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                providerData = new ProviderData();
                providerData.mobile_number = NumberActivity.enteredPhone;
                providerData.name = userName.getText().toString();
                providerData.type = SelectUserType.userType;
                providerData.transport_name = transportName.getText().toString();
                providerData.city = cityName.getText().toString();
                Toast.makeText(FirstTimeProfileActivity.this, "adding1", Toast.LENGTH_LONG).show();

                new Post().createUser(FirstTimeProfileActivity.this, providerData);

                Toast.makeText(FirstTimeProfileActivity.this, "adding2", Toast.LENGTH_LONG).show();
                addData();

                if(submitUserDetails() && SelectUserType.userType.equals("1")){
                    Intent intent = new Intent(FirstTimeProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if(submitUserDetails() && SelectUserType.userType.equals("2")){
                    Intent intent = new Intent(FirstTimeProfileActivity.this, DriverMainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private boolean submitUserDetails() {
        int count = 0;
        if (validateName()) {
            ++count;
        }

        if (validateTransportName()) {
            ++count;
        }

        if (validateCity()) {
            ++count;
        }

        if(count == 3){
            Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean validateName() {
        if (userName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(userName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validateTransportName(){
        if (transportName.getText().toString().trim().isEmpty()) {
            inputLayoutTransport.setError(getString(R.string.err_msg_transport));
            requestFocus(transportName);
            return false;
        } else {
            inputLayoutTransport.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateCity(){
        if (cityName.getText().toString().trim().isEmpty()) {
            inputLayoutCity.setError(getString(R.string.err_msg_city));
            requestFocus(cityName);
            return false;
        } else {
            inputLayoutCity.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private void getLocation() {

        Places.initialize(FirstTimeProfileActivity.this, "AIzaSyDD5e-SJP_E8SDLOHYz79IR79pVy6YQOgg");

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(FirstTimeProfileActivity.this);

        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .setCountry("ind")
                .setTypeFilter(TypeFilter.CITIES)
                .build(FirstTimeProfileActivity.this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Place place = Autocomplete.getPlaceFromIntent(data);
                cityName.setText(place.getName());
                Toast.makeText(FirstTimeProfileActivity.this, place.toString(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(FirstTimeProfileActivity.this, status.toString(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public void addData() {
        boolean isInserted = sharedData.insertData(userName.getText().toString(), NumberActivity.enteredPhone, SelectUserType.userType, cityName.getText().toString(), transportName.getText().toString());
        if(isInserted) {
            Toast.makeText(FirstTimeProfileActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent msg) {

        switch(keyCode) {
            case(KeyEvent.KEYCODE_BACK):
                Intent intent = new Intent(FirstTimeProfileActivity.this, SelectUserType.class);
                startActivity(intent);
                finish();
                return true;
        }
        return false;
    }
}
