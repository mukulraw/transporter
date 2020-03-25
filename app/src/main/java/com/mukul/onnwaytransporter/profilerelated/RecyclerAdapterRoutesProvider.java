package com.mukul.onnwaytransporter.profilerelated;

import android.content.Context;
import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mukul.onnwaytransporter.R;
import com.mukul.onnwaytransporter.networking.Post;

import java.util.List;

public class  RecyclerAdapterRoutesProvider extends RecyclerView.Adapter<ViewHolderOperatedRoutes> {
    public List<OperatedRoutesUserProvider> musers;
    public Context mContext;
    public String idd;


    public RecyclerAdapterRoutesProvider(List<OperatedRoutesUserProvider> musers, Context mContext){
        this.musers=musers;
        this.mContext=mContext;
    }

    public void setData(List<OperatedRoutesUserProvider> newMusers) {
        this.musers.clear();
        musers.addAll(newMusers);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderOperatedRoutes onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_items_routes
                ,viewGroup,false);
        return new ViewHolderOperatedRoutes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOperatedRoutes viewHolderOperatedRoutes, final int i) {
         final OperatedRoutesUserProvider sampleuser=musers.get(i);
        viewHolderOperatedRoutes.operatedRouteSourceProvider.setText(sampleuser.operatedRouteSourceProvider);
        viewHolderOperatedRoutes.operatedRouteDestinationProvider.setText(sampleuser.operatedRouteDestinationProvider);
        viewHolderOperatedRoutes.imageDeleteProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Post().deleteOperatedRoutes(mContext,sampleuser);
//                Intent intent = new Intent(mContext, UpdateRoutesActivity.class);
//                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return musers.size();
    }

}
