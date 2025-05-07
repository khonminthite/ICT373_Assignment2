/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: Address.java
 * @Purpose: A class that represents an address object.
 * @Assumptions:
 * @Limitations:
 */

package customer;

import java.io.Serializable;

public class Address implements Serializable {
    private int streetNumber;
    private String streetName;
    private String suburb;
    private int postcode;

    /**
     * Constructor for Address
     * 
     * @param streetNumber The street number
     * @param streetName   The street name
     * @param suburb       The suburb
     * @param postcode     The postcode
     */
    public Address(int streetNumber, String streetName, String suburb, int postcode) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.suburb = suburb;
        this.postcode = postcode;
    }

    /**
     * Get the street number
     * 
     * @return The street number
     */
    public int getStreetNumber() {
        return streetNumber;
    }

    /**
     * Get the street name
     * 
     * @return The street name
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Get the suburb
     * 
     * @return The suburb
     */
    public String getSuburb() {
        return suburb;
    }

    /**
     * Get the postcode
     * 
     * @return The postcode
     */
    public int getPostcode() {
        return postcode;
    }

    /**
     * Set the street number
     * 
     * @param streetNumber The street number
     */
    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    /**
     * Set the street name
     * 
     * @param streetName The street name
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Set the suburb
     * 
     * @param suburb The suburb
     */
    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    /**
     * Set the postcode
     * 
     * @param postcode The postcode
     */
    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    /**
     * Get the full address
     * 
     * @return The full address
     */
    @Override
    public String toString() {
        return streetNumber + " " + streetName + ", " + suburb + ", " + postcode;
    }
}
