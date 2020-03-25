package com.sumit.myapplication.profilerelated;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sumit.myapplication.R;


public class ViewHolderOperatedRoutes extends RecyclerView.ViewHolder {

    public ImageView imageDeleteProvider;
    public TextView operatedRouteSourceProvider;
    public TextView operatedRouteDestinationProvider;

    public ViewHolderOperatedRoutes(@NonNull View itemView) {
        super(itemView);
        operatedRouteSourceProvider=itemView.findViewById(R.id.operated_route_source_provider);
        operatedRouteDestinationProvider=itemView.findViewById(R.id.operated_route_destination_provider);
        imageDeleteProvider=(ImageView) itemView.findViewById(R.id.delete_image_provider);
    }
}