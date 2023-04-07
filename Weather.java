/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.*;
import org.json.simple.parser.*;

/**
 *
 * @author rrenv
 */
public class Weather {
    
    private String city;
    private double currWeather;
    private String currCondition;
    private static final String key = "205962d59a6c491b89d50234231602";
    private static final String URL = "http://api.weatherapi.com/v1/current.json?key=205962d59a6c491b89d50234231602&q=43.0096,-81.2737&aqi=no";

    
    public Weather (String city) {
        this.city = city;

        try {
            URL link = new URL(URL);
            HttpURLConnection con = (HttpURLConnection) link.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(con.getInputStream());
                String response = scanner.useDelimiter("\\A").next();
                scanner.close();

                // Parse the JSON response
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(response);

                /* If location changes:
                JSONObject location = (JSONObject) jsonObject.get("location");
                String city = (String)location.get("name");
                */
                JSONObject current = (JSONObject) jsonObject.get("current");
                currWeather = (double)current.get("temp_c");
                JSONObject condition = (JSONObject)current.get("condition");
                currCondition = (String)condition.get("text");
            }
        } catch (Exception e) {
            System.out.println("Error recovering weather details.");
            city = "Error ";
            currWeather = 1;
            currCondition = " weather details available.";
            
        }
    }
    
    public String getCity() {
        return city;
    }
    
    public double getCurrWeather() {
        return currWeather;
    }
    
    public String getCurrCondition() {
        return currCondition;
    }
    
    public void updateCurrWeather() {
        
    }
    
    public void updateCurrCondition() {
        
    }
    
    
    
    
}
