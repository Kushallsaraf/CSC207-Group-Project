package com.csc207.group.cache;

import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;

public class FirebaseApiCache implements ApiCache {
    public static final String BACKSLASH = "/";
    private final FirebaseRestClient firebaseRestClient;
    private final String basePath;

    public FirebaseApiCache(String basePath) {
        this.firebaseRestClient = new FirebaseRestClient();
        this.basePath = basePath;

    }

    @Override
    public void cacheJsonString(String requestType, String requestKey, String jsonString) {
        firebaseRestClient.putData(basePath + BACKSLASH + requestType + BACKSLASH + requestKey, jsonString);
    }

    @Override
    public boolean hasRequest(String requestType, String requestKey) {
        return firebaseRestClient.hasPath(basePath + BACKSLASH + requestType + BACKSLASH + requestKey);

    }

    @Override
    public void cacheJsonNode(String requestType, String requestKey, JsonNode response) {
        JSONArray array = response.getArray();
        firebaseRestClient.putData(basePath + BACKSLASH + requestType + BACKSLASH + requestKey, array.toString());

    }

    @Override
    public void cacheInt(String requestType, String requestKey, int value) {
        firebaseRestClient.putData(basePath + BACKSLASH + requestType + BACKSLASH + requestKey, String.valueOf(value));
    }

    @Override
    public String getResponse(String requestType, String requestKey) {
        String result = firebaseRestClient.getData(basePath + BACKSLASH + requestType + BACKSLASH + requestKey);
        if (result != null && !"null".equals(result)) {
            return result;
        }
        else {
            return null;
        }

    }

    @Override
    public void cacheString(String requestType, String requestKey, String response) {
        firebaseRestClient.putData(basePath + BACKSLASH + requestType + BACKSLASH + requestKey, response);
    }
}
