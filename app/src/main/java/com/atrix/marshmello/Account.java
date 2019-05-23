package com.atrix.marshmello;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

public class Account extends Fragment {

    public static Account newInstance() {
        Account fragment = new Account();
        return fragment;
    }

    @Override
    public  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = getActivity().findViewById(R.id.button_record);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HistoryDetect.class);
                startActivity(intent);
            }
        });
        TextView textView = getActivity().findViewById(R.id.textView);
        SharedPreferences storge = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        textView.setText("Ada \n\n肤质：" + storge.getString("type", ""));
    }
}

