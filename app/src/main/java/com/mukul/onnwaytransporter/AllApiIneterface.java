package com.mukul.onnwaytransporter;


import com.mukul.onnwaytransporter.bidDetailsPOJO.bidDetailsBean;
import com.mukul.onnwaytransporter.bidsPOJO.bidsBean;
import com.mukul.onnwaytransporter.confirm_full_POJO.confirm_full_bean;
import com.mukul.onnwaytransporter.myTrucksPOJO.myTrucksBean;
import com.mukul.onnwaytransporter.orderDetailsPOJO.orderDetailsBean;
import com.mukul.onnwaytransporter.ordersPOJO.ordersBean;
import com.mukul.onnwaytransporter.placeBidPOJO.placeBidBean;
import com.mukul.onnwaytransporter.postLoadPOJO.postLoadBean;
import com.mukul.onnwaytransporter.postedTrucksPOJO.postedTrucksBean;
import com.mukul.onnwaytransporter.profilePOJO.profileBean;
import com.mukul.onnwaytransporter.routesPOJO.routesBean;
import com.mukul.onnwaytransporter.truckDetailsPOJO.truckDetailsBean;
import com.mukul.onnwaytransporter.truckTypePOJO.truckTypeBean;
import com.mukul.onnwaytransporter.updateProfilePOJO.updateProfileBean;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

;

public interface AllApiIneterface {


    @Multipart
    @POST("android/login2.php")
    Call<loginBean> login(
            @Part("phone") String phone,
            @Part("token") String token
    );

    @Multipart
    @POST("android/update_provider_profile.php")
    Call<updateProfileBean> update_provider_profile(
            @Part("user_id") String user_id,
            @Part("user") String user,
            @Part("name") String name,
            @Part("transport_name") String transport_name,
            @Part("city") String city
    );

