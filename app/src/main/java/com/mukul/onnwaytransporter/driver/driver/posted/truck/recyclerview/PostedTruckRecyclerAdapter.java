package com.mukul.onnwaytransporter.driver.driver.posted.truck.recyclerview;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mukul.onnwaytransporter.R;

import java.util.List;

public class PostedTruckRecyclerAdapter extends RecyclerView.Adapter<ViewHolderPostedTruck>
{
    private List<PostedUser> postedUsers;
    public static  int quote_id;
    //constructor
    Context context;

public PostedTruckRecyclerAdapter(List<PostedUser> postedUsers, Context context)
{
    this.context=context;
    this.postedUsers=postedUsers;
}

    @NonNull
    @Override
    public ViewHolderPostedTruck onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.posted_truck_recycler_item,viewGroup,false);
        return new ViewHolderPostedTruck(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPostedTruck viewHolderPostedTruck, final int i) {

        PostedUser samplePostedUser=postedUsers.get(i);
        viewHolderPostedTruck.postedTruckEndLoc.setText(samplePostedUser.postedTruckEndLoc);
        viewHolderPostedTruck.postedTruckType.setText(samplePostedUser.postedTruckType);
        viewHolderPostedTruck.postedTruckStartLoc.setText(samplePostedUser.postedTruckStartLoc);
        viewHolderPostedTruck.postedTruckDate.setText(samplePostedUser.postedTruckDate);
    }

    @Override
    public int getItemCount() {
        return postedUsers.size();
    }

}
