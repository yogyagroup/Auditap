package com.yogyagroup.auditap.data;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public interface API {
    @FormUrlEncoded
    @POST("api/login")
    Call<UserCallback> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/receiptToday")
    Observable<ReceiptCallback> receiptToday(@Field("store_id") int store_id);

    @FormUrlEncoded
    @POST("api/receiptKeyword")
    Observable<ReceiptCallback> receiptKeyword(@Field("store_id") int store_id, @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST("api/photoByReceiptId")
    Observable<PhotoCallback> photoByReceiptId(@Field("receipt_id") int receipt_id);

    @FormUrlEncoded
    @POST("api/deletePhoto")
    Call<MessageCallback> deletePhoto(@Field("photo_id") int photo_id);

    @Multipart
    @POST("api/uploadPhoto")
    Call<PhotoUploadCallback> uploadPhoto(@Part("holder_id")RequestBody holder_id, @Part("receipt_id")RequestBody receipt_id, @Part MultipartBody.Part file);
}
