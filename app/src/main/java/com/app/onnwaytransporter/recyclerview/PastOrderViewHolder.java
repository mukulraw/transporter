package com.app.onnwaytransporter.recyclerview;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.onnwaytransporter.R;

public class PastOrderViewHolder extends RecyclerView.ViewHolder {
    public TextView pastDate;
    public TextView pastType;
    public TextView pastStartLoc;
    public TextView pastEndLoc;
    public TextView pastMaterialType;
    public TextView pastWeight;
    public TextView pastTotalAmount;
    public TextView pastDueAmount;

    public CardView pastOrderCardView;

    public PastOrderViewHolder(@NonNull View itemView) {
        super(itemView);
        pastOrderCardView = itemView.findViewById(R.id.past_order_recycler_card_id);
        pastDate = itemView.findViewById(R.id.past_date);
        pastType = itemView.findViewById(R.id.past_type);
        pastStartLoc = itemView.findViewById(R.id.past_start_loc);
        pastEndLoc = itemView.findViewById(R.id.past_end_loc);
        pastMaterialType = itemView.findViewById(R.id.past_material_type);
        pastWeight = itemView.findViewById(R.id.past_material_weight);
        pastTotalAmount = itemView.findViewById(R.id.past_total_amount);
        pastDueAmount = itemView.findViewById(R.id.past_due_amount);
    }
}