    @Multipart
    @POST("android/checkDriverRating.php")
    Call<confirm_full_bean> checkDriverRating(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/submitDriverRating.php")
    Call<confirm_full_bean> submitDriverRating(
            @Part("id") String id,
            @Part("driver_rating") String driver_rating
    );

    @Multipart
    @POST("android/getProviderProfile.php")
    Call<profileBean> getProviderProfile(
            @Part("user_id") String user_id
    );

    @GET("android/getSubject.php")
    Call<List<truckTypeBean>> getSubject();

    @Multipart
    @POST("android/submitFeedback.php")
    Call<confirm_full_bean> submitFeedback(
            @Part("user_id") String user_id,
            @Part("subject") String subject,
            @Part("mesage") String mesage
    );

    @Multipart
    @POST("android/updateProviderKYC.php")
    Call<confirm_full_bean> updateProviderKYC(
            @Part("user_id") String user_id,
            @Part("type") String type,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("android/getNewName.php")
    Call<updateProfileBean> getNewName(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/getBiddings.php")
    Call<bidsBean> getBiddings(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/getPastBids.php")
    Call<bidsBean> getPastBids(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/getBidDetails.php")
    Call<bidDetailsBean> getBidDetails(
            @Part("user_id") String user_id,
            @Part("id") String id
    );

    @Multipart
    @POST("android/placeBid.php")
    Call<placeBidBean> placeBid(
            @Part("user_id") String user_id,
            @Part("id") String id,
            @Part("amount") String amount,
            @Part("laod_type") String laod_type,
            @Part("source") String source,
            @Part("destination") String destination,
            @Part("truck_type") String truck_type,
            @Part("schedule") String schedule,
            @Part("weight") String weight,
            @Part("load_passing") String load_passing,
            @Part("remarks") String remarks,
            @Part("length") String length,
            @Part("width") String width,
            @Part("height") String height,
            @Part("quantity") String quantity,
            @Part("mid") String mid,
            @Part("truckTypeDetails") String truckTypeDetails,
            @Part("truckCapacity") String truckCapacity,
            @Part("boxLength") String boxLength,
            @Part("boxWidth") String boxWidth,
            @Part("boxArea") String boxArea,
            @Part("selectedArea") String selectedArea,
            @Part("remainingArea") String remainingArea,
            @Part("selected") String selected
    );

    @Multipart
    @POST("android/getTrucks.php")
    Call<List<truckTypeBean>> getTrucks(
            @Part("type") String type
    );

    @Multipart
    @POST("android/post_full_load.php")
    Call<postLoadBean> post_full_load(
            @Part("user_id") String user_id,
            @Part("laod_type") String laod_type,
            @Part("source") String source,
            @Part("destination") String destination,
            @Part("truck_type") String truck_type,
            @Part("schedule") String schedule,
            @Part("weight") String weight,
            @Part("load_passing") String load_passing,
            @Part("remarks") String remarks,
            @Part("length") String length,
            @Part("width") String width,
            @Part("height") String height,
            @Part("quantity") String quantity,
            @Part("mid") String mid,
            @Part("truckTypeDetails") String truckTypeDetails,
            @Part("truckCapacity") String truckCapacity,
            @Part("boxLength") String boxLength,
            @Part("boxWidth") String boxWidth,
            @Part("boxArea") String boxArea,
            @Part("selectedArea") String selectedArea,
            @Part("remainingArea") String remainingArea,
            @Part("selected") String selected
    );

    @Multipart
    @POST("android/getPostedTrucks.php")
    Call<postedTrucksBean> getPostedTrucks(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/getTruckDetails.php")
    Call<truckDetailsBean> getTruckDetails(
            @Part("id") String id
    );

    @Multipart
    @POST("android/cancel_posted_truck.php")
    Call<truckDetailsBean> cancel_posted_truck(
            @Part("id") String id
    );

    @Multipart
    @POST("android/getActiveOrders.php")
    Call<ordersBean> getActiveOrders(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/getActiveOrders2.php")
    Call<ordersBean> getActiveOrders2(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/getCompletedOrders.php")
    Call<ordersBean> getCompletedOrders(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/getCompletedOrders2.php")
    Call<ordersBean> getCompletedOrders2(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/providerOrderDetails.php")
    Call<orderDetailsBean> providerOrderDetails(
            @Part("id") String id
    );

    @Multipart
    @POST("android/start_order.php")
    Call<orderDetailsBean> start_order(
            @Part("id") String id
    );

    @Multipart
    @POST("android/complete.php")
    Call<orderDetailsBean> complete(
            @Part("id") String id
    );

    @Multipart
    @POST("android/addVehicleNumber.php")
    Call<ordersBean> addVehicleNumber(
            @Part("assign_id") String assign_id,
            @Part("vehicle_number") String vehicle_number,
            @Part("driver_number") String driver_number
    );

    @Multipart
    @POST("android/uploadDocuments.php")
    Call<ordersBean> uploadDocuments(
            @Part("assign_id") String assign_id,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("android/uploadPOD.php")
    Call<ordersBean> uploadPOD(
            @Part("assign_id") String assign_id,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("android/addLogs.php")
    Call<ordersBean> addLogs(
            @Part("delivery_id") String delivery_id,
            @Part("order_id") String order_id,
            @Part("lat") String lat,
            @Part("lng") String lng
    );

    @Multipart
    @POST("android/store_trucks_provider.php")
    Call<ordersBean> store_trucks_provider(
            @Part("user_id") String user_id,
            @Part("trucktype") String trucktype,
            @Part("truck_reg_no") String truck_reg_no,
            @Part("driver_name") String driver_name,
            @Part("driver_mobile_no") String driver_mobile_no,
            @Part MultipartBody.Part file1,
            @Part MultipartBody.Part file2
    );

    @Multipart
    @POST("android/mytrucksprovider.php")
    Call<myTrucksBean> mytrucksprovider(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/fetch_provider_source_des.php")
    Call<routesBean> fetch_provider_source_des(
            @Part("user_id") String user_id
    );

    @GET("android/getMaterial.php")
    Call<List<truckTypeBean>> getMaterial();

}
