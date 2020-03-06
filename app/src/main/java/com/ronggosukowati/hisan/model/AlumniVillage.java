package com.ronggosukowati.hisan.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlumniVillage implements Parcelable {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("district_id")
    @Expose
    private String districtId;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
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
        dest.writeString(this.districtId);
        dest.writeString(this.name);
        dest.writeString(this.meta);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public AlumniVillage() {
    }

    protected AlumniVillage(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.districtId = in.readString();
        this.name = in.readString();
        this.meta = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Parcelable.Creator<AlumniVillage> CREATOR = new Parcelable.Creator<AlumniVillage>() {
        @Override
        public AlumniVillage createFromParcel(Parcel source) {
            return new AlumniVillage(source);
        }

        @Override
        public AlumniVillage[] newArray(int size) {
            return new AlumniVillage[size];
        }
    };
}
