/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: EditAddAssociateCustomerController.java
 * @Purpose: Controller class for the edit add associate customer view
 * @Assumptions:
 * @Limitations:
 */

package controller.edit;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import model.MagazineModel;
import controller.EditController;
import customer.AssociateCustomer;
import customer.PayingCustomer;

public class EditAddAssociateCustomerController {
    private MagazineModel magazineModel;
    private AssociateCustomer associateCustomer;

    @FXML
    private ComboBox<String> payingCustomerEmail;

    @FXML
    private Button createAssociateCustomerButton;

    @FXML
    private Label payingCustomerEmailError;

    @FXML
    private Button cancelButton;

    /**
     * Constructor for EditAddAssociateCustomerController
     * 
     * @param magazineModel     The model for the magazine
     * @param associateCustomer The associate customer to be added
     */
    public EditAddAssociateCustomerController(MagazineModel magazineModel, AssociateCustomer associateCustomer) {
        this.magazineModel = magazineModel;
        this.associateCustomer = associateCustomer;
    }

    /**
     * Initializes the controller class. This method is automatically called after
     * the fxml file has been loaded.
     * Set the items in the payingCustomerEmail ComboBox
     */
    @FXML
    public void initialize() {
        // Get the emails of all existing paying customers
        List<String> emails = magazineModel.getMagazine().getCustomers().stream()
                .filter(customer -> (customer.getCustomerType().equals("Paying")))
                .map(customer -> ((PayingCustomer) customer).getEmail())
                .collect(Collectors.toList());

        // Set the items in the ComboBox
        payingCustomerEmail.setItems(FXCollections.observableArrayList(emails));
    }

    /**
     * createAssociateCustomerAction method is called when the
     * createAssociateCustomerButton is clicked
     * It associates the associate customer with the selected paying customer
     * and adds the associate customer to the data model
     * Redirects to the edit view
     * If no paying customer is selected, display an error message
     * and do not add the associate customer to the data model
     */
    @FXML
    void createAssociateCustomerAction() {
        // Get the selected email from the dropdown
        String selectedEmail = payingCustomerEmail.getValue();

        // If an email has been selected
        if (selectedEmail != null) {
            // Find the corresponding PayingCustomer object
            PayingCustomer selectedPayingCustomer = (PayingCustomer) magazineModel.getMagazine().getCustomers().stream()
                    .filter(customer -> (customer.getCustomerType().equals("Paying"))) // Filter for paying customers
                    .filter(customer -> ((PayingCustomer) customer).getEmail().equals(selectedEmail)) // Match the
                                                                                                      // selected email
                    .findFirst() // Get the first match
                    .orElse(null); // Return null if no match is found

            // If a matching PayingCustomer is found
            if (selectedPayingCustomer != null) {
                // Associate the selected PayingCustomer with the AssociateCustomer
                associateCustomer.setPayingCustomer(selectedPayingCustomer);
                // Add the AssociateCustomer to the selected PayingCustomer's list of associates
                selectedPayingCustomer.addAssociateCustomer(associateCustomer);
                // Add the AssociateCustomer to the magazine's customer list
                magazineModel.getMagazine().addCustomer(associateCustomer);

                // Get the current stage
                Stage stage = (Stage) createAssociateCustomerButton.getScene().getWindow();
                // Save the current state of the model
                magazineModel.saveModel(stage);

                try {
                    // Load the next scene
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Edit.fxml"));
                    loader.setControllerFactory(c -> new EditController(magazineModel));

                    VBox nextLayout = loader.load();
                    Scene nextScene = new Scene(nextLayout, 500, 400);

                    // Change to the next scene
                    stage.setScene(nextScene);
                } catch (IOException e) {
                    // Print the stack trace if an exception occurs
                    e.printStackTrace();
                }
            }
        } else {
            // If no email was selected, display an error message
            payingCustomerEmailError.setText("Please select a paying customer.");
        }
    }

    /**
     * cancelAction method is called when the cancelButton is clicked
     * It returns to the edit view
     */
    @FXML
    void cancelAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Edit.fxml"));
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
