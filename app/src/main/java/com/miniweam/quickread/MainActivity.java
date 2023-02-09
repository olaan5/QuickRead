package com.miniweam.quickread;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button exploreNewsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exploreNewsBtn = findViewById(R.id.explore_news_btn);

        exploreNewsBtn.setOnClickListener(View ->
                startActivity(new Intent(MainActivity.this, ExploreNewsActivity.class)));
    }
}