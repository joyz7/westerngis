/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212;

/**
 *
 * @author rrenv
 */
public class Developer extends User {
    
    public Developer(String username, String password) {
        super(username, password);
    }
    
    public boolean addPOI(int layerID, int xCoord, int yCoord, String roomNum, String name, String description) {
        newPOI = new POI(layerId, xCoord, yCoord, roomNum, name, description, true);
    }
    
    public boolean editPOI(Main main, int poiID, int layerID, int xCoord, int yCoord, String roomNum, String name, String description) {
        poiObject = main.getPOI(poiID);
        poiObject.setXCoord(xCoord);
        poiObject.setYCoord(yCoord);
        poiObject.setRoomNum(roomNum);
        poiObject.setName(name);
        poiObject.setDescription(description);
    }
    
    public boolean deletePOI (int poiID) {
        main.deletePOI(poiID);
    } 
}
