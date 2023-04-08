package com.cs2212;
import java.util.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * The Campus class represents a campus with a name and an address, and includes a map of buildings indexed by integer values. 
 * The Campus class provides methods to add a building to the map of buildings, get the map of buildings, get the name of the campus, and get the address of the campus.
 *
 * @author JOYZH
 */
public class Campus {
    private Map<Integer, Building> buildings;
    private String name;
    private String address;
    
    /**
     * Constructs a Campus object with the specified name and address, and initializes the map of buildings
     * to an empty HashMap.
     * @param name The name of the campus.
     * @param address The address of the campus.
     */
    public Campus(String name, String address) {
        buildings = new HashMap<Integer,Building>();
        this.name = name;
        this.address = address;
    }
    
    /**
     * Adds a Building object to the map of buildings.
     *
     * @param index The integer index value to associate with the building.
     * @param building The Building object to add to the campus.
     */
    public void addBuilding(int index, Building building) {
        buildings.put(index,building);
    }
    
    /**
     * Returns the map of buildings associated with the Campus object.
     * @return The map of buildings associated with the Campus object.
     */
    public Map getBuildings() {
        return buildings;
    }
    
     /**
     * Returns the name of the campus.
     * @return The name of the campus.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the address of the campus.
     * @return The address of the campus.
     */
    public String getAddress() {
        return address;
    }
}
