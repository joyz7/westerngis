/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212;
import java.io.*;
import java.util.*;
//import org.json.simple.*;
//import org.json.simple.parser.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author rrenv
 */
public class Main extends JFrame {
    
    private JFrame mainFrame;
    
    public Main() {
        
        mainFrame = new JFrame();      
      
        Border blackline = BorderFactory.createLineBorder(Color.black);

        JPanel favourites = new JPanel();
        favourites.setBounds(1000,0,200,80);
        favourites.setBorder(blackline);
        JLabel favouritesLabel = new JLabel("Favourites");
        favouritesLabel.setBounds(1000, 0, 20, 20);
        favourites.add(favouritesLabel);
        mainFrame.add(favourites);
             
        JPanel created = new JPanel();
        created.setBounds(1000,80,200,80);
        JLabel createdLabel = new JLabel("Created POIs");
        createdLabel.setBounds(1000, 80, 20, 20);
        mainFrame.add(created);
        mainFrame.add(createdLabel);
        
        JPanel classrooms = new JPanel();
        classrooms.setBounds(1000,160,200,80);
        JLabel classroomLabel = new JLabel("Created POIs");
        classroomLabel.setBounds(1000, 160, 20, 20);
        mainFrame.add(classrooms);
        mainFrame.add(classroomLabel);
        
        JPanel navigation = new JPanel();
        JPanel washrooms = new JPanel();
        JPanel entryExit = new JPanel();
        JPanel genLabs = new JPanel();
        JPanel resturaunts = new JPanel();
        JPanel csSpecfic = new JPanel();
        
        mainFrame.setSize(1200,800);//400 width and 500 height  
        mainFrame.setLayout(null);//using no layout managers  
        mainFrame.setVisible(true);//making the frame visible   

        
//        Campus campus = new Campus("Western University", "1151 Richmond Street, London");
        
//        // Load built in POIs from JSON
//        JSONParser parser = new JSONParser();
//                        
//        try {
//           Object obj = parser.parse(new FileReader("poi.json"));
//           JSONObject jsonObject = (JSONObject)obj;
//           JSONArray pois = (JSONArray) jsonObject.get("pois");
//
//           for(Object o : pois) {
//                JSONObject poi = (JSONObject) o;
//                long layerId = (long)poi.get("layerid");
//                long xCoord = (long)poi.get("xcoord");
//                long yCoord = (long)poi.get("ycoord");
//                String roomNum = (String)poi.get("roomnum");
//                String name = (String)poi.get("name");
//                String description = (String)poi.get("description");
//                boolean builtIn = (boolean)poi.get("builtin");
//  
//                POI newPoi = new POI(0, layerId, xCoord, yCoord, roomNum, name, description, builtIn);
//           }
//        } catch(Exception e) {
//            System.out.println("error");
//           e.printStackTrace();
//        }
        
        // Load user data from JSON
        
        // Load user creates POIs
        
        // Load favourites
        
        // Load current layers
   
    }
    
//    public POI getPOI(int poiId) {
//        
//    }
    
    public boolean deletePOI(int poiId) {
        return true;
    }
    
}
