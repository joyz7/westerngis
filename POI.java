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
    private long layerId;
    private long xcoord;
    private long ycoord;
    private String roomNum;
    private String name;
    private String description;
    private boolean builtIn;
    
    public POI(int id, long layerId, long xCoord, long yCoord, String roomNum, String name, String description, boolean builtIn) {
        this.id = id;
        this.layerId = layerId;
        this.xcoord = xCoord;
        this.ycoord = yCoord;
        this.roomNum = roomNum;
        this.name = name;
        this.description = description;
        this.builtIn = builtIn;
    }
    
    public int getId() {
        return id;
    }
    
    public long getLayerId() {
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
    
    private void setLayerId(long id) {
        layerId = id;
    }
    
    private void setXCoord(long x) {
        xcoord = x;
    }
    
    private void setYCoord(long y) {
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
