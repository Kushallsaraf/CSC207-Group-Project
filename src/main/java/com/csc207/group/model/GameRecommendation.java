package com.csc207.group.model;

/**A Game model that stores relevant game data for recommendations
 *
 */
public class GameRecommendation {

    private int year;
    private String title;
    private String coverImage;
    private int gameid;
    private double rating;


    public GameRecommendation(int gameid, String title, String coverImage, int year, double rating) {
        this.gameid = gameid;
        this.title = title;
        this.coverImage = coverImage;
        this.year = year;
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public int getGameid() {
        return gameid;
    }

    public double getRating() {
        return rating;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "GameRecommendation{" +
                "year=" + year +
                ", title='" + title + '\'' +
                ", coverImage='" + coverImage + '\'' +
                ", gameid=" + gameid +
                ", rating=" + rating +
                '}';
    }



}
