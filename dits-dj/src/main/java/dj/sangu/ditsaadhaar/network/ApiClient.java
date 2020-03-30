package dj.sangu.ditsaadhaar.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import dj.sangu.ditsaadhaar.utils.URLs;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static Retrofit retrofit = null;
     static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static ApiInterface getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URLs.Url_Api)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }
}
