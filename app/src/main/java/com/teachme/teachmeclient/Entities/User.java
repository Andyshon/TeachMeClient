package com.teachme.teachmeclient.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.teachme.teachmeclient.Models.GlobalConstants;

/**
 * Created by andyshon on 04.12.17.
 */

public class User {

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

    @SerializedName("registerDate")
    @Expose
    private String registerDate;

    @SerializedName("completedCoursesCount")
    @Expose
    private int completedCoursesCount;

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("fullName")
    @Expose
    private String fullName;

    // UserRoles:
    // 0 - Student
    // 1 - Teacher
    // 2 - Admin
    @SerializedName("userRole")
    @Expose
    private int userRole;

    @SerializedName("avatarPath")
    @Expose
    private String avatarPath;

    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;

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

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public int getCompletedCoursesCount() {
        return completedCoursesCount;
    }

    public void setCompletedCoursesCount(int completedCoursesCount) {
        this.completedCoursesCount = completedCoursesCount;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public GlobalConstants.UserRole getUserRole() {
        return GlobalConstants.UserRole.valueOf(userRole);
    }

    public void setUserRole(GlobalConstants.UserRole userRole) {
        this.userRole = userRole.getValue();
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}