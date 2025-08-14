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
