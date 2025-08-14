package com.csc207.group.cache;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FirebaseRestClient {
    public static final String STRING_DOT_JSON = ".json";
    public static final int SUCCESS_CODE_200 = 200;
    public static final int SUCCESS_CODE_204 = 204;
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();
    private final String baseUrl;

    public FirebaseRestClient() {
        this.baseUrl = "https://gamecentraldatabase-default-rtdb.firebaseio.com/";

    }

    public void putData(String path, String jsonData) {
        String url = baseUrl + path + STRING_DOT_JSON;
        RequestBody body = RequestBody.create(jsonData, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            System.out.println("PUT Response code: " + response.code());
            if (response.body() != null) {
                System.out.println("Response body: " + response.body().string());
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getData(String path) {
        String url = baseUrl + path + STRING_DOT_JSON;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.code() == SUCCESS_CODE_200 && response.body() != null) {
                return response.body().string();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public boolean hasPath(String path) {
        String response = getData(path);
        return response != null && !"null".equals(response);
    }

    public boolean deleteData(String path) {
        String url = baseUrl + path + STRING_DOT_JSON;

        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            int code = response.code();
            System.out.println("DELETE Response code: " + code);
            if (response.body() != null) {
                System.out.println("Response body: " + response.body().string());
            }
            // Firebase typically returns 200 on success (with body "null"); 204 is also OK.
            return code == SUCCESS_CODE_200 || code == SUCCESS_CODE_204;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
