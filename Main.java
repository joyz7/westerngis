/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212;
import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.JScrollPane;

/**
 *
 * @author rrenv
 */
public class Main extends JFrame {
    
    private JFrame mainFrame;
    
    public Main() {
                
//        // Load built in POIs from JSON
        JSONParser parser = new JSONParser();
        DefaultListModel<String> names = new DefaultListModel<>();           
        try {
           Object obj = parser.parse(new FileReader("src/main/java/com/cs2212/poi.json"));
           JSONObject jsonObject = (JSONObject)obj;
           JSONArray pois = (JSONArray) jsonObject.get("pois");

           for(Object o : pois) {
                JSONObject poi = (JSONObject) o;
                long layerId = (long)poi.get("layerid");
                long xCoord = (long)poi.get("xcoord");
                long yCoord = (long)poi.get("ycoord");
                String roomNum = (String)poi.get("roomnum");
                String name = (String)poi.get("name");
                String description = (String)poi.get("description");
                boolean builtIn = (boolean)poi.get("builtin");
  
                POI newPoi = new POI(0, layerId, xCoord, yCoord, roomNum, name, description, builtIn);
                names.addElement(newPoi.getName());
                
           }
        } catch(Exception e) {
            System.out.println("error");
           e.printStackTrace();
        }
        
        mainFrame = new JFrame();      
      
        Border blackBorder = BorderFactory.createLineBorder(Color.black);

        JPanel favourites = new JPanel(new BorderLayout());
        favourites.setBounds(900,0,300,80);
        favourites.setBorder(blackBorder);
        JLabel favouritesLabel = new JLabel("Favourites");
        favouritesLabel.setBounds(900, 0, 20, 20);
        JList<String> favouritesList = new JList<>(names);
        favouritesList.setBounds(900, 20, 300, 80);
        favourites.add(favouritesLabel);
        favourites.add(favouritesList);
        mainFrame.add(favourites);
             
        JPanel created = new JPanel();
        created.setBounds(900,80,300,80);
        created.setBorder(blackBorder);
        JLabel createdLabel = new JLabel("Created POIs");
        createdLabel.setBounds(900, 80, 20, 20);
        created.add(createdLabel);
        mainFrame.add(created);
        
        JPanel classrooms = new JPanel();
        classrooms.setBounds(900,160,300,80);
        classrooms.setBorder(blackBorder);
        JLabel classroomLabel = new JLabel("Classrooms");
        classroomLabel.setBounds(900, 160, 20, 20);
        classrooms.add(classroomLabel);
        mainFrame.add(classrooms);
        
        JPanel navigation = new JPanel();
        navigation.setBounds(900,240,300,80);
        navigation.setBorder(blackBorder);
        JLabel navigationLabel = new JLabel("Navigation");
        navigationLabel.setBounds(1000, 240, 20, 20);
        navigation.add(navigationLabel);
        mainFrame.add(navigation);
        
        JPanel washrooms = new JPanel();
        washrooms.setBounds(900,320,300,80);
        washrooms.setBorder(blackBorder);
        JLabel washroomLabel = new JLabel("Washrooms");
        washroomLabel.setBounds(1000, 320, 20, 20);
        washrooms.add(washroomLabel);
        mainFrame.add(washrooms);
        
        JPanel entryExit = new JPanel();
        entryExit.setBounds(900,400,300,80);
        entryExit.setBorder(blackBorder);
        JLabel entryExitLabel = new JLabel("Entry and Exit Points");
        entryExitLabel.setBounds(1000, 400, 20, 20);
        entryExit.add(entryExitLabel);
        mainFrame.add(entryExit);
        
        JPanel genLabs = new JPanel();
        genLabs.setBounds(900,480,300,80);
        genLabs.setBorder(blackBorder);
        JLabel genLabLabel = new JLabel("GenLabs");
        genLabLabel.setBounds(1000, 480, 20, 20);
        genLabs.add(genLabLabel);
        mainFrame.add(genLabs);
        
        JPanel resturaunts = new JPanel();
        resturaunts.setBounds(900,560,300,80);
        resturaunts.setBorder(blackBorder);
        JLabel resturauntLabel = new JLabel("Resturaunts");
        resturauntLabel.setBounds(1000, 560, 20, 20);
        resturaunts.add(resturauntLabel);
        mainFrame.add(resturaunts);
        
        JPanel csSpecfic = new JPanel();
        csSpecfic.setBounds(900,640,300,80);
        csSpecfic.setBorder(blackBorder);
        JLabel csSpecficLabel = new JLabel("CS Specfic");
        csSpecficLabel.setBounds(1000, 640, 20, 20);
        csSpecfic.add(csSpecficLabel);
        mainFrame.add(csSpecfic);
        
        mainFrame.setSize(1200,800);//400 width and 500 height  
        mainFrame.setLayout(null);//using no layout managers  
        mainFrame.setVisible(true);//making the frame visible   

        
        Campus campus = new Campus("Western University", "1151 Richmond Street, London");
        
        

//        
//        // Load user data from JSON
//        
//        // Load user creates POIs
//        
//        // Load favourites
//        
//        // Load current layers
   
       // }
    }
    /*
    public Main(User user, JSONArray createdPois, JSONArray favourites, JSONArray activeLayers) {
        
    } 
            
//    public POI getPOI(int poiId) {
//        
//    }
    
    public boolean deletePOI(int poiId) {
        return true;
    }
    */
}