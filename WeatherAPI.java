/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sophi
 */

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
//import com.google.gson.Gson;

public class WeatherAPI {

    private static final String key = "205962d59a6c491b89d50234231602";
    private static final String URL = "http://api.weatherapi.com/v1/current.json?key=205962d59a6c491b89d50234231602&q=43.0096,-81.2737&aqi=no";

    public static void main(String[] args) {
        String url = String.format(URL, key);
        System.out.println("Step 1");

        try {
            URL link = new URL(URL);
            HttpURLConnection con = (HttpURLConnection) link.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(con.getInputStream());
                String response = scanner.useDelimiter("\\A").next();
                scanner.close();

                System.out.println(response);

                // Parse the JSON response
                /*
                Gson g = new Gson();
                String location = g.fromJson(response);
                float temperature = g.fromJson(response);
                */
                //System.out.println("Current weather in"+ city + description + temperature);
            } else {
                System.out.println("Error");
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

}
