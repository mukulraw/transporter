package com.app.onnwaytransporter.biddetails;


import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.app.onnwaytransporter.recyclerview.RecyclerAdapterBid;

import com.app.onnwaytransporter.networking.Post;
import com.app.onnwaytransporter.preferences.SaveSharedPreference;
import com.app.onnwaytransporter.R;
import java.util.HashMap;
import java.util.Map;

public class SetBidDetailsActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {


    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    private Polyline currentPolyline;

    //finding the id of each
    TextView source,destination,distance,vehicle,material,date;
    EditText enterbid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_bid_details);

        float[] results = new float[1];
        Location.distanceBetween(Double.parseDouble(RecyclerAdapterBid.slat), Double.parseDouble(RecyclerAdapterBid.slng),
                Double.parseDouble(RecyclerAdapterBid.dlat), Double.parseDouble(RecyclerAdapterBid.dlng), results);

        place1 = new MarkerOptions().position(new LatLng(Double.parseDouble(RecyclerAdapterBid.slat), Double.parseDouble(RecyclerAdapterBid.slng))).title("Source: " + RecyclerAdapterBid.src);
        place2 = new MarkerOptions().position(new LatLng(Double.parseDouble(RecyclerAdapterBid.dlat), Double.parseDouble(RecyclerAdapterBid.dlng))).title("Destination: "+ RecyclerAdapterBid.dest);
        new FetchURL(SetBidDetailsActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        //findding the id

        source=findViewById(R.id.tv_source);
        destination=findViewById(R.id.tv_destination);
        distance=findViewById(R.id.tv_distance);
        vehicle=findViewById(R.id.tv_vechile_type);
        material=findViewById(R.id.tv_material_type);
        date=findViewById(R.id.tv_date);
        enterbid=findViewById(R.id.enter_bid);

        //todo
        /*source.setText(MyBidFragment.arrsource[RecyclerAdapterBid.quote_id]);
        destination.setText(MyBidFragment.arrdestination[RecyclerAdapterBid.quote_id]);
        int dist = (int) results[0] / 1000;
        distance.setText(Integer.toString(dist) + " kms approx");
//        distance.setText(MyBidFragment.arrweight[RecyclerAdapterBid.quote_id]);
        vehicle.setText(MyBidFragment.arrtrucktype[RecyclerAdapterBid.quote_id]);
        material.setText(MyBidFragment.arrmaterial[RecyclerAdapterBid.quote_id]);
        date.setText(MyBidFragment.arrdate[RecyclerAdapterBid.quote_id]);*/


        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_set_bid_details);
        mToolbar.setTitle("Bid Details");
        mToolbar.setNavigationIcon(R.drawable.backimagegray);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        mMap.addMarker(place1);
        mMap.addMarker(place2);

        CameraPosition googlePlex = CameraPosition.builder()
                .target(new LatLng(Double.parseDouble(RecyclerAdapterBid.slat),Double.parseDouble(RecyclerAdapterBid.slng)))
                .zoom(7)
                .bearing(0)
                .tilt(45)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 5000, null);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }


    public void submit(View view)
    {
        String sEnterBid=enterbid.getText().toString().trim();
        if(sEnterBid.isEmpty())
        {
            enterbid.setError("This Field is Compulsory!");
        }
        else
        {
            Map<String,String> map=new HashMap<>();
            //map.put("quote_id",""+MyBidFragment.arrquoteid[RecyclerAdapterBid.quote_id]);
            String mobile_no= SaveSharedPreference.getPhoneNoStatus(getApplicationContext());
            map.put("mobile_no",mobile_no);
            map.put("bid_price",sEnterBid);
            new Post().doPostSetBidDetails(this,map);

        }
    }
}
