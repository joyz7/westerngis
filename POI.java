/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

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
    private JLabel POILbl;

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
        try {
            BufferedImage POI_IMG = ImageIO.read(new File(".\\src\\main\\java\\com\\cs2212\\images\\poi.png"));
            Image SCALED_POI_IMG = POI_IMG.getScaledInstance(25, 40, Image.SCALE_SMOOTH);
        // Create a label to hold the POI image and set its bounds
            POILbl = new JLabel(new ImageIcon(SCALED_POI_IMG));
            POILbl.setBounds((int)xcoord, (int)ycoord, 25, 40);
            POILbl.setToolTipText("POI: " + this.name +"\nRm Num: " + this.roomNum + "\nDescription: " + this.description);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public JLabel getLbl(){
        return this.POILbl;
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
