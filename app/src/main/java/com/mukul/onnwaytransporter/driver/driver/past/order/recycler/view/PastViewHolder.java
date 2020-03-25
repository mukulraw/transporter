package com.mukul.onnwaytransporter.driver.driver.past.order.recycler.view;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mukul.onnwaytransporter.R;

public class PastViewHolder extends RecyclerView.ViewHolder
{
    public TextView pastDateDriver;
    public TextView pastTypeDriver;
    public TextView pastStartLocDriver;
    public TextView pastEndLocDriver;
    public TextView pastMaterialTypeDriver;
    public TextView pastWeightDriver;
    public TextView pastTotalAmountDriver;
    public TextView pastDueAmountDriver;

    public CardView pastOrderCardViewDriver;


    public PastViewHolder(@NonNull View itemView) {
        super(itemView);
        pastOrderCardViewDriver = itemView.findViewById(R.id.past_order_recycler_card_id_driver);
        pastDateDriver = itemView.findViewById(R.id.past_date_driver);
        pastTypeDriver = itemView.findViewById(R.id.past_type_driver);
        pastStartLocDriver = itemView.findViewById(R.id.past_start_loc_driver);
        pastEndLocDriver = itemView.findViewById(R.id.past_end_loc_driver);
        pastMaterialTypeDriver = itemView.findViewById(R.id.past_material_type_driver);
        pastWeightDriver = itemView.findViewById(R.id.past_material_weight_driver);
        pastTotalAmountDriver = itemView.findViewById(R.id.past_total_amount_driver);
        pastDueAmountDriver = itemView.findViewById(R.id.past_due_amount_driver);
    }
}
