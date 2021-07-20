package com.etcmobileapps.evcilhayvansahiplenme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionCheck {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("feature")
    @Expose
    private String feature;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

}