package com.csc207.group.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Achievement {
    public static final int FIFTY = 50;
    public static final int TWENTYFIVE = 25;
    public static final int FIVE = 5;
    @JsonProperty("id")
    private int achievementId;

    @JsonProperty("name")
    private String achievementName;

    @JsonProperty("description")
    private String achievementDescription;

    @JsonProperty("image")
    private String achievementImage;

    @JsonProperty("percent")
    private String achievementCompletionPercentage;

    public Achievement() {
        /* empty so it works with jackson */
    }
    // here's a normal constructor for manual use if required

    public Achievement(int achievementId,
                       String achievementName,
                       String achievementDescription,
                       String achievementImage,
                       String achievementCompletionPercentage) {

        this.achievementId = achievementId;
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
        this.achievementImage = achievementImage;
        this.achievementCompletionPercentage = achievementCompletionPercentage;
    }

    public int getAchievementID() {
        return achievementId;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public String getAchievementDescription() {
        return achievementDescription;
    }

    public String getAchievementImage() {
        return achievementImage;
    }

    public String getAchievementCompletionPercentage() {
        return achievementCompletionPercentage;
    }

    /**
     * Calculates the rarity of a given achievement.
     * @return the rarity as a string
     */
    public String calculateRarity() {
        // converts it to a double
        String rarity = "";
        double percentage = Double.parseDouble(this.achievementCompletionPercentage);
        if (percentage >= FIFTY) {
            rarity = "Common";
        }
        else if (percentage > TWENTYFIVE) {
            rarity = "Uncommon";
        }
        else if (percentage > FIVE) {
            rarity = "Rare";
        }
        else {
            rarity = "Ultra Rare";
        }
        return rarity;
    }

}

