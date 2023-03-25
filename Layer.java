/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
<<<<<<< HEAD
package com.mycompany.westernmap;
=======
package com.cs2212;
>>>>>>> a04d80a84edb05e1596bfed4d4d7eb871db09921

import java.util.*;

/**
 *
 * @author JOYZH
 */
public class Layer {
    private Map<Integer,Integer> poiRef;
    private String name;
    private int id;
    private boolean active;
<<<<<<< HEAD
    private Floor floor;
    
    private Layer(String name, boolean active, Floor floor) {
        poiRef = new HashMap<Integer,Integer>();
        this.name = name;
        this.id = 0;
        this.active = active;
        this.floor = floor;
=======
    private Set activeLayers;
    
    private Layer() {
        poiRef = new HashMap<Integer,Integer>();
        this.name = name;
        this.id = id;
        this.active = active;
        this.activeLayers = activeLayers;
>>>>>>> a04d80a84edb05e1596bfed4d4d7eb871db09921
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
<<<<<<< HEAD
=======

    public Set getActiveLayers() {
        return activeLayers;
    }
>>>>>>> a04d80a84edb05e1596bfed4d4d7eb871db09921
}