/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: CreatePayingCustomerController.java
 * @Purpose: Controller class for the create paying customer view
 * @Assumptions:
 * @Limitations:
 */

package controller.create;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.Scene;

import java.io.IOException;

import controller.CreateController;
import controller.ViewController;
import customer.PayingCustomer;
import helper.ValidationHelper;
import model.MagazineModel;

public class CreatePayingCustomerController {
    private MagazineModel magazineModel;
    private PayingCustomer payingCustomer;

    @FXML
    private ComboBox<String> paymentMethod;

    @FXML
    private TextField paymentDetails;

    @FXML
    private Button createPayingCustomerButton;

    @FXML
    private Button createAnotherCustomerButton;

    @FXML
    private Label paymentMethodError;

    @FXML
    private Label paymentDetailsError;

    @FXML
    private Button cancelButton;

    /**
     * Constructor for CreatePayingCustomerController
     * 
     * @param magazineModel  The model for the magazine
     * @param payingCustomer The paying customer to be created
     */
    public CreatePayingCustomerController(MagazineModel magazineModel, PayingCustomer payingCustomer) {
        this.magazineModel = magazineModel;
        this.payingCustomer = payingCustomer;
    }

    /**
     * createPayingCustomerAction method is called when the
     * createPayingCustomerButton is clicked
     * It creates a new PayingCustomer object with the entered payment method and
     * details
     * and adds it to the data model
     * If the input is valid, it saves the model and loads the View.fxml
     * If the input is invalid, it sets the error messages
     * and does not save the model
     */
    @FXML
    private void createPayingCustomerAction() {
        // Clear previous error messages
        paymentMethodError.setText("");
        paymentDetailsError.setText("");

        // Validate the input and set error messages
        if (paymentMethod.getValue() == null || paymentMethod.getValue().toString().isEmpty()) {
            paymentMethodError.setText("Payment method field is empty.");
        } else if (!ValidationHelper.validatePaymentMethod(paymentMethod.getValue())) {
            paymentMethodError.setText("Invalid payment method.");
        }

        if (paymentDetails.getText().isEmpty() || paymentDetails.getText().isBlank()) {
            paymentDetailsError.setText("Payment details field is empty.");
        } else if (!ValidationHelper.validatePaymentDetails(paymentDetails.getText())) {
            paymentDetailsError.setText("Invalid payment details.");
        }

        if (paymentMethodError.getText() == "" || paymentDetailsError.getText() == "") {
            // Set the payment method and details in the PayingCustomer object
            payingCustomer.setPaymentMethod(paymentMethod.getValue());
            payingCustomer.setPaymentDetail(Integer.parseInt(paymentDetails.getText()));

            // Add the PayingCustomer to the magazine's list of customers
            magazineModel.getMagazine().addCustomer(payingCustomer);

            // Get the Stage and pass it to the saveModel method
            Stage stage = (Stage) createPayingCustomerButton.getScene().getWindow();
            // Serialize the magazineModel and save it as a .ser file
            magazineModel.saveModel(stage);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
                // Set the controller factory
                loader.setControllerFactory(c -> new ViewController(magazineModel));

                VBox nextLayout = loader.load();
                Scene nextScene = new Scene(nextLayout, 740, 400);

                stage.setScene(nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * createAnotherCustomerAction method is called when the
     * createAnotherCustomerButton is clicked
     * It creates a new PayingCustomer object with the entered payment method and
     * details
     * and adds it to the data model
     * If there are any errors in the input, it sets the error messages
     * and does not add the PayingCustomer to the data model
     */
    @FXML
    private void createAnotherCustomerAction() {
        // Clear previous error messages
        paymentMethodError.setText("");
        paymentDetailsError.setText("");

        // Validate the input and set error messages
        if (paymentMethod.getValue() == null || paymentMethod.getValue().toString().isEmpty()) {
            paymentMethodError.setText("Payment method field is empty.");
        } else if (!ValidationHelper.validatePaymentMethod(paymentMethod.getValue())) {
            paymentMethodError.setText("Invalid payment method.");
        }

        if (paymentDetails.getText().isEmpty() || paymentDetails.getText().isBlank()) {
            paymentDetailsError.setText("Payment details field is empty.");
        } else if (!ValidationHelper.validatePaymentDetails(paymentDetails.getText())) {
            paymentDetailsError.setText("Invalid payment details.");
        }

        if (paymentMethodError.getText() == "" || paymentDetailsError.getText() == "") {
            // Set the payment method and details in the PayingCustomer object
            payingCustomer.setPaymentMethod(paymentMethod.getValue());
            payingCustomer.setPaymentDetail(Integer.parseInt(paymentDetails.getText()));

            // Add the PayingCustomer to the magazine's list of customers
            magazineModel.getMagazine().addCustomer(payingCustomer);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/create/CreateCustomer.fxml"));
                // Set the controller factory
                loader.setControllerFactory(c -> new CreateCustomerController(magazineModel));

                ScrollPane nextLayout = loader.load();
                Scene nextScene = new Scene(nextLayout, 400, 500);

                Stage stage = (Stage) createPayingCustomerButton.getScene().getWindow();
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
