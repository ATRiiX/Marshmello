package com.atrix.marshmello;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.atrix.marshmello.service.QueryService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class Camera extends Fragment {

    private int[] cid;
    private List<CardView> container;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    private String currentPhotoPath;
    private QueryService.QueryInit x;
    public static Camera newInstance() {
        Camera fragment = new Camera();
        return fragment;
    }

    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout1, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = getActivity().findViewById(R.id.button_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DetectResult.class);
                startActivityForResult(intent, 1);
//                dispatchTakePictureIntent();
            }
        });
        x = new QueryService.QueryInit();
        x.bindservice(getContext());


        super.onActivityCreated(savedInstanceState);
        initCid();
        initContainer();
    }


//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//                Log.d("debug:", "take photo file error!");
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(getContext(),
//                        "com.atrix.marshmello",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//            }
//        }
//    }

//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        currentPhotoPath = image.getAbsolutePath();
//        return image;
//    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bitmap bm = BitmapFactory.decodeFile(currentPhotoPath);
//            x.getBind().cacheSensorData(currentPhotoPath);
//            Intent intent = new Intent(getContext(), DetectResult.class);
//            startActivityForResult(intent, 3);
//        } else if(requestCode == 3 && resultCode == RESULT_CANCELED) {
//
//        }
        if(requestCode == 1 && resultCode == RESULT_CANCELED){

        }
    }

    private void initContainer() {
        container = new LinkedList<>();
        for (int i = 0; i < cid.length; i++) {
            CardView v = getActivity().findViewById(cid[i]);
            v.setOnClickListener(new Camera.MyListener());
            container.add(v);
        }
    }

    private void initCid() {
        cid = new int[]{R.id.tip1, R.id.tip2, R.id.tip3, R.id.tip4, R.id.tip5, R.id.tip6};
    }


    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setAction("com.atrix.marshmello.TipsAction");

            startActivity(intent);
        }
    }
}

