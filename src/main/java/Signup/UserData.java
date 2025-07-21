package Signup;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

/**
 * Reads the json file and stores the data into an easily accessible object.
 */
public class UserData {

    private JsonObject data;

    public UserData(String filePath) throws FileNotFoundException {

        FileReader reader = new FileReader(filePath);
        data = JsonParser.parseReader(reader).getAsJsonObject();

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



}
