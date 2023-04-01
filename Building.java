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
public class Building {
    private Map<String,Floor> floors;
    private String name;
    private String address;
    
    public Building(String name, String address) {
        floors = new HashMap<>();
        this.name = name;
        this.address = address;
    }
    
    public void addFloor(Floor floor) {
        floors.put(name,floor);
    }
    
    public Map getFloors() {
        return floors;
    }
        
    public String getName() {
        return name;
    }
    
    public String getAddress() {
        return address;
    }
}
