package com.app.onnwaytransporter.recyclerview;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.onnwaytransporter.R;

import java.util.List;

public class PostedTruckRecyclerAdapter extends RecyclerView.Adapter<ViewHolderPostedTruck> {
    private List<PostedUser> postedUsers;
    public static  int quote_id;
    //constructor
    Context context;

public PostedTruckRecyclerAdapter(List<PostedUser> postedUsers,Context context)
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
    public void onBindViewHolder(@NonNull final ViewHolderPostedTruck viewHolderPostedTruck, final int i) {

        PostedUser samplePostedUser=postedUsers.get(i);
        viewHolderPostedTruck.postedTruckEndLoc.setText(samplePostedUser.postedTruckEndLoc);
        viewHolderPostedTruck.postedTruckType.setText(samplePostedUser.postedTruckType);
        viewHolderPostedTruck.postedTruckStartLoc.setText(samplePostedUser.postedTruckStartLoc);
        viewHolderPostedTruck.postedTruckDate.setText(samplePostedUser.postedTruckDate);

        viewHolderPostedTruck.recyclerViewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, postedUsers.get(i).postedTruckDate, Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return postedUsers.size();
    }

}
