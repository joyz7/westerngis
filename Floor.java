/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212;

import java.util.*;

/**
 *
 * @author JOYZH
 */
public class Floor {
    private Map<Integer,Layer> layers;
    private int number;
    private Building building;
    private String image;
    
    public Floor(int number, Building building, String image) {
        layers = new HashMap<>();
        this.number = number;
        this.building = building;
        this.image = image;
    }
    
    public void addLayer(Layer layer) {
        layers.put(number,layer);
    }
    
    public Map getLayers() {
        return layers;
    }
    
    public int getNumber() {
        return number;
    }
    
    public Building getBuilding() {
        return building;
    }
    
    public String getImage() {
        return image;
    }
    
    /*private Map search() {
        return 
    }*/
}
