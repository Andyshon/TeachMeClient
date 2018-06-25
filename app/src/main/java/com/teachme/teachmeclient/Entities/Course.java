package com.teachme.teachmeclient.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andyshon on 14.12.2017.
 */

public class Course {

    @SerializedName("deleted")
    @Expose(serialize = false)
    private boolean deleted;

    @SerializedName("updatedAt")
    @Expose(serialize = false)
    private String updatedAt;

    @SerializedName("createdAt")
    @Expose(serialize = false)
    private String createdAt;

    @SerializedName("version")
    @Expose(serialize = false)
    private String version;

    @SerializedName("id")
    @Expose(serialize = false)
    private String id;

    @SerializedName("days")
    @Expose
    private int days;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("keywords")
    @Expose
    private String keywords;

    @SerializedName("userId")
    @Expose
    private String userId;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}