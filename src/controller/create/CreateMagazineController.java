/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: CreateMagazineController.java
 * @Purpose: Controller class for the create magazine view
 * @Assumptions:
 * @Limitations:
 */

package controller.create;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

import magazine.Magazine;
import controller.CreateController;
import helper.ValidationHelper;
import model.MagazineModel;

public class CreateMagazineController {
    private MagazineModel magazineModel = new MagazineModel();

    @FXML
    private TextField magazineCost;

    @FXML
    private Label costError;

    @FXML
    private Button createMagazineButton;

    @FXML
    private Button cancelButton;

    /**
     * createMagazineAction method is called when the createMagazineButton is
     * clicked
     * It validates the input and creates a new Magazine object
     * It then loads the CreateSupplement.fxml view
     * If the input is invalid, an error message is displayed
     */
    @FXML
    private void createMagazineAction() {
        // Clear previous error messages
        costError.setText("");

        // Get the input from the TextField
        String costInput = magazineCost.getText();

        // Validate the input
        if (ValidationHelper.validateCost(costInput) && costInput.length() > 0) {
            double cost = Double.parseDouble(costInput);

            // Create a new Magazine object with the entered cost
            Magazine magazine = new Magazine(cost);

            // Add the magazine to your data model
            magazineModel.setMagazine(magazine);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/create/CreateSupplement.fxml"));
                // Set the controller factory
                loader.setControllerFactory(c -> new CreateSupplementController(magazineModel));

                VBox nextLayout = loader.load();
                Scene nextScene = new Scene(nextLayout, 350, 180);

                Stage stage = (Stage) createMagazineButton.getScene().getWindow();
                stage.setScene(nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            costError.setText("Invalid cost.");
        }
    }

    /**
     * cancelAction method is called when the cancelButton is clicked
     * It loads the Create.fxml view
     */
    @FXML
    void cancelAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Create.fxml"));
            // Set the controller factory
            loader.setControllerFactory(c -> new CreateController(magazineModel));

            VBox nextLayout = loader.load();
            Scene nextScene = new Scene(nextLayout, 300, 200);

            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.setScene(nextScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}