package com.mukul.onnwaytransporter.profilerelated;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mukul.onnwaytransporter.AllApiIneterface;
import com.mukul.onnwaytransporter.R;
import com.mukul.onnwaytransporter.SharePreferenceUtils;
import com.mukul.onnwaytransporter.driver.profilerelated.DriverTruckDetails;
import com.mukul.onnwaytransporter.driver.profilerelated.DriverTruckDetailsRecyclerView.TruckDetailsRecyclerAdapter;
import com.mukul.onnwaytransporter.myTrucksPOJO.myTrucksBean;
import com.mukul.onnwaytransporter.networking.AppController;
import com.mukul.onnwaytransporter.networking.CustomRequest;
import com.mukul.onnwaytransporter.profilerelated.OperatedRoutesSampleUser.SampleOperatedRoutesProvider;
import com.mukul.onnwaytransporter.networking.Post;
import com.mukul.onnwaytransporter.preferences.SaveSharedPreference;
import com.mukul.onnwaytransporter.routesPOJO.Device;
import com.mukul.onnwaytransporter.routesPOJO.routesBean;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UpdateRoutesActivity extends AppCompatActivity {
    String routes[]={"Bhopal-Mumbai","Mumbai-Bikaner","Delhi-Haldia","Madras-Kolkata","Kota-Varanasi"};
    Integer[] imageId = {
            R.drawable.delete,
            R.drawable.delete,
            R.drawable.delete,
            R.drawable.delete,
            R.drawable.delete};

    Toolbar mToolbar;
    RecyclerView recyclerView;
    RecyclerAdapterRoutesProvider recyclerAdapterRoutesProvider;
    Context context;
    private FloatingActionButton addRoute_provider;

    ProgressBar progress;

    public UpdateRoutesActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_routes);

        context = this;

        //adding toolbar
        mToolbar = findViewById(R.id.toolbar_update_routes_provider);
        mToolbar.setTitle("Operated Routes");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView=findViewById(R.id.recyclerview_id_operated_routes_provider);


        addRoute_provider= findViewById(R.id.addNewRoute_Btn_provider);
        progress= findViewById(R.id.progressBar7);

//        //calling events for clicking on linear layout
        addRoute_provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateRoutesActivity.this, AddRoutesActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<routesBean> call = cr.fetch_provider_source_des(SharePreferenceUtils.getInstance().getString("userId"));

        call.enqueue(new Callback<routesBean>() {
            @Override
            public void onResponse(Call<routesBean> call, Response<routesBean> response) {

                recyclerAdapterRoutesProvider = new RecyclerAdapterRoutesProvider(response.body().getDevices(), context);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UpdateRoutesActivity.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(recyclerAdapterRoutesProvider);

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<routesBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    class RecyclerAdapterRoutesProvider extends RecyclerView.Adapter<ViewHolderOperatedRoutes> {
        public List<Device> musers;
        public Context mContext;
        public String idd;


        public RecyclerAdapterRoutesProvider(List<Device> musers, Context mContext){
            this.musers=musers;
            this.mContext=mContext;
        }

        public void setData(List<Device> newMusers) {
            this.musers.clear();
            musers.addAll(newMusers);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolderOperatedRoutes onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_items_routes
                    ,viewGroup,false);
            return new ViewHolderOperatedRoutes(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderOperatedRoutes viewHolderOperatedRoutes, final int i) {
            final Device sampleuser=musers.get(i);
            viewHolderOperatedRoutes.operatedRouteSourceProvider.setText(sampleuser.getSource());
            viewHolderOperatedRoutes.operatedRouteDestinationProvider.setText(sampleuser.getDestination());


            viewHolderOperatedRoutes.imageDeleteProvider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog progressDialog = new ProgressDialog(mContext, R.style.MyDialogTheme);
                    progressDialog.setTitle("Deleting Routes");
                    progressDialog.setMessage("Please wait, while we delete route");
                    progressDialog.show();
                    progressDialog.setCancelable(false);

                    String url = "https://www.onnway.com/android/provider_delete_source_des.php";

                    Map<String, String> params = new HashMap<>();
                    params.put("mobile_no", SharePreferenceUtils.getInstance().getString("userId"));
                    //params.put("type", addRoutesDataDetails.type);
                    params.put("source", sampleuser.getSource());
                    params.put("destination", sampleuser.getDestination());


                    CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new com.android.volley.Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            // Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                            //progressBar.setVisibility(View.INVISIBLE);
                            progressDialog.dismiss();

                            onResume();

                        }
                    }, new com.android.volley.Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError response) {
                            Log.d("Response: ", response.toString());
                        }
                    });
                    AppController.getInstance().addToRequestQueue(jsObjRequest);

                }
            });


        }

        @Override
        public int getItemCount() {
            return musers.size();
        }

    }

}
