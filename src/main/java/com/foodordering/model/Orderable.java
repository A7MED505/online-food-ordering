package com.foodordering.model;

/**
 * Interface for items that can be ordered in the food ordering system.
 * Demonstrates OOP principle: Interface implementation
 */
public interface Orderable {
    /**
     * Get the unique identifier of the orderable item
     * @return the item ID
     */
    String getId();
    
    /**
     * Get the name/title of the orderable item
     * @return the item name
     */
    String getName();
    
    /**
     * Get the price of the orderable item
     * @return the price in USD
     */
    double getPrice();
    
    /**
     * Get a description of the orderable item
     * @return item description
     */
    String getDescription();
    
    /**
     * Check if the item is currently available for ordering
     * @return true if available, false otherwise
     */
    boolean isAvailable();
}
