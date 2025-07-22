package Signup;
import com.google.gson.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import User.User;

/**
 * Responsible for reading and writing to the json file. Any checks that
 * are made for the json file will be done through this class.
 */
public class UserDataHandler {

    private JsonObject data;
    private String filePath;

    public UserDataHandler(String filePath) throws FileNotFoundException {

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

    public String [][] getArray(){
        JsonArray array = data.getAsJsonArray("Users");
        String[][] result = new String[array.size()][2];
        for (int i = 0; i < array.size(); i++) {
            JsonObject user = array.get(i).getAsJsonObject();
            result[i][0] = user.get("username").getAsString();
            result[i][1] = user.get("pwd").getAsString();
        }

        return result;

    }

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

    public static void main(String[] args) throws IOException {
        UserDataHandler handler = new UserDataHandler("src/main/java/Signup/users.json");
        System.out.println(handler.hasUser("alice"));
        System.out.println(handler.hasUser("bob"));
        System.out.println(handler.hasUser("drake"));
        System.out.println(handler.hasUser("billy"));
        handler.addUser("chuck", "3444");

    }
}
