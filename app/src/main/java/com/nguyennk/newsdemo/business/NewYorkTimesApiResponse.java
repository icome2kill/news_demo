package com.nguyennk.newsdemo.business;

import com.google.gson.annotations.SerializedName;
import com.nguyennk.newsdemo.model.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguye on 2/1/2016.
 */
public class NewYorkTimesApiResponse {
    private Response response;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response {
        @SerializedName("docs")
        private List<Article> articles = new ArrayList<Article>();

        public List<Article> getArticles() {
            return articles;
        }

        public void setArticles(List<Article> articles) {
            this.articles = articles;
        }
    }
}
