package com.atrix.marshmello;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atrix.marshmello.service.QueryService;

public class DetectResult extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.result);
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
        TextView textView = (TextView)findViewById(R.id.textView_res);
//        textView.setText("Ada,\n您的肤质为 干性" + data);
        progressBar.setVisibility(View.INVISIBLE);
        Button back = (Button)findViewById(R.id.button_ret_from_res);
        back.setOnClickListener(new MyListener());
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
