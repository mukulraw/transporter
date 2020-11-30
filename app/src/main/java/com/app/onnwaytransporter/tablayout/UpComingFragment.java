package com.app.onnwaytransporter.tablayout;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.onnwaytransporter.networking.Post;
import com.app.onnwaytransporter.preferences.SaveSharedPreference;
import com.app.onnwaytransporter.recyclerview.SampleUpcomingUsers;
import com.app.onnwaytransporter.recyclerview.UpcomingRecyclerAdapter;
import com.app.onnwaytransporter.R;

public class UpComingFragment extends Fragment {
    View view;
    UpcomingRecyclerAdapter upcomingRecyclerAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    public BottomSheetBehavior sheetBehavior;

    String up[]={"hello","hello"};
    RecyclerView recyclerView;
    public static String bId;

    static Activity activity;
    FragmentActivity myContext;

    public static FragmentManager fragmentManager;

    public UpComingFragment() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.upcoming_order_fragment,container,false);
       recyclerView=view.findViewById(R.id.recycler_view_upcoming_order);
       swipeRefreshLayout=view.findViewById(R.id.swipe_refresh_layout);


        activity = (Activity) view.getContext();

        LinearLayout linearLayout = view.findViewById(R.id.hello_bottom);
        sheetBehavior = BottomSheetBehavior.from(linearLayout);

        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        if(SaveSharedPreference.getCounterPostedStatus(getContext()).equals("0")) {
            Toast.makeText(getContext(), "hiiiiiii", Toast.LENGTH_SHORT).show();
            new Post().doUpcoming(getContext(),recyclerView);
        }
        else {

            if(SampleUpcomingUsers.sampleupcominguser== null) {
                new Post().doUpcoming(getContext(),recyclerView);
            }else
            {
                setRecyclerBid();
            }
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Post().doUpcoming(getContext(),recyclerView);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        fragmentManager = getFragmentManager();
        return view;
    }


    public void setRecyclerBid()
    {
        upcomingRecyclerAdapter=new UpcomingRecyclerAdapter(SampleUpcomingUsers.sampleupcominguser);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(upcomingRecyclerAdapter);
    }

    public static void enableBottomSheet(final String bid, String totalAmount, String orderStatus, String truckNumber, String deliveryAddress, String orderStatusButton) {

        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();

        Bundle args = new Bundle();
        args.putString("totalAmt", totalAmount);
        args.putString("delvAdd", deliveryAddress);

        if(orderStatus.equals("0")) {
            args.putString("truckNo", "Not Assigned");
            args.putString("ordrStat", "Truck not assigned");
            args.putString("btn", "Add Truck");
            bId = bid;
        } else if (orderStatus.equals("1")) {
            args.putString("truckNo", truckNumber);
            args.putString("ordrStat", "Waiting for Shipment");
            args.putString("btn", "Close");
        } else if (orderStatus.equals("2")) {
            args.putString("truckNo", truckNumber);
            args.putString("ordrStat", "Order on the way");
            args.putString("btn", "Track Order");
        } else if (orderStatus.equals("3")) {
            args.putString("truckNo", truckNumber);
            args.putString("ordrStat", "Order delivered");
            args.putString("btn", "Close");
        } else if (orderStatus.equals("4")) {
            args.putString("truckNo", truckNumber);
            args.putString("ordrStat", "POD pending");
            args.putString("btn", "Upload POD");
        } else if (orderStatus.equals("5")) {
            args.putString("truckNo", truckNumber);
            args.putString("ordrStat", "POD done");
            args.putString("btn", "Close");
        }
        bottomSheetFragment.setArguments(args);
        bottomSheetFragment.show(fragmentManager, bottomSheetFragment.getTag());

    }
}
