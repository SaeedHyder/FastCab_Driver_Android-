package com.app.fastcabdriver.retrofit;


import com.app.fastcabdriver.entities.AssignRideEnt;
import com.app.fastcabdriver.entities.CompleteRideDataEnt;
import com.app.fastcabdriver.entities.DriverEnt;
import com.app.fastcabdriver.entities.DriverFeedBackEnt;
import com.app.fastcabdriver.entities.DriverMsgesEnt;
import com.app.fastcabdriver.entities.ResponseWrapper;

import com.app.fastcabdriver.entities.UpdatedLocationEnt;

import com.app.fastcabdriver.entities.UserRideDetailRatingEnt;


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

    @FormUrlEncoded
    @POST("changepushnotificationstatus")
    Call<ResponseWrapper<DriverEnt>> ChangeNotifiationStatus(@Field("user_id") String user_id,
                                                           @Field("push_status") Integer status);

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

    @FormUrlEncoded
    @POST("user/changepassword")
    Call<ResponseWrapper<DriverEnt>> ChangePassword(@Field("old_password") String old_password,
                                                    @Field("password") String password,
                                                    @Field("password_confirmation") String password_confirmation,
                                                    @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("user/forgotpassword")
    Call<ResponseWrapper> ForgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("ride/driverassignstatus")
    Call<ResponseWrapper<AssignRideEnt>> AssignStatus(@Field("driver_id") int driver_id,
                                                      @Field("ride_id") int ride_id,
                                                      @Field("ride_status") int ride_status,
                                                      @Field("trip_status") int trip_status);


    @GET("ride/driverinprogressride")
    Call<ResponseWrapper<ArrayList<AssignRideEnt>>> InProgressRides(@Query("driver_id") int driver_id);

    @GET("ride/drivercompleteride")
    Call<ResponseWrapper<ArrayList<AssignRideEnt>>>  CompleteRides(@Query("driver_id") int driver_id);

    @FormUrlEncoded
    @POST("driver/feedback")
    Call<ResponseWrapper<DriverFeedBackEnt>> DriverFeedBack(@Field("user_id") int user_id,
                                                            @Field("driver_id") int driver_id,
                                                            @Field("ride_id") int ride_id,
                                                            @Field("rate") int rate,
                                                            @Field("type") String type
    );


    @GET("ride/completeridedetail")
    Call<ResponseWrapper<CompleteRideDataEnt>> CompleteRideUserDetail(@Query("ride_id") int ride_id,
                                                                      @Query("user_id") int user_id,
                                                                      @Query("driver_id") int driver_id
                                                                    );

    @FormUrlEncoded
    @POST("driver/driverlogout")
    Call<ResponseWrapper> LogoutDriver(@Field("driver_id") int driver_id);

    @FormUrlEncoded
    @POST("driver/driverchangelocation")
    Call<ResponseWrapper>UpdateLatLng(@Field("driver_id") String driver_id,
                                      @Field("latitude") String latitude,
                                      @Field("longitude") String longitude
                                      );
    @GET("driver/getdriverlocation")
    Call<ResponseWrapper<UpdatedLocationEnt>> getUpdatedLocation(@Query("driver_id")String driver_id);

    @GET("ride/userridedetail")
    Call<ResponseWrapper<UserRideDetailRatingEnt>> UserDetailForRating(@Query("ride_id") int ride_id);

}