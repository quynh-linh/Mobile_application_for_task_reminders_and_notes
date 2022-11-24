package com.example.doan.Retrofit2;

import retrofit2.Call;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface DataCilent {
    // Post
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
    // Task
    @FormUrlEncoded
    @POST("insertTask.php")
    Call<String> insertTask(@Field("id") String id,
                                @Field("title") String title,
                                @Field("content") String content,
                                @Field("time") String date,
                                @Field("date") String image,
                                @Field("user_id") String user_id);
    @FormUrlEncoded
    @POST("updateTask.php")
    Call<String> updateTask(@Field("id") String id,
                            @Field("title") String title,
                            @Field("content") String content,
                            @Field("time") String date,
                            @Field("date") String image,
                            @Field("user_id") String user_id);
    @FormUrlEncoded
    @POST("deleteTask.php")
    Call<String> removeTask(@Field("id") int id);
    // User
    @FormUrlEncoded
    @POST("loginUser.php")
    Call<String> loginUser(@Field("nameLogin") String nameLogin,
                           @Field("password") String password);
    @FormUrlEncoded
    @POST("insertUser.php")
    Call<String> addUser(@Field("nameLogin") String nameLogin,
                           @Field("password") String password);
    @FormUrlEncoded
    @POST("updateUser.php")
    Call<String> updateUser(@Field("fullname") String fullname,
                            @Field("phone") int phone,
                            @Field("birthday") String birthday,
                            @Field("nameLogin") String nameLogin);
    @FormUrlEncoded
    @POST("updateImageUser.php")
    Call<String> updateImageUser(@Field("image") String image,
                            @Field("nameLogin") String nameLogin);
}
