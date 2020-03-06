package com.ronggosukowati.hisan.services;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dev on 10/2/17.
 */

public class ApiBuilder {

    public static BaseApiService call(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .build();
                        return chain.proceed(request);
                    }
                });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hisan.id/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit.create(BaseApiService.class);
    }

}
