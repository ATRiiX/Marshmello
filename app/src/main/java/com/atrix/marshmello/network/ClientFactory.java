package com.atrix.marshmello.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientFactory {
    public static MarshmelloInterface newInterface(String url){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MarshmelloInterface iMarshmelloInterface=retrofit.create(MarshmelloInterface.class);

        return iMarshmelloInterface;
    }

    public static MarshmelloInterface newInterface(){
        return newInterface(MarshmelloInterface.BASE_URL_SERVER);
    }
}
