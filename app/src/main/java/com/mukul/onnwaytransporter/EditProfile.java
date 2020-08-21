package com.mukul.onnwaytransporter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.mukul.onnwaytransporter.driver.DriverMainActivity;
import com.mukul.onnwaytransporter.networking.AppController;
import com.mukul.onnwaytransporter.updateProfilePOJO.updateProfileBean;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class EditProfile extends AppCompatActivity {

    EditText transport, city;
    Button submit;
    ProgressBar progress;
    private int AUTOCOMPLETE_REQUEST_CODE = 1;

    boolean comp = true;
    String t = "";

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        user = getIntent().getStringExtra("user");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Edit Profile");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        transport = findViewById(R.id.editText10);
        city = findViewById(R.id.editText11);
        submit = findViewById(R.id.button);
        progress = findViewById(R.id.progressBar);

        city.setText(SharePreferenceUtils.getInstance().getString("city"));
        transport.setText(SharePreferenceUtils.getInstance().getString("email"));

        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Places.initialize(EditProfile.this, "AIzaSyDg928l41AL20avLOGqYVVHHYHyNTM3DMY");

                // Set the fields to specify which types of place data to return.
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountry("ind")
                        .setTypeFilter(TypeFilter.CITIES)
                        .build(EditProfile.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String t = transport.getText().toString();
                String c = city.getText().toString();

                if (t.length() > 0) {
                    if (c.length() > 0) {
                        update(t, c);
                    } else {
                        Toast.makeText(EditProfile.this, "Invalid city", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditProfile.this, "Invalid transport name", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    void update(String e, String c) {
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
                SharePreferenceUtils.getInstance().getString("name"),
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

                    Toast.makeText(EditProfile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    finish();


                } else {
                    Toast.makeText(EditProfile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(EditProfile.this, status.toString(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

}