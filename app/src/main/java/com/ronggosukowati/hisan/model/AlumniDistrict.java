package com.ronggosukowati.hisan.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlumniDistrict implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("meta")
    @Expose
    private String meta;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.cityId);
        dest.writeString(this.name);
        dest.writeString(this.meta);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public AlumniDistrict() {
    }

    protected AlumniDistrict(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.cityId = in.readString();
        this.name = in.readString();
        this.meta = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Parcelable.Creator<AlumniDistrict> CREATOR = new Parcelable.Creator<AlumniDistrict>() {
        @Override
        public AlumniDistrict createFromParcel(Parcel source) {
            return new AlumniDistrict(source);
        }

        @Override
        public AlumniDistrict[] newArray(int size) {
            return new AlumniDistrict[size];
        }
    };
}
