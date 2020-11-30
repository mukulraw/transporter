package com.app.onnwaytransporter.driver.drivertablayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.onnwaytransporter.OrderStatus.AssignTruck;
import com.app.onnwaytransporter.OrderStatus.UploadPOD;
import com.app.onnwaytransporter.driver.driver.past.order.recycler.view.PastRecyclerAdapter;
import com.app.onnwaytransporter.driver.driver.past.order.recycler.view.SamplePastUsers;
import com.app.onnwaytransporter.driver.drivernetworking.PostDriverData;
import com.app.onnwaytransporter.FetchDriverLocation;
import com.app.onnwaytransporter.preferences.SaveSharedPreference;
import com.app.onnwaytransporter.R;

public class DriverPastFragment extends Fragment
{
    View view;
    PastRecyclerAdapter pastRecyclerAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    public static String bIdDriver;

    String up[]={"hello","hello"};
    RecyclerView recyclerView;

    static Activity activity;

    public static BottomSheetBehavior bottomSheetBehavior;

    public static TextView total_amount_tv_driver, order_status_tv_driver, truck_number_tv_driver, delivery_address_tv_driver;
    public static Button order_status_btn_driver;

    public DriverPastFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.past_order_fragment_driver,container,false);
        recyclerView = view.findViewById(R.id.recycler_view_past_order_driver);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh_layout_past_driver);

        activity = (Activity) view.getContext();

        total_amount_tv_driver = view.findViewById(R.id.total_amount_tv);
        order_status_tv_driver = view.findViewById(R.id.order_status_tv);
        truck_number_tv_driver = view.findViewById(R.id.order_truck_number);
        delivery_address_tv_driver = view.findViewById(R.id.order_deleivery_address);
        order_status_btn_driver = view.findViewById(R.id.order_status_button);

        LinearLayout linearLayout = view.findViewById(R.id.hello_bottom);

        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        //btnBottomSheet.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        //btnBottomSheet.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        if(SaveSharedPreference.getCounterPostedStatus(getContext()).equals("0")) {
            Toast.makeText(getContext(), "hiiiiiii", Toast.LENGTH_SHORT).show();
            new PostDriverData().doPastDriver(getContext(),recyclerView);
        }
        else {

            if(SamplePastUsers.samplepastuser== null) {
                new PostDriverData().doPastDriver(getContext(),recyclerView);
            }else
            {
                setRecyclerBid();
            }
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new PostDriverData().doPastDriver(getContext(),recyclerView);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    public void setRecyclerBid() {
        pastRecyclerAdapter = new PastRecyclerAdapter(SamplePastUsers.samplepastuser);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(pastRecyclerAdapter);
    }

    public static void enableBottomSheet(final String bid, String totalAmount, String orderStatus, String truckNumber, String deliveryAddress, String orderStatusButton) {

        total_amount_tv_driver.setText(totalAmount);
        delivery_address_tv_driver.setText(deliveryAddress);

        if (orderStatus.equals("0")) {
            truck_number_tv_driver.setText("Not Assigned");
            order_status_tv_driver.setText("Truck not assigned");
            order_status_btn_driver.setText("Add Truck");
            order_status_btn_driver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bIdDriver = bid;
                    activity.startActivity(new Intent(activity, AssignTruck.class));
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            });
        } else if (orderStatus.equals("1")) {
            truck_number_tv_driver.setText(truckNumber);
            order_status_tv_driver.setText("Waiting for Shipment");
            order_status_btn_driver.setText("Close");
            order_status_btn_driver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            });
        } else if (orderStatus.equals("2")) {
            truck_number_tv_driver.setText(truckNumber);
            order_status_tv_driver.setText("Order on the way");
            order_status_btn_driver.setText("Track Order");
            order_status_btn_driver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    activity.startActivity(new Intent(activity, FetchDriverLocation.class));
                }
            });
        } else if (orderStatus.equals("3")) {
            truck_number_tv_driver.setText(truckNumber);
            order_status_tv_driver.setText("Order delivered");
            order_status_btn_driver.setText("Close");
            order_status_btn_driver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            });
        } else if (orderStatus.equals("4")) {
            truck_number_tv_driver.setText(truckNumber);
            order_status_tv_driver.setText("POD pending");
            order_status_btn_driver.setText("Upload POD");
            order_status_btn_driver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    activity.startActivity(new Intent(activity, UploadPOD.class));
                }
            });
        } else if (orderStatus.equals("5")) {
            truck_number_tv_driver.setText(truckNumber);
            order_status_tv_driver.setText("POD done");
            order_status_btn_driver.setText("Close");
            order_status_btn_driver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            });
        }
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
