package com.mukul.onnwaytransporter.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mukul.onnwaytransporter.R;
import com.mukul.onnwaytransporter.tablayout.PastFragment;

import java.util.List;

public class PastOrderRecyclerAdapter extends RecyclerView.Adapter<PastOrderViewHolder> {

    private List<PastOrder> pastOrders;

    public PastOrderRecyclerAdapter(List<PastOrder> pastOrders) {
        this.pastOrders = pastOrders;
    }
    @NonNull
    @Override
    public PastOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.past_order_recycler_item,
                viewGroup,false);
        return new PastOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PastOrderViewHolder pastOrderViewHolder, final int i) {

        PastOrder pastOrder = pastOrders.get(i);
        pastOrderViewHolder.pastDate.setText(pastOrder.pastDate);
        pastOrderViewHolder.pastType.setText(pastOrder.pastType);
        pastOrderViewHolder.pastStartLoc.setText(pastOrder.pastStartLoc);
        pastOrderViewHolder.pastEndLoc.setText(pastOrder.pastEndLoc);
        pastOrderViewHolder.pastTotalAmount.setText(pastOrder.pastTotalAmount);
        pastOrderViewHolder.pastWeight.setText(pastOrder.pastWeight);
        pastOrderViewHolder.pastMaterialType.setText(pastOrder.pastMaterialType);
        pastOrderViewHolder.pastDueAmount.setText(pastOrder.pastDueAmount);

        pastOrderViewHolder.pastOrderCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PastFragment.enableBottomSheet(pastOrders.get(i).pastId, "₹ " + pastOrders.get(i).pastTotalAmount,
                        pastOrders.get(i).pastStatus, pastOrders.get(i).pastTruckNumber, pastOrders.get(i).pastDeliveryAddress,
                        "asd");
            }
        });
    }

    @Override
    public int getItemCount() {
        return pastOrders.size();
    }
}
