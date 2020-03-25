package com.sumit.myapplication.driver.profilerelated.uploadkycimage;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiConfig {

    @Multipart
    @POST("android/imageupload.php")
//    Call<ServerResponse> upload(
//            @Header("Authorization") String authorization,
//            @PartMap Map<String, RequestBody> map
//    );
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file, @Part("name") String name, @Part("mobile_no") String mobileno, @Part("user_type") String usertype);
}
