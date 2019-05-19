
package com.atrix.marshmello.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MarshmelloInterface {
    String BASE_URL_SERVER="http://marshmello.atrix.cn:5000/";
    //TODO

    //get
    @GET("api/detect/{id}")
    Call<ResponseBody> queryID(@Path("id") String id);
    @GET("api/detect/{id}")
    Observable<String> queryIDRx(@Path("id") String id);

    @POST("api/upload")
    @FormUrlEncoded
    Call<String> uploadData(@Field("sensor") String sensor, @Field("touch") String touch);
    @POST("api/detect")
    @FormUrlEncoded
    Observable<String> uploadDataRx(@Field("sensor") String sensor, @Field("touch") String touch);


}
