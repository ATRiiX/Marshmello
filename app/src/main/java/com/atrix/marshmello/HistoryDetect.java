package com.atrix.marshmello;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class HistoryDetect extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_history);
        SharedPreferences storge = getSharedPreferences("data", MODE_PRIVATE);
        TextView type = (TextView)findViewById(R.id.textView9);
        TextView tips = (TextView)findViewById(R.id.textView10);
        String res = storge.getString("type", "未检测");
        type.setText(res);
        if(res.equals("中性")) {
            tips.setText("注意保湿");
        } else if(res.equals("油性")) {
            tips.setText("注意去油");
        } else if(res.equals("干性")) {
            tips.setText("注意保湿");
        } else if(res.equals("混合性")) {
            tips.setText("保持水油平衡");
        } else {
            tips.setText("");
        }
        ImageButton imageButton = (ImageButton)findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
