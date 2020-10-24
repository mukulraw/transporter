package com.mukul.onnwaytransporter.driver.profilerelated.DriverTruckDetailsRecyclerView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mukul.onnwaytransporter.R;

public class TruckDetailsViewHolder extends RecyclerView.ViewHolder{

    public TextView truckType,registrationNumber,driverName,driverNumber;
    public CardView truckDetailsCardHolder;

    ImageView trucktype;

    public TruckDetailsViewHolder(@NonNull View itemView) {
        super(itemView);
        truckDetailsCardHolder=itemView.findViewById(R.id.truck_details_driver);
        trucktype=itemView.findViewById(R.id.truck_image_driver);
        truckType=itemView.findViewById(R.id.driver_truck_type);
        registrationNumber=itemView.findViewById(R.id.registration_number);
        driverName=itemView.findViewById(R.id.driver_name);
        driverNumber=itemView.findViewById(R.id.driver_number);


    }
}
