package com.teachme.teachmeclient.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andyshon on 01.01.2018.
 */

public class ExerciseStudent {

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

    @SerializedName("studentAnswer")
    @Expose
    private String studentAnswer;

    @SerializedName("isDone")
    @Expose
    private Boolean isDone;

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("exerciseId")
    @Expose
    private String exerciseId;

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

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

}