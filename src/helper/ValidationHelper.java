/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite 
 * @Date: 
 * @File: ValidationHelper.java
 * @Purpose: A class that contains helper methods for validation
 * Contains methods for the following validations:
 * 1. Email validation
 * 2. Name validation
 * 4. Payment method validation (card / bank account only)
 * 5. Payment details validation (card number or bank account number only)
 * 6. Cost validation
 * 7. Integer validation
 * 8. Duplicate supplement validation
 * 9. Duplicate supplement validation excluding current supplement 
 * 10. Duplicate customer validation
 * @Assumptions: 
 * 1) The email validation is based on the general email format
 * 2) The name validation is just to check if the name contains only alphabets and special characters like ',. -
 * 3) The payment method validation is based on the two payment methods available  (card / bank)
 * 4) The payment details validation is just to check if the payment details contain only numbers
 * @Limitations:
 * 1) The payment details validation does not actually check if the card number or bank account number is valid
 */

package helper;

import java.util.ArrayList;

import customer.Customer;
import magazine.Supplement;

public class ValidationHelper {
    /**
     * Validate email
     * 
     * @param email email to validate
     * @return true if email is valid, false otherwise
     */
    public static boolean validateEmail(String email) {
        return email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9.-]+$");
    }

    /**
     * Validate name
     * 
     * @param name name to validate
     * @return true if name is valid, false otherwise
     */
    public static boolean validateName(String name) {
        return name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
    }

    /**
     * Validate payment method
     * 
     * @param paymentMethod payment method to validate
     * @return true if payment method is valid, false otherwise
     */
    public static boolean validatePaymentMethod(String paymentMethod) {
        return paymentMethod.equals("card") || paymentMethod.equals("bank");
    }

    /**
     * Validate payment details
     * 
     * @param paymentDetails payment details to validate
     * @return true if payment details is valid, false otherwise
     */
    public static boolean validatePaymentDetails(String paymentDetails) {
        return paymentDetails.matches("^[0-9]*$");
    }

    /**
     * Validate cost
     * 
     * @param costInput cost to validate
     * @return true if cost is valid, false otherwise
     */
    public static boolean validateCost(String costInput) {
        try {
            Double.parseDouble(costInput);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validate String is integer
     * 
     * @param str String to validate
     * @return true if integer is valid, false otherwise
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Check if supplement name is duplicate
     * 
     * @param supplements List of supplements
     * @param name        Name of supplement
     * @return true if supplement is duplicate, false otherwise
     */
    public static boolean isDuplicateSupplement(ArrayList<Supplement> supplements, String name) {
        for (Supplement supplement : supplements) {
            if (supplement.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    
     /**
     * Check if supplement name is duplicate, excluding the current supplement name
     * 
     * @param supplements List of supplements
     * @param name        Name of supplement to check
     * @param currentName Name of the supplement being edited
     * @return true if supplement is duplicate (excluding current), false otherwise
     */
    public static boolean isDuplicateSupplementExcludingCurrent(ArrayList<Supplement> supplements, String name, String currentName) {
        for (Supplement supplement : supplements) {
            if (supplement.getName().equalsIgnoreCase(name) && !supplement.getName().equalsIgnoreCase(currentName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if customer email is duplicate
     * 
     * @param customers List of customers
     * @param email     Email of customer
     * @return
     */
    public static boolean isDuplicateCustomer(ArrayList<Customer> customers, String email) {
        for (Customer customer : customers) {
            if (customer.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
}
