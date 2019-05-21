
package com.atrix.marshmello.network;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface MarshmelloInterface {
    String BASE_URL_SERVER="http://119.28.47.159:5000/";
    //TODO

    //get
    @GET("basketball/{id}")
    Call<ResponseBody> queryID(@Path("id") String id);
//    @GET("api/detect/{id}")
//    Observable<String> queryIDRx(@Path("id") String id);

    @Multipart
    @POST("basketball/upload")
    Call<ResponseBody> uploadData(@Part MultipartBody.Part part);
//    @POST("api/detect")
//    @FormUrlEncoded
//    Observable<String> uploadDataRx(@Field("sensor") String sensor, @Field("touch") String touch);


}
