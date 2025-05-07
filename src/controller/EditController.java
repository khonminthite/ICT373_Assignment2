/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: EditController.java
 * @Purpose: Controller class for the edit page
 * @Assumptions:
 * @Limitations:
 */

package controller;

import java.io.IOException;
import java.util.Optional;

import controller.edit.EditAddCustomerController;
import controller.edit.EditAddSupplementController;
import controller.edit.EditAssociateCustomerController;
import controller.edit.EditMagazineController;
import controller.edit.EditPayingCustomerController;
import controller.edit.EditSupplementController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import magazine.Supplement;
import model.MagazineModel;
import customer.AssociateCustomer;
import customer.Customer;
import customer.PayingCustomer;

public class EditController {
    private MagazineModel magazineModel;

    @FXML
    private ListView<String> supplementList;

    @FXML
    private ListView<String> customerList;

    @FXML
    private Button viewButton;

    @FXML
    private Button createButton;

    @FXML
    private Button editButton;

    @FXML
    private Button editCustomerInfoButton;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private Button editMagazineInfoButton;

    @FXML
    private Button editSupplementInfoButton;

    @FXML
    private Button addSupplementButton;

    @FXML
    private Button deleteSupplementButton;
    
    @FXML
    private Button saveButton;

    /**
     * Constructor
     * 
     * @param magazineModel the magazine model
     */
    public EditController(MagazineModel magazineModel) {
        this.magazineModel = magazineModel;
    }

