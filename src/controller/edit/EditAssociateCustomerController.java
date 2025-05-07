/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: EditAssociateCustomerController.java
 * @Purpose: Controller class for the edit associate customer view
 * @Assumptions:
 * @Limitations:
 */

package controller.edit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import controller.EditController;
import customer.Address;
import customer.AssociateCustomer;
import customer.PayingCustomer;
import helper.ValidationHelper;
import magazine.Supplement;
import model.MagazineModel;

public class EditAssociateCustomerController {
    private MagazineModel magazineModel;
    private AssociateCustomer associateCustomer;

    @FXML
    private TextField customerName;

    @FXML
    private Label nameError;

    @FXML
    private TextField customerEmail;

    @FXML
    private Label emailError;

    @FXML
    private TextField streetNumber;

    @FXML
    private Label streetNumberError;

    @FXML
    private TextField streetName;

    @FXML
    private Label streetNameError;

    @FXML
    private TextField suburb;

    @FXML
    private Label suburbError;

    @FXML
    private TextField postcode;

    @FXML
    private Label postcodeError;

    @FXML
    private TableView<Supplement> supplementSelection;

    @FXML
    private ComboBox<String> payingCustomerEmail;

    @FXML
    private Label payingCustomerEmailError;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    /**
     * Constructor for EditAssociateCustomerController
     * 
     * @param magazineModel     The magazine model
     * @param associateCustomer The associate customer to be edited
     */
    public EditAssociateCustomerController(MagazineModel magazineModel, AssociateCustomer associateCustomer) {
        this.magazineModel = magazineModel;
        this.associateCustomer = associateCustomer;
    }

    /**
     * Initializes the controller class. This method is automatically called after
     * the fxml file has been loaded.
     * Pre-fill the fields with the existing information from the associateCustomer
     * object
     */
    @FXML
    public void initialize() {
        // Pre-fill the fields with the existing information from the associateCustomer
        // object
        customerName.setText(associateCustomer.getName());
        customerEmail.setText(associateCustomer.getEmail());
        streetNumber.setText(Integer.toString(associateCustomer.getAddress().getStreetNumber()));
        streetName.setText(associateCustomer.getAddress().getStreetName());
        suburb.setText(associateCustomer.getAddress().getSuburb());
        postcode.setText(Integer.toString(associateCustomer.getAddress().getPostcode()));
        payingCustomerEmail.setValue(associateCustomer.getPayingCustomer().getEmail());

        // Create columns for the TableView
        TableColumn<Supplement, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Supplement, Double> costColumn = new TableColumn<>("Weekly Cost");
        costColumn.setCellValueFactory(new PropertyValueFactory<>("weeklyCost"));

        // Add the columns to the TableView
        supplementSelection.getColumns().add(nameColumn);
        supplementSelection.getColumns().add(costColumn);

        // Get the supplements from the magazine
        ObservableList<Supplement> supplements = FXCollections
                .observableArrayList(magazineModel.getMagazine().getSupplements());

        // Set the items in the TableView
        supplementSelection.setItems(supplements);
        
        // Allow multiple selection
        supplementSelection.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Pre-select the supplements according to the associateCustomer object
        for (Supplement supplement : associateCustomer.getSupplements()) {
            if (supplements.contains(supplement)) {
                supplementSelection.getSelectionModel().select(supplement);
            }
        }

        // Get the emails of all existing paying customers
        List<String> emails = magazineModel.getMagazine().getCustomers().stream()
                .filter(customer -> (customer.getCustomerType().equals("Paying")))
                .map(customer -> ((PayingCustomer) customer).getEmail())
                .collect(Collectors.toList());

        // Set the items in the ComboBox
        payingCustomerEmail.setItems(FXCollections.observableArrayList(emails));
    }

    /**
     * Save the edited associate customer
     * Navigate to the next scene with the created PayingCustomer and magazineModel
     * if there are no error messages
     * Otherwise, set error messages
     * and do not proceed with creating the customer
     */
    @FXML
    private void saveAction() {
        // Clear error message
        nameError.setText("");
        emailError.setText("");
        streetNumberError.setText("");
        streetNameError.setText("");
        postcodeError.setText("");
        payingCustomerEmailError.setText("");

        // Validate the input and set error messages
        if (customerName.getText().isEmpty()) {
            nameError.setText("Name is required.");
        } else if (!ValidationHelper.validateName(customerName.getText())) {
            nameError.setText("Invalid name.");
        }

        if (customerEmail.getText().isEmpty()) {
            emailError.setText("Email is required.");
        } else if (!ValidationHelper.validateEmail(customerEmail.getText())) {
            emailError.setText("Invalid email.");
        } else if (ValidationHelper.isDuplicateCustomer(magazineModel.getMagazine().getCustomers(),
                customerEmail.getText()) && !customerEmail.getText().equals(associateCustomer.getEmail())) {
            emailError.setText("Email already exists.");
        }

        if (streetNumber.getText().isEmpty()) {
            streetNumberError.setText("Street number is required.");
        } else if (!ValidationHelper.isInteger(streetNumber.getText())) {
            streetNumberError.setText("Invalid street number.");
        }

        if (streetName.getText().isEmpty()) {
            streetNameError.setText("Street name is required.");
        }

        if (postcode.getText().isEmpty()) {
            postcodeError.setText("Postcode is required.");
        } else if (!ValidationHelper.isInteger(postcode.getText())) {
            postcodeError.setText("Invalid postcode.");
        }

        if (payingCustomerEmail.getValue() == null) {
            payingCustomerEmailError.setText("Please select a paying customer.");
        }

        // If there are no error messages, proceed with creating the customer
        if (nameError.getText().equals("") && emailError.getText().equals("") && streetNumberError.getText().equals("")
                && streetNameError.getText().equals("") && postcodeError.getText().equals("")
                && payingCustomerEmailError.getText().equals("")) {
            String name = customerName.getText();
            String email = customerEmail.getText();
            Address address = new Address(Integer.parseInt(streetNumber.getText()), streetName.getText(),
                    suburb.getText(),
                    Integer.parseInt(postcode.getText()));
            ObservableList<Supplement> selectedSupplements = supplementSelection.getSelectionModel().getSelectedItems();

            String selectedEmail = payingCustomerEmail.getValue();

            PayingCustomer selectedPayingCustomer = (PayingCustomer) magazineModel.getMagazine().getCustomers()
                    .stream()
                    .filter(PayingCustomer.class::isInstance)
                    .filter(customer -> ((PayingCustomer) customer).getEmail().equals(selectedEmail))
                    .findFirst()
                    .orElse(null);

            if (selectedPayingCustomer != null) {
                // Edit existing customer
                magazineModel.editAssociateCustomer(associateCustomer, name, address, email,
                        new ArrayList<>(selectedSupplements), selectedPayingCustomer);

                Stage stage = (Stage) saveButton.getScene().getWindow();
                magazineModel.saveModel(stage);
            }

            // Navigate to the next scene with the created PayingCustomer and magazineModel
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

    /**
     * Handle the cancel button action
     * Navigate to the previous scene
     * with the magazineModel
     */
    @FXML
    private void cancelAction() {
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
