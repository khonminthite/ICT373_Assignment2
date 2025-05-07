/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: MagazineModel.java
 * @Purpose: A class that represents the model of the magazine service.
 * @Assumptions:
 * @Limitations:
 */

package model;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import customer.Address;
import customer.AssociateCustomer;
import customer.Customer;
import customer.PayingCustomer;
import magazine.Magazine;
import magazine.Supplement;

public class MagazineModel {
    private Magazine magazine;
    private String saveFileName;
    private String saveFilePath;

    /**
     * Constructor for MagazineModel
     */
    public MagazineModel() {
        this.magazine = null;
        this.saveFileName = "";
        this.saveFilePath = "";
    }

    /**
     * Get the magazine
     * 
     * @return The magazine object
     */
    public Magazine getMagazine() {
        return magazine;
    }

    /**
     * Get the file name of the saved file
     * 
     * @return The file name of the saved file
     */
    public String getSaveFileName() {
        return saveFileName;
    }

    /**
     * Set the magazine
     * 
     * @param magazine The magazine object
     */
    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }

    /**
     * Set the file name of the saved file
     * 
     * @param saveFileName The file name of the saved file
     */
    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    /**
     * Set the file path of the saved file
     * 
     * @param saveFilePath The file path of the saved file
     */
    public void setSaveFilePath(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    /**
     * Add a supplement to the magazine
     * 
     * @param supplement The supplement to be added
     */
    public void addSupplement(Supplement supplement) {
        this.magazine.addSupplement(supplement);
    }

    /**
     * Get a supplement by name
     * 
     * @param name The name of the supplement
     * @return The supplement with the given name
     */
    public Supplement getSupplementByName(String name) {
        for (Supplement supplement : this.magazine.getSupplements()) {
            if (supplement.getName().equals(name)) {
                return supplement;
            }
        }
        return null; // Return null if no supplement with the given name is found
    }

    /**
     * Get a customer by email
     * 
     * @param email The email of the customer
     * @return The customer with the given email
     */
    public Customer getCustomerByEmail(String email) {
        for (Customer customer : this.magazine.getCustomers()) {
            if (customer.getEmail().equals(email)) {
                return customer;
            }
        }
        return null; // Return null if no customer with the given email is found
    }

    /**
     * Edit the magazine's weekly cost
     * 
     * @param cost The new weekly cost
     */
    public void editMagazine(double cost) {
        this.magazine.setWeeklyCost(cost);
    }

    /**
     * Edit a supplement's name and cost
     * 
     * @param supplement The supplement to be edited
     * @param name       The new name
     * @param cost       The new cost
     */
    public void editSupplement(Supplement supplement, String name, double cost) {
        supplement.setName(name);
        supplement.setWeeklyCost(cost);
    }

    /**
     * Edit a Paying customer's details
     * 
     * @param payingCustomer      The paying customer to be edited
     * @param name                The new name
     * @param address             The new address
     * @param email               The new email
     * @param selectedSupplements The new list of supplements
     * @param paymentMethod       The new payment method
     * @param paymentDetails      The new payment details
     */
    public void editPayingCustomer(PayingCustomer payingCustomer, String name, Address address, String email,
            ArrayList<Supplement> selectedSupplements, String paymentMethod, Integer paymentDetails) {
        payingCustomer.setName(name);
        payingCustomer.setAddress(address);
        payingCustomer.setEmail(email);
        payingCustomer.setSupplements(selectedSupplements);
        payingCustomer.setPaymentMethod(paymentMethod);
        payingCustomer.setPaymentDetail(paymentDetails);
    }

    /**
     * Edit an Associate customer's details
     * 
     * @param associateCustomer      The associate customer to be edited
     * @param name                   The new name
     * @param address                The new address
     * @param email                  The new email
     * @param selectedSupplements    The new list of supplements
     * @param selectedPayingCustomer The new paying customer
     */
    public void editAssociateCustomer(AssociateCustomer associateCustomer,
            String name, Address address, String email, ArrayList<Supplement> selectedSupplements,
            PayingCustomer selectedPayingCustomer) {

        // Get the previous paying customer
        PayingCustomer oldPayingCustomer = (PayingCustomer) associateCustomer.getPayingCustomer();

        // If the previous paying customer is different from the new one
        if (!oldPayingCustomer.equals(selectedPayingCustomer)) {
            // Remove the associate customer from the previous paying customer's list of
            // associate customers
            oldPayingCustomer.removeAssociateCustomer(associateCustomer);

            // Add the associate customer to the new paying customer's list of associate
            // customers
            selectedPayingCustomer.addAssociateCustomer(associateCustomer);
        }

        // Update the associateCustomer in the customers list
        associateCustomer.setName(name);
        associateCustomer.setAddress(address);
        associateCustomer.setEmail(email);
        associateCustomer.setSupplements(selectedSupplements);
        associateCustomer.setPayingCustomer(selectedPayingCustomer);
    }

    /**
     * Delete a supplement from the magazine
     * 
     * @param supplement The supplement to be deleted
     */
    public void deleteSupplement(Supplement supplement) {
        // Remove the supplement from all customers
        for (Customer customer : this.magazine.getCustomers()) {
            customer.removeSupplement(supplement);
        }

        // Remove the supplement from the supplements list
        this.magazine.getSupplements().remove(supplement);
    }

    /**
     * Delete a customer from the magazine
     * 
     * @param customer The customer to be deleted
     */
    public void deleteCustomer(Customer customer) {
        // Remove the customer from the customers list
        this.magazine.getCustomers().remove(customer);

        // If the customer is a paying customer, remove all its associate customers from
        // Customer list as well
        if (customer.getCustomerType().equals("Paying")) {
            PayingCustomer payingCustomer = (PayingCustomer) customer;
            for (AssociateCustomer associateCustomer : payingCustomer.getAssociateCustomers()) {
                this.magazine.getCustomers().remove(associateCustomer);
            }
        } else {
            // If the customer is an associate customer, remove it from the paying
            // customer's list of associate customers
            PayingCustomer payingCustomer = (PayingCustomer) ((AssociateCustomer) customer).getPayingCustomer();
            payingCustomer.removeAssociateCustomer((AssociateCustomer) customer);
        }
    }

    /**
     * Save the model to a file
     * 
     * @param stage The stage to show the file chooser dialog
     */
    public void saveModel(Stage stage) {
        File file;
        if (!saveFilePath.equals("")) {
            file = new File(saveFilePath);
        } else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setTitle("Save Magazine");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SER files (*.ser)", "*.ser"));
            file = fileChooser.showSaveDialog(stage);
        }

        if (file != null) {
            try {
                FileOutputStream fileOut = new FileOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(this.magazine);
                setSaveFileName(file.getName());
                setSaveFilePath(file.getPath());
                out.close();
                fileOut.close();
                System.out.printf("Serialized data is saved in " + file.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Load the model from a file
     * 
     * @param stage The stage to show the file chooser dialog
     */
    public void loadModel(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SER files (*.ser)", "*.ser"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn);

                this.magazine = (Magazine) in.readObject();
                // Get the file name and save it
                setSaveFileName(file.getName());
                setSaveFilePath(file.getPath());

                in.close();
                fileIn.close();
                System.out.printf("Deserialized data is loaded from " + file.getPath());
            } catch (IOException i) {
                i.printStackTrace();
            } catch (ClassNotFoundException c) {
                System.out.println("Magazine not found");
                c.printStackTrace();
            }
        }
    }
}
