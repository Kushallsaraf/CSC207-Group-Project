package com.csc207.group.cache;

import kong.unirest.JsonNode;

/**
 * Caches API calls by mapping a type of request to a unique request which maps to its unique response.
 */

public interface ApiCache {
    /**
     * Checks if the cache has the request.
     * @param requestType The type of the request.
     * @param requestKey The key identifying the request.
     * @return If the cache contains the request.
     */
    boolean hasRequest(String requestType, String requestKey);

    /**
     * Caches a JsonNode.
     * @param requestType The type of the request.
     * @param requestKey The key identifying the request.
     * @param response The JsonNode to store.
     */
    void cacheJsonNode(String requestType, String requestKey, JsonNode response);

    /**
     * Caches an int.
     * @param requestType The type of the request.
     * @param requestKey The key identifying the request.
     * @param value The int to store.
     */
    void cacheInt(String requestType, String requestKey, int value);

    /**
     * Gets the cached response.
     * @param requestType The type of the request.
     * @param requestKey The key identifying the request.
     * @return cached response as a string.
     */
    String getResponse(String requestType, String requestKey);

    /**
     * Caches JsonStrings.
     * @param requestType The type of the request.
     * @param requestKey The key identifying the request.
     * @param jsonString The jsonString to store.
     */
    void cacheJsonString(String requestType, String requestKey, String jsonString);

    /**
     * Caches Strings.
     * @param requestType The type of the request.
     * @param requestKey The key identifying the request.
     * @param response The String value to store.
     */
    void cacheString(String requestType, String requestKey, String response);
}
