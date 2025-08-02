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

    /** Checks the cache to see if the same request has already been made.
     *
     * @param requestType
     * @param requestKey
     * @return
     */
    public boolean hasRequest(String requestType, String requestKey){
        if (data.has(requestType)) {
            JsonObject inner = data.getAsJsonObject(requestType);
            return inner.has(requestKey);
        }
        return false;

    }

    /**
     *
     * @param requestType
     * @param requestKey
     * @param response
     * @return
     */
    public JsonNode cacheResponse(String requestType, String requestKey, JsonNode response) {
        //TODO: some API responses might not require us to work with JsonNode but instead some other
        // object. In that case you will have to overload this method with the right parameters.

        // TODO: If your API client function returns a JsonNode, then update this method by adding another case to
        //  the switch statement
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

    /**
     *
     * @param requestType : the type of request being made
     * @param requestKey : the unique aspect of that specific request, for example the id of a game or dev
     * @param responseType : the return value of the api client function
     * @return the desired return object of the api client function converted to string so that the api client function
     * can create the desired object from the string
     */
    public String getResponse(String requestType, String requestKey, String responseType) {
        // TODO: If your api client function returns a new type of object, then you'll have to
        //  add another case to this switch statement. For example if your function returns an integer,
        //  then in this method you'll have to add a case IGDBApiClient.INTEGER and return a string
        //  representation which importantly you'll have to convert back to an integer in the api client method
        //


        if (!data.has(requestType)) return null;
        JsonObject section = data.getAsJsonObject(requestType);
        if (!section.has(requestKey)) return null;


        switch (responseType) {
            case IGDBApiClient.JSON_NODE_UNIREST:
                return section.toString();


            default:
                throw new IllegalArgumentException("Unsupported response type");
        }
    }


    /**
     *
     * @param filePath : filepath of cache file
     * @return the file contents as a JsonObect (gson)
     */
    private JsonObject loadData(String filePath) {

        try (FileReader reader = new FileReader(filePath)) {
            JsonElement element = JsonParser.parseReader(reader);
            if (element != null && element.isJsonObject()) {
                return element.getAsJsonObject();
            } else {
                System.out.println("Cache file is empty or not a JSON object — creating new JsonObject");
                return new JsonObject();
            }
        } catch (IOException e) {
            System.out.println("Failed to read cache file: " + e.getMessage());
            return new JsonObject();
        }


    }

    /** Overwrites cache file with current version of this.data
     *
     */
    private void updateCacheFile(){

        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(data, writer); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}