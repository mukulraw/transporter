package com.mukul.onnwaytransporter.profilerelated;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mukul.onnwaytransporter.R;
import com.mukul.onnwaytransporter.SharePreferenceUtils;
import com.mukul.onnwaytransporter.networking.AppController;
import com.mukul.onnwaytransporter.networking.CustomRequest;
import com.mukul.onnwaytransporter.networking.Post;
import com.mukul.onnwaytransporter.routesPOJO.Device;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  RecyclerAdapterRoutesProvider extends RecyclerView.Adapter<ViewHolderOperatedRoutes> {
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
                final ProgressDialog progressDialog = new ProgressDialog(mContext);
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


                CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        // Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                        //progressBar.setVisibility(View.INVISIBLE);
                        progressDialog.dismiss();


                    }
                }, new Response.ErrorListener() {

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
