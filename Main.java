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
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

/**
 *
 * @author rrenv
 */
public class Main extends JFrame {
    
    private JFrame mainFrame;
    private HashMap<Integer,POI> poiMap;
    private HashSet<POI> builtinPoiObjects;
    private HashSet<POI> createdPoiObjects;
    private HashSet<POI> favouritePoiObjects;
    private HashMap<String,JSONArray> createdPois;
    private HashMap<String,JSONArray> favourites;
    private HashMap<String,JSONArray> activeLayers;
    private HashMap<String,String> consumerMap;
    private HashMap<String,String> developerMap;
    private Campus campus;
    private User user;
    private int count;
    private boolean newUser;
    
    public Main(User user, boolean newUser, HashMap<String,JSONArray> createdPois, HashMap<String,JSONArray> favourites, HashMap<String,JSONArray> activeLayers, HashMap<String,String> consumers,  HashMap<String,String> developers) throws IOException {
        
        this.newUser = newUser;
        this.user = user;
        this.createdPois = createdPois;
        this.favourites = favourites;
        this.activeLayers = activeLayers;
        this.consumerMap = consumers;
        this.developerMap = developers;
        poiMap = new HashMap<>();
        builtinPoiObjects = new HashSet<>();
        createdPoiObjects = new HashSet<>();
        favouritePoiObjects = new HashSet<>();
        
        if (!newUser) {
            JSONArray poiArray = createdPois.get(user.getUsername());
            HashSet<Integer> createdPoiId = new HashSet<Integer>();
            if (poiArray != null) {
                for (Object o : poiArray) {
                    JSONObject poi = (JSONObject) o;
                    createdPoiId.add((Integer)poi.get("id"));
                }
            }

            JSONArray favouriteArray = favourites.get(user.getUsername());
            HashSet<Integer> favouritePoiId = new HashSet<Integer>();
            if (favouriteArray != null) {
                for (Object o : poiArray) {
                    JSONObject poi = (JSONObject) o;
                    createdPoiId.add((Integer)poi.get("id"));
                }
            }

            JSONArray layerArray = activeLayers.get(user.getUsername());
            HashSet<String> activeLayerId = new HashSet<String>();
            if (layerArray != null) {
                for (Object o : poiArray) {
                    JSONObject poi = (JSONObject) o;
                    createdPoiId.add((Integer)poi.get("id"));
                }
            }

            //Create set of user-created POIs
            for (Integer o : createdPoiId) {
                if (poiMap.containsKey(o)) {
                    createdPoiObjects.add(poiMap.get(o));
                }
            }

            //Create set of favourite POIs
            for (Integer o : favouritePoiId) {
                if (poiMap.containsKey(o)) {
                    favouritePoiObjects.add(poiMap.get(o));
                }
            }
        }
        
        try {
           JSONParser parser = new JSONParser();
           Object obj = parser.parse(new FileReader("src/main/java/com/cs2212/poi.json"));
           JSONObject jsonObject = (JSONObject)obj;
           JSONArray pois = (JSONArray) jsonObject.get("pois");

            for(Object o : pois) {
                JSONObject poi = (JSONObject) o;
                String layerId = (String)poi.get("layerid");
                long xCoord = (long)poi.get("xcoord");
                long yCoord = (long)poi.get("ycoord");
                String roomNum = (String)poi.get("roomnum");
                String name = (String)poi.get("name");
                String description = (String)poi.get("description");
                boolean builtIn = (boolean)poi.get("builtin");
                POI newPoi = new POI(count, layerId, xCoord, yCoord, roomNum, name, description, builtIn);
                newPoi.setMain(this);
                poiMap.put(count, newPoi);
                // Load built in POIs from JSON
                if (builtIn) {
                    builtinPoiObjects.add(newPoi);
                }
                count += 1;
           }
        } catch (Exception e) {
           e.printStackTrace();
        }
        createLayers();
        mainscreen mainscreen = new mainscreen(this, campus, poiMap);

    } 
    
