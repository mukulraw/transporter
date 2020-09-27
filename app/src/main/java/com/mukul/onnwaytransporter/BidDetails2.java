package com.mukul.onnwaytransporter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mukul.onnwaytransporter.bidDetailsPOJO.Data;
import com.mukul.onnwaytransporter.bidDetailsPOJO.bidDetailsBean;
import com.mukul.onnwaytransporter.networking.AppController;
import com.mukul.onnwaytransporter.placeBidPOJO.placeBidBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BidDetails2 extends AppCompatActivity implements OnMapReadyCallback {

    TextView orderid, orderdate, truck, source, destination, material, weight, distance, schedule;
    Button confirm;
    ProgressBar progress;

    String id;
    EditText amount;

    String sourceLAT = "", sourceLNG = "", destinationLAT = "", destinationLNG = "";

    TextView dimension, equal, quantity, total, phototitle;
    ImageView photo;

    private GoogleMap mMap;

    private Polyline mPolyline;
    private LatLng mOrigin;
    private LatLng mDestination;
    ArrayList<LatLng> mMarkerPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_details2);
        mMarkerPoints = new ArrayList<>();
        id = getIntent().getStringExtra("id");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Bid Details");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);

        dimension = findViewById(R.id.textView134);
        schedule = findViewById(R.id.textView88);
        phototitle = findViewById(R.id.textView140);
        equal = findViewById(R.id.textView135);
        quantity = findViewById(R.id.textView137);
        total = findViewById(R.id.textView139);
        photo = findViewById(R.id.imageView18);
        orderid = findViewById(R.id.textView16);
        confirm = findViewById(R.id.button4);
        amount = findViewById(R.id.amount);
        distance = findViewById(R.id.textView11);

        orderdate = findViewById(R.id.textView17);
        truck = findViewById(R.id.textView19);
        source = findViewById(R.id.textView20);
        destination = findViewById(R.id.textView21);
        material = findViewById(R.id.textView23);
        weight = findViewById(R.id.textView25);

        progress = findViewById(R.id.progressBar);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a = amount.getText().toString();

                if (a.length() > 0) {

                    progress.setVisibility(View.VISIBLE);

                    AppController b = (AppController) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                    Call<placeBidBean> call1 = cr.placeBid(SharePreferenceUtils.getInstance().getString("userId"), id, a);

                    call1.enqueue(new Callback<placeBidBean>() {
                        @Override
                        public void onResponse(Call<placeBidBean> call, Response<placeBidBean> response) {

                            if (response.body().getStatus().equals("1")) {
                                Toast.makeText(BidDetails2.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                Toast.makeText(BidDetails2.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<placeBidBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });
                } else {
                    Toast.makeText(BidDetails2.this, "Invalid Bid Amount", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        progress.setVisibility(View.VISIBLE);


        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        final Call<bidDetailsBean> call = cr.getBidDetails(SharePreferenceUtils.getInstance().getString("userId"), id);

        call.enqueue(new Callback<bidDetailsBean>() {
            @Override
            public void onResponse(Call<bidDetailsBean> call, Response<bidDetailsBean> response) {

                Data item = response.body().getData();
                truck.setText(item.getTruckType());
                source.setText(item.getSource());
                destination.setText(item.getDestination());
                material.setText(item.getMaterial());
                weight.setText(item.getWeight());
                schedule.setText(item.getSchedule());


                sourceLAT = item.getSourceLAT();
                sourceLNG = item.getSourceLNG();
                destinationLAT = item.getDestinationLAT();
                destinationLNG = item.getDestinationLNG();

                Location startPoint = new Location("Source");
                startPoint.setLatitude(Double.parseDouble(sourceLAT));
                startPoint.setLongitude(Double.parseDouble(sourceLNG));

                Location endPoint = new Location("Destination");
                endPoint.setLatitude(Double.parseDouble(destinationLAT));
                endPoint.setLongitude(Double.parseDouble(destinationLNG));

                distance.setText((startPoint.distanceTo(endPoint) / 1000) + " km");

                dimension.setText(item.getLength() + " X " + item.getWidth() + " X " + item.getHeight());

                if (item.getMaterial_image().length() > 0) {
                    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
                    ImageLoader loader = ImageLoader.getInstance();
                    loader.displayImage(item.getMaterial_image(), photo, options);

                    photo.setVisibility(View.VISIBLE);
                    phototitle.setVisibility(View.VISIBLE);
                } else {
                    photo.setVisibility(View.GONE);
                    phototitle.setVisibility(View.GONE);
                }


                float ll = Float.parseFloat(item.getLength());
                float ww = Float.parseFloat(item.getWidth());
                float hh = Float.parseFloat(item.getHeight());
                float qq = Float.parseFloat(item.getQuantity());

                equal.setText("= " + (ll * ww * hh) + " cu.ft.");
                quantity.setText(item.getQuantity());
                total.setText((ll * ww * hh * qq) + " cu.ft.");

                if (item.getBid().length() > 0) {
                    amount.setText(item.getBid());
                    amount.setClickable(false);
                    amount.setFocusable(false);
                    confirm.setVisibility(View.GONE);
                } else {
                    amount.setClickable(true);
                    amount.setFocusable(true);
                    confirm.setVisibility(View.VISIBLE);
                }

                progress.setVisibility(View.GONE);

                mOrigin = new LatLng(Double.parseDouble(sourceLAT), Double.parseDouble(sourceLNG));
                mDestination = new LatLng(Double.parseDouble(destinationLAT), Double.parseDouble(destinationLNG));

                mMarkerPoints.clear();
                mMap.clear();

/*
                Polyline polyline = googleMap.addPolyline(new PolylineOptions()
                        .clickable(true).add(latLng1).add(latLng2));



                polyline.setEndCap(new RoundCap());
                polyline.setColor(COLOR_BLACK_ARGB);
                polyline.setJointType(JointType.ROUND);
*/

                Marker melbourne = mMap.addMarker(
                        new MarkerOptions()
                                .position(mDestination)
                                .title("Current Location")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.home)));

                Marker melbourne2 = mMap.addMarker(
                        new MarkerOptions()
                                .position(mOrigin)
                                .title("Destination")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.home)));

                LatLngBounds.Builder builder = new LatLngBounds.Builder();

//the include method will calculate the min and max bound.
                builder.include(mOrigin);
                builder.include(mDestination);
                LatLngBounds bounds = builder.build();

                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                mMap.animateCamera(cu);

                drawRoute();

            }

            @Override
            public void onFailure(Call<bidDetailsBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }

    private void drawRoute() {

        // Getting URL to the Google Directions API
        String url = getDirectionsUrl(mOrigin, mDestination);

        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }


    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Key
        String key = "key=" + "AIzaSyBXQZx4qVICTJWyKTXO0Ji28GZjD4Pvavg";
        //String key = "key=" + getString(R.string.google_maps_key);

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception on download", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * A class to download data from Google Directions URL
     */
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("DownloadTask", "DownloadTask : " + data);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /**
     * A class to parse the Google Directions in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.BLACK);
            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                if (mPolyline != null) {
                    mPolyline.remove();
                }
                mPolyline = mMap.addPolyline(lineOptions);

            } else
                Toast.makeText(getApplicationContext(), "No route is found", Toast.LENGTH_LONG).show();
        }
    }

}