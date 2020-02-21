package com.example.job;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ErrorActivity extends AppCompatActivity {

    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        error = findViewById(R.id.textViewErrorE);
        error.setText(getIntent().getStringExtra("error"));
    }

    public void retry(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
