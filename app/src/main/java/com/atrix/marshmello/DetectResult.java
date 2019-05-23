package com.atrix.marshmello;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atrix.marshmello.service.QueryService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.security.AccessController.getContext;

public class DetectResult extends AppCompatActivity {
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ProgressBar progressBar;
    private TextView textView;
    private String currentPhotoPath;
    private QueryService.QueryInit x;
    private Thread thread;
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
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        textView = (TextView)findViewById(R.id.textView_res);
//        textView.setText("Ada,\n您的肤质为 干性" + data);
        progressBar.setVisibility(View.INVISIBLE);
        Button back = (Button)findViewById(R.id.button_ret_from_res);
        back.setOnClickListener(new MyListener());
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
            Bitmap bm = BitmapFactory.decodeFile(currentPhotoPath);
            x.getBind().cacheSensorData(currentPhotoPath);
            queryThread thread1 = new queryThread();
            thread1.init(textView, progressBar, x);
            thread = new Thread(thread1);
            thread.start();
            textView.setText("Ada,\n您的肤质为 ");
        }

    }
    class queryThread implements Runnable {
        TextView textView;
        ProgressBar progressBar;
        QueryService.QueryInit x;
        public void init(TextView textView, ProgressBar progressBar, QueryService.QueryInit x){
            this.textView = textView;
            this.progressBar = progressBar;
            this.x = x;
        }

        public void run(){
            while(!x.getBind().fetchEnable()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            progressBar.setVisibility(View.INVISIBLE);
            String data = x.getBind().fetchData();
            textView.setText("Ada,\n您的肤质为 干性" + data);
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
                    break;
                    default:
                        break;
            }
        }
    }
}
