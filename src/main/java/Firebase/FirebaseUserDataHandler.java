package Firebase;

import Cache.FirebaseRestClient;
import UserLoading.User;
import UserAuthentication.UserDataHandler;
import kong.unirest.json.JSONObject;

import java.io.IOException;

public class FirebaseUserDataHandler implements UserDataHandler {

    private final FirebaseRestClient client;

    public FirebaseUserDataHandler(FirebaseRestClient client) {
        this.client = client;
    }

    @Override
    public boolean usernameExists(String username) {
        return client.hasPath("Users/" + username);
    }

    @Override
    public void registerUser(String username, String hashedPassword) throws IOException {
        if (usernameExists(username)) {
            throw new IOException("Username already exists.");
        }

        String userJson = "{ \"pwd\": \"" + hashedPassword + "\" }";
        client.putData("Users/" + username, userJson);

        System.out.println("User '" + username + "' registered.");
    }

    @Override
    public User getUser(String usernameInput) {
        String json = client.getData("Users/" + usernameInput);

        if (json != null && !json.equals("null")) {
            JSONObject jsonObject = new JSONObject(json);
            String pwd = jsonObject.getString("pwd");
            return new User(usernameInput, pwd);
        }


        return null;
    }
}

