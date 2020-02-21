package com.example.job;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetJobsList {

    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("company_logo")
    @Expose
    private String company_logo;

    @SerializedName("created_at")
    @Expose
    private String time;

    public GetJobsList(String company, String id, String location, String title, String description, String company_logo, String time) {
        this.company = company;
        this.id = id;
        this.location = location;
        this.title = title;
        this.description = description;
        this.company_logo = company_logo;
        this.time = time;
    }

    public String getCompany() {
        return company;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public String getTime() {
        return time;
    }
}
