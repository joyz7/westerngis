package com.mycompany.westernmap;
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
    private Building building;
    private String name;
    private String address;
    
    private Campus() {
        buildings = new HashMap<Integer,Building>();
        this.building = building;
        this.name = name;
        this.address = address;
    }
    
    public Map getBuildings() {
        return buildings;
    }
    
    public Building getBuilding() {
        return building;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAddress() {
        return address;
    }
}
