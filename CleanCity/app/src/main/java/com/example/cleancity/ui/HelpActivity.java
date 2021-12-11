package com.example.cleancity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cleancity.R;

import java.util.Objects;

public class HelpActivity extends AppCompatActivity {
    ImageView ProfileImg;
    String rut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Objects.requireNonNull(getSupportActionBar()).hide();

        rut = getIntent().getStringExtra("Usuario");

        ProfileImg = findViewById(R.id.UserImg);
        ProfileImg.setOnClickListener(v -> {
            Intent I = new Intent(getBaseContext(), ProfileActivity.class);
            I.putExtra("Usuario", rut);
            startActivity(I);
            finish();
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent I = new Intent(getBaseContext(),MainActivity.class);
        I.putExtra("Usuario", rut);
        super.onBackPressed();
    }
}