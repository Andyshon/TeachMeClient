package com.teachme.teachmeclient.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andyshon on 17.01.18.
 */

public class CourseProgress {
    public CourseProgress(String name, String userId, String courseId){
        this.name = name;
        this.userId = userId;
        this.courseId = courseId;
        this.isStarted = true;
    }

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

    @SerializedName("userId")
    @Expose(serialize = false)
    private String userId;

    @SerializedName("courseId")
    @Expose(serialize = false)
    private String courseId;

    @SerializedName("isDone")
    @Expose
    private boolean isDone;

    @SerializedName("isStarted")
    @Expose
    private boolean isStarted;

    @SerializedName("name")
    @Expose
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getVersion() {
        return version;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public boolean isDone() {
        return isDone;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setDone(boolean done) {
        isDone = done;
    }



    public void setStarted(boolean started) {
        isStarted = started;
    }
}
