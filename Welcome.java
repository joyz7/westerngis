/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.cs2212;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Font;
import java.awt.Color;
import java.util.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/**
 *
 * @author rrenv
 */
public class Welcome extends JFrame implements ActionListener {

    private JFrame welcomeFrame;
    private JTextField user;
    private JTextField password;
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
           JSONArray userArray = (JSONArray) jsonObject.get("users");
           
           for(Object o : userArray) {
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
        
        // Create the JFrame
        welcomeFrame = new JFrame(); //creating instance of JFrame    
        welcomeFrame.setSize(1000,600);
        welcomeFrame.setLocationRelativeTo(null); // center JFrame in the middle of the screen
        welcomeFrame.setLayout(null);//using no layout managers  
        welcomeFrame.setVisible(true);//making the frame visible 
        
        // Title
        JLabel welcomeLabel = new JLabel("Welcome to Western Ontario GIS");
        welcomeLabel.setBounds(350, 150, 800, 40);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        welcomeFrame.add(welcomeLabel);
        
        // Error Message for Invalid Login
        JLabel errorMessageLogin = new JLabel("Invalid email or password. Please try again.");
        errorMessageLogin.setBounds(500, 250, 400, 30);
        errorMessageLogin.setVisible(false);
        errorMessageLogin.setForeground(Color.RED);
        welcomeFrame.add(errorMessageLogin);
        
        // Error Message for Invalid Sign Up
        JLabel errorMessageSignup = new JLabel("Username already exists. Please enter a different username.");
        errorMessageSignup.setBounds(500, 250, 400, 30);
        errorMessageSignup.setVisible(false);
        errorMessageSignup.setForeground(Color.RED);
        welcomeFrame.add(errorMessageSignup);

        // Text Field for Username
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
                if (users.containsKey(user.getText()) && users.get(user.getText()).equals(password.getText())) {
                    User oldUser = new User(user.getText(),password.getText());

                    JSONArray poiArray = createdPois.get(user.getText());
                    HashSet<Integer> createdPoiId = new HashSet<Integer>();
                    for (Object o : poiArray) {
                        createdPoiId.add((int)o);
                    }

                    JSONArray favouriteArray = favourites.get(user.getText());
                    HashSet<Integer> favouritePoiId = new HashSet<Integer>();
                    for (Object o : favouriteArray) {
                        favouritePoiId.add((int)o);
                    }

                    JSONArray layerArray = activeLayers.get(user.getText());
                    HashSet<Long> activeLayerId = new HashSet<Long>();
                    for (Object o : layerArray) {
                        activeLayerId.add((long)o);
                    }

                    try {
                        new Main(oldUser, createdPoiId, favouritePoiId, activeLayerId);
                    } catch (IOException ex) {
                        Logger.getLogger(Welcome.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                // If password doesn't match, pop up error message
                        errorMessageLogin.setVisible(true);
                        user.setText("");
                        password.setText("");
                }
            }
        });                        
        welcomeFrame.add(logIn);//adding button in JFrame
        logIn.setBounds(450,500,100, 40);//x axis, y axis, width, height          
        
        JButton signUp = new JButton(new AbstractAction("Sign Up") {//creating instance of JButton
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if username already exists in hashmap
                String username = user.getText();
                
                if (users.containsKey(username)) {
                    errorMessageSignup.setVisible(true);
                    user.setText("");
                    password.setText("");
                } else {
                    String newPassword = password.getText();
                    newUser = new User(username, newPassword);
                    users.put(username,newPassword);

                    createdPois.put(username,null);
                    favourites.put(username,null);
                    activeLayers.put(username,null);
                    welcomeFrame.setVisible(false);
                    welcomeFrame.dispose();
                    new Main();
                }
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
