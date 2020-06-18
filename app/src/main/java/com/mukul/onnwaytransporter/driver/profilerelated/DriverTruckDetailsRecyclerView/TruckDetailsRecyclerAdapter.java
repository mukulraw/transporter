package com.mukul.onnwaytransporter.driver.profilerelated.DriverTruckDetailsRecyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mukul.onnwaytransporter.R;
import com.mukul.onnwaytransporter.myTrucksPOJO.Datum;

import java.util.List;

public class TruckDetailsRecyclerAdapter extends RecyclerView.Adapter<TruckDetailsViewHolder> {

    private List<Datum> truckDetailsUsers;

    public TruckDetailsRecyclerAdapter(List<Datum> truckDetailsUsers){
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
        Datum sampleTruckDetails=truckDetailsUsers.get(i);
        truckDetailsViewHolder.truckType.setText(sampleTruckDetails.getTrucktype());
        truckDetailsViewHolder.registrationNumber.setText(sampleTruckDetails.getTruckRegNo());
        truckDetailsViewHolder.driverName.setText(sampleTruckDetails.getDriverName());
        truckDetailsViewHolder.driverNumber.setText(sampleTruckDetails.getDriverMobileNo());

    }

    @Override
    public int getItemCount() {
        return truckDetailsUsers.size();
    }
}
