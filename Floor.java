/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
<<<<<<< HEAD
package com.mycompany.westernmap;
=======
package com.cs2212;
>>>>>>> a04d80a84edb05e1596bfed4d4d7eb871db09921

import java.util.*;

/**
 *
 * @author JOYZH
 */
public class Floor {
    private Map<Integer,Layer> layers;
    private int number;
<<<<<<< HEAD
    private Building building;
    private String image;
    
    private Floor(int number, Building building, String image) {
=======
    private String building;
    private String image;
    
    private Floor() {
>>>>>>> a04d80a84edb05e1596bfed4d4d7eb871db09921
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
    
<<<<<<< HEAD
    public Building getBuilding() {
=======
    public String getBuilding() {
>>>>>>> a04d80a84edb05e1596bfed4d4d7eb871db09921
        return building;
    }
    
    public String getImage() {
        return image;
    }
    
    private Map search() {
        return 
    }
}
