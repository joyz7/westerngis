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
    private mainscreen mainframe;
    private boolean isFavourite;

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
    
    public boolean getFavourite() {
        return isFavourite;
    }
    
    public void setFavourite() {
        isFavourite = !isFavourite;
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
    
    public void setMainframe(mainscreen mainframe) {
        this.mainframe = mainframe;
    }

    @Override
    public String toString() {
        return name;
    }
    
    /*
    private void editPOIInfo() {
    	// Create a panel with a grid layout for the input boxes
        JPanel panel = new JPanel(new GridLayout(0, 2));

        // Add labels and text fields for point name, room number, and description
        panel.add(new JLabel("Point name:"));
        JTextField pointNameField = new JTextField();
        panel.add(pointNameField);

        panel.add(new JLabel("Room number:"));
        JTextField roomNumberField = new JTextField();
        panel.add(roomNumberField);

        panel.add(new JLabel("Description:"));
        JTextField descriptionField = new JTextField();
        panel.add(descriptionField);
        
        // Show the input dialog with the panel as the message
        int result = JOptionPane.showConfirmDialog(null, panel, "Enter point information", JOptionPane.OK_CANCEL_OPTION);

        // Check if the user clicked OK and get the input values
        if (result == JOptionPane.OK_OPTION) {
          String name = pointNameField.getText();
          String roomNum = roomNumberField.getText();
          String description = descriptionField.getText();
          
          if (result == JOptionPane.OK_OPTION && !pointNameField.getText().isEmpty() && !roomNumberField.getText().isEmpty() && !descriptionField.getText().isEmpty()) {
              
              // Edit POI
        	  this.setName(name);
        	  this.setRoomNum(name);
        	  this.setDescription(name);
        	  
                  JOptionPane.showMessageDialog(null, "Successfully Edited");
              }
              else{      
                JOptionPane.showMessageDialog(null, "Unsuccessful No POI Edited");
              }            
            } else {
                JOptionPane.showMessageDialog(null, "Unsuccessful No POI Edited");
        }

    }
    
    private void deletePOI(HashMap<Integer,POI> poiMap) {
    	int pid = this.getId();
    	poiMap.remove(pid);
    	
    	if (poiMap.get(pid) == null) {
               JOptionPane.showMessageDialog(null, "Successfully Deleted");
                  
         } else {
              JOptionPane.showMessageDialog(null, "Unsuccessful No POI Deleted");
      }
    }
   
    // Display POI info when location markers are clicked on
    private void displayPOIInfo(User user, HashMap<Integer, POI> poiMap, HashMap<String,String> developerMap, HashSet<POI> favourites) {
        String favOption = ""; // Text variable to change between favourite and unfavourite
        String[] buttons = {favOption, "Edit", "Delete"};
        boolean isDev = false; // Changes to true if user is a developer      
        
        // Check if user is developer
        for (Map.Entry<String, String> entry : developerMap.entrySet()) {
            String username = entry.getKey();
            if (username.equals(user.getUsername())) {
                isDev = true;
            }
        }
        
        // Create pop up panel
        JPanel POIPopUp = new JPanel(new GridLayout(6,0 ));
	    // Display Name
        POIPopUp.add(new JLabel("Name:"));
        JLabel POIName = new JLabel(this.getName());
        POIPopUp.add(POIName);
        // Display Room Number
        POIPopUp.add(new JLabel("Room Number:"));
        JLabel POIRoom = new JLabel(this.getRoomNum());
        POIPopUp.add(POIRoom);
        // Display Description
        POIPopUp.add(new JLabel("Description:"));
        JLabel POIDescription = new JLabel(this.getDescription());
        POIPopUp.add(POIDescription);
        
        //Display checkbox for favourite
        JCheckBox isFavourite = new JCheckBox("Favourite");
        POIPopUp.add(isFavourite);
        
        //Event listener for the favourite option
        isFavourite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 // FAVOURITES ???????????????????????????????????????????????????????????
            }
        });
       
        if (isDev == true) {  
        	//Add two additional buttons
        	JButton devEdit = new JButton("Edit");
	        POIPopUp.add(devEdit);
	        
	        JButton devDelete = new JButton("Delete");
	        POIPopUp.add(devDelete);
	      
	        //Event listener for edit
	        devEdit.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	editPOIInfo();
	            }
	        });
	        
	      //Event listener for delete
	        devDelete.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	deletePOI(poiMap);
	            }
	        });
	       
        }
*/
}