    /**
     * Initializes the controller class. This method is automatically called after
     * the fxml file has been loaded.
     * Populate the customer list with customer names
     * Populate the supplement list with supplement names
     */
    @FXML
    public void initialize() {
        // Populate the customer list with customer names
        for (Customer customer : magazineModel.getMagazine().getCustomers()) {
            customerList.getItems().add(customer.getEmail());
        }
        customerList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // Populate the supplement list with supplement names
        for (Supplement supplement : magazineModel.getMagazine().getSupplements()) {
            supplementList.getItems().add(supplement.getName());
        }
        supplementList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * Show an alert dialog
     * 
     * @param message the message to display
     */
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handle the view button action
     */
    @FXML
    private void viewAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
            // Set the controller factory
            loader.setControllerFactory(c -> new ViewController(magazineModel));

            VBox nextLayout = loader.load();
            Scene nextScene = new Scene(nextLayout, 900, 450);

            Stage stage = (Stage) viewButton.getScene().getWindow();
            stage.setScene(nextScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the create button action
     */
    @FXML
    private void createAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Create.fxml"));
            // Set the controller factory
            loader.setControllerFactory(c -> new CreateController(magazineModel));

            VBox nextLayout = loader.load();
            Scene nextScene = new Scene(nextLayout, 300, 200);

            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.setScene(nextScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the edit button action
     */
    @FXML
    private void editAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Edit.fxml"));
            // Set the controller factory
            loader.setControllerFactory(c -> new EditController(magazineModel));

            VBox nextLayout = loader.load();
            Scene nextScene = new Scene(nextLayout, 520, 400);

            Stage stage = (Stage) editButton.getScene().getWindow();
            stage.setScene(nextScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the edit customer info button action
     */
    @FXML
    private void editCustomerInfoAction() {
        String selectedCustomerEmail = customerList.getSelectionModel().getSelectedItem();
        if (selectedCustomerEmail != null) {
            Customer selectedCustomer = magazineModel.getCustomerByEmail(selectedCustomerEmail);
            if (selectedCustomer != null) {
                try {
                    FXMLLoader loader;
                    if (selectedCustomer.getCustomerType().equals("Paying")) {
                        loader = new FXMLLoader(getClass().getResource("/fxml/edit/EditPayingCustomer.fxml"));
                        loader.setControllerFactory(c -> new EditPayingCustomerController(magazineModel,
                                (PayingCustomer) selectedCustomer));
                    } else {
                        loader = new FXMLLoader(getClass().getResource("/fxml/edit/EditAssociateCustomer.fxml"));
                        loader.setControllerFactory(c -> new EditAssociateCustomerController(magazineModel,
                                (AssociateCustomer) selectedCustomer));
                    }

                    ScrollPane nextLayout = loader.load();
                    Scene nextScene = new Scene(nextLayout, 400, 500);

                    Stage stage = (Stage) customerList.getScene().getWindow();
                    stage.setScene(nextScene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("No customer found with the selected email.");
            }
        } else {
            showAlert("Please select a customer to edit.");
        }
    }

    /**
     * Handle the add customer button action
     */
    @FXML
    private void addCustomerAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edit/EditAddCustomer.fxml"));
            // Set the controller factory
            loader.setControllerFactory(c -> new EditAddCustomerController(magazineModel));

            ScrollPane nextLayout = loader.load();
            Scene nextScene = new Scene(nextLayout, 400, 500);

            Stage stage = (Stage) addCustomerButton.getScene().getWindow();
            stage.setScene(nextScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the delete customer button action
     */
    @FXML
    private void deleteCustomerAction() {
        String selectedCustomerEmail = customerList.getSelectionModel().getSelectedItem();
        if (selectedCustomerEmail != null) {
            // Create a confirmation dialog
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Customer");
            alert.setContentText("Are you sure you want to delete this customer?");

            // Handle the result of the confirmation dialog
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // If the "OK" button is clicked, delete the customer
                Customer selectedCustomer = magazineModel.getCustomerByEmail(selectedCustomerEmail);
                if (selectedCustomer != null) {
                    magazineModel.deleteCustomer(selectedCustomer);
                    Stage stage = (Stage) deleteCustomerButton.getScene().getWindow();
                    magazineModel.saveModel(stage);
                }
            } else {
                // If the "Cancel" button is clicked, do nothing
            }
        } else {
            showAlert("Please select a customer to delete.");
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Edit.fxml"));
            // Set the controller factory
            loader.setControllerFactory(c -> new EditController(magazineModel));

            VBox nextLayout = loader.load();
            Scene nextScene = new Scene(nextLayout, 520, 400);

            Stage stage = (Stage) deleteCustomerButton.getScene().getWindow();
            stage.setScene(nextScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the edit magazine info button action
     */
    @FXML
    private void editMagazineInfoAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edit/EditMagazine.fxml"));
            // Set the controller factory
            loader.setControllerFactory(c -> new EditMagazineController(magazineModel));

            VBox nextLayout = loader.load();
            Scene nextScene = new Scene(nextLayout, 350, 150);

            Stage stage = (Stage) editMagazineInfoButton.getScene().getWindow();
            stage.setScene(nextScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the edit supplement info button action
     */
    @FXML
    private void editSupplementInfoAction() {
        String selectedSupplementName = supplementList.getSelectionModel().getSelectedItem();
        if (selectedSupplementName != null) {
            Supplement selectedSupplement = magazineModel.getSupplementByName(selectedSupplementName);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edit/EditSupplement.fxml"));
                // Set the controller factory
                loader.setControllerFactory(c -> new EditSupplementController(magazineModel, selectedSupplement));

                VBox nextLayout = loader.load();
                Scene nextScene = new Scene(nextLayout, 350, 200);

                Stage stage = (Stage) editSupplementInfoButton.getScene().getWindow();
                stage.setScene(nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Please select a supplement to edit.");
        }
    }

    /**
     * Handle the add supplement button action
     */
    @FXML
    private void addSupplementAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edit/EditAddSupplement.fxml"));
            // Set the controller factory
            loader.setControllerFactory(c -> new EditAddSupplementController(magazineModel));

            VBox nextLayout = loader.load();
            Scene nextScene = new Scene(nextLayout, 350, 200);

            Stage stage = (Stage) addSupplementButton.getScene().getWindow();
            stage.setScene(nextScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the delete supplement button action
     */
    @FXML
    private void deleteSupplementAction() {
        String selectedSupplementName = supplementList.getSelectionModel().getSelectedItem();
        if (selectedSupplementName != null) {

            // Create a confirmation dialog
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Supplement");
            alert.setContentText("Are you sure you want to delete this supplement?");

            // Handle the result of the confirmation dialog
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // If the "OK" button is clicked, delete the customer
                Supplement selectedSupplement = magazineModel.getSupplementByName(selectedSupplementName);
                if (selectedSupplement != null) {
                    magazineModel.deleteSupplement(selectedSupplement);

                    Stage stage = (Stage) deleteSupplementButton.getScene().getWindow();
                    magazineModel.saveModel(stage);
                }
            } else {
                // If the "Cancel" button is clicked, do nothing
            }
        } else {
            showAlert("Please select a supplement to delete.");
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Edit.fxml"));
            // Set the controller factory
            loader.setControllerFactory(c -> new EditController(magazineModel));

            VBox nextLayout = loader.load();
            Scene nextScene = new Scene(nextLayout, 520, 400);

            Stage stage = (Stage) deleteSupplementButton.getScene().getWindow();
            stage.setScene(nextScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void saveAction() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        try {
            magazineModel.saveModel(stage);
        } catch (Exception e) {
            showAlert("Error saving magazine data: " + e.getMessage()); // Error alert
            e.printStackTrace(); // Log the error for debugging
        }
    }
}
