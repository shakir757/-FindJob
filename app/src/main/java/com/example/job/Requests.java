package com.example.job;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Requests {

    @GET("positions.json")
    Call<List<GetJobsList>> getJobs();

    @GET("positions.json?search={searchText}")
    Call <List<GetJobsList>> getJobsSearch(@Path("searchText")String searchText);
}
