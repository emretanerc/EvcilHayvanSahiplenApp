package com.etcmobileapps.evcilhayvansahiplenme.api;

import com.etcmobileapps.evcilhayvansahiplenme.model.AdsModel;
import com.etcmobileapps.evcilhayvansahiplenme.model.SearchModel;
import com.etcmobileapps.evcilhayvansahiplenme.model.UserModel;
import com.etcmobileapps.evcilhayvansahiplenme.model.VersionCheck;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Interface {

    @GET("ads")
    Call<List<AdsModel>> getAds();

    @GET("ads/dogs")
    Call<List<AdsModel>> getDogAds();

    @GET("ads/birds")
    Call<List<AdsModel>> getBirdAds();

    @GET("ads/cats")
    Call<List<AdsModel>> getCatAds();

    @GET("ads/others")
    Call<List<AdsModel>> getOtherAds();

    @GET("ads")
    Call<List<SearchModel>> getAdsSearch();

    @GET("ads/others")
    Call<List<SearchModel>> getOtherAdsSearch();

    @GET("search/{tags}")
    Call<List<SearchModel>> getSearch(@Path("tags") String tags);


    @GET("search/{city}/{tags}")
    Call<List<SearchModel>> getAdsSearchWithCity(@Path("tags") String tags,  @Path("city") String city);


    @GET("ads/id/{id}")
    Call<AdsModel> getAdDetails(@Path("id") Integer id);

    @GET("ads/user/{id}")
    Call<List<AdsModel>> getOwnAds(@Path("id") String id);

    @GET("ads/user/{id}")
    Call<List<SearchModel>> getOwnAdsId(@Path("id") String id);

    @DELETE("ads/id/{id}")
    Call<SearchModel> deleteAds(@Path("id") Integer id);


    @PUT("ads/id/confirmation/{id}")
    Call<SearchModel> unConfirmation(@Path("id") Integer id);

    @GET("version")
    Call<List<VersionCheck>> checkVersion();


    @FormUrlEncoded
    @PUT("ads/id/{id}")
    Call<AdsModel> editAds  (@Path("id") String id,
                             @Field("ad_ownerid") String ad_ownerid,
                             @Field("ad_ownername") String ad_ownername,
                             @Field("ad_ownertelephone") String ad_ownertelephone,
                             @Field("ad_type") String ad_type,
                             @Field("ad_name") String ad_name,
                             @Field("ad_detail") String ad_detail,
                             @Field("ad_category") String ad_category,
                             @Field("ad_altcategory") String ad_altcategory,
                             @Field("ad_age") String ad_age,
                             @Field("confirmation") Integer confirmation,
                             @Field("country") String country,
                             @Field("reason") String reason,
                             @Field("ad_image") String ad_image,
                             @Field("ad_image2") String ad_image2,
                             @Field("tags") String tags,
                             @Field("date") String date,
                             @Field("ad_sex") String ad_sex,
                             @Field("ad_view") Integer ad_view);



    @PUT("ads/view/{id}")
    Call<AdsModel> view  (@Path("id") Integer id);



    @FormUrlEncoded
    @POST("newuser")
    Call<UserModel> registerUser(@Field("user_id") String user_id,
                                 @Field("user_name") String user_name,
                                 @Field("user_email") String user_email,
                                 @Field("user_photo") String user_photo);



    @GET("users/{id}")
    Call<List<UserModel>>getUserSpecs(@Path("id") String id);

    @FormUrlEncoded
    @POST("new")
    Call<AdsModel> addNewAdd(@Field("ad_ownerid") String ad_ownerid,
                             @Field("ad_ownername") String ad_ownername,
                             @Field("ad_ownertelephone") String ad_ownertelephone,
                             @Field("ad_type") String ad_type,
                             @Field("ad_name") String ad_name,
                             @Field("ad_detail") String ad_detail,
                             @Field("ad_category") String ad_category,
                             @Field("ad_altcategory") String ad_altcategory,
                             @Field("ad_age") String ad_age,
                             @Field("confirmation") String confirmation,
                             @Field("country") String country,
                             @Field("reason") String reason,
                             @Field("ad_image") String ad_image,
                             @Field("ad_image2") String ad_image2,
                             @Field("tags") String tags,
                             @Field("date") String date,
                             @Field("ad_sex") String ad_sex,
                             @Field("ad_view") Integer ad_view);
}

