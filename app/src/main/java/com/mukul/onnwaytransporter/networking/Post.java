package com.mukul.onnwaytransporter.networking;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mukul.onnwaytransporter.OrderStatus.AssignTruckData;
import com.mukul.onnwaytransporter.driver.DriverMainActivity;
import com.mukul.onnwaytransporter.FetchDriverData;
import com.mukul.onnwaytransporter.MainActivity;
import com.mukul.onnwaytransporter.ProviderData;
import com.mukul.onnwaytransporter.profilerelated.OperatedRoutesSampleUser.AddRoutesdataDetailsProvider;
import com.mukul.onnwaytransporter.profilerelated.OperatedRoutesSampleUser.SampleOperatedRoutesProvider;
import com.mukul.onnwaytransporter.profilerelated.OperatedRoutesUserProvider;
import com.mukul.onnwaytransporter.profilerelated.RecyclerAdapterRoutesProvider;
import com.mukul.onnwaytransporter.profilerelated.changeprovidercity.ChangeProviderCityDetails;
import com.mukul.onnwaytransporter.profilerelated.changeprovidermobile.ChangeProviderMobileDetails;
import com.mukul.onnwaytransporter.profilerelated.changeprovidername.ChangeProviderNameDetails;
import com.mukul.onnwaytransporter.providerpartload.PartLoadDataDetails;
import com.mukul.onnwaytransporter.otp.CheckingPreRegistered;
import com.mukul.onnwaytransporter.recyclerview.PastOrder;
import com.mukul.onnwaytransporter.recyclerview.PastOrderRecyclerAdapter;
import com.mukul.onnwaytransporter.recyclerview.SamplePastOrder;
import com.mukul.onnwaytransporter.recyclerview.PostedTruckRecyclerAdapter;
import com.mukul.onnwaytransporter.recyclerview.PostedUser;
import com.mukul.onnwaytransporter.recyclerview.SamplePostedTruck;
import com.mukul.onnwaytransporter.preferences.SaveSharedPreference;
import com.mukul.onnwaytransporter.splash.SplashActivity;
import com.mukul.onnwaytransporter.recyclerview.SampleUpcomingUsers;
import com.mukul.onnwaytransporter.recyclerview.UpcomingRecyclerAdapter;
import com.mukul.onnwaytransporter.recyclerview.UpcomingUsers;
import com.mukul.onnwaytransporter.PostTruckDataDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Post {


    public static String getLat, getLng;
    public static Integer done = 0;

    //user data
    public static String userName;
    public static String transportName;
    public static String cityName;
    public static String userType;
    public static String mobileNumber;

    //posted truck
    String arrpostedTruckDate[];
    String arrpostedTrucktype[];
    String arrpostedTruckStartLoc[];
    String arrpostedTruckEndLoc[];
    PostedTruckRecyclerAdapter postedTruckRecyclerAdapter;

   //upcoming fragment
   public String arrupComingDate[], arrupComingtype[],
           arrupComingStartLoc[], arrupComingEndLoc[],
           arrmaterialType[], arrupComingWeight[],
           arrupComingTotalAmount[], arrupComingDueAmount[],
           arrupcomingStatus[], arrupComingTruckNumber[],
           arrupComingDeliveryAddress[], arrupComingId[];

    public String arrPastDate[], arrPasttype[],
            arrPastStartLoc[], arrPastEndLoc[],
            arrPastmaterialType[], arrPastWeight[],
            arrPastTotalAmount[], arrPastDueAmount[],
            arrPastStatus[], arrPastTruckNumber[],
            arrPastDeliveryAddress[], arrPastId[];

    public String arrOperatedRouteSourceProvider[],arrOperatedRouteDestinationProvider[];

    private ProgressDialog progressDialog;

    public void doPost(final Context context, String mPhoneNo) {
        String url = "https://www.onnway.com/android/otpverification.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile_no", mPhoneNo);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    public void createUser(final Context context, ProviderData providerData) {

        String url = "https://www.onnway.com/android/profile-loader.php";

        Toast.makeText(context, "In post1", Toast.LENGTH_LONG).show();
        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", providerData.mobile_number);
        params.put("name", providerData.name);
        params.put("type", providerData.type);
        params.put("transport_name", providerData.transport_name);
        params.put("city", providerData.city);
        Toast.makeText(context, "In post2", Toast.LENGTH_LONG).show();

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
//               progressBar.setVisibility(View.INVISIBLE);
//                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    public void getIfUserRegistered(final Context context, CheckingPreRegistered checkingPreRegistered) {

        String url = "https://www.onnway.com/android/profile_check_login.php";
        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", checkingPreRegistered.entered_mobile);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray userDetails = response.getJSONArray("users");


                    for (int i = 0; i < 1; i++) {

                        JSONObject details = userDetails.getJSONObject(i);

                        userName = details.getString("name");
                        transportName = details.getString("transport_name");
                        cityName = details.getString("city");
                        userType = details.getString("type");
                        mobileNumber = details.getString("mobile_no");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    public void doPostGetDriverLoc(final Context context, FetchDriverData fetchDriverData) {
        String url = "https://www.onnway.com/android/fetching_location.php";
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(fetchDriverData.bId));

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray values = response.getJSONArray("devices");
                    for (int i = 0; i < 1; i++) {

                        JSONObject location = values.getJSONObject(i);

                        getLat = location.getString("lat");
                        getLng = location.getString("lng");
                        done = 1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    public void doPostDriverDetails(final Context context, AssignTruckData assignTruckData) {
        String url = "https://www.onnway.com/android/storedriverandtruckno.php";
        Map<String, String> params = new HashMap<>();
        params.put("id", assignTruckData.bookingId);
        params.put("truck_no", assignTruckData.truckNumber);
        params.put("driver_mobile_no", assignTruckData.driverMobNumber);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
//               progressBar.setVisibility(View.INVISIBLE);
//                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
    public void doPostPostTruc(final Context context, PostTruckDataDetails postTruckDataDetails) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Posting Truck");
        progressDialog.setMessage("Please wait, while we post the truck for you");
//        progressDialog.show();
        progressDialog.setCancelable(false);

        String url = "https://www.onnway.com/android/post-truck.php";

        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", postTruckDataDetails.mobile_no);
        params.put("type", postTruckDataDetails.type);
        params.put("source", postTruckDataDetails.source);
        params.put("destination", postTruckDataDetails.destination);
        params.put("vehicle_type", postTruckDataDetails.vehicle_type);
        params.put("sch_date", postTruckDataDetails.sch_date);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

               Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
//               progressBar.setVisibility(View.INVISIBLE);
//                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }



    //post setbid details
    public void doPostSetBidDetails(final Context context, Map<String, String> params) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Submitting Bid!");
        progressDialog.setMessage("Please wait, while we are submitting the Bid!");
        progressDialog.show();
        progressDialog.setCancelable(false);

        String url = "https://www.onnway.com/android/storingproviderbids.php";
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                ((Activity)context).finish();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

//provider posted truck
    public void doPostPostedTruck(final Context context,final Map<String, String> params, final RecyclerView recyclerView, final SwipeRefreshLayout swipeRefreshLayout) {


        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Submitting!");
        progressDialog.setMessage("Please wait, while we are submitting the Bid!");
       // progressDialog.show();
        progressDialog.setCancelable(false);

        String url = "https://www.onnway.com/android/mypostedtruckprovider.php";
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();



                try {

                    int count = 0;

                    JSONArray jsonArray=response.getJSONArray("devices");
                    int len=jsonArray.length();
                    //Toast.makeText(context, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
                    arrpostedTrucktype=new String[jsonArray.length()];
                    arrpostedTruckDate=new String[jsonArray.length()];
                    arrpostedTruckStartLoc=new String[jsonArray.length()];
                    arrpostedTruckEndLoc= new String[jsonArray.length()];
                    while (count < jsonArray.length()) {

                        JSONObject jsonObject=jsonArray.getJSONObject(count);
                        arrpostedTrucktype[count]=jsonObject.getString("trucktype");
                        arrpostedTruckDate[count]=jsonObject.getString("sch_date");
                        arrpostedTruckStartLoc[count]=jsonObject.getString("source");
                        arrpostedTruckEndLoc[count]=jsonObject.getString("destination");
                        count++;
                    }

                    SamplePostedTruck.sampleposteduser=new ArrayList<>();
                    SamplePostedTruck.sampleposteduser=new ArrayList<>();
                    for(int i=0;i<arrpostedTruckEndLoc.length;i++)
                    {
                        PostedUser postedUser=new PostedUser();
                        postedUser.postedTruckDate=arrpostedTruckDate[i];
                        postedUser.postedTruckType=arrpostedTrucktype[i];
                        postedUser.postedTruckStartLoc=arrpostedTruckStartLoc[i];
                        postedUser.postedTruckEndLoc=arrpostedTruckEndLoc[i];
                        SamplePostedTruck.sampleposteduser.add(postedUser);
                    }

                   // Toast.makeText(context, ""+arrpostedTruckDate[0]+arrpostedTruckDate[1], Toast.LENGTH_SHORT).show();
                    postedTruckRecyclerAdapter=new PostedTruckRecyclerAdapter(SamplePostedTruck.sampleposteduser,context);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(postedTruckRecyclerAdapter);
                    //SaveSharedPreference.setCounterPosted(context, "1");
                   // Toast.makeText(context, ""+SaveSharedPreference.getCounterPostedStatus(context), Toast.LENGTH_SHORT).show();
                    SaveSharedPreference.setCounterPosted(context, "1");
                }
                catch (Exception ex)
                {

                }

               // ((Activity)context).finish();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    //upcoming

    public void doUpcoming(final Context context,final RecyclerView recyclerView) {
        String url = "https://www.onnway.com/android/ongoingorderprovider.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile_no", MainActivity.currenntMobileActive);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());

                try {

                    int count = 0;
                    JSONArray jsonArray = response.getJSONArray("devices");
                    int len = jsonArray.length();
                    //Toast.makeText(context, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
                    arrupComingId = new String[jsonArray.length()];
                    arrupComingDate = new String[jsonArray.length()];
                    arrupComingtype = new String[jsonArray.length()];
                    arrupComingStartLoc = new String[jsonArray.length()];
                    arrupComingEndLoc = new String[jsonArray.length()];
                    arrmaterialType = new String[jsonArray.length()];
                    arrupComingWeight = new String[jsonArray.length()];
                    arrupComingTotalAmount = new String[jsonArray.length()];
                    arrupComingDueAmount = new String[jsonArray.length()];
                    arrupcomingStatus = new String[jsonArray.length()];
                    arrupComingTruckNumber = new String[jsonArray.length()];
                    arrupComingDeliveryAddress = new String[jsonArray.length()];
                    while (count < jsonArray.length()) {

                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        arrupComingId[count] = jsonObject.getString("id");
                        arrupComingDate[count] = jsonObject.getString("sch_date");
                        arrupComingtype[count] = jsonObject.getString("trucktype");
                        arrupComingStartLoc[count] = jsonObject.getString("source");
                        arrupComingEndLoc[count] = jsonObject.getString("destination");
                        arrmaterialType[count] = jsonObject.getString("material_type");
                        arrupComingWeight[count] = jsonObject.getString("weight");
                        arrupComingTotalAmount[count] = jsonObject.getString("price");
                        arrupComingDueAmount[count] = jsonObject.getString("money_paid");
                        arrupcomingStatus[count] = jsonObject.getString("status");
                        arrupComingTruckNumber[count] = jsonObject.getString("truck_number");
                        arrupComingDeliveryAddress[count] = jsonObject.getString("drop_street");
                        count++;
                    }

                    SampleUpcomingUsers.sampleupcominguser = new ArrayList<>();
                    for (int i = 0; i < arrupComingDueAmount.length; i++) {
                        UpcomingUsers upcomingUsers = new UpcomingUsers();
                        upcomingUsers.upComingId = arrupComingId[i];
                        upcomingUsers.upComingDate = arrupComingDate[i];
                        upcomingUsers.upComingtype = arrupComingtype[i];
                        upcomingUsers.upComingStartLoc = arrupComingStartLoc[i];
                        upcomingUsers.upComingEndLoc = arrupComingEndLoc[i];

                        upcomingUsers.materialType = arrmaterialType[i];
                        upcomingUsers.upComingWeight = arrupComingWeight[i];
                        upcomingUsers.upComingTotalAmount = arrupComingTotalAmount[i];
                        upcomingUsers.upComingDueAmount = arrupComingDueAmount[i];
                        upcomingUsers.upComingStatus = arrupcomingStatus[i];
                        upcomingUsers.upComingTruckNumber = arrupComingTruckNumber[i];
                        upcomingUsers.upComingDeliveryAddress = arrupComingDeliveryAddress[i];

                        SampleUpcomingUsers.sampleupcominguser.add(upcomingUsers);
                    }

                    // Toast.makeText(context, ""+arrpostedTruckDate[0]+arrpostedTruckDate[1], Toast.LENGTH_SHORT).show();
                    UpcomingRecyclerAdapter upcomingRecyclerAdapter = new UpcomingRecyclerAdapter(SampleUpcomingUsers.sampleupcominguser);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(upcomingRecyclerAdapter);
                    SaveSharedPreference.setCounterUpcoming(context, "1");

                } catch (Exception ex) {

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }


    //past order provider
    public void doPast(final Context context,final RecyclerView recyclerView)
    {
        String url = "https://www.onnway.com/android/pastorderprovider.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile_no", MainActivity.currenntMobileActive);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());

                try {

                    int count = 0;
                    JSONArray jsonArray = response.getJSONArray("devices");
//                    int len=jsonArray.length();
                    //Toast.makeText(context, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
                    arrPastId = new String[jsonArray.length()];
                    arrPastDate = new String[jsonArray.length()];
                    arrPasttype = new String[jsonArray.length()];
                    arrPastStartLoc = new String[jsonArray.length()];
                    arrPastEndLoc = new String[jsonArray.length()];
                    arrPastmaterialType = new String[jsonArray.length()];
                    arrPastWeight = new String[jsonArray.length()];
                    arrPastTotalAmount = new String[jsonArray.length()];
                    arrPastDueAmount = new String[jsonArray.length()];
                    arrPastStatus = new String[jsonArray.length()];
                    arrPastTruckNumber = new String[jsonArray.length()];
                    arrPastDeliveryAddress = new String[jsonArray.length()];
                    while (count < jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        arrPastId[count] = jsonObject.getString("id");
                        arrPastDate[count] = jsonObject.getString("sch_date");//
                        arrPasttype[count] = jsonObject.getString("trucktype");//
                        arrPastStartLoc[count] = jsonObject.getString("source");//
                        arrPastEndLoc[count] = jsonObject.getString("destination");//
                        arrPastmaterialType[count] = jsonObject.getString("material_type");//
                        arrPastWeight[count] = jsonObject.getString("weight");//
                        arrPastTotalAmount[count] = jsonObject.getString("price");//
                        arrPastDueAmount[count] = jsonObject.getString("money_paid");//
                        arrPastStatus[count] = jsonObject.getString("status");
                        arrPastTruckNumber[count] = jsonObject.getString("truck_number");
                        arrPastDeliveryAddress[count] = jsonObject.getString("drop_street");
                        count++;
                    }

                    SamplePastOrder.pastOrders =new ArrayList<>();
//                    SampleUpcomingUsers.sampleupcominguser=new ArrayList<>();
                    for(int i = 0; i < arrPastDueAmount.length; ++i) {
                        PastOrder pastOrder = new PastOrder();
                        pastOrder.pastId = arrPastId[i];
                        pastOrder.pastDate = arrPastDate[i];
                        pastOrder.pastType = arrPasttype[i];
                        pastOrder.pastStartLoc = arrPastStartLoc[i];
                        pastOrder.pastEndLoc = arrPastEndLoc[i];
                        pastOrder.pastMaterialType = arrPastmaterialType[i];
                        pastOrder.pastWeight = arrPastWeight[i];
                        pastOrder.pastTotalAmount = arrPastTotalAmount[i];
                        pastOrder.pastDueAmount = arrPastDueAmount[i];
                        pastOrder.pastStatus = arrPastStatus[i];
                        pastOrder.pastTruckNumber = arrPastTruckNumber[i];
                        pastOrder.pastDeliveryAddress = arrPastDeliveryAddress[i];

                        SamplePastOrder.pastOrders.add(pastOrder);
                    }

                    // Toast.makeText(context, ""+arrpostedTruckDate[0]+arrpostedTruckDate[1], Toast.LENGTH_SHORT).show();
                    PastOrderRecyclerAdapter pastOrderRecyclerAdapter =new PastOrderRecyclerAdapter(SamplePastOrder.pastOrders);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(pastOrderRecyclerAdapter);
                    SaveSharedPreference.setCounterPast(context, "1");
                }
                catch (Exception ex) {

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    //driver posted truck
    public void doPostPostedTruckDriver(final Context context,final Map<String, String> params, final RecyclerView recyclerView, final SwipeRefreshLayout swipeRefreshLayout) {


        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Submitting!");
        progressDialog.setMessage("Please wait, while we are submitting the Bid!");
        // progressDialog.show();
        progressDialog.setCancelable(false);

        String url = "https://www.onnway.com/android/driver_posted_truck.php";
        params.put("mobile_no", DriverMainActivity.currenntMobileActive);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {

                    int count = 0;

                    JSONArray jsonArray=response.getJSONArray("devices");
                    int len=jsonArray.length();
                    //Toast.makeText(context, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
                    arrpostedTrucktype=new String[jsonArray.length()];
                    arrpostedTruckDate=new String[jsonArray.length()];
                    arrpostedTruckStartLoc=new String[jsonArray.length()];
                    arrpostedTruckEndLoc= new String[jsonArray.length()];
                    while (count < jsonArray.length()) {

                        JSONObject jsonObject=jsonArray.getJSONObject(count);
                        arrpostedTrucktype[count]=jsonObject.getString("trucktype");
                        arrpostedTruckDate[count]=jsonObject.getString("sch_date");
                        arrpostedTruckStartLoc[count]=jsonObject.getString("source");
                        arrpostedTruckEndLoc[count]=jsonObject.getString("destination");
                        count++;
                    }

                    SamplePostedTruck.sampleposteduser=new ArrayList<>();
                    SamplePostedTruck.sampleposteduser=new ArrayList<>();
                    for(int i=0;i<arrpostedTruckEndLoc.length;i++) {
                        PostedUser postedUser=new PostedUser();
                        postedUser.postedTruckDate=arrpostedTruckDate[i];
                        postedUser.postedTruckType=arrpostedTrucktype[i];
                        postedUser.postedTruckStartLoc=arrpostedTruckStartLoc[i];
                        postedUser.postedTruckEndLoc=arrpostedTruckEndLoc[i];
                        SamplePostedTruck.sampleposteduser.add(postedUser);
                    }

                    // Toast.makeText(context, ""+arrpostedTruckDate[0]+arrpostedTruckDate[1], Toast.LENGTH_SHORT).show();
                    postedTruckRecyclerAdapter=new PostedTruckRecyclerAdapter(SamplePostedTruck.sampleposteduser,context);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(postedTruckRecyclerAdapter);
                    //SaveSharedPreference.setCounterPosted(context, "1");
                    // Toast.makeText(context, ""+SaveSharedPreference.getCounterPostedStatus(context), Toast.LENGTH_SHORT).show();
                    SaveSharedPreference.setCounterPosted(context, "1");
                }
                catch (Exception ex)
                {

                }

                // ((Activity)context).finish();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
        Intent intent = new Intent(context, DriverMainActivity.class);
        context.startActivity(intent);
    }

    public void doPostChangeName(final Context context, ChangeProviderNameDetails changeProviderNameDetails) {

        String url = "https://www.onnway.com/android/requestnameprovider.php";

        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", changeProviderNameDetails.providerCurrentMobile);
        params.put("change_name", changeProviderNameDetails.providerChangedName);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
//               progressBar.setVisibility(View.INVISIBLE);
//                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    public void doPostChangePhone(final Context context, ChangeProviderMobileDetails changeProviderMobileDetails) {

        String url = "https://www.onnway.com/android/requestmobileprovider.php";

        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", changeProviderMobileDetails.providerCurrentMobile);
        params.put("change_mobile", changeProviderMobileDetails.providerChangedMobile);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
//               progressBar.setVisibility(View.INVISIBLE);
//                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    public void doPostChangeCity(final Context context, ChangeProviderCityDetails changeProviderCityDetails) {

        String url = "https://www.onnway.com/android/requestcityprovider.php";

        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", changeProviderCityDetails.providerCurrentMobile);
        params.put("change_city", changeProviderCityDetails.providerChangedCity);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
//               progressBar.setVisibility(View.INVISIBLE);
//                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }


    public void doPostProviderPartLoad(final Context context, PartLoadDataDetails partLoadDataDetails) {

        String url = "https://www.onnway.com/android/part_posted_truck.php";

        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", partLoadDataDetails.part_mobile_no);
        params.put("type", partLoadDataDetails.part_user_type);
        params.put("source", partLoadDataDetails.part_source_add);
        params.put("vehicle_type", partLoadDataDetails.part_vehicle_type);
        params.put("destination", partLoadDataDetails.part_dest_add);
        params.put("sch_date", partLoadDataDetails.part_sch_date);
        params.put("material_type", partLoadDataDetails.part_material_type);
        params.put("rem_payload", partLoadDataDetails.part_rem_payload);
        params.put("rem_payload_unit", partLoadDataDetails.part_rem_payload_unit);
        params.put("rem_len", partLoadDataDetails.part_rem_len);
        params.put("rem_len_unit", partLoadDataDetails.part_rem_len_unit);
        params.put("rem_wid", partLoadDataDetails.part_rem_wid);
        params.put("rem_wid_unit", partLoadDataDetails.part_rem_wid_unit);
        params.put("rem_hei", partLoadDataDetails.part_rem_hei);
        params.put("rem_hei_unit", partLoadDataDetails.part_rem_hei_unit);
        params.put("box_1", partLoadDataDetails.part_box1);
        params.put("box_2", partLoadDataDetails.part_box2);
        params.put("box_3", partLoadDataDetails.part_box3);
        params.put("box_4", partLoadDataDetails.part_box4);
        params.put("box_5", partLoadDataDetails.part_box5);
        params.put("box_6", partLoadDataDetails.part_box6);
        params.put("box_7", partLoadDataDetails.part_box7);
        params.put("box_8", partLoadDataDetails.part_box8);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
//               progressBar.setVisibility(View.INVISIBLE);
//                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
        if(SplashActivity.currentUserType.equals("2")){
            Intent intent = new Intent(context, DriverMainActivity.class);
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);

        }
    }
//fetch already added routes

    public void doOperatedRoutesProvider(final Context context,final RecyclerView recyclerViewOperatedRoute) {
        String url = "https://www.onnway.com/android/fetch_provider_source_des.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile_no", MainActivity.currenntMobileActive);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());

                try {

                    int count = 0;
                    JSONArray jsonArray=response.getJSONArray("devices");

                    Toast.makeText(context, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
                    arrOperatedRouteSourceProvider= new String[jsonArray.length()];
                    arrOperatedRouteDestinationProvider = new String[jsonArray.length()];
                    while (count < jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        arrOperatedRouteSourceProvider[count] = jsonObject.getString("source");//
                        arrOperatedRouteDestinationProvider[count] = jsonObject.getString("destination");//
                        count++;
                    }

                    SampleOperatedRoutesProvider.sampleOperatedRoutesProviders=new ArrayList<>();
                    for(int i = 0; i < arrOperatedRouteDestinationProvider.length; ++i) {
                        OperatedRoutesUserProvider operatedRoutesUserProvider = new OperatedRoutesUserProvider();
                        operatedRoutesUserProvider.operatedRouteSourceProvider= arrOperatedRouteSourceProvider[i];
                        operatedRoutesUserProvider.operatedRouteDestinationProvider = arrOperatedRouteDestinationProvider[i];
                        SampleOperatedRoutesProvider.sampleOperatedRoutesProviders.add(operatedRoutesUserProvider);
                    }

                    //Toast.makeText(context, ""+arrpostedTruckDate[0]+arrpostedTruckDate[1], Toast.LENGTH_SHORT).show();
                    RecyclerAdapterRoutesProvider recyclerAdapterRoutesProvider=new RecyclerAdapterRoutesProvider(SampleOperatedRoutesProvider.sampleOperatedRoutesProviders,context);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
                    recyclerViewOperatedRoute.setLayoutManager(linearLayoutManager);
                    recyclerViewOperatedRoute.setHasFixedSize(true);
                    recyclerViewOperatedRoute.setAdapter(recyclerAdapterRoutesProvider);
                    SaveSharedPreference.setCounterUpcoming(context, "1");
                }
                catch (Exception ex) {

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }


    //add new operated routes
    public void doAddRoutesProvider(final Context context, AddRoutesdataDetailsProvider addRoutesdataDetailsProvider) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Adding Routes");
        progressDialog.setMessage("Please wait, while we add route");
       // progressDialog.show();
        progressDialog.setCancelable(false);

        String url = "https://www.onnway.com/android/provider_source_des.php";

        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", addRoutesdataDetailsProvider.mobile_no_provider);
        //params.put("type", addRoutesDataDetails.type);
        params.put("source", addRoutesdataDetailsProvider.source_provider);
        params.put("destination", addRoutesdataDetailsProvider.destination_provider);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                //progressBar.setVisibility(View.INVISIBLE);
                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    //delete Operated routes

    public void deleteOperatedRoutes(Context context, OperatedRoutesUserProvider operatedRoutesUserProvider) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Deleting Routes");
        progressDialog.setMessage("Please wait, while we delete route");
        progressDialog.show();
        progressDialog.setCancelable(false);

        String url = "https://www.onnway.com/android/provider_delete_source_des.php";

        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", MainActivity.currenntMobileActive);
        //params.put("type", addRoutesDataDetails.type);
        params.put("source", operatedRoutesUserProvider.operatedRouteSourceProvider);
        params.put("destination", operatedRoutesUserProvider.operatedRouteDestinationProvider);


        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

               // Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                //progressBar.setVisibility(View.INVISIBLE);
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }



}
