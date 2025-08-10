package com.csc207.group.ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.csc207.group.ui.controller.UserProfileController;

/**Handles the logic for edit button press on user profile view
 *
 */
public class EditProfileSaveHandler implements EventHandler<ActionEvent> {

    private final TextField bioField;
    private final TextField imageField;
    private final Stage popupStage;
    private final UserProfileController controller;

    public EditProfileSaveHandler(TextField bioField, TextField imageField, Stage popupStage,
                                  UserProfileController controller) {
        this.bioField = bioField;
        this.imageField = imageField;
        this.popupStage = popupStage;
        this.controller = controller;
    }

    @Override
    public void handle(ActionEvent event) {
        String newBio = bioField.getText();
        String newImageUrl = imageField.getText();
        popupStage.close();
        controller.handleProfileUpdate(newBio, newImageUrl);
    }
}
