package com.mukul.onnwaytransporter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

public class OrderDetails extends AppCompatActivity {

    TextView orderid , orderdate , truck , source , destination , material , weight , date , status , fare , distance , paid;

    TextView vehiclenumber , drivernumber;

    Button add , upload1 , upload2;

    RecyclerView pod , documents;

    ProgressBar progress;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        id = getIntent().getStringExtra("id");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Order Details");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        orderid = findViewById(R.id.textView16);
        orderdate = findViewById(R.id.textView17);
        truck = findViewById(R.id.textView19);
        source = findViewById(R.id.textView20);
        destination = findViewById(R.id.textView21);
        material = findViewById(R.id.textView23);
        weight = findViewById(R.id.textView25);
        date = findViewById(R.id.textView29);
        status = findViewById(R.id.textView30);
        fare = findViewById(R.id.textView31);
        distance = findViewById(R.id.textView11);
        paid = findViewById(R.id.textView32);
        progress = findViewById(R.id.progressBar);

        vehiclenumber = findViewById(R.id.textView29);
        drivernumber = findViewById(R.id.textView35);

        add = findViewById(R.id.button3);
        upload1 = findViewById(R.id.button5);
        upload2 = findViewById(R.id.button6);

        pod = findViewById(R.id.pod);
        documents = findViewById(R.id.recyclerView2);

    }
}
