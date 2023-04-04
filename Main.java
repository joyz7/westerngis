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
import javax.swing.DefaultListModel;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.JScrollPane;
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
            for (Object o : poiArray) {
                createdPoiId.add((int)o);
            }

            JSONArray favouriteArray = favourites.get(user.getUsername());
            HashSet<Integer> favouritePoiId = new HashSet<Integer>();
            for (Object o : favouriteArray) {
                favouritePoiId.add((int)o);
            }

            JSONArray layerArray = activeLayers.get(user.getUsername());
            HashSet<Long> activeLayerId = new HashSet<Long>();
            for (Object o : layerArray) {
                activeLayerId.add((long)o);
            }

            //Create set of user-created POIs
            for (int o : createdPoiId) {
                if (poiMap.containsKey(o)) {
                    createdPoiObjects.add(poiMap.get(o));
                }
            }

            //Create set of favourite POIs
            for (int o : favouritePoiId) {
                if (poiMap.containsKey(o)) {
                    favouritePoiObjects.add(poiMap.get(o));
                }
            }
        }
        
        loadPOIs();
    } 
    
    public void loadPOIs() {
        
        // Create campus, building, and floor objects
        Campus campus = new Campus("Western University", "1151 Richmond Street, London");
        Building middlesex = new Building("Middlesex College", "1151 Richmond Street, London");
        Building health = new Building("Health Sciences Building", "1151 Huron Drive, London");
        Building alumni = new Building("Alumni Hall", "Lambton Dr, London");
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
        Layer mc = new Layer("Classrooms", true, m0);
        Layer ml1= new Layer("Labs", true, m1);
        Layer ml2= new Layer("Labs", true, m2);
        Layer mcs2 = new Layer("Collaborative Spaces", true, m2);
        Layer mcs3 = new Layer("Collaborative Spaces", true, m3);
        Layer ms = new Layer("Navigation", true, m0);
        Layer mr = new Layer("Resturaunts", true, m0);
        Layer mw0 = new Layer("Washrooms", true, m0);
        Layer mw1 = new Layer("Washrooms", true, m1);
        Layer mw2 = new Layer("Washrooms", true, m2);
        Layer mw3 = new Layer("Washrooms", true, m3);

        // Create tree of layers
        JSONParser parser = new JSONParser();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        TreeModel layers = new DefaultTreeModel(root);
        DefaultMutableTreeNode classroom = new DefaultMutableTreeNode("Classrooms");
        DefaultMutableTreeNode csSpecific = new DefaultMutableTreeNode("CS Specific");
        DefaultMutableTreeNode navigation = new DefaultMutableTreeNode("Navigation");
        DefaultMutableTreeNode restaurant = new DefaultMutableTreeNode("Restaurants");
        DefaultMutableTreeNode washroom = new DefaultMutableTreeNode("Washrooms");
        root.add(classroom);
        root.add(csSpecific);
        root.add(navigation);
        root.add(restaurant);
        root.add(washroom);
        
        // Load POIs from JSON
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
  
                POI newPoi = new POI(count, layerId, xCoord, yCoord, roomNum, name, description, builtIn);
                poiMap.put(count, newPoi);
                // Load built in POIs from JSON
                if (builtIn) {
                    builtinPoiObjects.add(newPoi);
                }
                if (layerId == 0) {
                    mc.addPoi(count, newPoi);
                    classroom.add(new DefaultMutableTreeNode(newPoi));
                } else if (layerId == 1) {
                    ml1.addPoi(count, newPoi);
                    csSpecific.add(new DefaultMutableTreeNode(newPoi));
                } else if (layerId == 2) {
                    ml2.addPoi(count, newPoi);
                    csSpecific.add(new DefaultMutableTreeNode(newPoi));
                } else if (layerId == 3) {
                    mcs2.addPoi(count, newPoi);
                    csSpecific.add(new DefaultMutableTreeNode(newPoi));
                } else if (layerId == 4) {
                    mcs3.addPoi(count, newPoi);
                    csSpecific.add(new DefaultMutableTreeNode(newPoi));
                } else if (layerId == 5) {
                    ms.addPoi(count, newPoi);
                    navigation.add(new DefaultMutableTreeNode(newPoi));
                } else if (layerId == 6) {
                    mr.addPoi(count, newPoi);
                    restaurant.add(new DefaultMutableTreeNode(newPoi));
                } else if (layerId == 7) {
                    mw0.addPoi(count, newPoi);
                    washroom.add(new DefaultMutableTreeNode(newPoi));
                } else if (layerId == 8) {
                    mw1.addPoi(count, newPoi);
                    washroom.add(new DefaultMutableTreeNode(newPoi));
                } else if (layerId == 9) {
                    mw2.addPoi(count, newPoi);
                    washroom.add(new DefaultMutableTreeNode(newPoi));
                } else if (layerId == 10) {
                    mw3.addPoi(count, newPoi);
                    washroom.add(new DefaultMutableTreeNode(newPoi));
                }
                count++;
           }
        } catch(Exception e) {
           e.printStackTrace();
        }

        try {
            mainscreen homePage = new mainscreen(this, layers, developerMap, favouritePoiObjects);     
        } catch (IOException e) {    
        }
    }
    
    public void addPOI(POI newPOI) {
        createdPoiObjects.add(newPOI);
        JSONArray poiArray = (JSONArray)createdPois.get(user.getUsername());
        JSONObject poi = new JSONObject();
        poi.put("pid", count);
        count += 1;
        poiArray.add(poi);
        createdPois.put(user.getUsername(), poiArray);
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
            poi.put("yoord", poiObject.getYCoord());
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
    
    public boolean deletePOI(int poiId) {
        return true;
    }
    
}