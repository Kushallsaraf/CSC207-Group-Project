package com.csc207.group.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.csc207.group.ui.controller.EditProfileSaveHandler;
import com.csc207.group.ui.controller.PersonalProfileController;

public class PersonalProfileView extends AbstractProfileView {

    private final Button editProfileButton = new Button("Edit");
    private PersonalProfileController controller;

    public PersonalProfileView() {
        super();
        textInfo.getChildren().add(editProfileButton);
    }

    public Button getEditProfileButton() {
        return editProfileButton;
    }

    public void setController(PersonalProfileController personalProfileController) {
        this.controller = personalProfileController;
    }

    public void showEditProfilePopup(String currentBio, String currentImageUrl) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Edit Profile");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setStyle("-fx-background-color: #121212;");

        Label bioLabel = new Label("Bio:");
        bioLabel.setStyle("-fx-text-fill: white;");
        TextField bioField = new TextField(currentBio);
        bioField.setStyle("-fx-text-fill: white; -fx-background-color: #1E1E1E; -fx-border-color: #333333;");


        Label imageLabel = new Label("Profile Image URL:");
        imageLabel.setStyle("-fx-text-fill: white;");
        TextField imageField = new TextField(currentImageUrl);
        imageField.setStyle("-fx-text-fill: white; -fx-background-color: #1E1E1E; -fx-border-color: #333333;");

        HBox buttonBox = new HBox(10);
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
        buttonBox.getChildren().addAll(saveButton, cancelButton);

        layout.getChildren().addAll(bioLabel, bioField, imageLabel, imageField, buttonBox);

        Scene scene = new Scene(layout, 350, 200);
        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL);

        saveButton.setOnAction(new EditProfileSaveHandler(bioField, imageField, popupStage, controller));

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popupStage.close();
            }
        });

        popupStage.showAndWait();
    }
}
