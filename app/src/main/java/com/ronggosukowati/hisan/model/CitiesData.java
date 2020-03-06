package com.ronggosukowati.hisan.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CitiesData {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("province_id")
    @Expose
    private String province_id;

    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
