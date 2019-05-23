package com.atrix.marshmello;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Shop extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.shop);
        class MyListener implements View.OnClickListener{
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.item_1) {
                    Intent intent = new Intent();
                    intent.setAction("Android.intent.action.VIEW");
                    Uri uri = Uri.parse("https://detail.tmall.com/item.htm?spm=a2252.10433362.6089207215.2.d71f3828Gq84H8&id=536088064956");
                    intent.setData(uri);
                    intent.setClassName("com.taobao.taobao", "com.taobao.android.shop.activity.ShopHomePageActivity");
                    startActivity(intent);
                }
            }
        }
    }
}
