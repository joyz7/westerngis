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
public class Floor {
    private Map<Integer,Layer> layers;
    private int number;
    private Building building;
    private String image;
    
    private Floor(int number, Building building, String image) {
        layers = new HashMap<Integer,Layer>();
        this.number = number;
        this.building = building;
        this.image = image;
    }
    
    public Map getLayers() {
        return layers;
    }
    
    public int getNumber() {
        return number;
    }
    
    public Building getBuilding() {
        return building;
    }
    
    public String getImage() {
        return image;
    }
    
    private Map search() {
        return 
    }
}
