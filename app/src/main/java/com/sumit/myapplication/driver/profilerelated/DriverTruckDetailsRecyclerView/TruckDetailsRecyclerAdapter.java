package com.sumit.myapplication.driver.profilerelated.DriverTruckDetailsRecyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sumit.myapplication.R;

import java.util.List;

public class TruckDetailsRecyclerAdapter extends RecyclerView.Adapter<TruckDetailsViewHolder> {

    private List<TruckDetailsUser> truckDetailsUsers;

    public TruckDetailsRecyclerAdapter(List<TruckDetailsUser> truckDetailsUsers){
        this.truckDetailsUsers=truckDetailsUsers;
    }

    @NonNull
    @Override
    public TruckDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.truck_details_items_recycler_view,
                viewGroup,false);
        return new TruckDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TruckDetailsViewHolder truckDetailsViewHolder, int i) {
        TruckDetailsUser sampleTruckDetails=truckDetailsUsers.get(i);
        truckDetailsViewHolder.truckType.setText(sampleTruckDetails.truckType);
        truckDetailsViewHolder.registrationNumber.setText(sampleTruckDetails.registrationNumber);
        truckDetailsViewHolder.driverName.setText(sampleTruckDetails.driverName);
        truckDetailsViewHolder.driverNumber.setText(sampleTruckDetails.driverNumber);

    }

    @Override
    public int getItemCount() {
        return truckDetailsUsers.size();
    }
}
