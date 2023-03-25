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
<<<<<<< HEAD
    private String name;
    private String address;
    
    public Campus(String name, String address) {
        buildings = new HashMap<Integer,Building>();
=======
    private Building building;
    private String name;
    private String address;
    
    private Campus() {
        buildings = new HashMap<Integer,Building>();
        this.building = building;
>>>>>>> a04d80a84edb05e1596bfed4d4d7eb871db09921
        this.name = name;
        this.address = address;
    }
    
    public Map getBuildings() {
        return buildings;
    }
    
    public Building getBuilding() {
<<<<<<< HEAD
        
=======
        return building;
>>>>>>> a04d80a84edb05e1596bfed4d4d7eb871db09921
    }
    
    public String getName() {
        return name;
    }
    
    public String getAddress() {
        return address;
    }
}
