//package com.csc207.group.data_access;
//
//import com.kwabenaberko.newsapilib.NewsApiClient;
//import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
//import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
//
//public class NewsClient {
//    private static final String API_KEY = "72006c1e793c44c28c9d66ed4214819d";
//    NewsApiClient newsApiClient = new NewsApiClient(API_KEY);
//
//    public void getGamingNews() {
//        newsApiClient.getTopHeadlines(
//                new TopHeadlinesRequest.Builder()
//                        .q("gaming")
//                        .language("en")
//                        .build(),
//                new NewsApiClient.ArticlesResponseCallback() {
//                    @Override
//                    public void onSuccess(ArticleResponse response) {
//                        System.out.println(response.getArticles().get(0).getTitle());
//                    }
//
//                    @Override
//                    public void onFailure(Throwable throwable) {
//                        System.out.println(throwable.getMessage());
//                    }
//                }
//        );
//    }
//}

package com.csc207.group.data_access;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class NewsClient {
    private static final String API_KEY = "72006c1e793c44c28c9d66ed4214819d";

    public JsonNode getGamingNews() {
        HttpResponse<JsonNode> response = Unirest.get("https://newsapi.org/v2/everything?q=gaming&apiKey=" + API_KEY)
                .asJson();

        return response.getBody();
    }
}