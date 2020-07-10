package com.example.baking.POJOs;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Step implements Parcelable {

    //Global Variables
    private String shortDescription = "";
    private String description = "";
    private String videoURL = "";
    private String thumbnailURL = "";

    //Public Constructor
    public Step(String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    protected Step(Parcel in) {
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    //Getters
    public String getShortDescription() { return shortDescription; }

    public String getDescription() { return description; }

    public String getVideoURL() { return videoURL; }

    public String getThumbnailURL() { return thumbnailURL; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }
}
