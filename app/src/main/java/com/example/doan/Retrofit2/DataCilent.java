package com.example.doan.Retrofit2;

import retrofit2.Call;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface DataCilent {
    // Gửi dạng file
    @Multipart
    @POST("uploadImage.php")
    Call<String> upLoadPhoto(@Part MultipartBody.Part partPhoto);
    // Gủi dạng chuỗi
    @FormUrlEncoded
    @POST("insertDinary.php")
    Call<String> insertDataNote(@Field("title") String title,
                                @Field("content") String content,
                                @Field("date") String date,
                                @Field("image") String image,
                                @Field("user_id") String user_id,
                                @Field("find_id") String find_id);
    @FormUrlEncoded
    @POST("updateDinaryPost.php")
    Call<String> updateDataNote(@Field("id") String id,
                                @Field("title") String title,
                                @Field("content") String content,
                                @Field("date") String date,
                                @Field("image") String image,
                                @Field("user_id") String user_id,
                                @Field("find_id") String find_id);
    @FormUrlEncoded
    @POST("deletePost.php")
    Call<String> removeDataPost(@Field("id") int id);
}
