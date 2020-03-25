package com.mukul.onnwaytransporter.driver.profilerelated.DriverTruckDetailsRecyclerView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mukul.onnwaytransporter.R;

public class TruckDetailsViewHolder extends RecyclerView.ViewHolder{

    public TextView truckType,registrationNumber,driverName,driverNumber;
    public CardView truckDetailsCardHolder;

    public TruckDetailsViewHolder(@NonNull View itemView) {
        super(itemView);
        truckDetailsCardHolder=itemView.findViewById(R.id.truck_details_driver);

        truckType=itemView.findViewById(R.id.driver_truck_type);
        registrationNumber=itemView.findViewById(R.id.registration_number);
        driverName=itemView.findViewById(R.id.driver_name);
        driverNumber=itemView.findViewById(R.id.driver_number);


    }
}
