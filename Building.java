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
    private Map<String,List<Floor>> floors;
    private List<Floor> array;
    private String name;
    private String address;
    
    public Building(String name, String address) {
        floors = new HashMap<>();
        array = new ArrayList<>();
        this.name = name;
        this.address = address;
    }
    
    public void addFloor(Floor floor) {
        array.add(floor);
        floors.put(name,array);
    }
    
    public Map getFloors() {
        return floors;
    }
    
    public Object[] getFloorsArray() {
        Object[] floorNum = new Object[array.size()];
        for (int i=0; i<array.size(); i++) {
            floorNum[i] = floors.get(name).get(i).getNumber();
        }
        return floorNum;
    }
        
    public String getName() {
        return name;
    }
    
    public String getAddress() {
        return address;
    }
}
