/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: ViewController.java
 * @Purpose: A class that represents the view controller for the application
 * @Assumptions:
 * @Limitations:
 */

package controller;

import java.io.IOException;

import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TreeItem;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import customer.Address;
import customer.AssociateCustomer;
import customer.Customer;
import customer.PayingCustomer;
import magazine.Supplement;
import model.MagazineModel;

public class ViewController {
    private MagazineModel magazineModel;

    private TreeItem<String> supplementsInfo;

    private TreeItem<String> rootCustomer;

    @FXML
    private TreeView<String> supplementList;

    @FXML
    private TreeView<String> customerList;

    @FXML
    private TreeView<String> infoPanel;

    @FXML
    private Button viewButton;

    @FXML
    private Button createButton;

    @FXML
    private Button editButton;

    /**
     * Constructor
     * 
     * @param magazineModel the magazine model
     */
    public ViewController(MagazineModel magazineModel) {
        this.magazineModel = magazineModel;
    }

    /**
     * Initialize the view controller
     * Populate the supplement list and customer list
     */
    @FXML
    public void initialize() {
        // Create a root item for the magazine service
        TreeItem<String> rootMagazineService = new TreeItem<>("Magazine Service");

        // Create a tree item for the magazine information
        TreeItem<String> magazineInfo = new TreeItem<>("Magazine");

        // Add the weekly cost of the magazine to the magazine information
        magazineInfo.getChildren()
                .add(new TreeItem<>("Weekly Cost:    " + magazineModel.getMagazine().getWeeklyCost()));

        // Add the magazine information to the root item
        rootMagazineService.getChildren().add(magazineInfo);

        // Create a tree item for the supplements information
        supplementsInfo = new TreeItem<>("Supplements");

        // For each supplement in the magazine, add its name to the supplements
        // information
        for (Supplement supplement : magazineModel.getMagazine().getSupplements()) {
            supplementsInfo.getChildren().add(new TreeItem<>(supplement.getName()));
        }

        // Add the supplements information to the root item
        rootMagazineService.getChildren().add(supplementsInfo);

        // Set the root item of the supplement list
        supplementList.setRoot(rootMagazineService);

        // Create a root item for the customers
        rootCustomer = new TreeItem<>("Customers");

        // For each customer in the magazine, add their email to the customers root item
        for (Customer customer : magazineModel.getMagazine().getCustomers()) {
            rootCustomer.getChildren().add(new TreeItem<>(customer.getEmail()));
        }

        // Set the root item of the customer list
        customerList.setRoot(rootCustomer);

        // Add a change listener to the supplement list that calls onSupplementSelected
        // when a supplement is selected
        supplementList.getSelectionModel().selectedItemProperty().addListener(this::onSupplementSelected);

        // Add a change listener to the customer list that calls onCustomerSelected when
        // a customer is selected
        customerList.getSelectionModel().selectedItemProperty().addListener(this::onCustomerSelected);
    }

    /**
     * Handle the event when a supplement is selected
     * 
     * @param observable the observable value
     * @param oldValue   the old value
     * @param newValue   the new value
     */
    private void onSupplementSelected(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue,
            TreeItem<String> newValue) {
        // Check if the new value is not null and is a descendant of supplementsInfo
        if (newValue != null && isDescendant(supplementsInfo, newValue)) {
            // Get the selected supplement from the magazine model
            Supplement selectedSupplement = magazineModel.getSupplementByName(newValue.getValue());

            // Create a new tree item for the supplement information
            TreeItem<String> supplementInfo = new TreeItem<>("Supplement");

            // Add the name of the supplement to the supplement information
            supplementInfo.getChildren().add(new TreeItem<>("Name:    " + selectedSupplement.getName()));

            // Add the weekly cost of the supplement to the supplement information
            supplementInfo.getChildren().add(new TreeItem<>("Weekly Cost:    " + selectedSupplement.getWeeklyCost()));

            // Set the root item of the info panel to the supplement information
            infoPanel.setRoot(supplementInfo);
        }
    }

    /**
     * Check if a tree item is a descendant of another tree item
     * 
     * @param parent
     * @param child
     * @return true if the child is a descendant of the parent, false otherwise
     */
    private boolean isDescendant(TreeItem<String> parent, TreeItem<String> child) {
        // Get the parent of the child node
        TreeItem<String> currentParent = child.getParent();

        // Loop until there are no more parent nodes
        while (currentParent != null) {
            // Check if the current parent node is the same as the parent node we're looking
            // for
            if (currentParent == parent) {
                // If it is, return true
                return true;
            }
            // If it's not, move up to the next parent node
            currentParent = currentParent.getParent();
        }
        // If we've gone through all the parent nodes and haven't found the parent we're
        // looking for, return false
        return false;
    }

