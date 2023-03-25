/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.westernmap;

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
    private Floor floor;
    
    private Layer(String name, boolean active, Floor floor) {
        poiRef = new HashMap<Integer,Integer>();
        this.name = name;
        this.id = 0;
        this.active = active;
        this.floor = floor;
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