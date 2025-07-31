package com.csc207.group.model;

public class Achievement {
    private int achievementId;
    private String achievementName;
    private String achievementDescription;
    private String achievementImage;
    private String achievementCompletionPercentage;

    public Achievement() { /* empty so it works with jackson */ }

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

    public int getAchievementID() { return achievementId; }

    public String getAchievementName() { return achievementName; }

    public String getAchievementDescription() { return achievementDescription; }

    public String getAchievementImage() { return achievementImage; }

    public String getAchievementCompletionPercentage() { return achievementCompletionPercentage; }

    public String calculateRarity() {
        // converts it to a double
        double percentage = Double.parseDouble(this.achievementCompletionPercentage);
        if (percentage >= 50) {
            return "Common";
        }
        else if (percentage > 25) {
            return "Uncommon";
        }
        else if (percentage > 5) {
            return "Rare";
        }
        else {
            return "Ultra Rare";
        }
    }

}

