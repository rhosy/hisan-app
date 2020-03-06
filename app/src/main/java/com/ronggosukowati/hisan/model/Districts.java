package com.ronggosukowati.hisan.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Districts {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<DistrictsData> districtsData;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DistrictsData> getDistrictsData() {
        return districtsData;
    }

    public void setDistrictsData(List<DistrictsData> districtsData) {
        this.districtsData = districtsData;
    }
}
