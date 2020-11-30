package com.app.onnwaytransporter.recyclerview;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.onnwaytransporter.R;

public class UpcomingViewHolder extends RecyclerView.ViewHolder
{
    public TextView upComingDate,upComingtype,
            upComingStartLoc,upComingEndLoc
            ,materialType,upComingWeight,
            upComingTotalAmount,upComingDueAmount;

    public CardView upcomingOrderCard;

    public UpcomingViewHolder(@NonNull View itemView) {
        super(itemView);
        upcomingOrderCard = itemView.findViewById(R.id.posted_truck_recycler_card_id);

        upComingDate=itemView.findViewById(R.id.upcoming_date);
        upComingtype=itemView.findViewById(R.id.upcoming_type);
        upComingStartLoc=itemView.findViewById(R.id.upcoming_start_loc);
        upComingEndLoc=itemView.findViewById(R.id.upcoming_end_loc);
        materialType=itemView.findViewById(R.id.upcoming_material_type);
        upComingWeight=itemView.findViewById(R.id.upcoming_material_weight);
        upComingTotalAmount=itemView.findViewById(R.id.upcoming_total_amount);
        upComingDueAmount=itemView.findViewById(R.id.upcoming_due_amount);


    }
}
