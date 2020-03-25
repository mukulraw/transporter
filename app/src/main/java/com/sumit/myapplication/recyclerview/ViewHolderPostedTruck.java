package com.sumit.myapplication.recyclerview;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sumit.myapplication.R;

public class ViewHolderPostedTruck extends RecyclerView.ViewHolder
{

    TextView postedTruckDate,postedTruckType,postedTruckStartLoc,postedTruckEndLoc;
    CardView recyclerViewCard;

    public ViewHolderPostedTruck(@NonNull View itemView) {
        super(itemView);
        recyclerViewCard = itemView.findViewById(R.id.posted_truck_recycler_card_id);
        postedTruckDate=itemView.findViewById(R.id.posted_truck_date);
        postedTruckType=itemView.findViewById(R.id.posted_truck_type);
        postedTruckStartLoc=itemView.findViewById(R.id.posted_truck_start_loc);
        postedTruckEndLoc=itemView.findViewById(R.id.posted_truck_end_loc);


    }
}
