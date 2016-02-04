package com.nguyennk.newsdemo.business;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by nguye on 2/1/2016.
 */
public class NewYorkTimesApiService {
    private static final String BASE_URL = "http://api.nytimes.com/svc/search/v2/";
    private static final Gson GSON;
    private static final HttpLoggingInterceptor INTERCEPTOR;
    private static final OkHttpClient CLIENT;
    private static final Retrofit RETROFIT;

    static {
        GSON = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
        INTERCEPTOR = new HttpLoggingInterceptor();
        INTERCEPTOR.setLevel(HttpLoggingInterceptor.Level.BODY);

        CLIENT = new OkHttpClient.Builder().addInterceptor(INTERCEPTOR).build();
        RETROFIT = new Retrofit.Builder().baseUrl(BASE_URL).client(CLIENT).addConverterFactory(GsonConverterFactory.create(GSON)).build();
    }

    public static NewYorkTimesApiEndpoint getApiService() {
        return RETROFIT.create(NewYorkTimesApiEndpoint.class);
    }
}
