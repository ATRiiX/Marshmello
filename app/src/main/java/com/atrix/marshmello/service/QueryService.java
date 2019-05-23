package com.atrix.marshmello.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.atrix.marshmello.network.ClientFactory;
import com.atrix.marshmello.network.MarshmelloInterface;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class QueryService extends Service {
    public QueryService() {
    }
    public static void startService(Context context){
        context.startService(new Intent(context, QueryService.class));
    }
    public static void stopService(Context context){
        context.stopService(new Intent(context, QueryService.class));
    }
    protected String path;
    protected Binder binder;
    protected MarshmelloInterface marshmelloInterface;

    protected String queryType;
    protected Thread queryth;
    boolean isfetched;


    @Override
    public void onCreate() {
        super.onCreate();
        path = "";
        queryth=new Thread();
        binder=new QueryBind();
        isfetched = false;
        marshmelloInterface = ClientFactory.newInterface();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    protected void stateChecker(){
        if(queryth.isAlive()){
            return;
            //thread dead continues;
        }
        queryth=new Thread(new QueryRunnable());
        queryth.start();
    }

    private void cleanDataZone() {
        path = "";
    }

    protected class QueryRunnable implements Runnable{
        protected int loopcnt;
        @Override
        public void run() {
            Response<ResponseBody> queryStatus;
            try{
                File file = new File(path);
                RequestBody requestFile  = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                Response<ResponseBody> response= marshmelloInterface.uploadData(filePart).execute();
                String qid = response.body().string();
                Log.d("debug", qid);
                loopcnt = 0;
                Thread.sleep(5000);
                do{
                    queryStatus = marshmelloInterface.queryID(qid).execute();
                    Thread.sleep(3000);
                    queryStatus = marshmelloInterface.queryID(qid).execute();
                    loopcnt++;
                    if(loopcnt > 1000){
                        throw new IOException("Time Out");
                    }
                }while (queryStatus.code()!= 200);
                isfetched = true;
                queryType = queryStatus.body().string();
//                showMessage(queryType);
            }catch (IOException e){
                e.printStackTrace();
            }catch (InterruptedException e){
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }
            cleanDataZone();
        }
    }

    private void showMessage(final String msg
    ){
        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(QueryService.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static class QueryInit{
        public QueryBind getBind() {
            return bind;
        }

        private QueryBind bind;
        private ServiceConnection connection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                bind=(QueryService.QueryBind)service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                bind=null;
            }
        };
        public void bindservice(Context context){
            Intent intent=new Intent(context, QueryService.class);
            context.bindService(intent,connection, Service.BIND_AUTO_CREATE);
        }
        public void unbindservice(Context context){
            context.unbindService(connection);
        }
    }



    public class QueryBind extends Binder{
        public void cacheSensorData(String data){
            if(queryth.isAlive()){
                return;
            }
            path = data;
            stateChecker();
        }
        public boolean fetchEnable() {
            return isfetched;
        }

        public String fetchData(){
            isfetched = false;
            return queryType;
        }
    }
}
