package com.etcmobileapps.evcilhayvansahiplenme.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ad_ownerid")
    @Expose
    private String adOwnerid;
    @SerializedName("ad_ownername")
    @Expose
    private String adOwnername;
    @SerializedName("ad_ownertelephone")
    @Expose
    private String adOwnertelephone;
    @SerializedName("ad_type")
    @Expose
    private String adType;
    @SerializedName("ad_name")
    @Expose
    private String adName;
    @SerializedName("ad_detail")
    @Expose
    private String adDetail;
    @SerializedName("ad_category")
    @Expose
    private String adCategory;
    @SerializedName("ad_altcategory")
    @Expose
    private String adAltcategory;
    @SerializedName("ad_age")
    @Expose
    private String adAge;
    @SerializedName("confirmation")
    @Expose
    private Integer confirmation;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("ad_image")
    @Expose
    private String adImage;
    @SerializedName("ad_image2")
    @Expose
    private String adImage2;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("ad_sex")
    @Expose
    private String adSex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdOwnerid() {
        return adOwnerid;
    }

    public void setAdOwnerid(String adOwnerid) {
        this.adOwnerid = adOwnerid;
    }

    public String getAdOwnername() {
        return adOwnername;
    }

    public void setAdOwnername(String adOwnername) {
        this.adOwnername = adOwnername;
    }

    public String getAdOwnertelephone() {
        return adOwnertelephone;
    }

    public void setAdOwnertelephone(String adOwnertelephone) {
        this.adOwnertelephone = adOwnertelephone;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getAdDetail() {
        return adDetail;
    }

    public void setAdDetail(String adDetail) {
        this.adDetail = adDetail;
    }

    public String getAdCategory() {
        return adCategory;
    }

    public void setAdCategory(String adCategory) {
        this.adCategory = adCategory;
    }

    public String getAdAltcategory() {
        return adAltcategory;
    }

    public void setAdAltcategory(String adAltcategory) {
        this.adAltcategory = adAltcategory;
    }

    public String getAdAge() {
        return adAge;
    }

    public void setAdAge(String adAge) {
        this.adAge = adAge;
    }

    public Integer getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(Integer confirmation) {
        this.confirmation = confirmation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAdImage() {
        return adImage;
    }

    public void setAdImage(String adImage) {
        this.adImage = adImage;
    }

    public String getAdImage2() {
        return adImage2;
    }

    public void setAdImage2(String adImage2) {
        this.adImage2 = adImage2;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAdSex() {
        return adSex;
    }

    public void setAdSex(String adSex) {
        this.adSex = adSex;
    }

}