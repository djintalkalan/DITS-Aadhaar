package dj.sangu.ditsaadhaar.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import dj.sangu.ditsaadhaar.modal.AdminModal;
import dj.sangu.ditsaadhaar.modal.OperatorModal;


public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "dj.sangu.sharedpref";
    private static final String USER_LOGIN_KEY = "USER_LOGIN_KEY";
    private static final String ADMIN_LOGIN_KEY = "ADMIN_LOGIN_KEY";
    private static final String LOGIN_TYPE = "LOGIN_TYPE";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }


    public void userLogin(OperatorModal customerData) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(customerData);
        editor.putString(USER_LOGIN_KEY, json);
        editor.putInt(LOGIN_TYPE, Constants.USER_LOGGED_IN_KEY);
        editor.apply();
    }
    public void adminLogin(AdminModal adminModal) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(adminModal);
        editor.putString(ADMIN_LOGIN_KEY, json);
        editor.putInt(LOGIN_TYPE, Constants.ADMIN_LOGGED_IN_KEY);
        editor.apply();
    }
    public OperatorModal getCustomer() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(USER_LOGIN_KEY, null);
        return new Gson().fromJson(json, OperatorModal.class);
    }

    public AdminModal getAdmin() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(ADMIN_LOGIN_KEY, null);
        return new Gson().fromJson(json, AdminModal.class);
    }

    public int isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(LOGIN_TYPE, Constants.LOGGED_OUT_KEY);
    }

    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


}
