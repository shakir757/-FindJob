package com.example.job;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class JobActivity extends AppCompatActivity {

    TextView title, location, description, date;
    ImageView image;
    String title1, location1, description1, date1, url1, url2;
    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        title = findViewById(R.id.textViewTitleJob);
        location = findViewById(R.id.textViewLocationJob);
        description = findViewById(R.id.textViewDescriptionJob);
        date = findViewById(R.id.textViewDateJob);
        image = findViewById(R.id.imageViewJob);

        number = getIntent().getIntExtra("number", number);

        if(number == 1) {
        title1 = getIntent().getStringExtra("title1");
        location1 = getIntent().getStringExtra("location1");
        description1 = getIntent().getStringExtra("description1");
        date1 = getIntent().getStringExtra("date1");
        url1 = getIntent().getStringExtra("image1");

        title.setText(title1);
        location.setText(location1);
        description.setText(Html.fromHtml(description1));
        date.setText("Created date: " + date1);
        Glide.with(this).load(url1).into(image);
        }

        if(number == 2) {
            title1 = getIntent().getStringExtra("title2");
            location1 = getIntent().getStringExtra("location2");
            description1 = getIntent().getStringExtra("description2");
            date1 = getIntent().getStringExtra("date2");
            url1 = getIntent().getStringExtra("image2");

            title.setText(title1);
            location.setText(location1);
            description.setText(Html.fromHtml(description1));
            date.setText("Created date: " + date1);
            Glide.with(this).load(url1).into(image);
        }
    }

}
