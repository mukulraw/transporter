package com.mukul.onnwaytransporter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.mukul.onnwaytransporter.networking.AppController;
import com.mukul.onnwaytransporter.orderDetailsPOJO.Data;
import com.mukul.onnwaytransporter.orderDetailsPOJO.Doc;
import com.mukul.onnwaytransporter.orderDetailsPOJO.Pod;
import com.mukul.onnwaytransporter.orderDetailsPOJO.orderDetailsBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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

        vehiclenumber = findViewById(R.id.textView291);
        drivernumber = findViewById(R.id.textView35);

        add = findViewById(R.id.button3);
        upload1 = findViewById(R.id.button5);
        upload2 = findViewById(R.id.button6);

        pod = findViewById(R.id.pod);
        documents = findViewById(R.id.recyclerView2);

        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<orderDetailsBean> call = cr.providerOrderDetails(id);

        call.enqueue(new Callback<orderDetailsBean>() {
            @Override
            public void onResponse(Call<orderDetailsBean> call, Response<orderDetailsBean> response) {

                Data item = response.body().getData();
                truck.setText(item.getTruckType());
                source.setText(item.getPickupAddress() + ", " + item.getPickupCity() + ", " + item.getPickupPincode() + ", " + item.getSource());
                destination.setText(item.getDropAddress() + ", " + item.getDropCity() + ", " + item.getDropPincode() + ", " + item.getDestination());
                //destination.setText(item.getDestination());
                material.setText(item.getMaterial());
                weight.setText(item.getWeight());
                date.setText(item.getSchedule());
                status.setText(item.getStatus());
                fare.setText(item.getFare());
                paid.setText(item.getPaid());

                vehiclenumber.setText(item.getVehicleNumber());
                drivernumber.setText(item.getDriverNumber());

                if (item.getVehicleNumber().length() > 0)
                {
                    add.setVisibility(View.GONE);
                    vehiclenumber.setVisibility(View.VISIBLE);
                    drivernumber.setVisibility(View.VISIBLE);
                }
                else
                {
                    add.setVisibility(View.VISIBLE);
                    vehiclenumber.setVisibility(View.GONE);
                    drivernumber.setVisibility(View.GONE);
                }


                PODAdapter adapter = new PODAdapter(OrderDetails.this , item.getPod());
                GridLayoutManager manager = new GridLayoutManager(OrderDetails.this , 2);
                pod.setAdapter(adapter);
                pod.setLayoutManager(manager);

                DocAdapter adapter2 = new DocAdapter(OrderDetails.this , item.getDoc());
                GridLayoutManager manager2 = new GridLayoutManager(OrderDetails.this , 2);
                documents.setAdapter(adapter2);
                documents.setLayoutManager(manager2);

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<orderDetailsBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    class PODAdapter extends RecyclerView.Adapter<PODAdapter.ViewHolder>
    {

        List<Pod> list = new ArrayList<>();
        Context context;

        public PODAdapter(Context context , List<Pod> list)
        {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.image_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Pod item = list.get(position);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getName() , holder.image , options);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            ImageView image;
            ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
            }
        }
    }

    class DocAdapter extends RecyclerView.Adapter<DocAdapter.ViewHolder>
    {

        List<Doc> list = new ArrayList<>();
        Context context;

        public DocAdapter(Context context , List<Doc> list)
        {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.image_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Doc item = list.get(position);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getName() , holder.image , options);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            ImageView image;
            ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
            }
        }
    }

}
