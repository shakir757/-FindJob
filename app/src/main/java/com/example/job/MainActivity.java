package com.example.job;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // Strings and ints
    private String[] test = {"text", "text1", "text2", "text3", "text4",
            "text5", "text6", "text7", "text8", "text9", "text10", "text11"};
    int number;
    String searchText, errorS;
    String img_url1, descriptionS1, img_url2, descriptionS2;
    String putTitle1, putLocation1, putTitle2, putLocation2, date1, date2;

    // Layout items
    TextView title1, location1, description1, error, title2, location2, description2;
    ImageView image1, image2;
    ProgressBar progB1, progB2;
    EditText editTextSearch;
    ListView lvJobs;
    ConstraintLayout cl1, cl2, notFound;

    // Retrofit
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://jobs.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    final Requests requests = retrofit.create(Requests.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        error = findViewById(R.id.textViewError);
        progB1 = findViewById(R.id.progressBar1);
        progB2 = findViewById(R.id.progressBar2);
        editTextSearch = findViewById(R.id.editTextSearch);
        cl1 = findViewById(R.id.constraintLayout1);
        cl2 = findViewById(R.id.constraintLayout2);
        notFound = findViewById(R.id.constraintLayoutNotFound);

        title1 = findViewById(R.id.textViewTitle1);
        location1 = findViewById(R.id.textViewLocation1);
        description1 = findViewById(R.id.textViewDescription1);
        image1 = findViewById(R.id.imageViewIcon1);

        title2 = findViewById(R.id.textViewTitle2);
        location2 = findViewById(R.id.textViewLocation2);
        description2 = findViewById(R.id.textViewDescription2);
        image2 = findViewById(R.id.imageViewIcon2);

        editTextSearch.addTextChangedListener(textWatcher);

        // lvJobs = findViewById(R.id.listView);
        // ArrayAdapter<String> adapter = new ArrayList<String>(this, android.R.layout.simple_list_item_1, test);
        // lvJobs.setAdapter(adapter);

        /* Вообщем я совершенно не умею пользоваться списками, у меня получается полный бред
        В интернете вчера (20 февраля) ничего полезного не нашел, особенно как делать такие сложные списки
        Там были самые примитивные, поэтому вот так вот

        Что касается поиска, я знаю как это делать, но т.к. я не умею работать со списками, не могу реализовать
        Напишу код в комментариях
        Мы делаем тот же самый Get запрос: @GET("positions.json?search={searchText}")
        Call <List<GetJobsList>> getJobs(@Path("searchText")String searchText);
        После, нам просто надо будет взять текст с editText и закинуть в функцию, которая принимает значение textSearch
        ---
        P.s. спустя 3 часа. Я все же пробую сделать поиск с помощью TextWatcher */

        //Покажу впринципе что я могу инфу спарсить
        parceJobs();
    }

    private void parceJobs() {

        Call<List<GetJobsList>> call = requests.getJobs();

        call.enqueue(new Callback<List<GetJobsList>>() {
            @Override
            public void onResponse(Call<List<GetJobsList>> call, Response<List<GetJobsList>> response) {

                if(!response.isSuccessful()){
                    error.setText("Code: " + response.code());
                    Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                // First Card
                img_url1 = response.body().get(0).getCompany_logo();
                Glide.with(MainActivity.this).load(img_url1).into(image1);
                title1.setText(response.body().get(0).getCompany());
                location1.setText(response.body().get(0).getLocation());
                descriptionS1 = response.body().get(0).getDescription();
                description1.setText(Html.fromHtml(descriptionS1));

                putTitle1 = response.body().get(0).getTitle();
                putLocation1 = response.body().get(0).getLocation();
                date1 = response.body().get(0).getTime();


                // Second card
                img_url2 = response.body().get(1).getCompany_logo();
                Glide.with(MainActivity.this).load(img_url2).into(image2);
                title2.setText(response.body().get(1).getCompany());
                location2.setText(response.body().get(1).getLocation());
                descriptionS2 = response.body().get(1).getDescription();
                description2.setText(Html.fromHtml(descriptionS2));

                putTitle2 = response.body().get(1).getTitle();
                putLocation2 = response.body().get(1).getLocation();
                date2 = response.body().get(1).getTime();

                // Progress complete
                progB1.setVisibility(View.INVISIBLE);
                progB2.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<List<GetJobsList>> call, Throwable t) {
                Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
                errorS = t.getMessage();
                intent.putExtra("error", errorS);
                startActivity(intent);
            }
        });
    }

    public void gotoFavorite(View view) {
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
    }

    public void job1(View view) {
        number = 1;
        Intent intent  = new Intent(this, JobActivity.class);
        intent.putExtra("number", number);
        intent.putExtra("title1", putTitle1);
        intent.putExtra("location1", putLocation1);
        intent.putExtra("description1", descriptionS1);
        intent.putExtra("time1", date1);
        intent.putExtra("image1", img_url1);
        startActivity(intent);
    }

    public void job2(View view) {
        number = 2;
        Intent intent  = new Intent(this, JobActivity.class);
        intent.putExtra("number", number);
        intent.putExtra("title2", putTitle2);
        intent.putExtra("location2", putLocation2);
        intent.putExtra("description2", descriptionS2);
        intent.putExtra("time2", date2);
        intent.putExtra("image2", img_url2);
        startActivity(intent);
    }

    public void dialog(View view) {
        String[] variants = {"None", "Full time", "Part Time"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Employment type")
                /* .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "You click yes!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "You click no :(", Toast.LENGTH_SHORT).show();
                    }
                }) */
                .setMultiChoiceItems(variants, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Toast.makeText(MainActivity.this, "Шакир не умеет делать Progress Dialog", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            searchText = editTextSearch.getText().toString().trim();

            // Возможно реализовал что-то не правильно, поэтому приложение крашается
            // Если будет приходит ошибка, отображается то что ничего не найдено!

            Call<List<GetJobsList>> call = requests.getJobsSearch(searchText);

            call.enqueue(new Callback<List<GetJobsList>>() {
                @Override
                public void onResponse(Call<List<GetJobsList>> call, Response<List<GetJobsList>> response) {
                    if(!response.isSuccessful()){
                        error.setText("Code: " + response.code());
                        Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    progB1.setVisibility(View.VISIBLE);
                    progB2.setVisibility(View.VISIBLE);

                    // First Card
                    img_url1 = response.body().get(0).getCompany_logo();
                    Glide.with(MainActivity.this).load(img_url1).into(image1);
                    title1.setText(response.body().get(0).getCompany());
                    location1.setText(response.body().get(0).getLocation());
                    descriptionS1 = response.body().get(0).getDescription();
                    description1.setText(Html.fromHtml(descriptionS1));

                    putTitle1 = response.body().get(0).getTitle();
                    putLocation1 = response.body().get(0).getLocation();
                    date1 = response.body().get(0).getTime();


                    // Second card
                    img_url2 = response.body().get(1).getCompany_logo();
                    Glide.with(MainActivity.this).load(img_url2).into(image2);
                    title2.setText(response.body().get(1).getCompany());
                    location2.setText(response.body().get(1).getLocation());
                    descriptionS2 = response.body().get(1).getDescription();
                    description2.setText(Html.fromHtml(descriptionS2));

                    putTitle2 = response.body().get(1).getTitle();
                    putLocation2 = response.body().get(1).getLocation();
                    date2 = response.body().get(1).getTime();

                    // Progress complete
                    progB1.setVisibility(View.INVISIBLE);
                    progB2.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<List<GetJobsList>> call, Throwable t) {
                    cl1.setVisibility(View.INVISIBLE);
                    cl2.setVisibility(View.INVISIBLE);
                    notFound.setVisibility(View.VISIBLE);
                }
            });
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
