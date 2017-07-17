package com.app.fastcab.retrofit;


import com.app.fastcab.entities.DriverEnt;
import com.app.fastcab.entities.DriverMsgesEnt;
import com.app.fastcab.entities.ResponseWrapper;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface WebService {

    @FormUrlEncoded
    @POST("notification/updatedevicetoken")
    Call<ResponseWrapper> updateToken(@Field("user_id") String userid,
                                      @Field("device_type") String deviceType,
                                      @Field("device_token") String token);

    @Multipart
    @POST("driver/register")
    Call<ResponseWrapper<DriverEnt>> registerUser(@Part("full_name") RequestBody Fullname,
                                                  @Part("dob") RequestBody dob,
                                                  @Part("phone_no") RequestBody PhoneNumber,
                                                  @Part("address") RequestBody address,
                                                  @Part("license_no") RequestBody license_no,
                                                  @Part("work_history") RequestBody work_history,
                                                  @Part("brief_introduction") RequestBody brief_introduction,
                                                  @Part MultipartBody.Part profile_picture,
                                                  @Part("latitude") RequestBody latitude,
                                                  @Part("longitude") RequestBody longitude
    );


    @GET("driver/getprofile")
    Call<ResponseWrapper<DriverEnt>> getProfile(
            @Query("driver_id") int driver_id);

    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseWrapper<DriverEnt>> loginDriver(@Field("email") String email,
                                             @Field("password") String password
    );

    @FormUrlEncoded
    @POST("driver/update")
    Call<ResponseWrapper<DriverEnt>> UpdateDriver(@Field("driver_id") int driver_id,
                                                  @Field("phone_no") String phone_no,
                                                  @Field("address") String address,
                                                  @Field("dob") String dob
    );


    @GET("cms/drivermessage")
    Call<ResponseWrapper<ArrayList<DriverMsgesEnt>>> DriverMsges();

    @FormUrlEncoded
    @POST("driver/changeavaibilitystatus")
    Call<ResponseWrapper<DriverEnt>> GoOnline(@Field("driver_id") int driver_id,
                                                  @Field("avaibility_status") String avaibility_status,
                                                  @Field("latitude") String latitude,
                                                  @Field("longitude") String longitude
    );






}