package com.atrix.marshmello;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atrix.marshmello.service.QueryService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetectResult extends AppCompatActivity {
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ProgressBar progressBar;
    private TextView textView;
    private String currentPhotoPath;
    private QueryService.QueryInit x;
    private Thread thread;
    private Handler handler;
    private boolean fetch;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.result);
        x = new QueryService.QueryInit();
        x.bindservice(this);
        dispatchTakePictureIntent();
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView_res);
        Button back = findViewById(R.id.button_ret_from_res);
        back.setOnClickListener(new MyListener());
        Button history = findViewById(R.id.button_to_cal);
        history.setOnClickListener(new MyListener());
    }

    private void initSharedPreferences(String s) {
        SharedPreferences.Editor logInFlag = getSharedPreferences("data", MODE_PRIVATE).edit();
        logInFlag.putString("type", s);
        logInFlag.apply();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d("debug:", "take photo file error!");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.atrix.marshmello",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            final Bitmap bm = BitmapFactory.decodeFile(currentPhotoPath);
            x.getBind().cacheSensorData(currentPhotoPath);
            queryThread thread1 = new queryThread();
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 1:
                            progressBar.setVisibility(View.INVISIBLE);
                            textView.setText(checkString((String) msg.obj));

                            break;
                        default:
                            break;
                    }
                }

                public String checkString(String res) {
                    int flag = res.indexOf("basketball");
                    if (flag == -1) {
                        return "Ada, 识别失败！请重新拍照";
                    } else {
                        String type = "";
                        switch (res.charAt(flag + 11)) {
                            case '0':
                                initSharedPreferences("干性");
                                type = "Ada, 您的肤质为干性";
                                break;
                            case '1':
                                initSharedPreferences("中性");
                                type = "Ada, 您的肤质为中性";
                                break;
                            case '2':
                                initSharedPreferences("混合性");
                                type = "Ada, 您的肤质为混合性";
                                break;
                            case '3':
                                initSharedPreferences("油性");
                                type = "Ada, 您的肤质为油性";
                                break;
                            default:
                                break;
                        }
                        return type;
                    }
                }
            };
            thread = new Thread(thread1);
            thread.start();
            textView.setText("Ada,您的肤质为 ");
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_CANCELED) {
            finish();
        }

    }
    class queryThread implements Runnable {
        public void run(){
            while(!x.getBind().fetchEnable()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = handler.obtainMessage();
            message.what = 1;
            String data = x.getBind().fetchData();
            message.obj = data;
            handler.sendMessage(message);
        }
    }

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.button_ret_from_res:
                    setResult(RESULT_CANCELED, intent);
                    finish();
                    break;
                case R.id.button_to_cal:
                    intent = new Intent(v.getContext(), HistoryDetect.class);
                    startActivity(intent);
                    break;
                    default:
                        break;
            }
        }
    }
}
