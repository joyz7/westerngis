/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212;
import java.util.HashSet;
/**
 *The Consumer class represents a user who has the ability to add their own personal pois and favourite pois
 * @author rrenv
 */
public class Consumer extends User {
    
    private HashSet<POI> pois;
    private HashSet<POI> favourites;
    private HashSet<Layer> activeLayers;
    
    /**
     * Creates a Consumer with the specified username and password and to store their own POIs
     * @param username the username of the user
     * @param password the password of the user
     */
    public Consumer (String username, String password) {
        super(username, password);
        this.pois = new HashSet<POI>();
        this.favourites = new HashSet<POI>();
        this.activeLayers = new HashSet<Layer>();  
        // soap made this change
    }
    
    /**
     * Returns a set of the POIs added by the user.
     * @return a set of the POIs added by the user
     */
    public HashSet<POI> getPOIs () {
        return this.pois;
    }
    
    /**
     * Returns a set of the POIs marked as favourite by the user.
     * @return a set of the POIs marked as favourite by the user
     */
    public HashSet<POI> getFavourites () {
        return this.favourites;
    }
    
    /**
     * Returns a set of the layers currently active for the user.
     * @return a set of the layers currently active for the user
     */
    public HashSet<Layer> getLayers () {
        return this.activeLayers;
    }
    
    /**
     * Sets the layers that should be active for the user.
     * @param newActiveLayers the new set of active layers
     * @return true if the set of active layers was successfully updated, false otherwise
     */
    public boolean setActiveLayers (HashSet<Layer> newActiveLayers) {
        this.activeLayers = newActiveLayers;
        return true;
    }
    
     /**
     * Adds a new POI to the user's list of added POIs.
     * @param newPOI the new POI to add
     * @return true if the POI was successfully added, false otherwise
     */
    public boolean addPOI (POI newPOI) {
        this.pois.add(newPOI);
        return true;
    }
    
    /**
     * Marks a POI as a favourite for the user.
     * @param newFavourite the POI to mark as a favourite
     * @return true if the POI was successfully marked as a favourite, false otherwise
     */
    public boolean addFavourite (POI newFavourite) {
        this.favourites.add(newFavourite);
        return true;
    }
    
    /**
     * Deletes a POI from the user's list of added POIs.
     * @param poi the POI to delete
     * @return true if the POI was successfully deleted, false otherwise
     */
    public boolean deletePOI (POI poi) {
        this.pois.remove(poi);
        return true;
    }
    
    /**
     * Deletes a POI from the user's list of favourites.
     * @param poi the POI to delete
     * @return true if the POI was successfully deleted from the list of favourites, false otherwise
     */
    public boolean deleteFavourite (POI poi) {
        this.pois.remove(poi);
        return true;
    }
}
