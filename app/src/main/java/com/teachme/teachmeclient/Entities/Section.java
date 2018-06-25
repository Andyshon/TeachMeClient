package com.teachme.teachmeclient.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andyshon on 15.12.2017.
 */

public class Section {

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

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("courseId")
    @Expose
    private String courseId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

}