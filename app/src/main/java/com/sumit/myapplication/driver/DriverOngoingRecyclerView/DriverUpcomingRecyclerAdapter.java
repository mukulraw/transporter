package com.sumit.myapplication.driver.DriverOngoingRecyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sumit.myapplication.driver.drivertablayout.DriverUpComingFragment;
import com.sumit.myapplication.R;

import java.util.List;

public class DriverUpcomingRecyclerAdapter extends RecyclerView.Adapter<DriverUpcomingViewHolder>
{
    private List<DriverUpcomingUsers> driverUpcomingUsers;
    public DriverUpcomingRecyclerAdapter(List<DriverUpcomingUsers> driverUpcomingUsers) {
        this.driverUpcomingUsers = driverUpcomingUsers;
    }

    @NonNull
    @Override
    public DriverUpcomingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.upcoming_order_driver_recycler_item,
                viewGroup,false);
        return new DriverUpcomingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverUpcomingViewHolder driverUpcomingViewHolder, final int i) {
        DriverUpcomingUsers sampleUpcomingUser= driverUpcomingUsers.get(i);

        driverUpcomingViewHolder.upComingDateDriver.setText(sampleUpcomingUser.upComingDateDriver);
        driverUpcomingViewHolder.upComingtypeDriver.setText(sampleUpcomingUser.upComingTypeDriver);
        driverUpcomingViewHolder.upComingStartLocDriver.setText(sampleUpcomingUser.upComingStartLocDriver);
        driverUpcomingViewHolder.upComingEndLocDriver.setText(sampleUpcomingUser.upComingEndLocDriver);
        driverUpcomingViewHolder.upComingTotalAmountDriver.setText(sampleUpcomingUser.upComingTotalAmountDriver);
        driverUpcomingViewHolder.upComingWeightDriver.setText(sampleUpcomingUser.upComingWeightDriver);
        driverUpcomingViewHolder.materialTypeDriver.setText(sampleUpcomingUser.upComingMaterialTypeDriver);
        driverUpcomingViewHolder.upComingDueAmountDriver.setText(sampleUpcomingUser.upComingDueAmountDriver);

        driverUpcomingViewHolder.upcomingOrderCardDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DriverUpComingFragment.enableBottomSheet(driverUpcomingUsers.get(i).upComingIdDriver,
                        "₹ " + driverUpcomingUsers.get(i).upComingTotalAmountDriver,
                        driverUpcomingUsers.get(i).upComingStatusDriver,
                        driverUpcomingUsers.get(i).upComingTruckNumberDriver,
                        driverUpcomingUsers.get(i).upComingDeliveryAddressDriver,
                        "gds");
            }
        });
    }

    @Override
    public int getItemCount() {
        return driverUpcomingUsers.size();
    }
}
