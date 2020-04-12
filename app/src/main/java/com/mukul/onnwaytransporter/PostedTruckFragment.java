package com.mukul.onnwaytransporter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mukul.onnwaytransporter.networking.AppController;
import com.mukul.onnwaytransporter.networking.Post;
import com.mukul.onnwaytransporter.postedTrucksPOJO.Datum;
import com.mukul.onnwaytransporter.postedTrucksPOJO.postedTrucksBean;
import com.mukul.onnwaytransporter.recyclerview.PostedTruckRecyclerAdapter;
import com.mukul.onnwaytransporter.recyclerview.SamplePostedTruck;
import com.mukul.onnwaytransporter.preferences.SaveSharedPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostedTruckFragment extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progress;
    List<Datum> list;
    OrderAdapter adapter;
    GridLayoutManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_ongoing_order, container, false);
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

        Call<postedTrucksBean> call = cr.getPostedTrucks(SharePreferenceUtils.getInstance().getString("userId"));

        call.enqueue(new Callback<postedTrucksBean>() {
            @Override
            public void onResponse(Call<postedTrucksBean> call, Response<postedTrucksBean> response) {

                adapter.setData(response.body().getData());

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<postedTrucksBean> call, Throwable t) {
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
            View view = inflater.inflate(R.layout.order_list_model , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final Datum item = list.get(position);

            holder.type.setText(item.getLaodType());
            holder.orderid.setText("Truck #" + item.getId());
            holder.date.setText(item.getCreated());
            holder.source.setText(item.getSource());
            holder.destination.setText(item.getDestination());
            holder.material.setText(item.getLoadPassing());
            holder.weight.setText(item.getSchedule());
            holder.truck.setText(item.getTruckType());
            holder.status.setText(item.getStatus());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , TruckDetails.class);
                    intent.putExtra("id" , item.getId());
                    context.startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder
        {

            TextView type , orderid , date , source , destination , material , weight , freight , truck , status;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                type = itemView.findViewById(R.id.textView65);
                orderid = itemView.findViewById(R.id.textView66);
                date = itemView.findViewById(R.id.textView67);
                source = itemView.findViewById(R.id.textView69);
                destination = itemView.findViewById(R.id.textView70);
                material = itemView.findViewById(R.id.textView72);
                weight = itemView.findViewById(R.id.textView74);
                freight = itemView.findViewById(R.id.textView76);
                truck = itemView.findViewById(R.id.textView64);
                status = itemView.findViewById(R.id.textView79);

            }
        }
    }

}
