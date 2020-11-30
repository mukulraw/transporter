package com.app.onnwaytransporter.driver.profilerelated.DriverTruckDetailsRecyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.onnwaytransporter.R;
import com.app.onnwaytransporter.myTrucksPOJO.Datum;

import java.util.List;

public class TruckDetailsRecyclerAdapter extends RecyclerView.Adapter<TruckDetailsViewHolder> {

    private List<Datum> truckDetailsUsers;
    Context context;

    public TruckDetailsRecyclerAdapter(List<Datum> truckDetailsUsers, Context context){
        this.truckDetailsUsers=truckDetailsUsers;
        this.context=context;
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

        try {
            if (sampleTruckDetails.getTruck_type2().equals("open truck")) {
                truckDetailsViewHolder.trucktype.setImageDrawable(context.getDrawable(R.drawable.open));
            } else if (sampleTruckDetails.getTruck_type2().equals("trailer")) {
                truckDetailsViewHolder.trucktype.setImageDrawable(context.getDrawable(R.drawable.trailer));
            } else {
                truckDetailsViewHolder.trucktype.setImageDrawable(context.getDrawable(R.drawable.container));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return truckDetailsUsers.size();
    }
}
