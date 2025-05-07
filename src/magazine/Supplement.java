/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: Supplements.java
 * @Purpose: A class that represents a supplement.
 * A supplement has a name and a weekly cost.
 * @Assumptions:
 * @Limitations:
 */

package magazine;

import java.io.Serializable;

public class Supplement implements Serializable {
    private String name;
    private double weeklyCost;

    /**
     * Constructor for Supplement
     * 
     * @param name       The name of the supplement
     * @param weeklyCost The weekly cost of the supplement
     */
    public Supplement(String name, double weeklyCost) {
        this.name = name;
        this.weeklyCost = weeklyCost;
    }

    /**
     * Get the name of the supplement
     * 
     * @return The name of the supplement
     */
    public String getName() {
        return name;
    }

    /**
     * Get the weekly cost of the supplement
     * 
     * @return The weekly cost of the supplement
     */
    public double getWeeklyCost() {
        return weeklyCost;
    }

    /**
     * Set the name of the supplement
     * 
     * @param name The name of the supplement
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the weekly cost of the supplement
     * 
     * @param weeklyCost The weekly cost of the supplement
     */
    public void setWeeklyCost(double weeklyCost) {
        this.weeklyCost = weeklyCost;
    }
}
