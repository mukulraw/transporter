package com.app.onnwaytransporter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.app.onnwaytransporter.driver.DriverMainActivity;
import com.app.onnwaytransporter.networking.AppController;
import com.app.onnwaytransporter.updateProfilePOJO.updateProfileBean;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Profile2 extends AppCompatActivity {

    EditText name, transport, city;
    Button submit;
    ProgressBar progress;
    private int AUTOCOMPLETE_REQUEST_CODE = 1;

    boolean comp = true;
    String t = "";

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile3);

        user = getIntent().getStringExtra("user");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Short Profile");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        name = findViewById(R.id.editText9);
        transport = findViewById(R.id.editText10);
        city = findViewById(R.id.editText11);
        submit = findViewById(R.id.button);
        progress = findViewById(R.id.progressBar);


        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Places.initialize(Profile2.this, "AIzaSyDqE-hIGTcdZA2RNswLLaNwN92dJUEYEnA");

                // Set the fields to specify which types of place data to return.
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountry("ind")
                        .setTypeFilter(TypeFilter.CITIES)
                        .build(Profile2.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n = name.getText().toString();
                String t = transport.getText().toString();
                String c = city.getText().toString();

                if (n.length() > 0) {
                    update(n, t, c);
                } else {
                    Toast.makeText(Profile2.this, "Invalid name", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    void update(String n, String e, String c) {
        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<updateProfileBean> call = cr.update_provider_profile(
                SharePreferenceUtils.getInstance().getString("userId"),
                user,
                n,
                e,
                c
        );

        call.enqueue(new Callback<updateProfileBean>() {
            @Override
            public void onResponse(Call<updateProfileBean> call, Response<updateProfileBean> response) {

                if (response.body().getStatus().equals("1")) {

                    SharePreferenceUtils.getInstance().saveString("name", response.body().getData().getName());
                    SharePreferenceUtils.getInstance().saveString("email", response.body().getData().getEmail());
                    SharePreferenceUtils.getInstance().saveString("city", response.body().getData().getCity());
                    SharePreferenceUtils.getInstance().saveString("type", response.body().getData().getType());
                    SharePreferenceUtils.getInstance().saveString("company", response.body().getData().getCompany());

                    SharePreferenceUtils.getInstance().saveString("user", user);

                    Toast.makeText(Profile2.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    if (user.equals("transporter")) {
                        Intent intent = new Intent(Profile2.this, MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    } else {
                        Intent intent = new Intent(Profile2.this, DriverMainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }


                } else {
                    Toast.makeText(Profile2.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<updateProfileBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                city.setText(place.getName());

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(Profile2.this, status.toString(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

}