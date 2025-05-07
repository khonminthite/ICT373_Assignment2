/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: CreateAssociateCustomerController.java
 * @Purpose: Controller class for the create associate customer view
 * @Assumptions:
 * @Limitations:
 */

package controller.create;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import controller.CreateController;
import controller.ViewController;
import customer.AssociateCustomer;
import customer.PayingCustomer;
import model.MagazineModel;

public class CreateAssociateCustomerController {
    MagazineModel magazineModel;
    AssociateCustomer associateCustomer;

    @FXML
    private ComboBox<String> payingCustomerEmail;

    @FXML
    private Label payingCustomerEmailError;

    @FXML
    private Button createAnotherCustomerButton;

    @FXML
    private Button createAssociateCustomerButton;

    @FXML
    private Button cancelButton;

    /**
     * Constructor for CreateAssociateCustomerController
     * 
     * @param magazineModel     The model for the magazine
     * @param associateCustomer The associate customer to be added
     */
    public CreateAssociateCustomerController(MagazineModel magazineModel, AssociateCustomer associateCustomer) {
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
                .filter(customer -> (customer.getCustomerType().equals(
                        "Paying")))
                .map(customer -> ((PayingCustomer) customer).getEmail())
                .collect(Collectors.toList());

        // Set the items in the ComboBox
        payingCustomerEmail.setItems(FXCollections.observableArrayList(emails));
    }

    /**
     * createAnotherCustomerAction method is called when the
     * createAnotherCustomerButton is clicked
     * Associate the associate customer with the selected paying customer and add
     * the associate customer to the data model
     * Redirect to the create customer view
     * If no paying customer is selected, display an error message and do not add
     * the associate customer to the data model
     */
    @FXML
    private void createAnotherCustomerAction() {
        String selectedEmail = payingCustomerEmail.getValue();
        if (selectedEmail != null) {
            PayingCustomer selectedPayingCustomer = (PayingCustomer) magazineModel.getMagazine().getCustomers().stream()
                    .filter(customer -> (customer.getCustomerType().equals("Paying")))
                    .filter(customer -> ((PayingCustomer) customer).getEmail().equals(selectedEmail))
                    .findFirst()
                    .orElse(null);

            if (selectedPayingCustomer != null) {
                associateCustomer.setPayingCustomer(selectedPayingCustomer);
                selectedPayingCustomer.addAssociateCustomer(associateCustomer);
                magazineModel.getMagazine().addCustomer(associateCustomer);

                Stage stage = (Stage) createAssociateCustomerButton.getScene().getWindow();

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/create/CreateCustomer.fxml"));
                    loader.setControllerFactory(c -> new CreateCustomerController(magazineModel));

                    ScrollPane nextLayout = loader.load();
                    Scene nextScene = new Scene(nextLayout, 400, 500);

                    stage.setScene(nextScene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            payingCustomerEmailError.setText("Please select a paying customer.");
        }
    }

    /**
     * createAssociateCustomerAction method is called when the
     * createAssociateCustomerButton is clicked
     * Associate the associate customer with the selected paying customer and add
     * the associate customer to the data model
     * Redirect to the view
     * If no paying customer is selected, display an error message and do not add
     * the associate customer to the data model
     */
    @FXML
    private void createAssociateCustomerAction() {
        String selectedEmail = payingCustomerEmail.getValue();
        if (selectedEmail != null) {
            PayingCustomer selectedPayingCustomer = (PayingCustomer) magazineModel.getMagazine().getCustomers().stream()
                    .filter(PayingCustomer.class::isInstance)
                    .filter(customer -> ((PayingCustomer) customer).getEmail().equals(selectedEmail))
                    .findFirst()
                    .orElse(null);

            if (selectedPayingCustomer != null) {
                associateCustomer.setPayingCustomer(selectedPayingCustomer);
                selectedPayingCustomer.addAssociateCustomer(associateCustomer);
                magazineModel.getMagazine().addCustomer(associateCustomer);

                Stage stage = (Stage) createAssociateCustomerButton.getScene().getWindow();
                magazineModel.saveModel(stage);

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
                    loader.setControllerFactory(c -> new ViewController(magazineModel));

                    VBox nextLayout = loader.load();
                    Scene nextScene = new Scene(nextLayout, 740, 400);

                    stage.setScene(nextScene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            payingCustomerEmailError.setText("Please select a paying customer.");
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
