/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: CreateSupplementController.java
 * @Purpose: Controller class for the create supplement view
 * @Assumptions:
 * @Limitations:
 */

package controller.create;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

import magazine.Supplement;
import controller.CreateController;
import helper.ValidationHelper;
import model.MagazineModel;

public class CreateSupplementController {
    private MagazineModel magazineModel;
    private boolean supplementCreated = false; // Flag to track supplement creation

    @FXML
    private TextField supplementName;

    @FXML
    private Label nameError;

    @FXML
    private TextField supplementCost;

    @FXML
    private Label costError;

    @FXML
    private Button createSupplementButton;

    @FXML
    private Button finishButton;

    @FXML
    private Button cancelButton;

    /**
     * Constructor for CreateSupplementController
     * 
     * @param magazineModel The model for the magazine
     */
    public CreateSupplementController(MagazineModel magazineModel) {
        this.magazineModel = magazineModel;
    }

    /**
     * createSupplementAction method is called when the
     * createSupplementButton is clicked
     * If the name and cost are valid, it creates a new Supplement object with the
     * entered name and cost
     * and adds it to the data model
     * It clears the text fields for the next supplement
     * If there are any errors in the input, it sets the error messages
     * and does not add the Supplement to the data model
     */
    @FXML
    private void createSupplementAction() {
        // Clear previous error messages
        nameError.setText("");
        costError.setText("");

        // Get the input from the TextField
        String nameInput = supplementName.getText();
        String costInput = supplementCost.getText();
        double cost = 0.0;

        // Check if the name is not empty
        if (nameInput.isEmpty()) {
            nameError.setText("Name field is empty.");
        } else if (ValidationHelper.isDuplicateSupplement(magazineModel.getMagazine().getSupplements(),
                nameInput)) {
            // Check if the name is unique
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
            // Create a new Supplement object with the entered name and cost
            Supplement supplement = new Supplement(nameInput, cost);

            // Add the supplement to your data model
            magazineModel.addSupplement(supplement);
            supplementCreated = true; // Set the flag to true

            // Clear the text fields for the next supplement
            supplementName.clear();
            supplementCost.clear();
        }
    }

    /**
     * finishAction method is called when the finishButton is
     * clicked
     * If check the user has at least one supplement in data model
     * If not, ask the user to create one Supplement object with creatSupplementAction method
     * If the user has at least one supplement in date model
     * Then, redirects to the create customer view
     */
    @FXML
    private void finishAction() {
    // Check if at least one supplement has been created
        if (!supplementCreated) {
        // Show an error message that at least one supplement must be created
            nameError.setText("You must create at least one supplement before finishing.");
            return;
        }

    // If at least one supplement has been created, navigate to the next stage
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/create/CreateCustomer.fxml"));
            loader.setControllerFactory(c -> new CreateCustomerController(magazineModel));

            ScrollPane nextLayout = loader.load();
            Scene nextScene = new Scene(nextLayout, 400, 500);

            Stage stage = (Stage) createSupplementButton.getScene().getWindow();
            stage.setScene(nextScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * cancelAction method is called when the cancelButton is clicked
     * It returns to the create view
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