    /**
     * Create a tree item for customer information
     * 
     * @param selectedCustomer the selected customer
     * @return the tree item for customer information
     */
    private TreeItem<String> createCustomerInfo(Customer selectedCustomer) {
        // Create a new TreeItem for the customer information
        TreeItem<String> customerInfo = new TreeItem<>("Information Panel");

        // Add the customer type, name, and email to the customer information
        customerInfo.getChildren().add(new TreeItem<>("Customer Type:    " + selectedCustomer.getCustomerType()));
        customerInfo.getChildren().add(new TreeItem<>("Name:    " + selectedCustomer.getName()));
        customerInfo.getChildren().add(new TreeItem<>("Email:    " + selectedCustomer.getEmail()));

        // Get the customer's address
        Address address = selectedCustomer.getAddress();

        // Create a new TreeItem for the address information and add it to the customer
        // information
        TreeItem<String> addressInfo = new TreeItem<>("Address");
        addressInfo.getChildren().add(new TreeItem<>("Street Number:    " + address.getStreetNumber()));
        addressInfo.getChildren().add(new TreeItem<>("Street Name:    " + address.getStreetName()));
        addressInfo.getChildren().add(new TreeItem<>("Suburb:    " + address.getSuburb()));
        addressInfo.getChildren().add(new TreeItem<>("Postcode:    " + address.getPostcode()));
        customerInfo.getChildren().add(addressInfo);

        // Create a new TreeItem for the supplements information and add it to the
        // customer information
        TreeItem<String> supplementsInfo = new TreeItem<>("Supplement(s)");
        for (Supplement supplement : selectedCustomer.getSupplements()) {
            supplementsInfo.getChildren().add(new TreeItem<>(supplement.getName()));
        }
        customerInfo.getChildren().add(supplementsInfo);

        // Check if the customer is a paying customer
        if (selectedCustomer.getCustomerType().equals("Paying")) {
            PayingCustomer payingCustomer = (PayingCustomer) selectedCustomer;

            // Create a new TreeItem for the associate customers information and add it to
            // the customer information
            TreeItem<String> associateCustomersInfo = new TreeItem<>("Associate Customer(s)");
            for (AssociateCustomer associateCustomer : payingCustomer.getAssociateCustomers()) {
                associateCustomersInfo.getChildren().add(new TreeItem<>(associateCustomer.getEmail()));
            }
            customerInfo.getChildren().add(associateCustomersInfo);

            // Add the payment method and payment detail to the customer information
            customerInfo.getChildren().add(new TreeItem<>("Payment Method:    " + payingCustomer.getPaymentMethod()));
            customerInfo.getChildren().add(new TreeItem<>("Payment Detail:    " + payingCustomer.getPaymentDetail()));

            // Add the billing history to the customer information
            String billingHistory = payingCustomer.getBillingHistory(magazineModel.getMagazine());
            TreeItem<String> billingHistoryInfo = new TreeItem<>("Billing History");
            billingHistoryInfo.getChildren().add(new TreeItem<>(billingHistory));
            customerInfo.getChildren().add(billingHistoryInfo);
        } else if (selectedCustomer.getCustomerType().equals("Associate")) {
            // If the customer is an associate customer, add the paying customer's email to
            // the customer information
            AssociateCustomer associateCustomer = (AssociateCustomer) selectedCustomer;
            customerInfo.getChildren().add(
                    new TreeItem<>("Paying Customer:    " + associateCustomer.getPayingCustomer().getEmail()));
        }

        // Return the customer information
        return customerInfo;
    }

    /**
     * Handle the event when a customer is selected
     * 
     * @param observable the observable value
     * @param oldValue   the old value
     * @param newValue   the new value
     */
    private void onCustomerSelected(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue,
            TreeItem<String> newValue) {
        // Check if the new value is not null and its parent is the root customer
        if (newValue != null && newValue.getParent() == rootCustomer) {
            // Get the selected customer based on the new value
            Customer selectedCustomer = magazineModel.getCustomerByEmail(newValue.getValue());

            // Create a new task to create the customer information
            Task<TreeItem<String>> task = new Task<TreeItem<String>>() {
                @Override
                protected TreeItem<String> call() throws Exception {
                    // Call the createCustomerInfo method with the selected customer
                    return createCustomerInfo(selectedCustomer);
                }
            };

            // Set the action when the task is succeeded
            task.setOnSucceeded(e -> {
                // Set the root of the info panel with the value of the task
                infoPanel.setRoot(task.getValue());
            });

            // Set the action when the task is failed
            task.setOnFailed(e -> {
                // Get the exception of the task
                Throwable exception = task.getException();
                // Print the error message to the console
                System.err.println("Error occurred: " + exception.getMessage());
                // Display an error message to the user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error occurred while retrieving customer information.");
                alert.setContentText("Please refresh the page and try again.");
                alert.showAndWait();
            });

            // Start the task in a new thread
            new Thread(task).start();
        }
    }

    /**
     * Handle the event when the view button is clicked
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
     * Handle the event when the create button is clicked
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
     * Handle the event when the edit button is clicked
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

}
