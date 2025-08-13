package com.csc207.group.service;

import com.csc207.group.model.Screenshot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class ScreenshotService {

    // class to parse screenshot objects that we get from the json and return
    // them as a list of Screenshot instances

    public static List<Screenshot> parseScreenshotsFromJson(String json) throws JsonProcessingException {

        List<Screenshot> screenshotList = new ArrayList<>();

        // creating a mapper
        ObjectMapper mapper = new ObjectMapper();

        // parsing into the root node and the screenshots node

        JsonNode rootNode = mapper.readTree(json);

        if (rootNode.isArray() && !rootNode.isEmpty()) {
            rootNode = rootNode.get(0);
        }

        JsonNode screenshotsNode = rootNode.get("results");

        if (screenshotsNode != null && screenshotsNode.isArray()) {

            for (JsonNode screenshotNode : screenshotsNode) {

                int imageID = screenshotNode.get("id").asInt();
                String imageURL = screenshotNode.get("image").asText();
                int imageWidth = screenshotNode.get("width").asInt();
                int imageHeight = screenshotNode.get("height").asInt();
                boolean imageVisibility = Boolean.parseBoolean(screenshotNode.get("is_deleted").asText());

                // adding new screenshot  object to array
                screenshotList.add(new Screenshot(imageID,
                        imageURL,
                        imageWidth,
                        imageHeight,
                        imageVisibility));


            }

        }

        return screenshotList;


    }


}
