package com.cs2212;
import java.util.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author JOYZH
 */
public class Campus {
    private Map<Integer, Building> buildings;
    private String name;
    private String address;

    public Campus(String name, String address) {
        buildings = new HashMap<Integer,Building>();
        this.name = name;
        this.address = address;
    }
    
    public Map getBuildings() {
        return buildings;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAddress() {
        return address;
    }
}
