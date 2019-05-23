package com.atrix.marshmello;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

public class Shop extends Fragment {
    private int[] cid;
    private List<CardView> container;
    public static Shop newInstance() {
        Shop fragment = new Shop();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initCid();
        initContainer();
    }

    private void initContainer() {
        container = new LinkedList<>();
        for(int i = 0; i < cid.length; i++) {
            CardView v = getActivity().findViewById(cid[i]);
            v.setOnClickListener(new MyListener());
            container.add(v);
        }
    }

    private void initCid() {
        cid = new int[]{R.id.item_1, R.id.item_2, R.id.item_3, R.id.item_4, R.id.item_5, R.id.item_6, R.id.item_7, R.id.item_8};
    }



    class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setAction("Android.intent.action.VIEW");
            intent.setClassName("com.taobao.taobao", "com.taobao.android.detail.wrapper.activity.DetailActivity");
            Uri uri;
            switch (view.getId()) {
                case R.id.item_1:
                    uri = Uri.parse("https://item.taobao.com/item.htm?spm=a230r.1.14.175.35bd7308G6HuXd&id=558706177804&ns=1&abbucket=18#detail");
                    intent.setData(uri);
                    startActivity(intent);
                    break;
                case R.id.item_2:
                    uri = Uri.parse("https://item.taobao.com/item.htm?spm=a230r.1.14.83.400c5a3ePoiVIg&id=592093584077&ns=1&abbucket=18#detail");
                    intent.setData(uri);
                    startActivity(intent);
                    break;
                case R.id.item_3:
                    uri = Uri.parse("https://item.taobao.com/item.htm?spm=a230r.1.14.85.db4e4867KHZ01T&id=573341065725&ns=1&abbucket=18#detail");
                    intent.setData(uri);
                    startActivity(intent);
                    break;
                case  R.id.item_4:
                    uri = Uri.parse("https://item.taobao.com/item.htm?spm=a230r.1.14.173.46793904t6OlHq&id=578522045999&ns=1&abbucket=18#detail");
                    intent.setData(uri);
                    startActivity(intent);
                    break;
                case  R.id.item_5:
                    uri = Uri.parse("https://item.taobao.com/item.htm?spm=a230r.1.14.34.7d6a6509mGiZ2v&id=593385929779&ns=1&abbucket=18#detail");
                    intent.setData(uri);
                    startActivity(intent);
                    break;
                case R.id.item_6:
                    uri = Uri.parse("https://item.taobao.com/item.htm?spm=a230r.1.14.88.201b1424LlxcCi&id=549163005848&ns=1&abbucket=18#detail");
                    intent.setData(uri);
                    startActivity(intent);
                    break;
                case R.id.item_7:
                    uri = Uri.parse("https://item.taobao.com/item.htm?spm=a230r.1.14.58.aab147af0R3KGU&id=7683162143&ns=1&abbucket=10#detail");
                    intent.setData(uri);
                    startActivity(intent);
                    break;
                case R.id.item_8:
                    uri = Uri.parse("https://item.taobao.com/item.htm?spm=a230r.1.14.93.2ba77733P0cSWr&id=520396697317&ns=1&abbucket=10#detail");
                    intent.setData(uri);
                    startActivity(intent);
                    break;
                    default:
                        break;
            }
        }
    }
}
