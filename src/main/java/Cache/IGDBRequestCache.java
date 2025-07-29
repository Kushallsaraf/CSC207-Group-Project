package Cache;

import com.google.gson.*;
import kong.unirest.JsonNode;
import data_access.IGDBApiClient;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IGDBRequestCache {
    private JsonObject data;
    private String filePath;
    public static final String JSON_NODE = "JsonNode";


   public IGDBRequestCache(String filePath){
       this.filePath = filePath;
       this.data = loadData(filePath);
   }


    public boolean hasRequest(String requestType, String requestKey){
        if (data.has(requestType)) {
            JsonObject inner = data.getAsJsonObject(requestType);
            return inner.has(requestKey);
        }
        return false;

    }

    public JsonNode cacheResponse(String requestType, String requestKey, JsonNode response) {
       switch (requestType){
           case (IGDBApiClient.GAMES_BY_NAME_REQUEST):
               JsonObject section;
               if (data.has(requestType)) {
                   section = data.getAsJsonObject(requestType); //key is request key, and value is jsonarray


               } else {
                   section = new JsonObject();
               }

               JSONArray orgArray = response.getArray();
               JsonArray gsonArray = JsonParser.parseString(orgArray.toString()).getAsJsonArray();
               section.add(requestKey, gsonArray);
               data.add(requestType, section);
               updateCacheFile();


               return response;





       }
return new JsonNode("");

       }





    private JsonObject loadData(String filePath) {

        try (FileReader reader = new FileReader(filePath)) {
            JsonElement element = JsonParser.parseReader(reader);
            if (element != null && element.isJsonObject()) {
                return element.getAsJsonObject();
            } else {
                System.out.println("Cache file is empty or not a JSON object — creating new JsonObject");
                return new JsonObject(); // fallback
            }
        } catch (IOException e) {
            System.out.println("Failed to read cache file: " + e.getMessage());
            return new JsonObject(); // fallback if file doesn't exist or is unreadable
        }


    }


    private void updateCacheFile(){

        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(data, writer); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getResponse(String requestType, String requestKey, String responseType) {

        if (!data.has(requestType)) return null;
        JsonObject section = data.getAsJsonObject(requestType);
        if (!section.has(requestKey)) return null;

        String raw = section.get(requestKey).toString();

        switch (responseType) {
            case IGDBApiClient.JSON_NODE_UNIREST:
                return new JsonNode(raw);

            default:
                throw new IllegalArgumentException("Unsupported response type");
        }
    }
}