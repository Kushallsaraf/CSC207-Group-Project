package UserAuthentication;

import com.google.gson.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class JSONUserDataHandler implements UserDataHandler {

    private JsonObject data;
    private String filePath;

    public JSONUserDataHandler(String filePath) throws FileNotFoundException {

        FileReader reader = new FileReader(filePath);
        data = JsonParser.parseReader(reader).getAsJsonObject();
        this.filePath = filePath;

    }
    /**
     * checks if a user with the same username exists
     */
    public boolean hasUser(String username){
        return getUsers().containsKey(username);

    }

    public void addUser(String username, String password) throws IOException {
        if (!hasUser(username)){
            JsonObject newUser = new JsonObject();
            newUser.addProperty("username", username);
            newUser.addProperty("pwd", password);
            JsonArray usersArray = data.getAsJsonArray("Users");
            usersArray.add(newUser);
            data.add("Users", usersArray);
            try (Writer writer = new FileWriter(filePath)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(data, writer);
            }
        }
    }

    public boolean deleteUser(String username){
        if (hasUser(username)){
            JsonArray usersArray = data.getAsJsonArray("Users");
            JsonArray updatedArray = new JsonArray();
            for (JsonElement userElement : usersArray) {
                JsonObject userObj = userElement.getAsJsonObject();
                String currentUsername = userObj.get("username").getAsString();
                if (!currentUsername.equals(username)) {
                    updatedArray.add(userObj);}

            }
            data.add("Users", updatedArray);
            try (Writer writer = new FileWriter(filePath)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(data, writer);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        } return false;}








    public Map<String, User> getUsers(){
        Map<String, User> users = new HashMap<String, User>();
        JsonArray array = data.getAsJsonArray("Users");

        for (int i = 0; i < array.size(); i++) {
            JsonObject user = array.get(i).getAsJsonObject();
            users.put(user.get("username").getAsString(), new User(user.get("username").getAsString(),
                    user.get("pwd").getAsString()));
        }
        return users;

    }

    @Override
    public boolean usernameExists(String username) {
        return getUsers().containsKey(username);
    }

    @Override
    public void registerUser(String username, String hashedPassword) throws IOException {
        if (!hasUser(username)){
            JsonObject newUser = new JsonObject();
            newUser.addProperty("username", username);
            newUser.addProperty("pwd", hashedPassword);
            JsonArray usersArray = data.getAsJsonArray("Users");
            usersArray.add(newUser);
            data.add("Users", usersArray);
            try (Writer writer = new FileWriter(filePath)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(data, writer);
            }
        }

    }
}
