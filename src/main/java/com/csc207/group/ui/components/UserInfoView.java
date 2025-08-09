//package ui.components;
//
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.VBox;
//
//public class UserInfoView extends VBox {
//
//    private final ImageView profileImageView;
//    private final Label usernameLabel;
//    private final Label bioLabel;
//
//    public UserInfoView(String username, String bio, Image profileImage) {
//        this.setSpacing(10);
//        this.setPadding(new Insets(20));
//        this.setAlignment(Pos.CENTER);
//        this.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc;");
//
//        this.profileImageView = new ImageView(profileImage);
//        this.profileImageView.setFitHeight(100);
//        this.profileImageView.setFitWidth(100);
//        this.profileImageView.setPreserveRatio(true);
//        this.profileImageView.setSmooth(true);
//
//        this.usernameLabel = new Label(username);
//        this.usernameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
//
//        this.bioLabel = new Label(bio);
//        this.bioLabel.setWrapText(true);
//        this.bioLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555555;");
//        this.bioLabel.setMaxWidth(300);
//
//        this.getChildren().addAll(profileImageView, usernameLabel, bioLabel);
//    }
//
//    public void setProfileImage(Image image) {
//        this.profileImageView.setImage(image);
//    }
//
//    public void setBio(String bio) {
//        this.bioLabel.setText(bio);
//    }
//
//    public void setUsername(String username) {
//        this.usernameLabel.setText(username);
//    }
//}