    public void createLayers() {
        
        // Create campus, building, and floor objects
        campus = new Campus("Western University", "1151 Richmond Street, London");
        Building middlesex = new Building("Middlesex College", "1151 Richmond Street, London", 5);
        Building health = new Building("Health Sciences Building", "1151 Huron Drive, London", 4);
        Building alumni = new Building("Alumni Hall", "Lambton Dr, London", 3);
        campus.addBuilding(0,alumni);
        campus.addBuilding(1,middlesex);
        campus.addBuilding(2,health);
        Floor m0 = new Floor(0, middlesex, "src/main/java/com/cs2212/images/Middlesex College-0.png");
        Floor m1 = new Floor(1, middlesex, "src/main/java/com/cs2212/images/Middlesex College-1.png");
        Floor m2 = new Floor(2, middlesex, "src/main/java/com/cs2212/images/Middlesex College-2.png");
        Floor m3 = new Floor(3, middlesex, "src/main/java/com/cs2212/images/Middlesex College-3.png");
        Floor m4 = new Floor(4, middlesex, "src/main/java/com/cs2212/images/Middlesex College-4.png");
        middlesex.addFloor(m0);
        middlesex.addFloor(m1);
        middlesex.addFloor(m2);
        middlesex.addFloor(m3);
        middlesex.addFloor(m4);
        Floor h0 = new Floor(0, health, "");
        Floor h1 = new Floor(1, health, "src/main/java/com/cs2212/images/Health Sciences Building-1.png");
        Floor h2 = new Floor(2, health, "src/main/java/com/cs2212/images/Health Sciences Building-2.png");
        Floor h3 = new Floor(3, health, "src/main/java/com/cs2212/images/Health Sciences Building-3.png");
        Floor h4 = new Floor(4, health, "src/main/java/com/cs2212/images/Health Sciences Building-4.png");
        health.addFloor(h1);
        health.addFloor(h2);
        health.addFloor(h3);
        health.addFloor(h4);
        Floor a0 = new Floor(0, alumni, "src/main/java/com/cs2212/images/Alumni Hall-0.png");
        Floor a1 = new Floor(1, alumni, "src/main/java/com/cs2212/images/Alumni Hall-1.png");
        Floor a2 = new Floor(2, alumni, "src/main/java/com/cs2212/images/Alumni Hall-2.png");
        alumni.addFloor(a0);
        alumni.addFloor(a1);
        alumni.addFloor(a2);
        
        HashMap<Character, String> layerTypes = new HashMap<>();
        layerTypes.put('f', "Favourites");
        layerTypes.put('u', "User Created");
        layerTypes.put('c', "Classrooms");
        layerTypes.put('r', "Resturaunts");
        layerTypes.put('n', "Navigation");
        layerTypes.put('e', "Entry Exit Points");
        layerTypes.put('g', "Gen Labs");
        layerTypes.put('w', "Washrooms");
        layerTypes.put('s', "CS Specfic");
        for (Object building : campus.getBuildings().values()) {
            Building currBuilding = (Building) building;
            char buildingKey = currBuilding.getName().charAt(0);
            Integer numFloors = currBuilding.getNumFloors();
            
            int i;
            if (buildingKey == 'H') {
                i = 1;
            } else {
                i = 0;
            }
            for (Object floor : currBuilding.getArray()) {
                Floor currFloor = (Floor) floor;
                Integer floorNum = currFloor.getNumber();
                for (Map.Entry<Character, String> entry : layerTypes.entrySet()) {
                    Character layerKey = entry.getKey();
                    String layerName = entry.getValue();
                    Layer newLayer = new Layer(layerName, true, currFloor, buildingKey + Integer.toString(floorNum) + layerKey);
                }
            }
        }
    }
        
    public TreeModel makeTree(Floor floor) {
        
        Integer floorNum = floor.getNumber();
        char buildingKey = Character.toLowerCase(floor.getBuilding().getName().charAt(0));     
        // Create tree of layers
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        
        TreeModel layers = new DefaultTreeModel(root);
        DefaultMutableTreeNode favouriteLayer = new DefaultMutableTreeNode("Favourites");
        DefaultMutableTreeNode usercreatedLayer = new DefaultMutableTreeNode("User-Created POIs");
        DefaultMutableTreeNode classroom = new DefaultMutableTreeNode("Classrooms");
        DefaultMutableTreeNode csSpecific = new DefaultMutableTreeNode("CS Specific");
        DefaultMutableTreeNode navigation = new DefaultMutableTreeNode("Navigation");
        DefaultMutableTreeNode restaurant = new DefaultMutableTreeNode("Restaurants");
        DefaultMutableTreeNode washroom = new DefaultMutableTreeNode("Washrooms");
        DefaultMutableTreeNode entryExit = new DefaultMutableTreeNode("Entry Exit");
        DefaultMutableTreeNode genLabs = new DefaultMutableTreeNode("Gen Labs");
        root.add(favouriteLayer);
        root.add(usercreatedLayer);
        root.add(classroom);
        root.add(csSpecific);
        root.add(navigation);
        root.add(restaurant);
        root.add(washroom);
        
        for (Object poi : poiMap.values()) {
            POI currPOI = (POI) poi;
            String layerId = currPOI.getLayerId();
            char poiBuilding = layerId.charAt(0);
            Integer poiFloor = Character.getNumericValue(layerId.charAt(1));
            char layerType =  layerId.charAt(2);
            if (poiBuilding == buildingKey && poiFloor == floorNum) {
                if (layerType == 'f') {
                    favouriteLayer.add(new DefaultMutableTreeNode(currPOI));
                } else if (layerType == 'u') {
                    usercreatedLayer.add(new DefaultMutableTreeNode(currPOI));
                } else if (layerType == 'c') {
                    classroom.add(new DefaultMutableTreeNode(currPOI));
                } else if (layerType == 'e') {
                    entryExit.add(new DefaultMutableTreeNode(currPOI));
                } else if (layerType == 'r') {
                    restaurant.add(new DefaultMutableTreeNode(currPOI));
                } else if (layerType == 'g') {
                    genLabs.add(new DefaultMutableTreeNode(currPOI));
                } else if (layerType == 'n') {
                    navigation.add(new DefaultMutableTreeNode(currPOI));
                } else if (layerType == 's') {
                    csSpecific.add(new DefaultMutableTreeNode(currPOI));
                } else {
                    washroom.add(new DefaultMutableTreeNode(currPOI));
                }
            }
       }
        String tree = getTreeText(layers, root, "");
        System.out.println(tree);
        return layers;
    }
    
