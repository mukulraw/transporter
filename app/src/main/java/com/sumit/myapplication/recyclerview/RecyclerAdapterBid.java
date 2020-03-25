package com.sumit.myapplication.recyclerview;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sumit.myapplication.biddetails.SetBidDetailsActivity;
import com.sumit.myapplication.MyBidFragment;
import com.sumit.myapplication.R;

import java.util.List;

public class RecyclerAdapterBid extends RecyclerView.Adapter<ViewHolder>
{
    private List<BidUser> bidUsers;

    public static  int quote_id;
    public static String slat, slng, dlat, dlng, src, dest;

    //constructor
    Context context;
    public RecyclerAdapterBid(List<BidUser> bidUsers, Context context) {
        this.bidUsers=bidUsers;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bid_recycler_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        BidUser sampleBidUser= bidUsers.get(i);

        viewHolder.tvTruckType.setText(sampleBidUser.truckType);
        viewHolder.tvDate.setText(sampleBidUser.date);
        viewHolder.tvMaterial.setText(sampleBidUser.material);
        viewHolder.tvSource.setText(sampleBidUser.source);
        viewHolder.tvWeight.setText(sampleBidUser.weight);
        viewHolder.tvDestination.setText(sampleBidUser.destination);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slat = bidUsers.get(i).sourceLat;
                slng = bidUsers.get(i).sourceLng;
                dlat = bidUsers.get(i).destLat;
                dlng = bidUsers.get(i).destLng;
                src = bidUsers.get(i).source;
                dest = bidUsers.get(i).destination;
                Toast.makeText(context, slat + " " + slng + " " + dlat + " " + dlng, Toast.LENGTH_LONG).show();
                Toast.makeText(context, ""+i, Toast.LENGTH_SHORT).show();
                quote_id=i;
                context.startActivity(new Intent(context, SetBidDetailsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return bidUsers.size();
    }

    public void removeItem(int pos)
    {
        bidUsers.remove(pos);
        MyBidFragment.recyclerView.removeViewAt(pos);
        MyBidFragment.recyclerAdapterBid.notifyItemRemoved(pos);
        MyBidFragment.recyclerAdapterBid.notifyItemRangeChanged(pos,bidUsers.size());
        notifyItemRemoved(pos);

    }
}
