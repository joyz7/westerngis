/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212;

import java.util.*;

/**
 * The Layer class represents a layer of pois on a floor in a building.
 * @author JOYZH
 */
public class Layer {
    private String name;
    private String id;
    private Floor floor;
    
    /**
     * Creates a new Layer object with the specified name, active status, floor, and ID.
     * @param name the name of the layer
     * @param active the active status of the layer
     * @param floor the floor on which the layer is located
     * @param id the ID of the layer
     */
    public Layer(String name, boolean active, Floor floor, String id) {
        this.name = name;
        this.id = id;
        this.floor = floor;
    }
    
    /**
     * Returns the name of the layer.
     * @return the name of the layer
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the ID of the layer.
     * @return the ID of the layer
     */
    public String getID() {
        return id;
    }
}