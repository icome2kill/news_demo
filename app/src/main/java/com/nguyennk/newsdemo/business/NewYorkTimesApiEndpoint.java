package com.nguyennk.newsdemo.business;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nguye on 2/1/2016.
 */
public interface NewYorkTimesApiEndpoint {
    @GET("articlesearch.json?sort=newest&fl=headline,web_url,snippet,source,multimedia,pub_date,section_name&hl=true&api-key=e86955bb5e9bb5e338e287d91b70e238:9:74248941")
    Call<NewYorkTimesApiResponse> searchArticle(@Query("q") String query, @Query("page") int page);

    @GET("articlesearch.json?sort=newest&fl=headline,web_url,snippet,source,multimedia,pub_date,section_name&hl=true&api-key=e86955bb5e9bb5e338e287d91b70e238:9:74248941")
    Call<NewYorkTimesApiResponse> getAllArticle(@Query("page") int page);
}
