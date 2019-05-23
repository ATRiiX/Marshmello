package com.atrix.marshmello;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.atrix.marshmello.service.QueryService;

import java.util.LinkedList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;

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
            }
        });
        x = new QueryService.QueryInit();
        x.bindservice(getContext());


        super.onActivityCreated(savedInstanceState);
        initCid();
        initContainer();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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

