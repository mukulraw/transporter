package com.mukul.onnwaytransporter.tablayout;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mukul.onnwaytransporter.networking.Post;
import com.mukul.onnwaytransporter.recyclerview.PastOrderRecyclerAdapter;
import com.mukul.onnwaytransporter.recyclerview.SamplePastOrder;
import com.mukul.onnwaytransporter.preferences.SaveSharedPreference;
import com.mukul.onnwaytransporter.R;

public class PastFragment extends Fragment
{
    View view;
    PastOrderRecyclerAdapter pastOrderRecyclerAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    String up[]={"hello","hello"};
    private RecyclerView recyclerView;

    public static String bId;

    static Activity activity;

    public PastFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.past_order_fragment,container,false);

        recyclerView = view.findViewById(R.id.recycler_view_past_order);

        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh_layout_past);

        activity = (Activity) view.getContext();

        if(SaveSharedPreference.getCounterPostedStatus(getContext()).equals("0")) {
            Toast.makeText(getContext(), "1st block", Toast.LENGTH_SHORT).show();
            new Post().doPast(getContext(),recyclerView);
        }
        else {
            if(SamplePastOrder.pastOrders == null) {
                Toast.makeText(getContext(), "2nd block", Toast.LENGTH_SHORT).show();
                new Post().doPast(getContext(),recyclerView);
            }else
            {
                Toast.makeText(getContext(), "3rd block", Toast.LENGTH_SHORT).show();
                setRecyclerBid();
            }
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Post().doPast(getContext(),recyclerView);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    public void setRecyclerBid()
    {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        pastOrderRecyclerAdapter = new PastOrderRecyclerAdapter(SamplePastOrder.pastOrders);
        recyclerView.setAdapter(pastOrderRecyclerAdapter);
    }

    public static void enableBottomSheet(final String bid, String totalAmount, String orderStatus, String truckNumber, String deliveryAddress, String orderStatusButton) {
        bId = bid;
    }
}

