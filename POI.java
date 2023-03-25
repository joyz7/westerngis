/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212;

/**
 *
 * @author JOYZH
 */
public class POI {
    private int id;
    private int layerId;
    private int xcoord;
    private int ycoord;
    private String roomNum;
    private String name;
    private String description;
    private boolean isBuiltIn;
    
    public POI() {
        this.id = id;
        this.layerId = layerId;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.roomNum = roomNum;
        this.name = name;
        this.description = description;
        this.isBuiltIn = isBuiltIn;
    }
    
    public int getID() {
        return id;
    }
    
    public int getLayerId() {
        return layerId;
    }
    
    public int getXCoord() {
        return xcoord;
    }
    
    public int getYCoord() {
        return ycoord;
    }
    
    public String getRoomNum() {
        return roomNum;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isBuiltIn() {
        return isBuiltIn;
    }
    
    private void setLayerId(int id) {
        layerId = id;
    }
    
    private void setXCoord(int x) {
        xcoord = x;
    }
    
    private void setYCoord(int y) {
        ycoord = y;
    }
    
    private void setRoomNum(String num) {
        roomNum = num;
    }
    
    private void setName(String n) {
        name = n;
    }
    
    private void setDescription(String desc) {
        description = desc;
    }
}