    private String getTreeText(TreeModel model, Object object, String indent) {
        String myRow = indent + object + "\n";
        for (int i = 0; i < model.getChildCount(object); i++) {
            myRow += getTreeText(model, model.getChild(object, i), indent + "  ");
        }
        return myRow;
    }
    
    /*public checkLayer(Floor floor) {
        HashMap<Integer, Layer> layerMap = (HashMap) floor.getLayers();
        for (int i=0; i<layerMap.size(); i++) {
            Layer layer = layerMap.get(i);
            for (POI j : layer.getPois()) {
                if (j.getLayerId() == ) {
                    System.out.println(j.isActive());
                    //Show POI on map
                }
            }
        }
    }*/
    
    public void addPOI(POI newPOI) {
        poiMap.put(count, newPOI); //Add to local hashmap
        System.out.println(poiMap.get(count).getLayerId());
        createdPoiObjects.add(newPOI);
        JSONArray poiArray = (JSONArray)createdPois.get(user.getUsername());
        JSONObject poi = new JSONObject();
        poi.put("pid", count);
        count += 1;
        if (poiArray != null) {
            poiArray.add(poi);
            createdPois.put(user.getUsername(), poiArray);
        } else {
            JSONArray newPoiArray = new JSONArray();
            newPoiArray.add(poi);
            createdPois.put(user.getUsername(), newPoiArray);
        }
    }
    
    public DefaultListModel<String> search(String searchText, Building building) {
        DefaultListModel<String> searchResultsList = new DefaultListModel<>();     
        //Get a string to compare later with the POI layer id
        char currBuilding = building.getName().toLowerCase().charAt(0);

        //search through all pois
        for (POI specificPoi : poiMap.values()) { //loop through POI map and compare layer id

            if (specificPoi.getLayerId().charAt(0) == currBuilding) { //get pois in the current building 

                //Search for room number
               if (searchText.equals(specificPoi.getRoomNum())) {
                   System.out.println("Room number: " + specificPoi.getRoomNum());
                   System.out.println("POI ID: " + specificPoi.getId());
                   searchResultsList.addElement(specificPoi.getName());
               }

               //Search for name
               if (searchText.equals(specificPoi.getName())) {
                   System.out.println("Room number: " + specificPoi.getName());
                   System.out.println("POI ID: " + specificPoi.getId());
                   searchResultsList.addElement(specificPoi.getName());
               }

               String[] strArray = specificPoi.getDescription().split(" ");

               //Search for description
               for (int k = 0; k < strArray.length; k++) {
                   if (searchText.equals(strArray[k])) {
                       System.out.println("Description: " + specificPoi.getDescription());
                       System.out.println("POI ID: " + specificPoi.getId());
                       searchResultsList.addElement(specificPoi.getName());
                   }
                } 
            }
        }
        return searchResultsList;
    }
    
