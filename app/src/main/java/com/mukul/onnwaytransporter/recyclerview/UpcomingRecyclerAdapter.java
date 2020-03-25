package com.mukul.onnwaytransporter.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mukul.onnwaytransporter.R;
import com.mukul.onnwaytransporter.tablayout.UpComingFragment;

import java.util.List;

public class UpcomingRecyclerAdapter extends RecyclerView.Adapter<UpcomingViewHolder>
{

    private List<UpcomingUsers> upcomingUsers;


    public UpcomingRecyclerAdapter(List<UpcomingUsers> upcomingUsers)
    {
        this.upcomingUsers = upcomingUsers;
    }

    @NonNull
    @Override
    public UpcomingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.upcoming_order_recycler_item,
                viewGroup,false);
        return new UpcomingViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingViewHolder upcomingViewHolder, final int i) {
        UpcomingUsers sampleUpcomingUser=upcomingUsers.get(i);
        upcomingViewHolder.upComingDate.setText(sampleUpcomingUser.upComingDate);
        upcomingViewHolder.upComingtype.setText(sampleUpcomingUser.upComingtype);
        upcomingViewHolder.upComingStartLoc.setText(sampleUpcomingUser.upComingStartLoc);
        upcomingViewHolder.upComingEndLoc.setText(sampleUpcomingUser.upComingEndLoc);
        upcomingViewHolder.upComingTotalAmount.setText(sampleUpcomingUser.upComingTotalAmount);
        upcomingViewHolder.upComingWeight.setText(sampleUpcomingUser.upComingWeight);
        upcomingViewHolder.materialType.setText(sampleUpcomingUser.materialType);
        upcomingViewHolder.upComingDueAmount.setText(sampleUpcomingUser.upComingDueAmount);

        upcomingViewHolder.upcomingOrderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpComingFragment.enableBottomSheet(upcomingUsers.get(i).upComingId,"₹ " + upcomingUsers.get(i).upComingTotalAmount, upcomingUsers.get(i).upComingStatus, upcomingUsers.get(i).upComingTruckNumber, upcomingUsers.get(i).upComingDeliveryAddress, "gds");
            }
        });
    }

    @Override
    public int getItemCount() {
        return upcomingUsers.size();
    }
}
