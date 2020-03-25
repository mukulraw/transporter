package com.mukul.onnwaytransporter.driver.driver.posted.truck.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mukul.onnwaytransporter.R;

public class ViewHolderPostedTruck extends RecyclerView.ViewHolder
{

    TextView postedTruckDate,postedTruckType,postedTruckStartLoc,postedTruckEndLoc;

    public ViewHolderPostedTruck(@NonNull View itemView) {
        super(itemView);
        postedTruckDate=itemView.findViewById(R.id.posted_truck_date);
        postedTruckType=itemView.findViewById(R.id.posted_truck_type);
        postedTruckStartLoc=itemView.findViewById(R.id.posted_truck_start_loc);
        postedTruckEndLoc=itemView.findViewById(R.id.posted_truck_end_loc);


    }
}
