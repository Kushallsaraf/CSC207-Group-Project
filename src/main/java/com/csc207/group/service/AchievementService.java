package com.csc207.group.service;

import com.csc207.group.model.Achievement;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is used to parse the achievement objects from a Json
 */
public class AchievementService {
    // this class can take the json objects and parse them to create a proper list
    // achievements as we'll need for each game
    public static List<Achievement> parseAchievementsFromJson(String json) throws JsonProcessingException {
        // list we will return
        List<Achievement> achievementList = new ArrayList<Achievement>();
        // parsing

        ObjectMapper mapper = new ObjectMapper();
        // this parses the json string into a navigatable tree like object
        JsonNode rootNode = mapper.readTree(json);
        JsonNode achievementsNode = rootNode.get("results");
        if (achievementsNode != null) {
            // looping over each node thats an achievement we parse each
            // attribute and create an achievement obj
            for (JsonNode achievementNode : achievementsNode) {
                // parse each attribute of an achievement
                // decided to use path instead of get here as get can crash
                // in the case that some value is missing
                int achievementId = achievementNode.path("id").asInt();
                String achievementName = achievementNode.path("name").asText();
                String achievementDescription = achievementNode.path("description").asText();
                String achievementImage = achievementNode.path("image").asText();
                String achievementPercent = achievementNode.path("percent").asText();

                // now create achievement
                achievementList.add(new Achievement(
                        achievementId,
                        achievementName,
                        achievementDescription,
                        achievementImage,
                        achievementPercent));

            }
        }
        return achievementList;

    }
}
