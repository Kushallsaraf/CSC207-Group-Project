package com.csc207.group.service;

import Cache.IGDBFirebaseAPICache;
import data_access.IGDBApiClient;

public class GenreService {
    private final IGDBApiClient apiClient = new IGDBApiClient(new IGDBFirebaseAPICache());


}
