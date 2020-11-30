package com.app.onnwaytransporter.driver.profilerelated.DriverOperatedRoutesRecyclerView;

import android.content.Context;
import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.onnwaytransporter.R;

import java.util.List;

public class   DriverOperatedRoutesRecyclerAdapter extends RecyclerView.Adapter<DriverOperatedRoutesViewHolder> {
    public List<DriverOperatedRoutesUser> musers;
    public Context mContext;
    public String idd;


    public DriverOperatedRoutesRecyclerAdapter(List<DriverOperatedRoutesUser> musers, Context mContext){
      this.musers=musers;
      this.mContext=mContext;
    }

    @NonNull
    @Override
    public DriverOperatedRoutesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.driver_operated_routes_user_recyclerview
                ,viewGroup,false);
        return new DriverOperatedRoutesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverOperatedRoutesViewHolder driverOperatedRoutesViewHolder, final int i) {
        final DriverOperatedRoutesUser sampleuser = musers.get(i);
        driverOperatedRoutesViewHolder.operatedRouteSourcedriver.setText(sampleuser.operatedRouteSourceDriver);
        driverOperatedRoutesViewHolder.operatedRouteDestinationdriver.setText(sampleuser.operatedRouteDestinationDriver);
    }

    @Override
    public int getItemCount() {
        return musers.size();
    }

}
