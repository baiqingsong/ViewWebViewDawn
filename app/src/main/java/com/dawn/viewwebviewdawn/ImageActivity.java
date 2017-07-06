package com.dawn.viewwebviewdawn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by 90449 on 2017/7/6.
 */

public class ImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        String imageViewUrl = getIntent().getStringExtra("imgUrl");
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        Glide.with(this).load(imageViewUrl).into(imageView);
    }
}
