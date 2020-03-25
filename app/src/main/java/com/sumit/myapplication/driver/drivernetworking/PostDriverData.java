package com.sumit.myapplication.driver.drivernetworking;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sumit.myapplication.driver.DriverMainActivity;
import com.sumit.myapplication.driver.DriverOngoingRecyclerView.DriverSampleUpcomingUsers;
import com.sumit.myapplication.driver.DriverOngoingRecyclerView.DriverUpcomingRecyclerAdapter;
import com.sumit.myapplication.driver.DriverOngoingRecyclerView.DriverUpcomingUsers;
import com.sumit.myapplication.driver.driver.past.order.recycler.view.PastRecyclerAdapter;
import com.sumit.myapplication.driver.driver.past.order.recycler.view.PastUsers;
import com.sumit.myapplication.driver.driver.past.order.recycler.view.SamplePastUsers;
import com.sumit.myapplication.driver.ongoingorderdriverlocation.OngoingOrderDriverLocation;
import com.sumit.myapplication.driver.profilerelated.DriverOperatedRoutesRecyclerView.AddRoutesDataDetails;
import com.sumit.myapplication.driver.profilerelated.DriverOperatedRoutesRecyclerView.DriverOperatedRoutesRecyclerAdapter;
import com.sumit.myapplication.driver.profilerelated.DriverOperatedRoutesRecyclerView.DriverOperatedRoutesUser;
import com.sumit.myapplication.driver.profilerelated.DriverOperatedRoutesRecyclerView.SampleOperatedRoutesDriver;
import com.sumit.myapplication.driver.profilerelated.DriverTruckDetailsRecyclerView.AddTruckDetailsUser;
import com.sumit.myapplication.driver.profilerelated.DriverTruckDetailsRecyclerView.SampleTruckDetails;
import com.sumit.myapplication.driver.profilerelated.DriverTruckDetailsRecyclerView.TruckDetailsRecyclerAdapter;
import com.sumit.myapplication.driver.profilerelated.DriverTruckDetailsRecyclerView.TruckDetailsUser;
import com.sumit.myapplication.driver.profilerelated.changecity.ChangeCityDetails;
import com.sumit.myapplication.driver.profilerelated.changename.ChangeNameDetails;
import com.sumit.myapplication.driver.profilerelated.changephone.ChangePhoneDetails;
import com.sumit.myapplication.MainActivity;
import com.sumit.myapplication.networking.AppController;
import com.sumit.myapplication.networking.CustomRequest;
import com.sumit.myapplication.preferences.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostDriverData {

    public static String responseMsg;
    public String arrupComingDateDriver[], arrupComingTypeDriver[],
            arrupComingStartLocDriver[], arrupComingEndLocDriver[],
            arrupComingMaterialTypeDriver[], arrupComingWeightDriver[],
            arrupComingTotalAmountDriver[], arrupComingDueAmountDriver[],
            arrupcomingStatusDriver[], arrupComingTruckNumberDriver[],
            arrupComingDeliveryAddressDriver[], arrupComingIdDriver[];

    public String arrPastDateDriver[], arrPastTypeDriver[],
            arrPastStartLocDriver[], arrPastEndLocDriver[],
            arrPastMaterialTypeDriver[], arrPastWeightDriver[],
            arrPastTotalAmountDriver[], arrPastDueAmountDriver[],
            arrPastStatusDriver[], arrPastTruckNumberDriver[],
            arrPastDeliveryAddressDriver[], arrPastIdDriver[];

    public String arrOperatedRouteSourceDriver[],arrOperatedRouteDestinationdriver[];

    public String arrTruckType[],arrRegistrationNumber[],arrDriverName[],arrDriverNumber[];

    private ProgressDialog progressDialog;

    public void doPostOngoingOrderDriverLoc(final Context context, OngoingOrderDriverLocation ongoingOrderDriverLocation) {


        String url = "https://www.onnway.com/android/send_location.php";

        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(ongoingOrderDriverLocation.bookingId));
        params.put("lat", String.valueOf(ongoingOrderDriverLocation.driverLatitude));
        params.put("lng", String.valueOf(ongoingOrderDriverLocation.driverLongitude));

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                responseMsg = response.toString();
//                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
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

    public void doPostChangeName(final Context context, ChangeNameDetails changeNameDetails) {

        String url = "https://www.onnway.com/android/requestnamedriver.php";

        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", changeNameDetails.driverMobile);
        params.put("change_name", changeNameDetails.driverChangedName);

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

    public void doPostChangePhone(final Context context, ChangePhoneDetails changePhoneDetails) {

        String url = "https://www.onnway.com/android/requestmobiledriver.php";

        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", changePhoneDetails.driverMobile);
        params.put("change_mobile", changePhoneDetails.driverChangedPhone);

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

    public void doPostChangeCity(final Context context, ChangeCityDetails changeCityDetails) {

        String url = "https://www.onnway.com/android/requestcitydriver.php";

        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", changeCityDetails.driverMobile);
        params.put("change_city", changeCityDetails.driverChangedCity);

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

    //upcoming order driver
    public void doUpcomingDriver(final Context context,final RecyclerView recyclerViewDriver) {
        String url = "https://www.onnway.com/android/ongoingdriverorder.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile_no", DriverMainActivity.currenntMobileActive);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());

                try {

                    int count = 0;
                    JSONArray jsonArray=response.getJSONArray("devices");
                    int len=jsonArray.length();

                    //Toast.makeText(context, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
                    arrupComingIdDriver = new String[jsonArray.length()];
                    arrupComingDateDriver = new String[jsonArray.length()];
                    arrupComingTypeDriver = new String[jsonArray.length()];
                    arrupComingStartLocDriver = new String[jsonArray.length()];
                    arrupComingEndLocDriver = new String[jsonArray.length()];
                    arrupComingMaterialTypeDriver = new String[jsonArray.length()];
                    arrupComingWeightDriver = new String[jsonArray.length()];
                    arrupComingTotalAmountDriver = new String[jsonArray.length()];
                    arrupComingDueAmountDriver = new String[jsonArray.length()];
                    arrupcomingStatusDriver = new String[jsonArray.length()];
                    arrupComingTruckNumberDriver = new String[jsonArray.length()];
                    arrupComingDeliveryAddressDriver = new String[jsonArray.length()];
                    while (count < jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        arrupComingIdDriver[count] = jsonObject.getString("id");//
                        arrupComingDateDriver[count] = jsonObject.getString("sch_date");//
                        arrupComingTypeDriver[count] = jsonObject.getString("trucktype");//
                        arrupComingStartLocDriver[count] = jsonObject.getString("source");//
                        arrupComingEndLocDriver[count] = jsonObject.getString("destination");//
                        arrupComingMaterialTypeDriver[count] = jsonObject.getString("material_type");//
                        arrupComingWeightDriver[count] = jsonObject.getString("weight");//
                        arrupComingTotalAmountDriver[count] = jsonObject.getString("price");//
                        arrupComingDueAmountDriver[count] = jsonObject.getString("money_paid");//
                        arrupcomingStatusDriver[count] = jsonObject.getString("status");//
                        arrupComingTruckNumberDriver[count] = jsonObject.getString("truck_number");//
                        arrupComingDeliveryAddressDriver[count] = jsonObject.getString("drop_street");//
                        count++;
                    }

                    DriverSampleUpcomingUsers.sampleupcominguserdriver=new ArrayList<>();
                    for(int i = 0; i < arrupComingDueAmountDriver.length; ++i) {
                        DriverUpcomingUsers upcomingUsers = new DriverUpcomingUsers();
                        upcomingUsers.upComingIdDriver = arrupComingIdDriver[i];
                        upcomingUsers.upComingDateDriver = arrupComingDateDriver[i];
                        upcomingUsers.upComingTypeDriver = arrupComingTypeDriver[i];
                        upcomingUsers.upComingStartLocDriver = arrupComingStartLocDriver[i];
                        upcomingUsers.upComingEndLocDriver = arrupComingEndLocDriver[i];
                        upcomingUsers.upComingMaterialTypeDriver = arrupComingMaterialTypeDriver[i];
                        upcomingUsers.upComingWeightDriver = arrupComingWeightDriver[i];
                        upcomingUsers.upComingTotalAmountDriver = arrupComingTotalAmountDriver[i];
                        upcomingUsers.upComingDueAmountDriver = arrupComingDueAmountDriver[i];
                        upcomingUsers.upComingStatusDriver = arrupcomingStatusDriver[i];
                        upcomingUsers.upComingTruckNumberDriver = arrupComingTruckNumberDriver[i];
                        upcomingUsers.upComingDeliveryAddressDriver = arrupComingDeliveryAddressDriver[i];
                        DriverSampleUpcomingUsers.sampleupcominguserdriver.add(upcomingUsers);
                    }

                    // Toast.makeText(context, ""+arrpostedTruckDate[0]+arrpostedTruckDate[1], Toast.LENGTH_SHORT).show();
                    DriverUpcomingRecyclerAdapter driverUpcomingRecyclerAdapter =new DriverUpcomingRecyclerAdapter(DriverSampleUpcomingUsers.sampleupcominguserdriver);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
                    recyclerViewDriver.setLayoutManager(linearLayoutManager);
                    recyclerViewDriver.setHasFixedSize(true);
                    recyclerViewDriver.setAdapter(driverUpcomingRecyclerAdapter);
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

    //past order driver
    public void doPastDriver(final Context context,final RecyclerView recyclerView) {
        String url = "https://www.onnway.com/android/pastdriverorder.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile_no", DriverMainActivity.currenntMobileActive);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());

                try {

                    int count = 0;
                    JSONArray jsonArray = response.getJSONArray("devices");
                    int len=jsonArray.length();

                    //Toast.makeText(context, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
                    arrPastIdDriver = new String[jsonArray.length()];
                    arrPastDateDriver = new String[jsonArray.length()];
                    arrPastTypeDriver = new String[jsonArray.length()];
                    arrPastStartLocDriver = new String[jsonArray.length()];
                    arrPastEndLocDriver = new String[jsonArray.length()];
                    arrPastMaterialTypeDriver = new String[jsonArray.length()];
                    arrPastWeightDriver = new String[jsonArray.length()];
                    arrPastTotalAmountDriver = new String[jsonArray.length()];
                    arrPastDueAmountDriver = new String[jsonArray.length()];
                    arrPastStatusDriver = new String[jsonArray.length()];
                    arrPastTruckNumberDriver = new String[jsonArray.length()];
                    arrPastDeliveryAddressDriver = new String[jsonArray.length()];
                    while (count < jsonArray.length()) {

                        JSONObject jsonObject=jsonArray.getJSONObject(count);
                        arrPastIdDriver[count] = jsonObject.getString("id");//
                        arrPastDateDriver[count] = jsonObject.getString("sch_date");//
                        arrPastTypeDriver[count] = jsonObject.getString("trucktype");//
                        arrPastStartLocDriver[count] = jsonObject.getString("source");//
                        arrPastEndLocDriver[count] = jsonObject.getString("destination");//
                        arrPastMaterialTypeDriver[count] = jsonObject.getString("material_type");//
                        arrPastWeightDriver[count] = jsonObject.getString("weight");//
                        arrPastTotalAmountDriver[count] = jsonObject.getString("price");//
                        arrPastDueAmountDriver[count] = jsonObject.getString("money_paid");//
                        arrPastStatusDriver[count] = jsonObject.getString("status");//
                        arrPastTruckNumberDriver[count] = jsonObject.getString("truck_number");//
                        arrPastDeliveryAddressDriver[count] = jsonObject.getString("drop_street");//
                        count++;
                    }

                    SamplePastUsers.samplepastuser =new ArrayList<>();
                    for(int i = 0;i < arrPastDueAmountDriver.length; i++) {
                        PastUsers pastUsers = new PastUsers();
                        pastUsers.pastIdDriver = arrPastIdDriver[i];
                        pastUsers.pastDateDriver = arrPastDateDriver[i];
                        pastUsers.pastTypeDriver = arrPastTypeDriver[i];
                        pastUsers.pastStartLocDriver = arrPastStartLocDriver[i];
                        pastUsers.pastEndLocDriver = arrPastEndLocDriver[i];
                        pastUsers.pastMaterialTypeDriver = arrPastMaterialTypeDriver[i];
                        pastUsers.pastWeightDriver = arrPastWeightDriver[i];
                        pastUsers.pastTotalAmountDriver = arrPastTotalAmountDriver[i];
                        pastUsers.pastDueAmountDriver = arrPastDueAmountDriver[i];
                        pastUsers.pastStatusDriver = arrPastStatusDriver[i];
                        pastUsers.pastTruckNumberDriver = arrPastTruckNumberDriver[i];
                        pastUsers.pastDeliveryAddressDriver = arrPastDeliveryAddressDriver[i];

                        SamplePastUsers.samplepastuser.add(pastUsers);
                    }

                    // Toast.makeText(context, ""+arrpostedTruckDate[0]+arrpostedTruckDate[1], Toast.LENGTH_SHORT).show();
                    PastRecyclerAdapter pastRecyclerAdapter =new PastRecyclerAdapter(SamplePastUsers.samplepastuser);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(pastRecyclerAdapter);
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
    //fetch already added routes

    public void doOperatedRoutes(final Context context,final RecyclerView recyclerViewOperatedRoute) {
        String url = "https://www.onnway.com/android/fetch_driver_source_des.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile_no", DriverMainActivity.currenntMobileActive);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());

                try {

                    int count = 0;
                    JSONArray jsonArray=response.getJSONArray("devices");

                    //Toast.makeText(context, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
                    arrOperatedRouteSourceDriver= new String[jsonArray.length()];
                    arrOperatedRouteDestinationdriver = new String[jsonArray.length()];
                    while (count < jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        arrOperatedRouteSourceDriver[count] = jsonObject.getString("source");//
                        arrOperatedRouteDestinationdriver[count] = jsonObject.getString("destination");//
                        count++;
                    }

                    SampleOperatedRoutesDriver.sampleOperatedRoutes=new ArrayList<>();
                    for(int i = 0; i < arrOperatedRouteDestinationdriver.length; ++i) {
                        //Toast.makeText(context, "hilolll", Toast.LENGTH_SHORT).show();

                        DriverOperatedRoutesUser driverOperatedRoutesUser = new DriverOperatedRoutesUser();
                        driverOperatedRoutesUser.operatedRouteSourceDriver= arrOperatedRouteSourceDriver[i];
                        driverOperatedRoutesUser.operatedRouteDestinationDriver = arrOperatedRouteDestinationdriver[i];
                        SampleOperatedRoutesDriver.sampleOperatedRoutes.add(driverOperatedRoutesUser);
                    }

                    // Toast.makeText(context, ""+arrpostedTruckDate[0]+arrpostedTruckDate[1], Toast.LENGTH_SHORT).show();
                    DriverOperatedRoutesRecyclerAdapter driverOperatedRoutesRecyclerAdapter=new DriverOperatedRoutesRecyclerAdapter(SampleOperatedRoutesDriver.sampleOperatedRoutes,context);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
                    recyclerViewOperatedRoute.setLayoutManager(linearLayoutManager);
                    recyclerViewOperatedRoute.setHasFixedSize(true);
                    recyclerViewOperatedRoute.setAdapter(driverOperatedRoutesRecyclerAdapter);
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
    public void doAddRoutesDriver(final Context context, AddRoutesDataDetails addRoutesDataDetails) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Adding Routes");
        progressDialog.setMessage("Please wait, while we add route");
        progressDialog.show();
        progressDialog.setCancelable(false);

        String url = "https://www.onnway.com/android/driver_source_des.php";

        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", addRoutesDataDetails.mobile_no);
        //params.put("type", addRoutesDataDetails.type);
        params.put("source", addRoutesDataDetails.source);
        params.put("destination", addRoutesDataDetails.destination);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
//               progressBar.setVisibility(View.INVISIBLE);
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
    //fetch truck details  driver
    public void doDriverDetailsData(final Context context,final RecyclerView recyclerView) {
        String url = "https://www.onnway.com/android/mytrucksprovider.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile_no", MainActivity.currenntMobileActive);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());

                try {

                    int count = 0;
                    JSONArray jsonArray=response.getJSONArray("devices");

                    //Toast.makeText(context, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
                    arrTruckType= new String[jsonArray.length()];
                    arrRegistrationNumber = new String[jsonArray.length()];
                    arrDriverName= new String[jsonArray.length()];
                    arrDriverNumber= new String[jsonArray.length()];
                    while (count < jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        arrTruckType[count] = jsonObject.getString("trucktype");
                        arrRegistrationNumber[count] = jsonObject.getString("truck_reg_no");
                        arrDriverName[count] = jsonObject.getString("driver_name");//
                        arrDriverNumber[count] = jsonObject.getString("driver_mobile_no");//
                        count++;
                    }

                    SampleTruckDetails.sampleTruckDetails=new ArrayList<>();
                    for(int i = 0; i < arrDriverNumber.length; ++i) {
                        TruckDetailsUser truckDetailsUser = new TruckDetailsUser();
                        truckDetailsUser.truckType= arrTruckType[i];
                        truckDetailsUser.registrationNumber= arrRegistrationNumber[i];
                        truckDetailsUser.driverName= arrDriverName[i];
                        truckDetailsUser.driverNumber= arrDriverNumber[i];
                        SampleTruckDetails.sampleTruckDetails.add(truckDetailsUser);
                    }

                    // Toast.makeText(context, ""+arrpostedTruckDate[0]+arrpostedTruckDate[1], Toast.LENGTH_SHORT).show();
                    TruckDetailsRecyclerAdapter truckDetailsRecyclerAdapter=new TruckDetailsRecyclerAdapter(SampleTruckDetails.sampleTruckDetails);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(truckDetailsRecyclerAdapter);
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

    //add new truck details
    public void doPostTruckDetails(final Context context, AddTruckDetailsUser addTruckDetailsUser) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Adding Truck details");
        progressDialog.setMessage("Please wait, while we add truck details");
        progressDialog.show();
        progressDialog.setCancelable(false);

        String url = "https://www.onnway.com/android/store_trucks_provider.php";

        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", addTruckDetailsUser.mobile_no);
        //params.put("type", addTruckDetailsUser.type);
        params.put("trucktype", addTruckDetailsUser.truckType);
        params.put("truck_reg_no", addTruckDetailsUser.reg_no);
        params.put("driver_name", addTruckDetailsUser.driverName);
        params.put("driver_mobile_no", addTruckDetailsUser.driverNumber);


        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
//               progressBar.setVisibility(View.INVISIBLE);
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
