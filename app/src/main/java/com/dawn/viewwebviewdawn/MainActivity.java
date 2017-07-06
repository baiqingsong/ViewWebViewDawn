package com.dawn.viewwebviewdawn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void jumpToWebView(View view){
        startActivity(new Intent(this, WebActivity.class));
    }
    public void jumpToHtml5(View view){
        Intent intent = new Intent("com.dawn.activity.htmlactivity");
        Bundle bundle = new Bundle();
        bundle.putString("url", "http://www.baidu.com");
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }
}
