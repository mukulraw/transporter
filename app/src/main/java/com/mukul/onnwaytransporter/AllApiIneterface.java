package com.mukul.onnwaytransporter;


import com.mukul.onnwaytransporter.bidDetailsPOJO.bidDetailsBean;
import com.mukul.onnwaytransporter.bidsPOJO.bidsBean;
import com.mukul.onnwaytransporter.orderDetailsPOJO.orderDetailsBean;
import com.mukul.onnwaytransporter.ordersPOJO.ordersBean;
import com.mukul.onnwaytransporter.placeBidPOJO.placeBidBean;
import com.mukul.onnwaytransporter.postLoadPOJO.postLoadBean;
import com.mukul.onnwaytransporter.postedTrucksPOJO.postedTrucksBean;
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
            @Part("name") String name,
            @Part("transport_name") String transport_name,
            @Part("city") String city
    );

    @Multipart
    @POST("android/getBiddings.php")
    Call<bidsBean> getBiddings(
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
            @Part("amount") String amount
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
            @Part("quantity") String quantity
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
    @POST("android/getActiveOrders.php")
    Call<ordersBean> getActiveOrders(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/getCompletedOrders.php")
    Call<ordersBean> getCompletedOrders(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/providerOrderDetails.php")
    Call<orderDetailsBean> providerOrderDetails(
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

}
