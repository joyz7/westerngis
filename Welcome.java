/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.cs2212;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author rrenv
 */
public class Welcome extends JFrame implements ActionListener {

    private JFrame welcomeFrame;
    private JTextField user, password;
    private User newUser;
    private HashMap<String,String> users;
    private HashMap<String,JSONArray> createdPois;
    private HashMap<String,JSONArray> favourites;
    private HashMap<String,JSONArray> activeLayers;
    
    public Welcome() {
        users = new HashMap<>();
        createdPois = new HashMap<>();
        favourites = new HashMap<>();
        activeLayers = new HashMap<>();
        
        JSONParser parser = new JSONParser();                        
        try {
           Object obj = parser.parse(new FileReader("src/main/java/com/cs2212/users.json"));
           JSONObject jsonObject = (JSONObject)obj;
           JSONArray array = (JSONArray) jsonObject.get("users");
           
           for(Object o : array) {
                JSONObject user = (JSONObject) o;
                
                // Load user data from JSON
                String username = (String)user.get("username");
                String password = (String)user.get("password");
                users.put(username,password);
                
                JSONArray poi = (JSONArray)user.get("createdpois");
                createdPois.put(username,poi);

                // Load favourites
                JSONArray favourite = (JSONArray)user.get("favourites");
                favourites.put(username,favourite);

                // Load active layers
                JSONArray layer = (JSONArray)user.get("activelayers");
                activeLayers.put(username,layer);
           }
        } catch(Exception e) {
           e.printStackTrace();
        }
        
        welcomeFrame = new JFrame(); //creating instance of JFrame    
        welcomeFrame.setSize(1200,800);//400 width and 500 height  
        welcomeFrame.setLayout(null);//using no layout managers  
        welcomeFrame.setVisible(true);//making the frame visible 
        
        JLabel welcomeLabel = new JLabel("Welcome to Western Ontario GIS");
        welcomeLabel.setBounds(500, 200, 500, 40);
        welcomeFrame.add(welcomeLabel);
        
        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setBounds(400, 300, 100, 30);
        welcomeFrame.add(usernameLabel);

        user = new JTextField();
        user.setBounds(500,300,300,20);
        welcomeFrame.add(user);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(400, 400, 100, 30);
        welcomeFrame.add(passwordLabel);

        password = new JTextField();
        password.setBounds(500,400,300,20);
        welcomeFrame.add(password);
        
        JButton logIn = new JButton(new AbstractAction("Log In") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if user in user hashmap and if password matches
                if (users.containsKey(user.getText())) {
                    if (users.get(user.getText()).equals(password.getText())) {
                        User oldUser = new User(user.getText(),password.getText());
                        
                        JSONArray poiArray = createdPois.get(user.getText());
                        HashSet<Integer> createdPoi = new HashSet<Integer>();
                        for (Object o : poiArray) {
                            createdPoi.add((int)o);
                        }
                        
                        JSONArray favouriteArray = favourites.get(user.getText());
                        HashSet<Integer> favourite = new HashSet<Integer>();
                        for (Object o : favouriteArray) {
                            favourite.add((int)o);
                        }

                        JSONArray layerArray = activeLayers.get(user.getText());
                        HashSet<Integer> activeLayer = new HashSet<Integer>();
                        for (Object o : layerArray) {
                            activeLayer.add((int)o);
                        }
                        
                        new Main(oldUser, createdPoi, favourite, activeLayer);
                    } else {
                    // If password doesn't match, pop up error message
  
                    }
                }
            }
        });                        
        welcomeFrame.add(logIn);//adding button in JFrame
        logIn.setBounds(450,500,100, 40);//x axis, y axis, width, height          
        
        JButton signUp = new JButton(new AbstractAction("Sign Up") {//creating instance of JButton
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if username already exists in hashmap
                newUser = new User(user.getText(), password.getText());
                welcomeFrame.setVisible(false);
                welcomeFrame.dispose();
                new Main();
            }
        });
        welcomeFrame.add(signUp);
        signUp.setBounds(600, 500, 100, 40);
    }
   
     public static void main(String[] args) {
        new Welcome();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

};
