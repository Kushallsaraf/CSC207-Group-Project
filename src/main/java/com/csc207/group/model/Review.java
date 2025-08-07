package model;

public class Review {
    private  String userid;
    private  String content;
    private  int gameid;
    private  double rating;

    public Review(String userid, String content, int gameid, double rating){

        this.content = content;
        this.gameid = gameid;
        this.rating = rating;
        this.userid = userid;
    }

    public void editReview(String newContent, double newRating){
        this.content = newContent;
        this.rating = newRating;

    }

    public int getGameid() {
        return gameid;
    }


    public String getUserid() {
        return userid;
    }

    public String getContent() {
        return content;
    }

    public double getRating() {
        return rating;
    }
}
