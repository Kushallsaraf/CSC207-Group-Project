package data_access;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class IGDBApiClient {

    private static final String CLIENT_ID = "8iht4bl3vlgdgy2j6jtp9l63ky4ee1";
    private static final String ACCESS_TOKEN = "r7scsy47par3y00mi8wha41bayl6fm";
    private static final String BASE_URL = "https://api.igdb.com/v4/";

    public JsonNode callEndpoint(String endpoint, String body) {
        HttpResponse<JsonNode> response = Unirest.post(BASE_URL + endpoint)
                .header("Client-ID", CLIENT_ID)
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .header("Accept", "application/json")
                .body(body)
                .asJson();

        if (response.getStatus() != 200) {
            throw new RuntimeException("API call failed with status: " + response.getStatus());
        }

        return response.getBody();
    }

    public void shutdown() {
        Unirest.shutDown();
    }
}
