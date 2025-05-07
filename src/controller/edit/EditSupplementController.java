/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: EditSupplementController.java
 * @Purpose: Controller class for the edit supplement view
 * @Assumptions:
 * @Limitations:
 */

package controller.edit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import helper.ValidationHelper;
import controller.EditController;
import magazine.Supplement;
import model.MagazineModel;

public class EditSupplementController {
    MagazineModel magazineModel;
    Supplement supplement;

    @FXML
    private TextField supplementName;

    @FXML
    private Label nameError;

    @FXML
    private TextField supplementCost;

    @FXML
    private Label costError;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    /**
     * Constructor for EditSupplementController
     * 
     * @param magazineModel The magazine model
     * @param supplement    The supplement to be edited
     */
    public EditSupplementController(MagazineModel magazineModel, Supplement supplement) {
        this.magazineModel = magazineModel;
        this.supplement = supplement;
    }

    /**
     * Initialize the EditSupplementController
     */
    public void initialize() {
        // Pre-fill the supplementName and supplementCost with the existing information
        // of the selected supplement
        supplementName.setText(supplement.getName());
        supplementCost.setText(String.valueOf(supplement.getWeeklyCost()));
    }

    /**
     * Save the edited supplement
     */
    @FXML
    void saveAction() {
        // Clear previous error messages
        nameError.setText("");
        costError.setText("");

        // Get the input from the TextField
        String nameInput = supplementName.getText();
        String costInput = supplementCost.getText();
        double cost = 0.0;

        // Check if the name is not empty
        if (nameInput.isEmpty() || nameInput.isBlank()) {
            nameError.setText("Name field is empty.");
        } else if (ValidationHelper.isDuplicateSupplementExcludingCurrent(magazineModel.getMagazine().getSupplements(),
                nameInput, supplement.getName())) {
        // Check if the name is unique, excluding the current supplement's name
            nameError.setText("Name is not unique.");
        }

        if (costInput.isEmpty()) {
            costError.setText("Cost field is empty.");
        } else if (ValidationHelper.validateCost(costInput)) {
            cost = Double.parseDouble(costInput);
        } else {
            costError.setText("Invalid cost.");
        }

        if (nameError.getText().isEmpty() && costError.getText().isEmpty()) {
            // Add the supplement to your data model
            magazineModel.editSupplement(supplement, nameInput, cost);

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
        }
    }

    /**
     * Cancel the edit supplement action
     */
    @FXML
    void cancelAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Edit.fxml"));
            // Set the controller factory
            loader.setControllerFactory(c -> new EditController(magazineModel));

            VBox nextLayout = loader.load();
            Scene nextScene = new Scene(nextLayout, 520, 400);

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.setScene(nextScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
