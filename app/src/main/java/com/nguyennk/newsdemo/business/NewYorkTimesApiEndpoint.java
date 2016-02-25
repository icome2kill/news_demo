package com.nguyennk.newsdemo.business;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nguye on 2/1/2016.
 */
public interface NewYorkTimesApiEndpoint {
    @GET("articlesearch.json?sort=newest&fl=headline,web_url,snippet,source,multimedia,pub_date,section_name&hl=true&api-key=e86955bb5e9bb5e338e287d91b70e238:9:74248941")
    Call<NewYorkTimesApiResponse> searchArticle(@Query("q") String query, @Query("page") int page);

    @GET("articlesearch.json?sort=newest&fl=headline,web_url,snippet,source,multimedia,pub_date,section_name&hl=true&api-key=e86955bb5e9bb5e338e287d91b70e238:9:74248941")
    Call<NewYorkTimesApiResponse> getAllArticle(@Query("fq") String filter, @Query("page") int page);

    /**
     * Created by nguye on 2/1/2016.
     */
    class Factory {
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
}
