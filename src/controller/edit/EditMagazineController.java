/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: EditMagazineController.java
 * @Purpose: Controller class for the edit magazine view
 * @Assumptions:
 * @Limitations:
 */

package controller.edit;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import model.MagazineModel;
import controller.EditController;
import helper.ValidationHelper;

public class EditMagazineController {
    MagazineModel magazineModel;

    @FXML
    private TextField magazineCost;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label costError;

    /**
     * Constructor for EditMagazineController
     */
    public EditMagazineController(MagazineModel magazineModel) {
        this.magazineModel = magazineModel;
    }

    /**
     * Initializes the controller class. This method is automatically called after
     * the fxml file has been loaded.
     */
    @FXML
    public void initialize() {
        // Set the current cost of the magazine
        magazineCost.setText(String.valueOf(magazineModel.getMagazine().getWeeklyCost()));
    }

    /**
     * Save the edited magazine
     */
    @FXML
    void saveAction() {
        // Clear previous error messages
        costError.setText("");

        // Get the input from the TextField
        String costInput = magazineCost.getText();

        // Validate the input
        if (ValidationHelper.validateCost(costInput)) {
            double cost = Double.parseDouble(costInput);

            // Add the magazine to your data model
            magazineModel.editMagazine(cost);

            // Save model
            Stage stage = (Stage) saveButton.getScene().getWindow();
            magazineModel.saveModel(stage);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Edit.fxml"));
                // Set the controller factory
                loader.setControllerFactory(c -> new EditController(magazineModel));

                VBox nextLayout = loader.load();
                Scene nextScene = new Scene(nextLayout, 520, 400);

                stage.setScene(nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            costError.setText("Invalid cost.");
        }
    }

    /**
     * Handle the cancel button action
     */
    @FXML
    void cancelAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Edit.fxml"));
            // Set the controller factory
            loader.setControllerFactory(c -> new EditController(magazineModel));

            VBox nextLayout = loader.load();
            Scene nextScene = new Scene(nextLayout, 520, 400);

            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.setScene(nextScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
