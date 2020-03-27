package com.mukul.onnwaytransporter;


import com.mukul.onnwaytransporter.bidDetailsPOJO.bidDetailsBean;
import com.mukul.onnwaytransporter.bidsPOJO.bidsBean;
import com.mukul.onnwaytransporter.placeBidPOJO.placeBidBean;
import com.mukul.onnwaytransporter.updateProfilePOJO.updateProfileBean;

import java.util.List;

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
            @Part("email") String email,
            @Part("city") String city,
            @Part("type") String type,
            @Part("company") String company,
            @Part("user") String user
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

}
