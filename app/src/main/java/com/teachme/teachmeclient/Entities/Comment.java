package com.teachme.teachmeclient.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by andyshon on 09.01.2018.
 */

public class Comment {

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

    @SerializedName("commentText")
    @Expose
    private String commentText;

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("exerciseId")
    @Expose
    private String exerciseId;

    @SerializedName("user")
    @Expose(serialize = false)
    private User user;

    @SerializedName("commentRatings")
    @Expose(serialize = false)
    private List<CommentRating> commentRatings = null;


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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CommentRating> getCommentRatings() {
        return commentRatings;
    }

    public void setCommentRatings(List<CommentRating> commentRatings) {
        this.commentRatings = commentRatings;
    }

    public int CalculateOverallRating() {
        int sum = 0;
        for (CommentRating cr : commentRatings) {
            sum += cr.getRating();
        }
        return sum;
    }
}