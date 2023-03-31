/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212;
import java.util.HashSet;
/**
 *
 * @author rrenv
 */
public class Consumer extends User {
    
    private HashSet<POI> pois;
    private HashSet<POI> favourites;
    private HashSet<Layer> activeLayers;
            
    public Consumer (String username, String password) {
        super(username, password);
        this.pois = new HashSet<POI>();
        this.favourites = new HashSet<POI>();
        this.activeLayers = new HashSet<Layer>();  
        // soap made this change
    }
    
    public HashSet<POI> getPOIs () {
        return this.pois;
    }
    
    public HashSet<POI> getFavourites () {
        return this.favourites;
    }
    
    public HashSet<Layer> getLayers () {
        return this.activeLayers;
    }
    
    public boolean setActiveLayers (HashSet<Layer> newActiveLayers) {
        this.activeLayers = newActiveLayers;
        return true;
    }
    
    public boolean addPOI (POI newPOI) {
        this.pois.add(newPOI);
        return true;
    }
    
    public boolean addFavourite (POI newFavourite) {
        this.favourites.add(newFavourite);
        return true;
    }
    
    public boolean deletePOI (POI poi) {
        this.pois.remove(poi);
        return true;
    }
        
    public boolean deleteFavourite (POI poi) {
        this.pois.remove(poi);
        return true;
    }
}
