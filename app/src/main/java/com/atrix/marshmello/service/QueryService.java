package com.atrix.marshmello.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class QueryService extends Service {
    public QueryService() {
    }
    public static void startService(Context context){
        context.startService(new Intent( context, QueryService.class));
    }
    public static void stopService(Context context){
        context.stopService(new Intent(context, QueryService.class));
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }




    @Override
    public void onCreate() {
        super.onCreate();

        //TODO

    }

}
