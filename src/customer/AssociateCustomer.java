/**
 * @Title: ICT373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: AssociateCustomer.java
 * @Purpose: A child class of Customer that represents an associate customer
 * An Associate Customer is a customer whose subscription is paid for by a specified Paying Customer.
 * @Assumptions:
 * @Limitations:
 */

package customer;

import java.io.Serializable;
import java.util.ArrayList;

import magazine.*;

public class AssociateCustomer extends Customer implements Serializable {
    private PayingCustomer payingCustomer;

    /**
     * Constructor for AssociateCustomer without paying customer
     * 
     * @param name        The name of the customer
     * @param address     The address of the customer
     * @param email       The email address of the customer
     * @param supplements The list of supplements the customer is interested in
     */
    public AssociateCustomer(String name, Address address, String email, ArrayList<Supplement> supplements) {
        super("Associate", name, address, email, supplements);
    }

    /**
     * Constructor for AssociateCustomer with paying customer
     * 
     * @param name           The name of the customer
     * @param address        The address of the customer
     * @param email          The email address of the customer
     * @param supplements    The list of supplements the customer is interested in
     * @param payingCustomer The paying customer that is paying for the subscription
     */
    public AssociateCustomer(String name, Address address, String email, ArrayList<Supplement> supplements,
            Customer payingCustomer) {
        super("Associate", name, address, email, supplements);
        this.payingCustomer = (PayingCustomer) payingCustomer;
    }

    /**
     * Constructor for AssociateCustomer without supplements
     * 
     * @param name           The name of the customer
     * @param address        The address of the customer
     * @param email          The email address of the customer
     * @param payingCustomer The paying customer that is paying for the subscription
     */
    public AssociateCustomer(String name, Address address, String email, Customer payingCustomer) {
        super("Associate", name, address, email);
        this.payingCustomer = (PayingCustomer) payingCustomer;
    }

    /**
     * Get the paying customer
     * 
     * @return The paying customer
     */
    public Customer getPayingCustomer() {
        return payingCustomer;
    }

    /**
     * Set the paying customer
     * 
     * @param payingCustomer The paying customer
     */
    public void setPayingCustomer(Customer payingCustomer) {
        this.payingCustomer = (PayingCustomer) payingCustomer;
    }

    /**
     * Calculate the weekly payment for the customer
     * 
     * @param magazine The magazine service the customer is subscribed to
     * @return The weekly payment for the customer
     */
    public double calculateWeeklyPayment(Magazine magazine) {
        double weeklyPayment = magazine.getWeeklyCost();
        for (Supplement supplement : getSupplements()) {
            weeklyPayment += supplement.getWeeklyCost();
        }
        return weeklyPayment;
    }

    /**
     * Calculate the monthly payment for the customer
     * 
     * @param magazine The magazine service the customer is subscribed to
     * @return The monthly payment for the customer
     */
    public double calculateMonthlyPayment(Magazine magazine) {
        double monthlyPayment = magazine.getWeeklyCost() * 4;
        for (Supplement supplement : getSupplements()) {
            monthlyPayment += supplement.getWeeklyCost() * 4;
        }
        return monthlyPayment;
    }
}
