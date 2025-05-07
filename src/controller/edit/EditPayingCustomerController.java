/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: EditPayingCustomerController.java
 * @Purpose: Controller class for the edit paying customer view
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

import magazine.Supplement;
import model.MagazineModel;
import controller.EditController;
import customer.Address;
import customer.PayingCustomer;
import helper.ValidationHelper;

public class EditPayingCustomerController {
    private MagazineModel magazineModel;
    private PayingCustomer payingCustomer;

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
    private ComboBox<String> paymentMethod;

    @FXML
    private Label paymentMethodError;

    @FXML
    private TextField paymentDetails;

    @FXML
    private Label paymentDetailsError;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    /**
     * Constructor for EditPayingCustomerController
     * 
     * @param magazineModel  The magazine model
     * @param payingCustomer The paying customer to be edited
     */
    public EditPayingCustomerController(MagazineModel magazineModel, PayingCustomer payingCustomer) {
        this.magazineModel = magazineModel;
        this.payingCustomer = payingCustomer;
    }

    /**
     * Initialize the EditPayingCustomerController
     */
    @FXML
    public void initialize() {
        // Pre-fill the fields with the existing information from the payingCustomer
        // object
        customerName.setText(payingCustomer.getName());
        customerEmail.setText(payingCustomer.getEmail());
        streetNumber.setText(Integer.toString(payingCustomer.getAddress().getStreetNumber()));
        streetName.setText(payingCustomer.getAddress().getStreetName());
        suburb.setText(payingCustomer.getAddress().getSuburb());
        postcode.setText(Integer.toString(payingCustomer.getAddress().getPostcode()));
        paymentMethod.setValue(payingCustomer.getPaymentMethod());
        paymentDetails.setText(Integer.toString(payingCustomer.getPaymentDetail()));

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

        // Pre-select the supplements according to the payingCustomer object
        for (Supplement supplement : payingCustomer.getSupplements()) {
            if (supplements.contains(supplement)) {
                supplementSelection.getSelectionModel().select(supplement);
            }
        }
    }

    /**
     * Save the edited paying customer
     */
    @FXML
    private void saveAction() {
        // Clear Error message
        nameError.setText("");
        emailError.setText("");
        streetNumberError.setText("");
        streetNameError.setText("");
        postcodeError.setText("");
        paymentMethodError.setText("");
        paymentDetailsError.setText("");

        // Validate the input and set error messages
        if (customerName.getText().equals("")) {
            nameError.setText("Name is required.");
        } else if (!ValidationHelper.validateName(customerName.getText())) {
            nameError.setText("Invalid name.");
        }

        if (customerEmail.getText().equals("")) {
            emailError.setText("Email is required.");
        } else if (!ValidationHelper.validateEmail(customerEmail.getText())) {
            emailError.setText("Invalid email.");
        } else if (ValidationHelper.isDuplicateCustomer(magazineModel.getMagazine().getCustomers(),
                customerEmail.getText()) && !customerEmail.getText().equals(payingCustomer.getEmail())) {
            emailError.setText("Email already exists.");
        }

        if (streetNumber.getText().equals("")) {
            streetNumberError.setText("Street number is required.");
        } else if (!ValidationHelper.isInteger(streetNumber.getText())) {
            streetNumberError.setText("Invalid street number.");
        }

        if (streetName.getText().equals("")) {
            streetNameError.setText("Street name is required.");
        }

        if (postcode.getText().equals("")) {
            postcodeError.setText("Postcode is required.");
        } else if (!ValidationHelper.isInteger(postcode.getText())) {
            postcodeError.setText("Invalid postcode.");
        }

        if (paymentMethod.getValue() == null) {
            paymentMethodError.setText("Payment method is required.");
        } else if (!ValidationHelper.validatePaymentMethod(paymentMethod.getValue())) {
            paymentMethodError.setText("Invalid payment method");
        }

        if (paymentDetails.getText().equals("")) {
            paymentDetailsError.setText("Payment details is required.");
        } else if (!ValidationHelper.validatePaymentDetails(paymentDetails.getText())) {
            paymentDetailsError.setText("Invalid payment details");
        }

        // If there are no error messages, proceed with creating the customer
        if (nameError.getText().equals("") && emailError.getText().equals("") && streetNumberError.getText().equals("")
                && streetNameError.getText().equals("")
                && postcodeError.getText().equals("") && paymentMethodError.getText().equals("")
                && paymentDetailsError.getText().equals("")) {
            String name = customerName.getText();
            String email = customerEmail.getText();
            Address address = new Address(Integer.parseInt(streetNumber.getText()), streetName.getText(),
                    suburb.getText(),
                    Integer.parseInt(postcode.getText()));
            ObservableList<Supplement> selectedSupplements = supplementSelection.getSelectionModel().getSelectedItems();

            // Edit existing paying customer with new information
            magazineModel.editPayingCustomer(payingCustomer, name, address, email,
                    new ArrayList<>(selectedSupplements), paymentMethod.getValue(),
                    Integer.parseInt(paymentDetails.getText()));

            // Get the Stage and pass it to the saveModel method
            Stage stage = (Stage) saveButton.getScene().getWindow();
            // Serialize the magazineModel and save it as a .ser file
            magazineModel.saveModel(stage);

            // Navigate to the next scene with the created PayingCustomer and magazineModel
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
     * Cancel the edit paying customer action
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
