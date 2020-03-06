package com.ronggosukowati.hisan.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlumniData implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nik")
    @Expose
    private Long nik;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("tmp_lahir")
    @Expose
    private Integer tmpLahir;
    @SerializedName("tgl_lahir")
    @Expose
    private String tglLahir;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("province_id")
    @Expose
    private Integer provinceId;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("district_id")
    @Expose
    private Integer districtId;
    @SerializedName("village_id")
    @Expose
    private Long villageId;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("tahun_masuk")
    @Expose
    private Integer tahunMasuk;
    @SerializedName("tahun_keluar")
    @Expose
    private Integer tahunKeluar;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("province")
    @Expose
    private AlumniProvince alumniProvince;
    @SerializedName("city")
    @Expose
    private AlumniCity alumniCity;
    @SerializedName("district")
    @Expose
    private AlumniDistrict alumniDistrict;
    @SerializedName("village")
    @Expose
    private AlumniVillage alumniVillage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getNik() {
        return nik;
    }

    public void setNik(Long nik) {
        this.nik = nik;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTmpLahir() {
        return tmpLahir;
    }

    public void setTmpLahir(Integer tmpLahir) {
        this.tmpLahir = tmpLahir;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Long getVillageId() {
        return villageId;
    }

    public void setVillageId(Long villageId) {
        this.villageId = villageId;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Integer getTahunMasuk() {
        return tahunMasuk;
    }

    public void setTahunMasuk(Integer tahunMasuk) {
        this.tahunMasuk = tahunMasuk;
    }

    public Integer getTahunKeluar() {
        return tahunKeluar;
    }

    public void setTahunKeluar(Integer tahunKeluar) {
        this.tahunKeluar = tahunKeluar;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    public AlumniProvince getAlumniProvince() {
        return alumniProvince;
    }

    public void setAlumniProvince(AlumniProvince alumniProvince) {
        this.alumniProvince = alumniProvince;
    }

    public AlumniCity getAlumniCity() {
        return alumniCity;
    }

    public void setAlumniCity(AlumniCity alumniCity) {
        this.alumniCity = alumniCity;
    }

    public AlumniDistrict getAlumniDistrict() {
        return alumniDistrict;
    }

    public void setAlumniDistrict(AlumniDistrict alumniDistrict) {
        this.alumniDistrict = alumniDistrict;
    }

    public AlumniVillage getAlumniVillage() {
        return alumniVillage;
    }

    public void setAlumniVillage(AlumniVillage alumniVillage) {
        this.alumniVillage = alumniVillage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.nik);
        dest.writeString(this.name);
        dest.writeValue(this.tmpLahir);
        dest.writeString(this.tglLahir);
        dest.writeString(this.phone);
        dest.writeValue(this.provinceId);
        dest.writeValue(this.cityId);
        dest.writeValue(this.districtId);
        dest.writeValue(this.villageId);
        dest.writeString(this.alamat);
        dest.writeValue(this.tahunMasuk);
        dest.writeValue(this.tahunKeluar);
        dest.writeString(this.keterangan);
        dest.writeString(this.foto);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeParcelable(this.alumniProvince, flags);
        dest.writeParcelable(this.alumniCity, flags);
        dest.writeParcelable(this.alumniDistrict, flags);
        dest.writeParcelable(this.alumniVillage, flags);
    }

    public AlumniData() {
    }

    protected AlumniData(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nik = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.tmpLahir = (Integer) in.readValue(Integer.class.getClassLoader());
        this.tglLahir = in.readString();
        this.phone = in.readString();
        this.provinceId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.cityId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.districtId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.villageId = (Long) in.readValue(Long.class.getClassLoader());
        this.alamat = in.readString();
        this.tahunMasuk = (Integer) in.readValue(Integer.class.getClassLoader());
        this.tahunKeluar = (Integer) in.readValue(Integer.class.getClassLoader());
        this.keterangan = in.readString();
        this.foto = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.alumniProvince = in.readParcelable(AlumniProvince.class.getClassLoader());
        this.alumniCity = in.readParcelable(AlumniCity.class.getClassLoader());
        this.alumniDistrict = in.readParcelable(AlumniDistrict.class.getClassLoader());
        this.alumniVillage = in.readParcelable(AlumniVillage.class.getClassLoader());
    }

    public static final Parcelable.Creator<AlumniData> CREATOR = new Parcelable.Creator<AlumniData>() {
        @Override
        public AlumniData createFromParcel(Parcel source) {
            return new AlumniData(source);
        }

        @Override
        public AlumniData[] newArray(int size) {
            return new AlumniData[size];
        }
    };
}

