/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: EditAddPayingCustomerController.java
 * @Purpose: Controller class for the edit add paying customer view
 * @Assumptions:
 * @Limitations:
 */

package controller.edit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import controller.EditController;
import customer.PayingCustomer;
import helper.ValidationHelper;
import model.MagazineModel;

public class EditAddPayingCustomerController {
    private MagazineModel magazineModel;
    private PayingCustomer payingCustomer;

    @FXML
    private Label paymentMethodError;

    @FXML
    private TextField paymentDetails;

    @FXML
    private ComboBox<String> paymentMethod;

    @FXML
    private Button createPayingCustomerButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label paymentDetailsError;

    /**
     * Constructor for EditAddPayingCustomerController
     * 
     * @param magazineModel  The model for the magazine
     * @param payingCustomer The paying customer to be added
     */
    public EditAddPayingCustomerController(MagazineModel magazineModel, PayingCustomer payingCustomer) {
        this.magazineModel = magazineModel;
        this.payingCustomer = payingCustomer;
    }

    /**
     * createPayingCustomerAction method is called when the
     * createPayingCustomerButton is clicked
     * It creates a new PayingCustomer object with the entered payment method and
     * details
     * and adds it to the data model
     * It then returns to the edit view
     * If there are any errors in the input, it sets the error messages
     * and does not add the PayingCustomer to the data model
     */
    @FXML
    void createPayingCustomerAction() {
        // Clear previous error messages
        paymentMethodError.setText("");
        paymentDetailsError.setText("");

        // Validate the input and set error messages
        if (paymentMethod.getValue() == null || paymentMethod.getValue().toString().isEmpty()) {
            paymentMethodError.setText("Payment method field is empty.");
        } else if (!ValidationHelper.validatePaymentMethod(paymentMethod.getValue())) {
            paymentMethodError.setText("Invalid payment method.");
        }

        if (paymentDetails.getText().isEmpty()) {
            paymentDetailsError.setText("Payment details field is empty.");
        } else if (!ValidationHelper.validatePaymentDetails(paymentDetails.getText())) {
            paymentDetailsError.setText("Invalid payment details.");
        }

        if (paymentMethodError.getText().equals("") && paymentDetailsError.getText().equals("")) {
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Edit.fxml"));
                // Set the controller factory
                loader.setControllerFactory(c -> new EditController(magazineModel));

                VBox nextLayout = loader.load();
                Scene nextScene = new Scene(nextLayout, 500, 400);

                stage.setScene(nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Cancel the edit paying customer action
     * It returns to the edit view
     */
    @FXML
    void cancelAction() {
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
