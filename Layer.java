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
public class Layer {
    private String name;
    private String id;
    private Floor floor;
    
    public Layer(String name, boolean active, Floor floor, String id) {
        this.name = name;
        this.id = id;
        this.floor = floor;
    }
    
    public String getName() {
        return name;
    }
    
    public String getID() {
        return id;
    }
}