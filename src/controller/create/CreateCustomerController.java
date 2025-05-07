/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: CreateCustomerController.java
 * @Purpose: Controller class for the create customer view
 * @Assumptions:
 * @Limitations:
 */

package controller.create;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import java.io.IOException;
import java.util.ArrayList;

import controller.CreateController;
import magazine.Supplement;
import customer.Address;
import customer.PayingCustomer;
import helper.ValidationHelper;
import customer.AssociateCustomer;
import customer.Customer;
import model.MagazineModel;

public class CreateCustomerController {
    private MagazineModel magazineModel;

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
    private TextField postcode;

    @FXML
    private Label postcodeError;

    @FXML
    private TableView<Supplement> supplementSelection;

    @FXML
    private Button createPayingCustomerButton;

    @FXML
    private Button createAssociateCustomerButton;

    @FXML
    private Button cancelButton;

    /**
     * Constructor for CreateCustomerController
     * 
     * @param magazineModel The model for the magazine
     */
    public CreateCustomerController(MagazineModel magazineModel) {
        this.magazineModel = magazineModel;
    }

    /**
     * Initialize the TableView with the supplements from the magazine
     * and allow multiple selection
     */
    @FXML
    public void initialize() {
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
    }

    /**
     * Create a PayingCustomer object with the entered data and navigate to the next
     * scene
     * If there are any errors in the input, set the error messages and do not
     * create the PayingCustomer
     */
    @FXML
    private void createPayingCustomerAction() {
        // Clear previous error messages
        nameError.setText("");
        emailError.setText("");
        streetNumberError.setText("");
        postcodeError.setText("");
        streetNameError.setText("");

        // Validate the input and set error messages
        if (customerName.getText().isEmpty()) {
            nameError.setText("Name field is empty.");
        } else if (!ValidationHelper.validateName(customerName.getText())) {
            nameError.setText("Invalid name.");
        }

        if (customerEmail.getText().isEmpty()) {
            emailError.setText("Email field is empty.");
        } else if (!ValidationHelper.validateEmail(customerEmail.getText())) {
            emailError.setText("Invalid email.");
        } else if (ValidationHelper.isDuplicateCustomer(magazineModel.getMagazine().getCustomers(),
                customerEmail.getText())) {
            emailError.setText("Email is not unique.");
        }

        if (streetNumber.getText().isEmpty()) {
            streetNumberError.setText("Street number field is empty.");
        } else if (!ValidationHelper.isInteger(streetNumber.getText())) {
            streetNumberError.setText("Invalid street number.");
        }

        if (postcode.getText().isEmpty()) {
            postcodeError.setText("Postcode field is empty.");
        } else if (!ValidationHelper.isInteger(postcode.getText())) {
            postcodeError.setText("Invalid postcode.");
        }

        if (streetName.getText().isEmpty()) {
            streetNameError.setText("Street name field is empty.");
        }

        // If there are no error messages, proceed with creating the customer
        if (nameError.getText().equals("") && emailError.getText().equals("") && streetNumberError.getText().equals("")
                && postcodeError.getText().equals("") && streetNameError.getText().equals("")) {
            String name = customerName.getText();
            String email = customerEmail.getText();
            Address address = new Address(Integer.parseInt(streetNumber.getText()), streetName.getText(),
                    suburb.getText(),
                    Integer.parseInt(postcode.getText()));
            ObservableList<Supplement> selectedSupplements = supplementSelection.getSelectionModel().getSelectedItems();

            // Create a new PayingCustomer object with the entered data
            PayingCustomer payingCustomer = new PayingCustomer(name, address, email,
                    new ArrayList<>(selectedSupplements));

            // Navigate to the next scene with the created PayingCustomer and magazineModel
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/create/CreatePayingCustomer.fxml"));

                // Set the controller factory
                loader.setControllerFactory(c -> new CreatePayingCustomerController(magazineModel, payingCustomer));

                VBox nextLayout = loader.load();
                Scene nextScene = new Scene(nextLayout, 320, 200);

                Stage stage = (Stage) createPayingCustomerButton.getScene().getWindow();
                stage.setScene(nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Create an AssociateCustomer object with the entered data and navigate to the
     * next scene
     * If there are any errors in the input, set the error messages and do not
     * create the AssociateCustomer
     * If there are no paying customers, display an alert and return
     */
    @FXML
    private void createAssociateCustomerAction() {
        // Check if there are any paying customers
        boolean hasPayingCustomers = false;
        for (Customer customer : magazineModel.getMagazine().getCustomers()) {
            if (customer.getCustomerType().equals("Paying")) {
                hasPayingCustomers = true;
                break;
            }
        }

        if (!hasPayingCustomers) {
            // Display an alert and return
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Paying Customers");
            alert.setContentText("Please create a paying customer first.");
            alert.showAndWait();
            return;
        }

        // Clear previous error messages
        nameError.setText("");
        emailError.setText("");
        streetNumberError.setText("");
        postcodeError.setText("");
        streetNameError.setText("");

        // Validate the input and set error messages
        if (customerName.getText().isEmpty()) {
            nameError.setText("Name field is empty.");
        } else if (!ValidationHelper.validateName(customerName.getText())) {
            nameError.setText("Invalid name.");
        }

        if (customerEmail.getText().isEmpty()) {
            emailError.setText("Email field is empty.");
        } else if (!ValidationHelper.validateEmail(customerEmail.getText())) {
            emailError.setText("Invalid email.");
        } else if (ValidationHelper.isDuplicateCustomer(magazineModel.getMagazine().getCustomers(),
                customerEmail.getText())) {
            emailError.setText("Email is not unique.");
        }

        if (streetNumber.getText().isEmpty()) {
            streetNumberError.setText("Street number field is empty.");
        } else if (!ValidationHelper.isInteger(streetNumber.getText())) {
            streetNumberError.setText("Invalid street number.");
        }

        if (postcode.getText().isEmpty()) {
            postcodeError.setText("Postcode field is empty.");
        } else if (!ValidationHelper.isInteger(postcode.getText())) {
            postcodeError.setText("Invalid postcode.");
        }

        if (streetName.getText().isEmpty()) {
            streetNameError.setText("Street name field is empty.");
        }

        // If there are no error messages, proceed with creating the customer
        if (nameError.getText().equals("") && emailError.getText().equals("") && streetNumberError.getText().equals("")
                && postcodeError.getText().equals("") && streetNameError.getText().equals("")) {
            String name = customerName.getText();
            String email = customerEmail.getText();
            Address address = new Address(Integer.parseInt(streetNumber.getText()), streetName.getText(),
                    suburb.getText(),
                    Integer.parseInt(postcode.getText()));
            ObservableList<Supplement> selectedSupplements = supplementSelection.getSelectionModel().getSelectedItems();

            // Create a new AssociateCustomer object with the entered data
            AssociateCustomer associateCustomer = new AssociateCustomer(name, address, email,
                    new ArrayList<>(selectedSupplements));

            // Navigate to the next scene with the created AssociateCustomer and
            // magazineModel
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/create/CreateAssociateCustomer.fxml"));

                // Set the controller factory
                loader.setControllerFactory(c -> new CreateAssociateCustomerController(magazineModel,
                        associateCustomer));

                VBox nextLayout = loader.load();
                Scene nextScene = new Scene(nextLayout, 340, 200);

                Stage stage = (Stage) createAssociateCustomerButton.getScene().getWindow();
                stage.setScene(nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
