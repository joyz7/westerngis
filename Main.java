/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212;
import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

/**
 *
 * @author rrenv
 */
public class Main {
    
    public static void main(String[] args) {
        
        // Load log in screen and check user credentials
        
        Campus campus = new Campus("Western University", "1151 Richmond Street, London");
        
        // Load built in POIs from JSON
        JSONParser parser = new JSONParser();
                        
        try {
           Object obj = parser.parse(new FileReader("C:/Users/JOYZH/OneDrive/Documents/NetBeansProjects/WesternMap/src/main/java/com/cs2212/poi.json"));
           JSONObject jsonObject = (JSONObject)obj;
           JSONArray pois = (JSONArray) jsonObject.get("pois");

           int id = 0;
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
           }
        } catch(Exception e) {
            System.out.println("error");
           e.printStackTrace();
        }
    }
    
    public POI getPOI(int poiId) {
        
    }
    
    public boolean deletePOI(int poiId) {
        return true;
    }
    
}