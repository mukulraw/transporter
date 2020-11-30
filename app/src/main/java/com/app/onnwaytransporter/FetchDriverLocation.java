package com.app.onnwaytransporter;

import android.os.Handler;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.app.onnwaytransporter.networking.Post;

public class FetchDriverLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker mCurrLocationMarker;
    final Handler handler = new Handler();
    public FetchDriverData fetchDriverData = new FetchDriverData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_driver_location);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        fetchDriverData.bId = 1;
        new Post().doPostGetDriverLoc(getApplication(), fetchDriverData);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    fetchDriverData.bId = 1;
                    new Post().doPostGetDriverLoc(getApplication(), fetchDriverData);
                    double lat = Double.parseDouble(Post.getLat);
                    double lng = Double.parseDouble(Post.getLng);

                    LatLng latLng = new LatLng(lat, lng);
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    if(mCurrLocationMarker != null){
                        mCurrLocationMarker.remove();
                    }

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title("Current Position");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    mCurrLocationMarker = mMap.addMarker(markerOptions);

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    Toast.makeText(FetchDriverLocation.this, Post.getLat+Post.getLng, Toast.LENGTH_LONG).show();
                    handler.postDelayed(this, 5000);

                }
            }, 5000);

    }

    //closing the postDelay method
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            handler.removeCallbacksAndMessages(null);
        }
        return super.onKeyDown(keyCode, event);
    }
}
