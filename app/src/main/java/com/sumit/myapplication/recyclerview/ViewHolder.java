package com.sumit.myapplication.recyclerview;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sumit.myapplication.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    //variable declaration
    public TextView tvTruckType, tvMaterial, tvWeight, tvSource, tvDestination, tvDate;
    CardView cardView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        //finding the id of each and every one
        tvTruckType = itemView.findViewById(R.id.tv_truck_type);
        tvMaterial = itemView.findViewById(R.id.tv_material);
        tvWeight = itemView.findViewById(R.id.tv_weight);
        tvSource = itemView.findViewById(R.id.tv_source);
        tvDestination = itemView.findViewById(R.id.tv_destination);
        tvDate = itemView.findViewById(R.id.tv_date);
        //finding the id of cardv
        cardView = itemView.findViewById(R.id.bid_recycler_card_id);
        //handling the onclick on card item

    }


}
