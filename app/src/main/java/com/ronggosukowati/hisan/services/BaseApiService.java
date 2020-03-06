package com.ronggosukowati.hisan.services;

import com.ronggosukowati.hisan.model.Alumni;
import com.ronggosukowati.hisan.model.Cities;
import com.ronggosukowati.hisan.model.Districts;
import com.ronggosukowati.hisan.model.GeneralResponse;
import com.ronggosukowati.hisan.model.Provincies;
import com.ronggosukowati.hisan.model.User;
import com.ronggosukowati.hisan.model.Villages;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dev on 10/2/17.
 */

public interface BaseApiService {

    @FormUrlEncoded
    @POST("api/login")
    Call<User> loginUser(@Field("email") String email,
                         @Field("password") String password);

    @GET("api/cities")
    Call<Cities> getCities();

    @GET("api/provinces")
    Call<Provincies> getProvincies();

    @GET("api/get-city")
    Call<Cities> getCity(@Query("province_id") Integer province_id);

    @GET("api/get-district")
    Call<Districts> getDistrict(@Query("city_id") Integer city_id);

    @GET("api/get-village")
    Call<Villages> getVillage(@Query("district_id") Integer district_id);

    @FormUrlEncoded
    @POST("api/add-data")
    Call<GeneralResponse> addData(@Field("nik") String nik,
                                  @Field("name") String name,
                                  @Field("tmp_lahir") String tmp_lahir,
                                  @Field("tgl_lahir") String tgl_lahir,
                                  @Field("phone") String phone,
                                  @Field("province_id") String province_id,
                                  @Field("city_id") String city_id,
                                  @Field("district_id") String district_id,
                                  @Field("village_id") String village_id,
                                  @Field("alamat") String alamat,
                                  @Field("tahun_masuk") String tahun_masuk,
                                  @Field("tahun_keluar") String tahun_keluar,
                                  @Field("keterangan") String keterangan,
                                  @Field("token") String token,
                                  @Field("foto") String foto);

    @GET("api/data")
    Call<Alumni> getData(@Query("token") String token);

    @DELETE("api/delete-data")
    Call<GeneralResponse> deleteData(@Query("id") int id);

    @FormUrlEncoded
    @POST("api/edit-data/{id}")
    Call<GeneralResponse> editData(@Path("id") Integer id,
                                   @Field("nik") String nik,
                                   @Field("name") String name,
                                   @Field("tmp_lahir") Integer tmp_lahir,
                                   @Field("tgl_lahir") String tgl_lahir,
                                   @Field("phone") String phone,
                                   @Field("province_id") Integer province_id,
                                   @Field("city_id") Integer city_id,
                                   @Field("district_id") Integer district_id,
                                   @Field("village_id") Long village_id,
                                   @Field("alamat") String alamat,
                                   @Field("tahun_masuk") Integer tahun_masuk,
                                   @Field("tahun_keluar") Integer tahun_keluar,
                                   @Field("keterangan") String keterangan);

    @FormUrlEncoded
    @POST("api/update-foto/{id}")
    Call<GeneralResponse> updatePhoto(@Path("id") Integer id,
                                      @Field("foto") String foto);
}

