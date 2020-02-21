package com.example.job;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Requests {

    @GET("positions.json")
    Call<List<GetJobsList>> getJobs();
}
