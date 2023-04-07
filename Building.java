/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212;

import java.util.*;

/**
 * A Building represents a structure containing a number of Floors.
 * Floors can be added to the building via the addFloor() method.
 * The building maintains a List of Floors as well as a Map of Floors keyed by name.
 *
 * @author JOYZH
 */

public class Building {
    private Map<String,List<Floor>> floors;
    private List<Floor> array;
    private String name;
    private String address;
    private Integer numFloors;
    
     /**
     * Constructs a new Building with the specified name, address, and number of floors.
     * Initializes the floors map and array list.
     * @param name the name of the building
     * @param address the address of the building
     * @param numFloors the number of floors in the building
     */
    public Building(String name, String address, Integer numFloors) {
        floors = new HashMap<>();
        array = new ArrayList<>();
        this.name = name;
        this.address = address;
        this.numFloors = numFloors;
    }
    
     /**
     * Adds a Floor to the Building.
     * The Floor is added to the List of Floors and the Map of Floors keyed by the Building's name.
     * @param floor the Floor to add to the Building
     */
    public void addFloor(Floor floor) {
        array.add(floor);
        floors.put(name,array);
    }
    
    /**
     * Returns the Map of Floors keyed by the Building's name.
     * @return the Map of Floors keyed by the Building's name
     */
    public Map getFloors() {
        return floors;
    }
    
    /**
     * Returns the List of Floors.
     * @return the List of Floors
     */
    public List<Floor> getArray() {
        return array;
    }
    
    /**
     * Returns the number of floors in the Building.
     * @return the number of floors in the Building
     */
    public Integer getNumFloors() {
        return this.numFloors;
    }
    
     /**
     * Returns an Object array containing the numbers of the Floors in the Building.
     * @return an Object array containing the numbers of the Floors in the Building
     */
    public Object[] getFloorsArray() {
        Object[] floorNum = new Object[array.size()];
        for (int i=0; i<array.size(); i++) {
            floorNum[i] = floors.get(name).get(i).getNumber();
        }
        return floorNum;
    }
       
    /**
     * Returns the name of the Building.
     * @return the name of the Building
     */
    public String getName() {
        return name;
    }
    
     /**
     * Returns the address of the Building.
     * @return the address of the Building
     */
    public String getAddress() {
        return address;
    }
}
