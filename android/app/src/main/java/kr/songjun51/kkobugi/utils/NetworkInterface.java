package kr.songjun51.kkobugi.utils;

import java.util.List;

import kr.songjun51.kkobugi.models.FacebookUser;
import kr.songjun51.kkobugi.models.StatusData;
import kr.songjun51.kkobugi.models.TodayData;
import kr.songjun51.kkobugi.models.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by KOHA_DESKTOP on 2016. 6. 29..
 */
public interface NetworkInterface {

    /*
    * ````APIs here
    * */
    @GET("/auth/facebook/token")
    Call<FacebookUser> loginByFacebook(@Query("access_token") String token);

    @POST("/auth/register")
    @FormUrlEncoded
    Call<User> register(@Field("name") String name, @Field("phone") String phone, @Field("passwd") String passwd);

    @POST("/auth/authenticate")
    @FormUrlEncoded
    Call<User> authenticate(@Field("token") String token);

    @POST("/auth/login")
    @FormUrlEncoded
    Call<User> login(@Field("phone") String phone, @Field("passwd") String passwd);

    @POST("/auth/destroy")
    @FormUrlEncoded
    Call<User> destroy(@Field("id") String id);

    @GET("/friend/facebook/find")
    Call<List<User>> callFacebookFriend(@Query("access_token") String token);

    @POST("/friend/local/find")
    @FormUrlEncoded
    Call<List<User>> callLocalFriend(@Field("phone") String phone);

    @POST("/friend/add")
    @FormUrlEncoded
    Call<StatusData> addFriend(@Field("phone") String phone);

    @POST("/friend/getlist")
    @FormUrlEncoded
    Call<List<User>> getFriendsFriend(@Field("id") String id);

    @POST("/friend/getinfo")
    @FormUrlEncoded
    Call<User> getFriendInfo(@Field("id") String id);

    @POST("/data/add/today")
    @FormUrlEncoded
    Call<TodayData> addTodayData(@Field("date") String date, @Field("percent") String percent, @Field("user_id") String user_id);

    @POST("/data/getdata/array")
    @FormUrlEncoded
    Call<List<TodayData>> getDataArray(@Field("id") String id);

    @POST("/data/getdata/rank")
    @FormUrlEncoded
    Call<List<User>> getFriendRank(@Field("id") String id);

}
