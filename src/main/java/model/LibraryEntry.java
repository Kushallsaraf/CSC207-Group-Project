package model;

import javafx.scene.image.Image;

/**The model for what we want to store in a user's library which does not require
 * every aspect of a Game.
 *
 */
public class LibraryEntry extends GamePreview {
    private Review userReview;


    public LibraryEntry(String title, int year, Image coverImage, int gameid) {
        super(title, year, coverImage, gameid);

    }

    public void setUserReview(Review review){
        this.userReview = review;
    }

    public Review getUserReview(){
        return this.userReview;
    }

    public void editUserReview(String content, double starRating){
        this.userReview.editReview(content, starRating);
    }
}
