package com.sumit.myapplication;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.sumit.myapplication.networking.Post;
import com.sumit.myapplication.recyclerview.PostedTruckRecyclerAdapter;
import com.sumit.myapplication.recyclerview.SamplePostedTruck;
import com.sumit.myapplication.preferences.SaveSharedPreference;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostedTruckFragment extends Fragment {

    public ImageView imageView;


    public static String arrpostedTruckDate[]={"hhhhhhh"};
    public static String arrpostedTrucktype[]={"hhhhhhh"};
    public static String arrpostedTruckStartLoc[]={"hhhhhhh"};
    public static String arrpostedTruckEndLoc[]={"hhhhhhh"};
   public SwipeRefreshLayout swipeRefreshLayout;
    public static RecyclerView recyclerView;
    public static PostedTruckRecyclerAdapter postedTruckRecyclerAdapter;

//    @BindView(R.id.bottom_)
//    LinearLayout layoutBottomSheet;


    public PostedTruckFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_posted_truck, container, false);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh_layout);
        recyclerView=view.findViewById(R.id.recycler_view_posted_truck);
//        imageView=view.findViewById(R.id.imageview_back);

        ButterKnife.bind(view);


        final Map<String,String> map=new HashMap<>();
        String mobile_no= SaveSharedPreference.getPhoneNoStatus(getContext().getApplicationContext());
        map.put("mobile_no",mobile_no);


         //
        if(SaveSharedPreference.getCounterPostedStatus(getContext()).equals("0")) {
            Toast.makeText(getContext(), "hiiiiiii", Toast.LENGTH_SHORT).show();
           new Post().doPostPostedTruck(getContext(),map,recyclerView,swipeRefreshLayout);
        }
        else {

            if(SamplePostedTruck.sampleposteduser== null) {
                new Post().doPostPostedTruck(getContext(),map,recyclerView,swipeRefreshLayout);
            }else
            {
                setRecyclerBid();
            }
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Post().doPostPostedTruck(getContext(),map,recyclerView,swipeRefreshLayout);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return  view;
    }


    public void setRecyclerBid()
    {
        postedTruckRecyclerAdapter=new PostedTruckRecyclerAdapter(SamplePostedTruck.sampleposteduser,getActivity());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(postedTruckRecyclerAdapter);
    }
}
