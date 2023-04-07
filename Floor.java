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
public class Floor {
    private Map<Integer,List<Layer>> layers;
    private List<Layer> array;
    private int number;
    private Building building;
    private String image;
    
    public Floor(int number, Building building, String image) {
        layers = new HashMap<>();
        array = new ArrayList<>();
        this.number = number;
        this.building = building;
        this.image = image;
    }
    
    public void addLayer(Layer layer) {
        array.add(layer);
        layers.put(number,array);
    }
    
    public Map getLayers() {
        return layers;
    }
    
    public Object[] getLayersArray() {
        Object[] layerNum = new Object[array.size()];
        for (int i=0; i<array.size(); i++) {
            layerNum[i] = layers.get(number).get(i).getID();
        }
        return layerNum;
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
    
}
