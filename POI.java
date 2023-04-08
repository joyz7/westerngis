/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.*;

/**
 * The POI class represents a point of interest.  
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
    private mainscreen mainframe;
    private boolean isFavourite;

    /** Creates a new POI with the given parameters and initializes its flags.
     * @param id The ID of the POI
     * @param layerId The ID of the layer the POI belongs to
     * @param xCoord The X-coordinate of the POI in the map
     * @param yCoord The Y-coordinate of the POI in the map
     * @param roomNum The room number of the POI
     * @param name The name of the POI
     * @param description The description of the POI
     * @param builtIn Whether the POI is built-in or not
     */
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
        this.isFavourite = false;
        
        try {
            BufferedImage POI_IMG = ImageIO.read(new File("src/main/java/com/cs2212/images/poi.png"));
            Image SCALED_POI_IMG = POI_IMG.getScaledInstance(25, 40, Image.SCALE_SMOOTH);
        // Create a label to hold the POI image and set its bounds
            POILbl = new JLabel(new ImageIcon(SCALED_POI_IMG));
            POILbl.setBounds((int)xcoord, (int)ycoord, 25, 40);
            POILbl.setToolTipText("POI: " + this.name +"\nRm Num: " + this.roomNum + "\nDescription: " + this.description);
            
            POILbl.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Clicked");
                    System.out.println(id);
                    mainframe.displayPOI(id);
                }
            });   
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    /** Returns the label that holds the POI image.
     * @return the label that holds the POI image.
     */
    public JLabel getLbl(){
        return this.POILbl;
    }
    
    /**
    Returns the ID of the POI.
    @return the ID of the POI.
    */
    public int getId() {
        return id;
    }
    
    /**
     * Returns the layer ID of the POI.
     * @return the layer ID of the POI.
    */
    public String getLayerId() {
        return layerId;
    }
    
    /**
     * Returns the x-coordinate of the POI.
     * @return the x-coordinate of the POI.
    */
    public long getXCoord() {
        return xcoord;
    }
    
    /**
     * Returns the y-coordinate of the POI.
     * @return the y-coordinate of the POI.
    */
    public long getYCoord() {
        return ycoord;
    }
    
    /**
     * Returns the roomNum of the POI.
     * @return the roomNum of the POI.
    */
    public String getRoomNum() {
        return roomNum;
    }
    
    /**
     * Returns the name of the POI.
     * @return the name of the POI.
    */
    public String getName() {
        return name;
    }
    
    /**
     * Returns a boolean that measure if the POI is a favourite 
     * @return a boolean that measure if the POI is a favourite 
    */
    public boolean getFavourite() {
        return isFavourite;
    }
    
    /**
     * Flips the boolean of isFavourite variable
    */
    public void setFavourite() {
        isFavourite = !isFavourite;
    }
    
    /**
     * Returns the description of the POI.
     * @return the description of the POI.
    */
    public String getDescription() {
        return description;
    }
    
    /**
     * Returns a boolean that measure if the POI is a built in variable
     * @return a boolean that measure if the POI is a built in variable 
    */
    public boolean isBuiltIn() {
        return builtIn;
    }
    
    /**
     * Sets the layer ID of the POI.
     * @param id the layer ID to be set.
     */
    public void setLayerId(String id) {
        layerId = id;
    }
    
    /**
     * Sets the X-coordinate of the POI.
     * @param x the X-coordinate to be set.
    */
    public void setXCoord(long x) {
        xcoord = x;
    }
    
    /**
     * Sets the Y-coordinate of the POI.
     * @param y the Y-coordinate to be set.
     */
    public void setYCoord(long y) {
        ycoord = y;
    }
    
    /**
     * Sets the room number of the POI.
     * @param num the room number to be set.
    */
    public void setRoomNum(String num) {
        roomNum = num;
    }
    
    /**
     * Sets the name of the POI.
     * @param n the name to be set.
    */
    public void setName(String n) {
        name = n;
    }
    
    /**
     * Sets the description of the POI.
     * @param desc the description to be set.
     */
    public void setDescription(String desc) {
        description = desc;
    }
    
    /**
     * Returns a boolean indicating whether the POI is active or not.
     * @return a boolean indicating whether the POI is active or not.
     */
    public boolean isActive() {
        return isActive;
    }
    
    /**
     * Sets the active status of the POI.
     */
    public void setActive() {
        isActive = !isActive;
    }
    
    /**
     * Sets the mainframe of the POI.
     * @param mainframe the mainframe to be set.
     */
    public void setMainframe(mainscreen mainframe) {
        this.mainframe = mainframe;
    }
    
    /**
     * Returns the name of the POI.
     * @return the name of the POI.
     */

    public String checkBoxText() {
        return name;
    }
    
    @Override
    public String toString() {
        String layer = this.getLayerId().substring(0,1);
        if (layer.equals("a")) {
            return "AH: " + name;
        } else if (layer.equals("m")) {
            return "MC: " + name;
        } else if (layer.equals("h")){ 
            return "HSB: " + name;
        }
        return name;
    }
    
}
