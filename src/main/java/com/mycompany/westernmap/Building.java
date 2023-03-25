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
public class Building {
    private Map<Integer,Floor> floors;
    private Floor floor;
    private String name;
    private String address;
    
    private Building() {
        floors = new HashMap<Integer,Floor>();
        this.floor = floor;
        this.name = name;
        this.address = address;
    }
    
    public Map getBuildings() {
        return floors;
    }
    
    public Floor getFloor() {
        return floor;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAddress() {
        return address;
    }
}
