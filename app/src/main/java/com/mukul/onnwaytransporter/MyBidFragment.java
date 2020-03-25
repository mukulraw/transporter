package com.mukul.onnwaytransporter;


import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mukul.onnwaytransporter.recyclerview.BidUser;
import com.mukul.onnwaytransporter.recyclerview.RecyclerAdapterBid;
import com.mukul.onnwaytransporter.recyclerview.SampleBidUser;
import com.mukul.onnwaytransporter.preferences.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyBidFragment extends Fragment {

    public static String arrsourceLat[];
    public static String arrsourceLng[];
    public static String arrdestLat[];
    public static String arrdestLng[];
    public static String arrtrucktype[];
    public static String arrmaterial[];
    public static String arrweight[];
    public static String arrsource[];
    public static String arrdestination[];
    public static String arrdate[];
    public static String arrquoteid[];
    public static RecyclerView recyclerView;
    public static RecyclerAdapterBid recyclerAdapterBid;
    SwipeRefreshLayout swipeRefreshLayout;

    private ShimmerFrameLayout mShimmerViewContainer;

    public MyBidFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_bid, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.recycler_view);

        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);

        //checking networrk
        if (SaveSharedPreference.getCounterBidStatus(getContext()).equals("0")) {
            new MyBidFragment.BackgroundTaskBid().execute();
        } else {

            if (SampleBidUser.samplebiduser == null) {
                new MyBidFragment.BackgroundTaskBid().execute();
            } else {
                setRecyclerBid();
            }
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new MyBidFragment.BackgroundTaskBid().execute();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;

    }


    public void setRecyclerBid() {

        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);

        recyclerAdapterBid = new RecyclerAdapterBid(SampleBidUser.samplebiduser, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapterBid);

    }


    //setting in recycler view
    ///backgound task testing
    class BackgroundTaskBid extends AsyncTask<Void, Void, String> {

        String json_url = "https://www.onnway.com/android/my-bids.php";
        String JSON_STRING;

        @Override
        protected String doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Something went wrong";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mShimmerViewContainer.startShimmerAnimation();

            String ans = "";
            try {

                int count = 0;

                JSONArray jsonArray = new JSONArray(s);
                int len = jsonArray.length();
                Toast.makeText(getContext(), "" + jsonArray.length(), Toast.LENGTH_SHORT).show();
                arrsourceLat = new String[jsonArray.length()];
                arrsourceLng = new String[jsonArray.length()];
                arrdestLat = new String[jsonArray.length()];
                arrdestLng = new String[jsonArray.length()];

                arrtrucktype = new String[jsonArray.length()];
                arrmaterial = new String[jsonArray.length()];
                arrweight = new String[jsonArray.length()];
                arrsource = new String[jsonArray.length()];
                arrdestination = new String[jsonArray.length()];
                arrdate = new String[jsonArray.length()];
                arrquoteid = new String[jsonArray.length()];
                while (count < jsonArray.length()) {

                    JSONObject jsonObject = jsonArray.getJSONObject(count);
                    arrsourceLat[count] = jsonObject.getString("slat");
                    arrsourceLng[count] = jsonObject.getString("slng");
                    arrdestLat[count] = jsonObject.getString("dlat");
                    arrdestLng[count] = jsonObject.getString("dlng");
                    arrtrucktype[count] = jsonObject.getString("trucktype");
                    arrmaterial[count] = jsonObject.getString("material");
                    arrweight[count] = jsonObject.getString("weight");
                    arrsource[count] = jsonObject.getString("source");
                    arrdestination[count] = jsonObject.getString("destination");
                    arrdate[count] = jsonObject.getString("sch_date");
                    arrquoteid[count] = jsonObject.getString("quote_id");
                    count++;
                }

                SampleBidUser.samplebiduser = new ArrayList<>();
                for (int i = 0; i < arrdate.length; i++) {
                    BidUser bidUser = new BidUser();
                    bidUser.sourceLat = arrsourceLat[i];
                    bidUser.sourceLng = arrsourceLng[i];
                    bidUser.destLat = arrdestLat[i];
                    bidUser.destLng = arrdestLng[i];

                    bidUser.date = arrdate[i];
                    bidUser.destination = arrdestination[i];
                    bidUser.source = arrsource[i];
                    bidUser.material = arrmaterial[i];
                    bidUser.weight = arrweight[i];
                    bidUser.truckType = arrtrucktype[i];
                    SampleBidUser.samplebiduser.add(bidUser);
                }

                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);

                recyclerAdapterBid = new RecyclerAdapterBid(SampleBidUser.samplebiduser, getActivity());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(recyclerAdapterBid);
                SaveSharedPreference.setCounterBid(getContext(), "1");


                Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                //sheemer
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }
}

