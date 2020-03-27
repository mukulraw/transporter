package com.mukul.onnwaytransporter;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mukul.onnwaytransporter.bidsPOJO.Datum;
import com.mukul.onnwaytransporter.bidsPOJO.bidsBean;
import com.mukul.onnwaytransporter.networking.AppController;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyBidFragment extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progress;
    List<Datum> list;
    OrderAdapter adapter;
    GridLayoutManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_bid, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_ongoing_order);
        progress = view.findViewById(R.id.progress);
        list = new ArrayList<>();

        adapter = new OrderAdapter(getContext(), list);
        manager = new GridLayoutManager(getContext() , 1);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();

        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getActivity().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<bidsBean> call = cr.getBiddings(SharePreferenceUtils.getInstance().getString("userId"));

        call.enqueue(new Callback<bidsBean>() {
            @Override
            public void onResponse(Call<bidsBean> call, Response<bidsBean> response) {

                adapter.setData(response.body().getData());

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<bidsBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    static class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>
    {

        Context context;
        List<Datum> list = new ArrayList<>();

        OrderAdapter(Context context, List<Datum> list)
        {
            this.context = context;
            this.list = list;
        }

        void setData(List<Datum> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.bid_list_model , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

            holder.setIsRecyclable(false);

            final Datum item = list.get(position);

            holder.type.setText(item.getLaodType());
            //holder.orderid.setText("Order #" + item.getId());
            holder.date.setText(item.getCreated());
            holder.source.setText(item.getSource());
            holder.destination.setText(item.getDestination());
            holder.material.setText(item.getMaterial());
            holder.weight.setText(item.getWeight());
            holder.truck.setText(item.getTruckType());


            if (item.getBid().equals("0"))
            {
                holder.placed.setVisibility(View.GONE);
            }
            else
            {
                holder.placed.setVisibility(View.VISIBLE);
            }

            String dateString = item.getCreated();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = null;
            try {
                date = sdf.parse(dateString);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.HOUR, 1);

                Date startDate = calendar.getTime();

                Date currentTime = Calendar.getInstance().getTime();

                //long diffInMs = currentTime.getTime() - startDate.getTime();
                long diffInMs = startDate.getTime() - currentTime.getTime();

                Log.d("ms1" , String.valueOf(startDate.getTime()));
                Log.d("ms" , String.valueOf(currentTime.getTime()));

                if (diffInMs > 0)
                {
                    long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);

                    CountDownTimer timer = new CountDownTimer(diffInMs , 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            holder.freight.setText(convertSecondsToHMmSs(millisUntilFinished / 1000));
                        }

                        @Override
                        public void onFinish() {

                        }
                    };

                    timer.start();
                }
                else
                {
                    holder.freight.setText(" - ");
                }



            } catch (ParseException e) {
                e.printStackTrace();
            }







            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , BidDetails.class);
                    intent.putExtra("id" , item.getId());
                    context.startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public static String convertSecondsToHMmSs(long seconds) {
            long s = seconds % 60;
            long m = (seconds / 60) % 60;
            long h = (seconds / (60 * 60)) % 24;
            return String.format("%d:%02d:%02d", h,m,s);
        }

        static class ViewHolder extends RecyclerView.ViewHolder
        {

            TextView type , date , source , destination , material , weight , freight , truck , placed;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                type = itemView.findViewById(R.id.textView65);
                date = itemView.findViewById(R.id.textView67);
                source = itemView.findViewById(R.id.textView69);
                destination = itemView.findViewById(R.id.textView70);
                material = itemView.findViewById(R.id.textView72);
                weight = itemView.findViewById(R.id.textView74);
                freight = itemView.findViewById(R.id.textView66);
                truck = itemView.findViewById(R.id.textView64);
                placed = itemView.findViewById(R.id.textView8);

            }
        }
    }

}

