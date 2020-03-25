package com.mukul.onnwaytransporter.profilerelated;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mukul.onnwaytransporter.R;
import com.mukul.onnwaytransporter.profilerelated.OperatedRoutesSampleUser.SampleOperatedRoutesProvider;
import com.mukul.onnwaytransporter.networking.Post;
import com.mukul.onnwaytransporter.preferences.SaveSharedPreference;

public class UpdateRoutesActivity extends AppCompatActivity {
    String routes[]={"Bhopal-Mumbai","Mumbai-Bikaner","Delhi-Haldia","Madras-Kolkata","Kota-Varanasi"};
    Integer[] imageId = {
            R.drawable.delete,
            R.drawable.delete,
            R.drawable.delete,
            R.drawable.delete,
            R.drawable.delete};

    Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerAdapterRoutesProvider recyclerAdapterRoutesProvider;
    SwipeRefreshLayout swipeRefreshLayout;
    Context context;
    private FloatingActionButton addRoute_provider;


    public UpdateRoutesActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_routes);

        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }
        //adding toolbar
        toolbar= (Toolbar) findViewById(R.id.toolbar_update_routes_provider);
        toolbar.setTitle("Operated Routes");
        toolbar.setNavigationIcon(R.drawable.backimagegray);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        recyclerView=findViewById(R.id.recyclerview_id_operated_routes_provider);
        swipeRefreshLayout=findViewById(R.id.swipe_refresh_layout_operated_routes_provider);


        if(SaveSharedPreference.getCounterPostedStatus((this)).equals("0")) {
            Toast.makeText(this, "hiiiiiii", Toast.LENGTH_SHORT).show();
            new Post().doOperatedRoutesProvider(UpdateRoutesActivity.this,recyclerView);
        }
        else {

            if(SampleOperatedRoutesProvider.sampleOperatedRoutesProviders == null) {
                new Post().doOperatedRoutesProvider(UpdateRoutesActivity.this,recyclerView);
            }else
            {
                setRecyclerBid();
            }
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Post().doOperatedRoutesProvider(UpdateRoutesActivity.this,recyclerView);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //adding more routes
        addRoute_provider=(FloatingActionButton) findViewById(R.id.addNewRoute_Btn_provider);

//        //calling events for clicking on linear layout
        addRoute_provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateRoutesActivity.this, AddRoutesActivity.class);
                startActivity(intent);

            }
        });

    }
    public void setRecyclerBid() {
        recyclerAdapterRoutesProvider = new RecyclerAdapterRoutesProvider(SampleOperatedRoutesProvider.sampleOperatedRoutesProviders,context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapterRoutesProvider);
    }
}
