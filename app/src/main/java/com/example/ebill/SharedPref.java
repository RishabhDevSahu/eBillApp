package com.example.ebill;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private static boolean IS_LOGIN = false;
    private static final String PREF_NAME = "Welcome";
    private SharedPreferences pref;

    public SharedPref(Context context){
        pref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
    }

    public boolean getLoginInfo(){
        return IS_LOGIN;
    }

    public void setLoginInfo(boolean info){
        IS_LOGIN = info;
    }
}
