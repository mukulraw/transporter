package com.app.onnwaytransporter.driver.driver.past.order.recycler.view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.onnwaytransporter.driver.drivertablayout.DriverPastFragment;
import com.app.onnwaytransporter.R;

import java.util.List;

public class PastRecyclerAdapter extends RecyclerView.Adapter<PastViewHolder>
{

    private List<PastUsers> pastUsers;

    public PastRecyclerAdapter(List<PastUsers> pastusers) {
        this.pastUsers = pastusers;

    }

    @NonNull
    @Override
    public PastViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.past_order_driver_recycler_item,
                viewGroup,false);
        return new PastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PastViewHolder pastViewHolder, final int i) {
        PastUsers samplePastUser = pastUsers.get(i);

        pastViewHolder.pastDateDriver.setText(samplePastUser.pastDateDriver);
        pastViewHolder.pastTypeDriver.setText(samplePastUser.pastTypeDriver);
        pastViewHolder.pastStartLocDriver.setText(samplePastUser.pastStartLocDriver);
        pastViewHolder.pastEndLocDriver.setText(samplePastUser.pastEndLocDriver);
        pastViewHolder.pastTotalAmountDriver.setText(samplePastUser.pastTotalAmountDriver);
        pastViewHolder.pastWeightDriver.setText(samplePastUser.pastWeightDriver);
        pastViewHolder.pastMaterialTypeDriver.setText(samplePastUser.pastMaterialTypeDriver);
        pastViewHolder.pastDueAmountDriver.setText(samplePastUser.pastDueAmountDriver);

        pastViewHolder.pastOrderCardViewDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DriverPastFragment.enableBottomSheet(pastUsers.get(i).pastIdDriver,
                        "₹ " + pastUsers.get(i).pastTotalAmountDriver,
                        pastUsers.get(i).pastStatusDriver,
                        pastUsers.get(i).pastTruckNumberDriver,
                        pastUsers.get(i).pastDeliveryAddressDriver,
                        "gds");
            }
        });
    }

    @Override
    public int getItemCount() {
        return pastUsers.size();
    }
}
