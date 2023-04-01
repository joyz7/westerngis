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
public class Layer {
    private HashSet<Integer> poiRef;
    private String name;
    private int id;
    private boolean active;
    private Floor floor;
    
    public Layer(String name, boolean active, Floor floor) {
        this.name = name;
        //this.id = 0;
        this.active = active;
        this.floor = floor;
    }
    
    public void addPoi(int id, POI poi) {
        if (poi.getLayerId() == id) {
            poiRef.add(poi.getId());
        }
    }
    
    public boolean isActive() {
        return active;
    }
    
    public String getName() {
        return name;
    }
    
    public int getID() {
        return id;
    }
}