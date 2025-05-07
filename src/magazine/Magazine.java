/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: Magazine.java
 * @Purpose: A class that represents a magazine service object.
 * A magazine service has a weekly cost, a list of supplements, and a list of customers.
 * @Assumptions:
 * @Limitations:
 */

package magazine;

import java.io.Serializable;
import java.util.ArrayList;

import customer.Customer;

public class Magazine implements Serializable {
    private double weeklyCost;
    private ArrayList<Supplement> supplements;
    private ArrayList<Customer> customers;

    /**
     * Constructor for Magazine
     * 
     * @param weeklyCost The weekly cost of the magazine service
     */
    public Magazine(double weeklyCost) {
        this.weeklyCost = weeklyCost;
        this.supplements = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    /**
     * Get the weekly cost of the magazine service
     * 
     * @return The weekly cost of the magazine service
     */
    public double getWeeklyCost() {
        return weeklyCost;
    }

    /**
     * Get the list of supplements
     * 
     * @return The list of supplements
     */
    public ArrayList<Supplement> getSupplements() {
        return supplements;
    }

    /**
     * Get the list of customers
     * 
     * @return The list of customers
     */
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    /**
     * Set the weekly cost of the magazine service
     * 
     * @param weeklyCost The weekly cost of the magazine service
     */
    public void setWeeklyCost(double weeklyCost) {
        this.weeklyCost = weeklyCost;
    }

    /**
     * Set the list of supplements
     * 
     * @param supplements The list of supplements
     */
    public void setSupplements(ArrayList<Supplement> supplements) {
        this.supplements = supplements;
    }

    /**
     * Set the list of customers
     * 
     * @param customers The list of customers
     */
    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Add a supplement to the list of supplements
     * 
     * @param supplement The supplement to add
     */
    public void addSupplement(Supplement supplement) {
        this.supplements.add(supplement);
    }

    /**
     * Add a customer to the list of customers
     * 
     * @param customer The customer to add
     */
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }
}
