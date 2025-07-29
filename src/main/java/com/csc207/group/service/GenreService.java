package com.csc207.group.service;

import Cache.IGDBRequestCache;
import com.csc207.group.model.Genre;
import data_access.IGDBApiClient;
import kong.unirest.JsonNode;
import kong.unirest.JsonResponse;
import kong.unirest.json.JSONArray;

public class GenreService {
    private final IGDBApiClient apiClient = new IGDBApiClient(new IGDBRequestCache
            ("Cache/igdb_requests_cache.json"));


}
