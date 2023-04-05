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
    private String layerId;
    private long xcoord;
    private long ycoord;
    private String roomNum;
    private String name;
    private String description;
    private boolean builtIn;
    private boolean isActive;
    
    public POI(int id, String layerId, long xCoord, long yCoord, String roomNum, String name, String description, boolean builtIn) {
        this.id = id;
        this.layerId = layerId;
        this.xcoord = xCoord;
        this.ycoord = yCoord;
        this.roomNum = roomNum;
        this.name = name;
        this.description = description;
        this.builtIn = builtIn;
        this.isActive = false;
    }
    
    public int getId() {
        return id;
    }
    
    public String getLayerId() {
        return layerId;
    }
    
    public long getXCoord() {
        return xcoord;
    }
    
    public long getYCoord() {
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
        return builtIn;
    }
    
    public void setLayerId(String id) {
        layerId = id;
    }
    
    public void setXCoord(long x) {
        xcoord = x;
    }
    
    public void setYCoord(long y) {
        ycoord = y;
    }
    
    public void setRoomNum(String num) {
        roomNum = num;
    }
    
    public void setName(String n) {
        name = n;
    }
    
    public void setDescription(String desc) {
        description = desc;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive() {
        isActive = !isActive;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
