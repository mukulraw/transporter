package com.mukul.onnwaytransporter.profilePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("gst")
    @Expose
    private String gst;
    @SerializedName("ba_verify")
    @Expose
    private String baVerify;
    @SerializedName("fa_verify")
    @Expose
    private String faVerify;
    @SerializedName("bd_verify")
    @Expose
    private String bdVerify;
    @SerializedName("fd_verify")
    @Expose
    private String fdVerify;
    @SerializedName("br_verify")
    @Expose
    private String brVerify;
    @SerializedName("fr_verify")
    @Expose
    private String frVerify;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("back_aadhar")
    @Expose
    private String backAadhar;
    @SerializedName("front_aadhar")
    @Expose
    private String frontAadhar;
    @SerializedName("back_driving")
    @Expose
    private String backDriving;
    @SerializedName("front_driving")
    @Expose
    private String frontDriving;
    @SerializedName("back_registration")
    @Expose
    private String backRegistration;
    @SerializedName("front_registration")
    @Expose
    private String frontRegistration;
    @SerializedName("created")
    @Expose
    private String created;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getBaVerify() {
        return baVerify;
    }

    public void setBaVerify(String baVerify) {
        this.baVerify = baVerify;
    }

    public String getFaVerify() {
        return faVerify;
    }

    public void setFaVerify(String faVerify) {
        this.faVerify = faVerify;
    }

    public String getBdVerify() {
        return bdVerify;
    }

    public void setBdVerify(String bdVerify) {
        this.bdVerify = bdVerify;
    }

    public String getFdVerify() {
        return fdVerify;
    }

    public void setFdVerify(String fdVerify) {
        this.fdVerify = fdVerify;
    }

    public String getBrVerify() {
        return brVerify;
    }

    public void setBrVerify(String brVerify) {
        this.brVerify = brVerify;
    }

    public String getFrVerify() {
        return frVerify;
    }

    public void setFrVerify(String frVerify) {
        this.frVerify = frVerify;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBackAadhar() {
        return backAadhar;
    }

    public void setBackAadhar(String backAadhar) {
        this.backAadhar = backAadhar;
    }

    public String getFrontAadhar() {
        return frontAadhar;
    }

    public void setFrontAadhar(String frontAadhar) {
        this.frontAadhar = frontAadhar;
    }

    public String getBackDriving() {
        return backDriving;
    }

    public void setBackDriving(String backDriving) {
        this.backDriving = backDriving;
    }

    public String getFrontDriving() {
        return frontDriving;
    }

    public void setFrontDriving(String frontDriving) {
        this.frontDriving = frontDriving;
    }

    public String getBackRegistration() {
        return backRegistration;
    }

    public void setBackRegistration(String backRegistration) {
        this.backRegistration = backRegistration;
    }

    public String getFrontRegistration() {
        return frontRegistration;
    }

    public void setFrontRegistration(String frontRegistration) {
        this.frontRegistration = frontRegistration;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
