package com.example.recipesapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe implements Parcelable {

    @SerializedName("title")
    @Expose
    private String mTitle;

    @SerializedName("publisher")
    @Expose
    private String mPublisher;


    @SerializedName("recipe_id")
    @Expose
    private String mRecipeId;

    @SerializedName("image_url")
    @Expose
    private String mImageUrl;

    @SerializedName("social_rank")
    @Expose
    private float mSocialRank;

    @SerializedName("source_url")
    @Expose
    private String mSourceUrl;

    public String getSourceUrl() {
        return mSourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        mSourceUrl = sourceUrl;
    }

    public Recipe(String title, String publisher, String recipeId, String imageUrl, float socialRank, String sourceUrl) {
        mTitle = title;
        mPublisher = publisher;
        mRecipeId = recipeId;
        mImageUrl = imageUrl;
        mSocialRank = socialRank;
        mSourceUrl = sourceUrl;
    }

    public Recipe() {
    }


    @Override
    public String toString() {
        return "Recipe{" +
                "mTitle='" + mTitle + '\'' +
                ", mPublisher='" + mPublisher + '\'' +
                ", mRecipeId='" + mRecipeId + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                ", mSocialRank=" + mSocialRank +
                ", mSourceUrl='" + mSourceUrl + '\'' +
                '}';
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public void setPublisher(String publisher) {
        mPublisher = publisher;
    }


    public String getRecipeId() {
        return mRecipeId;
    }

    public void setRecipeId(String recipeId) {
        mRecipeId = recipeId;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public float getSocialRank() {
        return mSocialRank;
    }

    public void setSocialRank(float socialRank) {
        mSocialRank = socialRank;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mPublisher);
        dest.writeString(this.mRecipeId);
        dest.writeString(this.mImageUrl);
        dest.writeFloat(this.mSocialRank);
        dest.writeString(this.mSourceUrl);
    }

    protected Recipe(Parcel in) {
        this.mTitle = in.readString();
        this.mPublisher = in.readString();
        this.mRecipeId = in.readString();
        this.mImageUrl = in.readString();
        this.mSocialRank = in.readFloat();
        this.mSourceUrl = in.readString();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
