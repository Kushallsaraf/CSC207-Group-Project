package Signup;
import com.google.gson.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import User.User;

/**
 * Responsible for reading and writing to the json file. Any time information
 * is written to or read from the user data file will be done through
 * this class.
 *
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


}
