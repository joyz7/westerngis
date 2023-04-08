/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212;

import java.util.*;

/**
 * The Floor class represents a floor in a building. It contains a list of layers that make up the floor.
 * @author JOYZH
 */
public class Floor {
    private Map<Integer,List<Layer>> layers;
    private List<Layer> array;
    private int number;
    private Building building;
    private String image;
    
    /**
     * Creates a new Floor object with the specified number, building, and image.
     * @param number the number of the floor
     * @param building the building to which this floor belongs
     * @param image the image associated with this floor
     */
    public Floor(int number, Building building, String image) {
        layers = new HashMap<>();
        array = new ArrayList<>();
        this.number = number;
        this.building = building;
        this.image = image;
    }
    
    /**
     * Adds a layer to the floor.
     * @param layer the layer to add to the floor
     */
    public void addLayer(Layer layer) {
        array.add(layer);
        layers.put(number,array);
    }
    
    /**
     * Returns a map of all the layers on the floor.
     * @return a map of all the layers on the floor
     */
    public Map getLayers() {
        return layers;
    }
    
    /**
     * Returns an array of all the layer numbers on the floor.
     * @return an array of all the layer numbers on the floor
     */
    public Object[] getLayersArray() {
        Object[] layerNum = new Object[array.size()];
        for (int i=0; i<array.size(); i++) {
            layerNum[i] = layers.get(number).get(i).getID();
        }
        return layerNum;
    }
    
    /**
     * Returns the number of the floor.
     * @return the number of the floor
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Returns the building to which this floor belongs.
     * @return the building to which this floor belongs
     */
    public Building getBuilding() {
        return building;
    }
    
    /**
     * Returns the image associated with this floor.
     * @return the image associated with this floor
     */
    public String getImage() {
        return image;
    }
    
}
