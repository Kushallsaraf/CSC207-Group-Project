package com.csc207.group.views;

import com.csc207.group.model.Game;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.swing.text.html.ImageView;

import javafx.scene.*;
import java.util.function.Consumer;

public class GameDetailViewFunc extends VBox{
    VBox titleAndTags = new VBox(10);
    Label title;
    HBox genre = new HBox(10);

    HBox userScore = new HBox(10);
    Label scoreLabel = new Label();

    HBox mainContent = new HBox(30);
    ImageView imageView;

    VBox buttons = new VBox(10);
    Button wishlistButton = new Button("Add to Wishlist");
    Button libraryButton = new Button("Add to Library");

    VBox synopsis;
    VBox overview;


    public GameDetailViewFunc() {
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;");


    }

    private Node createTag(String text) {
        javafx.scene.control.Label tag = new javafx.scene.control.Label(" " + text + " ");
        tag.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 4 8; -fx-background-radius: 5;");
        return tag;
    }

    private VBox createSection(String title, String content) {
        VBox section = new VBox(5);
        javafx.scene.control.Label header = new javafx.scene.control.Label(title);
        header.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        Text body = new Text(content);
        body.setStyle("-fx-font-size: 14px; -fx-fill: #ccc;");
        body.setWrappingWidth(600);

        section.getChildren().addAll(header, body);
        return section;
    }


}
