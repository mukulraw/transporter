package com.sumit.myapplication.driver.DriverOngoingRecyclerView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sumit.myapplication.R;

public class DriverUpcomingViewHolder extends RecyclerView.ViewHolder
{
    public TextView upComingDateDriver;
    public TextView upComingtypeDriver;
    public TextView upComingStartLocDriver;
    public TextView upComingEndLocDriver;
    public TextView materialTypeDriver;
    public TextView upComingWeightDriver;
    public TextView upComingTotalAmountDriver;
    public TextView upComingDueAmountDriver;

    public CardView upcomingOrderCardDriver;

    public DriverUpcomingViewHolder(@NonNull View itemView) {
        super(itemView);
        upcomingOrderCardDriver = itemView.findViewById(R.id.posted_truck_recycler_card_id_driver);

        upComingDateDriver = itemView.findViewById(R.id.upcoming_date_driver);
        upComingtypeDriver = itemView.findViewById(R.id.upcoming_type_driver);
        upComingStartLocDriver = itemView.findViewById(R.id.upcoming_start_loc_driver);
        upComingEndLocDriver = itemView.findViewById(R.id.upcoming_end_loc_driver);
        materialTypeDriver = itemView.findViewById(R.id.upcoming_material_type_driver);
        upComingWeightDriver = itemView.findViewById(R.id.upcoming_material_weight_driver);
        upComingTotalAmountDriver = itemView.findViewById(R.id.upcoming_total_amount_driver);
        upComingDueAmountDriver = itemView.findViewById(R.id.upcoming_due_amount_driver);
    }
}
