package com.mukul.onnwaytransporter.driver.profilerelated.DriverOperatedRoutesRecyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mukul.onnwaytransporter.R;

public class DriverOperatedRoutesViewHolder extends RecyclerView.ViewHolder {

    public ImageView image_delete;
    public TextView operatedRouteSourcedriver;
    public TextView operatedRouteDestinationdriver;
    public DriverOperatedRoutesViewHolder(@NonNull View itemView) {
        super(itemView);
        operatedRouteSourcedriver=itemView.findViewById(R.id.operated_route_source_driver);
        operatedRouteDestinationdriver=itemView.findViewById(R.id.operated_route_destination_driver);
        image_delete=(ImageView) itemView.findViewById(R.id.delete_image_driver);


    }
}
