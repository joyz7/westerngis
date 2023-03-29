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
    private static HashMap<Integer,User> users;
    private static HashMap<Integer,JSONArray> createdPois;
    private static HashMap<Integer,JSONArray> favourites;
    private static HashMap<Integer,JSONArray> activeLayers;
    
    public Welcome() {
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
                // Check if user in user hashmap
                
                // If user in hashmap, check if password matches
                
                // If password doesn't match, pop up error message
        
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
        users = new HashMap<>();
        createdPois = new HashMap<>();
        favourites = new HashMap<>();
        activeLayers = new HashMap<>();

        JSONParser parser = new JSONParser();
                        
        try {
           Object obj = parser.parse(new FileReader("C:/Users/JOYZH/OneDrive/Documents/NetBeansProjects/WesternMap/src/main/java/com/cs2212/users.json"));
           JSONObject jsonObject = (JSONObject)obj;
           JSONArray array = (JSONArray) jsonObject.get("users");
           
           int id = 0;
           for(Object o : array) {
                JSONObject user = (JSONObject) o;
                
                // Load user data from JSON
                String username = (String)user.get("layerid");
                String password = (String)user.get("password");
                User newUser = new User(username,password);
                users.put(id,newUser);
                
                boolean consumer = (boolean)user.get("consumer");
                if (consumer) {
                    // Load user created POIs
                    JSONArray poi = (JSONArray)user.get("createdpois");
                    createdPois.put(id,poi);

                    // Load favourites
                    JSONArray favourite = (JSONArray)user.get("favourites");
                    favourites.put(id,favourite);

                    // Load active layers
                    JSONArray layer = (JSONArray)user.get("activelayers");
                    createdPois.put(id,layer);
                }
                id++;
           }
        } catch(Exception e) {
           e.printStackTrace();
        }
        
        new Welcome();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

};