    public void logOut() {
        // Save POI data
        JSONArray pois = new JSONArray();
        for (Map.Entry<Integer, POI> entry : poiMap.entrySet()) {
            Integer id = entry.getKey();
            POI poiObject = entry.getValue();
            JSONObject poi = new JSONObject();
            poi.put("pid", id);
            poi.put("name", poiObject.getName());
            poi.put("xcoord", poiObject.getXCoord());
            poi.put("ycoord", poiObject.getYCoord());
            poi.put("roomnum", poiObject.getRoomNum());
            poi.put("layerid", poiObject.getLayerId());
            poi.put("builtin", poiObject.isBuiltIn());  
            poi.put("description", poiObject.getDescription());
            pois.add(poi);
        }
        JSONObject poiJSON = new JSONObject();
        poiJSON.put("pois", pois);
        try {
            FileWriter file = new FileWriter("src/main/java/com/cs2212/test.json");
            file.write(poiJSON.toJSONString());
            file.close();
        } catch (Exception error) {
            error.printStackTrace();
        }
        
        // Load user data
        int numUsers = 0;
        JSONArray users = new JSONArray();
        for (Map.Entry<String, String> entry : consumerMap.entrySet()) {
            String username = entry.getKey();
            Object password = entry.getValue();
            JSONObject consumer = new JSONObject();
            consumer.put("userid", numUsers);
            consumer.put("username", username);
            consumer.put("password", password);
            consumer.put("consumer", true);
            consumer.put("createdpois", createdPois.get(username));
            consumer.put("favourites", favourites.get(username));
            consumer.put("activelayers", activeLayers.get(username));  
            users.add(consumer);
            numUsers += 1;
        }
        
        for (Map.Entry<String, String> entry : developerMap.entrySet()) {
            String username = entry.getKey();
            Object password = entry.getValue();
            JSONObject developer = new JSONObject();
            developer.put("userid", numUsers);
            developer.put("username", username);
            developer.put("password", password);
            developer.put("consumer", false);
            developer.put("createdpois", null);
            developer.put("favourites", null);
            developer.put("activelayers", null);  
            users.add(developer);
            numUsers += 1;
        }
        JSONObject userJSON = new JSONObject();
        userJSON.put("users", users);
        try {
            FileWriter file = new FileWriter("src/main/java/com/cs2212/users.json");
            file.write(userJSON.toJSONString());
            file.close();
        } catch (Exception error) {
            error.printStackTrace();
        }
 
    }
    public int getCount() {
      return count;  
    }
    
    public HashMap getPoiMap() {
        return poiMap;
    }
    
     private void editPOIInfo(POI poiToEdit) {
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
        	  poiToEdit.setName(name);
        	  poiToEdit.setRoomNum(roomNum);
        	  poiToEdit.setDescription(description);
        	  
                  JOptionPane.showMessageDialog(null, "Successfully Edited");
              }
              else{      
                JOptionPane.showMessageDialog(null, "Unsuccessful No POI Edited");
              }            
            } else {
                JOptionPane.showMessageDialog(null, "Unsuccessful No POI Edited");
        }

    }
    
    private void deletePOI(POI poiToDelete, HashMap<Integer,POI> poiMap) {
    	int pid = poiToDelete.getId();
    	poiMap.remove(pid);
    	
    	if (poiMap.get(pid) == null) {
               JOptionPane.showMessageDialog(null, "Successfully Deleted");
               
         } else {
              JOptionPane.showMessageDialog(null, "Unsuccessful No POI Deleted");
      }
    }
    
    public void displayPOI(int poiID) {
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
        
        //Get the POI object
        POI poiToDisplay = poiMap.get(poiID);
        
        // Create pop up panel
        JPanel POIPopUp = new JPanel(new GridLayout(6,0 ));
	    // Display Name
        POIPopUp.add(new JLabel("Name:"));
        JLabel POIName = new JLabel(poiToDisplay.getName());
        POIPopUp.add(POIName);
        // Display Room Number
        POIPopUp.add(new JLabel("Room Number:"));
        JLabel POIRoom = new JLabel(poiToDisplay.getRoomNum());
        POIPopUp.add(POIRoom);
        // Display Description
        POIPopUp.add(new JLabel("Description:"));
        JLabel POIDescription = new JLabel(poiToDisplay.getDescription());
        POIPopUp.add(POIDescription);
       
        if (isDev == true) {  
        	//Add two additional buttons
        	JButton devEdit = new JButton("Edit");
	        POIPopUp.add(devEdit);
	        
	        JButton devDelete = new JButton("Delete");
	        POIPopUp.add(devDelete);
	      
	        //Event listener for edit
	        devEdit.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	editPOIInfo(poiToDisplay);
	            }
	        });
	        
	      //Event listener for delete
	        devDelete.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	deletePOI(poiToDisplay, poiMap);
	            }
	        });
	       
        } else {
            //Display checkbox for favourite
            JCheckBox isFavourite = new JCheckBox("Favourite");
            POIPopUp.add(isFavourite);

            //Event listener for the favourite option
            isFavourite.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                     // FAVOURITES ???????????????????????????????????????????????????????????
                }
            });
        }
         JOptionPane.showConfirmDialog(null, POIPopUp, "Information", JOptionPane.DEFAULT_OPTION); 
    }
    
}
