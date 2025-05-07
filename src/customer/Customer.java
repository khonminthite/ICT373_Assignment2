/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: Customer.java
 * @Purpose: An abstract class that represents a customer
 * A customer can be a paying customer or an associate customer
 * A customer contains a customer type, name, email address, and a list of supplements they are interested in
 * @Assumptions:
 * @Limitations:
 */

package customer;

import java.io.Serializable;
import java.util.ArrayList;

import magazine.Supplement;

public abstract class Customer implements Serializable {
    private String customerType;
    private String name;
    private String email;
    private Address address;
    private ArrayList<Supplement> supplements;

    /**
     * Constructor for with supplements
     * 
     * @param customerType The type of customer
     * @param name         The name of the customer
     * @param address      The address of the customer
     * @param email        The email address of the customer
     * @param supplements  The list of supplements the customer is interested in
     */
    public Customer(String customerType, String name, Address address, String email,
            ArrayList<Supplement> supplements) {
        this.customerType = customerType;
        this.name = name;
        this.address = address;
        this.email = email;
        this.supplements = supplements;
    }

    /**
     * Constructor for without supplements
     * 
     * @param customerType The type of customer
     * @param name         The name of the customer
     * @param address      The address of the customer
     * @param email        The email address of the customer
     */
    public Customer(String customerType, String name, Address address, String email) {
        this.customerType = customerType;
        this.name = name;
        this.address = address;
        this.email = email;
        this.supplements = new ArrayList<Supplement>();
    }

    /**
     * Get the type of customer
     * 
     * @return The type of customer
     */
    public String getCustomerType() {
        return customerType;
    }

    /**
     * Get the name of the customer
     * 
     * @return The name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Get the address of the customer
     * 
     * @return The address of the customer
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Get the email address of the customer
     * 
     * @return The email address of the customer
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get the list of supplements the customer is interested in
     * 
     * @return The list of supplements the customer is interested in
     */
    public ArrayList<Supplement> getSupplements() {
        return supplements;
    }

    /**
     * Add a supplement to the list of supplements the customer is interested in
     * 
     * @param name The name of the supplement
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the address of the customer
     * 
     * @param address The address of the customer
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Set the email address of the customer
     * 
     * @param email The email address of the customer
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set the list of supplements the customer is interested in
     * 
     * @param supplements The list of supplements the customer is interested in
     */
    public void setSupplements(ArrayList<Supplement> supplements) {
        this.supplements = supplements;
    }

    /**
     * Add a supplement to the list of supplements the customer is interested in
     * 
     * @param supplement The supplement to add
     */
    public void removeSupplement(Supplement supplement) {
        this.supplements.remove(supplement);
    }
}
