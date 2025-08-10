package com.csc207.group.cache;

import okhttp3.*;

import java.io.IOException;

public class FirebaseRestClient {
    private static final OkHttpClient client = new OkHttpClient();
    private final String baseUrl;

    public FirebaseRestClient() {
        this.baseUrl = "https://gamecentraldatabase-default-rtdb.firebaseio.com/";

    }

    public void putData(String path, String jsonData) {
        String url = baseUrl + path + ".json";
        RequestBody body = RequestBody.create(jsonData, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("PUT Response code: " + response.code());
            if (response.body() != null) {
                System.out.println("Response body: " + response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getData(String path) {
        String url = baseUrl + path + ".json";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 200 && response.body() != null) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean hasPath(String path) {
        String response = getData(path);
        return response != null && !response.equals("null");
    }

    public boolean deleteData(String path) {
        String url = baseUrl + path + ".json";

        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            int code = response.code();
            System.out.println("DELETE Response code: " + code);
            if (response.body() != null) {
                System.out.println("Response body: " + response.body().string());
            }
            // Firebase typically returns 200 on success (with body "null"); 204 is also OK.
            return code == 200 || code == 204;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }




}




