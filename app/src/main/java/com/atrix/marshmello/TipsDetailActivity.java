package com.atrix.marshmello;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class TipsDetailActivity extends AppCompatActivity {
    private boolean isCollect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        isCollect = false;
        setContentView(R.layout.tipdetail);
        ImageButton imageButton = findViewById(R.id.collections);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCollect) {
                    v.setBackgroundResource(R.drawable.ic_grade_black_24dp);
                } else {
                    v.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                }
                isCollect = !isCollect;
            }
        });
    }
}
