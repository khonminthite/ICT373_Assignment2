/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: PayingCustomer.java
 * @Purpose: A child class of Customer that represents a paying customer
 * A paying customer has a payment method, payment detail and a list of associate customers
 * A payment method/detail could only be a specified credit card or a bank account
 * @Assumptions: 
 * @Limitations: 
 */

package customer;

import java.io.Serializable;
import java.util.ArrayList;

import magazine.Magazine;
import magazine.Supplement;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PayingCustomer extends Customer implements Serializable {
    private String paymentMethod;
    private Integer paymentDetail;
    private ArrayList<AssociateCustomer> associateCustomers;

    /**
     * Constructor for PayingCustomer without payment method and payment detail
     * 
     * @param name        The name of the customer
     * @param address     The address of the customer
     * @param email       The email address of the customer
     * @param supplements The list of supplements the customer is interested in
     */
    public PayingCustomer(String name, Address address, String email, ArrayList<Supplement> supplements) {
        super("Paying", name, address, email, supplements);
        this.paymentMethod = "";
        this.paymentDetail = 0;
        this.associateCustomers = new ArrayList<AssociateCustomer>();
    }

    /**
     * Constructor for PayingCustomer with payment method and payment detail
     * 
     * @param name          The name of the customer
     * @param address       The address of the customer
     * @param email         The email address of the customer
     * @param supplements   The list of supplements the customer is interested in
     * @param paymentMethod The payment method of the customer
     * @param paymentDetail The payment detail of the customer
     */
    public PayingCustomer(String name, Address address, String email,
            ArrayList<Supplement> supplements, String paymentMethod,
            Integer paymentDetail) {
        super("Paying", name, address, email, supplements);
        this.paymentMethod = paymentMethod;
        this.paymentDetail = paymentDetail;
        this.associateCustomers = new ArrayList<AssociateCustomer>();
    }

    /**
     * Constructor for PayingCustomer without supplements
     * 
     * @param name          The name of the customer
     * @param address       The address of the customer
     * @param email         The email address of the customer
     * @param paymentMethod The payment method of the customer
     * @param paymentDetail The payment detail of the customer
     */
    public PayingCustomer(String name, Address address, String email, String paymentMethod,
            Integer paymentDetail) {
        super("Paying", name, address, email);
        this.paymentMethod = paymentMethod;
        this.paymentDetail = paymentDetail;
        this.associateCustomers = new ArrayList<AssociateCustomer>();
    }

    /**
     * Get the payment method of the customer
     * 
     * @return The payment method of the customer
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Get the payment detail of the customer
     * 
     * @return The payment detail of the customer
     */
    public Integer getPaymentDetail() {
        return paymentDetail;
    }

    /**
     * Get the list of associate customers
     * 
     * @return The list of associate customers
     */
    public ArrayList<AssociateCustomer> getAssociateCustomers() {
        return associateCustomers;
    }

    /**
     * Set the payment method of the customer
     * 
     * @param paymentMethod The payment method of the customer
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Set the payment detail of the customer
     * 
     * @param paymentDetail The payment detail of the customer
     */
    public void setPaymentDetail(Integer paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    /**
     * Set the list of associate customers
     * 
     * @param associateCustomers The list of associate customers
     */
    public void setAssociateCustomers(ArrayList<AssociateCustomer> associateCustomers) {
        this.associateCustomers = associateCustomers;
    }

    /**
     * Add an associate customer to the list of associate customers
     * 
     * @param associateCustomer The associate customer to be added
     */
    public void addAssociateCustomer(AssociateCustomer associateCustomer) {
        this.associateCustomers.add(associateCustomer);
    }

    /**
     * Remove an associate customer from the list of associate customers
     * 
     * @param associateCustomer The associate customer to be removed
     */
    public void removeAssociateCustomer(AssociateCustomer associateCustomer) {
        associateCustomers.remove(associateCustomer);
    }

    /**
     * Calculate the monthly payment for the customer
     * 
     * @param magazine The magazine service the customer is interested in
     * @return The monthly payment for the customer
     */
    public double calculateMonthlyPayment(Magazine magazine) {
        double monthlyPayment = magazine.getWeeklyCost() * 4;
        for (Supplement supplement : getSupplements()) {
            monthlyPayment += supplement.getWeeklyCost() * 4;
        }
        for (AssociateCustomer associateCustomer : associateCustomers) {
            monthlyPayment += associateCustomer.calculateMonthlyPayment(magazine);
        }
        return monthlyPayment;
    }

    /**
     * Calculate the weekly payment for the customer
     * 
     * @param magazine The magazine service the customer is interested in
     * @return The weekly payment for the customer
     */
    public double calculateWeeklyPayment(Magazine magazine) {
        double weeklyPayment = magazine.getWeeklyCost();
        for (Supplement supplement : getSupplements()) {
            weeklyPayment += supplement.getWeeklyCost();
        }
        for (AssociateCustomer associateCustomer : associateCustomers) {
            weeklyPayment += associateCustomer.calculateWeeklyPayment(magazine);
        }
        return weeklyPayment;
    }

    /**
     * Get the billing history for the customer
     * 
     * @param magazine The magazine service the customer is interested in
     * @return The billing history for the customer
     */
    public String getBillingHistory(Magazine magazine) {
        StringBuilder billingHistory = new StringBuilder();
        ExecutorService executorService = Executors.newFixedThreadPool(4); // Use a pool with a specific number of threads

        // Task to calculate monthly payment
        Callable<Double> monthlyPaymentTask = () -> calculateMonthlyPayment(magazine);

        // Task to build the main billing information
        Callable<String> billingInfoTask = () -> {
            StringBuilder info = new StringBuilder();
            info.append("==========================\n");
            info.append("Bill for Last Month\n");
            info.append("-----------------------------------\n");
            info.append("Total Amount Due for the Month - ")
                .append(String.format("$%.2f", calculateMonthlyPayment(magazine)))
                .append("\n\n");
            return info.toString();
        };

        // Task to build itemized costs
        Callable<String> itemizedCostTask = () -> {
            StringBuilder itemized = new StringBuilder();
            itemized.append("Itemized Cost for the Month\n");
            itemized.append("-----------------------------------");
            itemized.append("\nMagazine Cost - ")
                    .append(String.format("$%.2f", magazine.getWeeklyCost() * 4))
                    .append("\n");

            for (Supplement supplement : this.getSupplements()) {
                itemized.append(supplement.getName())
                        .append(" Cost - ")
                        .append(String.format("$%.2f", supplement.getWeeklyCost() * 4))
                        .append("\n");
            }
            return itemized.toString();
        };

        // Task to build associate customer costs
        Callable<String> associateCostsTask = () -> {
            StringBuilder associateCosts = new StringBuilder();
            ArrayList<AssociateCustomer> associateCustomers = this.getAssociateCustomers();
            if (!associateCustomers.isEmpty()) {
                associateCosts.append("\nAssociate Customer Costs\n");
                associateCosts.append("-----------------------------------");
            }

            for (AssociateCustomer associateCustomer : associateCustomers) {
                associateCosts.append("\nAssociate Customer Email: ")
                        .append(associateCustomer.getEmail())
                        .append("\nMagazine Cost - ")
                        .append(String.format("$%.2f", magazine.getWeeklyCost() * 4))
                        .append("\n");

                for (Supplement supplement : associateCustomer.getSupplements()) {
                    associateCosts.append(supplement.getName())
                            .append(" Cost - ")
                            .append(String.format("$%.2f", supplement.getWeeklyCost() * 4))
                            .append("\n");
                }
            }
            return associateCosts.toString();
        };

        try {
            // Submit tasks and get futures
            Future<String> billingInfoFuture = executorService.submit(billingInfoTask);
            Future<String> itemizedCostFuture = executorService.submit(itemizedCostTask);
            Future<String> associateCostsFuture = executorService.submit(associateCostsTask);

            // Append results from futures
            billingHistory.append(billingInfoFuture.get());
            billingHistory.append(itemizedCostFuture.get());
            billingHistory.append(associateCostsFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Handle exceptions 
        } finally {
            executorService.shutdown(); // Always shut down the executor
        }

        billingHistory.append("==========================\n");
        return billingHistory.toString();
    }
}
