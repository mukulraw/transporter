package com.app.onnwaytransporter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.app.onnwaytransporter.networking.AppController;
import com.app.onnwaytransporter.truckTypePOJO.truckTypeBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Materials extends AppCompatActivity {

    String srcAddress, destAddress, tid, loadType, pickUpDate;

    Spinner material, weight;
    EditText length, width, height, remarks;
    TextView total;
    ProgressBar progress;
    Button next;

    List<String> mat, weis;
    List<String> mids;
    String mid, wei , capcaity , len , wid , trucktitle, passing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials);

        srcAddress = getIntent().getStringExtra("source");
        destAddress = getIntent().getStringExtra("destination");
        tid = getIntent().getStringExtra("tid");
        loadType = getIntent().getStringExtra("loadtype");
        pickUpDate = getIntent().getStringExtra("date");
        capcaity = getIntent().getStringExtra("capcaity");
        len = getIntent().getStringExtra("length");
        wid = getIntent().getStringExtra("width");
        passing = getIntent().getStringExtra("passing");
        trucktitle = getIntent().getStringExtra("trucktitle");

        mat = new ArrayList<>();
        weis = new ArrayList<>();
        mids = new ArrayList<>();

        Toolbar mToolbar = findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Booking Details");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progress = findViewById(R.id.progressBar);
        material = findViewById(R.id.material);
        weight = findViewById(R.id.weight);
        length = findViewById(R.id.editText5);
        width = findViewById(R.id.editText6);
        height = findViewById(R.id.editText7);
        total = findViewById(R.id.textView54);
        next = findViewById(R.id.button);
        remarks = findViewById(R.id.editText15);

        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<List<truckTypeBean>> call = cr.getMaterial();

        call.enqueue(new Callback<List<truckTypeBean>>() {
            @Override
            public void onResponse(Call<List<truckTypeBean>> call, Response<List<truckTypeBean>> response) {

                mat.clear();
                mids.clear();

                for (int i = 0; i < response.body().size(); i++) {
                    mat.add(response.body().get(i).getTitle());
                    mids.add(response.body().get(i).getId());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Materials.this,
                        android.R.layout.simple_list_item_1, mat);

                material.setAdapter(adapter);

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<truckTypeBean>> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        Call<List<truckTypeBean>> call2 = cr.getWeight();

        call2.enqueue(new Callback<List<truckTypeBean>>() {
            @Override
            public void onResponse(Call<List<truckTypeBean>> call, Response<List<truckTypeBean>> response) {

                weis.clear();

                for (int i = 0; i < response.body().size(); i++) {
                    weis.add(response.body().get(i).getTitle());
                }

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Materials.this,
                        android.R.layout.simple_list_item_1, weis);

                weight.setAdapter(adapter2);

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<truckTypeBean>> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mid = mids.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                wei = weis.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        length.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0) {

                    float ll = Float.parseFloat(length.getText().toString());
                    float ww = Float.parseFloat(width.getText().toString());
                    float hh = Float.parseFloat(height.getText().toString());

                    total.setText(ll * ww * hh + " cu. ft.");

                }


            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        width.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0) {

                    float ll = Float.parseFloat(length.getText().toString());
                    float ww = Float.parseFloat(width.getText().toString());
                    float hh = Float.parseFloat(height.getText().toString());

                    total.setText(ll * ww * hh + " cu. ft.");

                }


            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0) {

                    float ll = Float.parseFloat(length.getText().toString());
                    float ww = Float.parseFloat(width.getText().toString());
                    float hh = Float.parseFloat(height.getText().toString());

                    total.setText(ll * ww * hh + " cu. ft.");

                }


            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0) {


                    Intent intent = new Intent(Materials.this, SelectSpace.class);
                    intent.putExtra("src", srcAddress);
                    intent.putExtra("des", destAddress);
                    intent.putExtra("dat", pickUpDate);
                    intent.putExtra("tid", tid);
                    intent.putExtra("passing", passing);
                    intent.putExtra("wei", wei);
                    intent.putExtra("mid", mid);
                    intent.putExtra("loa", loadType);
                    intent.putExtra("trucktitle", trucktitle);
                    intent.putExtra("capcaity", Float.parseFloat(capcaity));
                    intent.putExtra("length", Float.parseFloat(len));
                    intent.putExtra("width", Float.parseFloat(wid));
                    intent.putExtra("len", length.getText().toString());
                    intent.putExtra("desc", remarks.getText().toString());
                    intent.putExtra("wid", width.getText().toString());
                    intent.putExtra("hei", height.getText().toString());
                    startActivity(intent);


                } else {
                    Toast.makeText(Materials.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}