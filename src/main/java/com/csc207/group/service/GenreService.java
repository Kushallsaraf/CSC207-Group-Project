//package com.csc207.group.service;
//
//import com.csc207.group.model.Genre;
//import data_access.IGDBApiClient;
//import kong.unirest.JsonNode;
//import kong.unirest.JsonResponse;
//import kong.unirest.json.JSONArray;
//
//public class GenreService {
//    private final IGDBApiClient apiClient = new IGDBApiClient();
//
//    public Genre getGamesByGenre(String genre) {
//        JsonNode json = apiClient.getGamesByGenre(genre);
//        JSONArray array = json.getArray();
//    }
//}
