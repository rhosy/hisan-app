package com.ronggosukowati.hisan.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ronggosukowati.hisan.activity.LoginActivity;
import com.ronggosukowati.hisan.model.UserData;

/**
 * Created by dev on 9/15/17.
 */

public class SharePrefmanager {

    private static final String SHARED_PREF_NAME="hisan";
    private static final String KEY_ID_USER="keyiduser";
    private static final String KEY_NAMA="keynama";
    private static final String KEY_EMAIL="keyemail";
    private static final String KEY_USER_LEVEL="keyuserlevel";
    private static final String KEY_TOKEN="keytoken";

    private static SharePrefmanager mInstance;
    private static Context mCtx;

    private SharePrefmanager(Context mContext){
        mCtx = mContext;
    }

    public static synchronized SharePrefmanager getInstance(Context context){
        if (mInstance == null){
            mInstance = new SharePrefmanager(context);
        }

        return mInstance;
    }

    public void userLogin(UserData user){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID_USER, user.getId());
        editor.putString(KEY_NAMA, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_USER_LEVEL, user.getLevel());
        editor.putString(KEY_TOKEN, user.getApiToken());

        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;

    }

    public UserData getUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserData(
                sharedPreferences.getInt(KEY_ID_USER, 0),
                sharedPreferences.getString(KEY_NAMA, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_USER_LEVEL, null),
                sharedPreferences.getString(KEY_TOKEN, null)
        );
    }



    public void logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent in = new Intent(mCtx, LoginActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mCtx.startActivity(in);
    }

}
