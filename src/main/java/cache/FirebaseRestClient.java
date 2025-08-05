package cache;

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


}




