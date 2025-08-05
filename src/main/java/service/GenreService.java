package service;

import cache.IGDBFirebaseAPICache;
import data_access.IGDBApiClient;

public class GenreService {
    private final IGDBApiClient apiClient = new IGDBApiClient(new IGDBFirebaseAPICache());


}
