package com.ronggosukowati.hisan.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VillagesData {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("district_id")
    @Expose
    private String district_id;

    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
