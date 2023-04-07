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
    private HashMap<String,String> consumers;
    private HashMap<String,String> developers;
    private HashMap<String,JSONArray> createdPois;
    private HashMap<String,JSONArray> favourites;
    private HashMap<String,JSONArray> activeLayers;
    
    public Welcome() {
        consumers = new HashMap<>();
        developers = new HashMap<>();
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
                
                if ((boolean)user.get("consumer")) {

                    consumers.put(username,password);

                    JSONArray poi = (JSONArray)user.get("createdpois");
                    createdPois.put(username,poi);

                    // Load favourites
                    JSONArray favourite = (JSONArray)user.get("favourites");
                    favourites.put(username,favourite);

                    // Load active layers
                    JSONArray layer = (JSONArray)user.get("activelayers");
                    activeLayers.put(username,layer);
                } else {
                    developers.put(username, password);
                }
           }
        } catch(Exception e) {
           System.out.println("Error with the initial file");
        }
        
        // Create the JFrame
        welcomeFrame = new JFrame(); //creating instance of JFrame    
        welcomeFrame.setSize(1000,600);
        welcomeFrame.setLocationRelativeTo(null); // center JFrame in the middle of the screen
        welcomeFrame.setLayout(null);//using no layout managers  
        welcomeFrame.setVisible(true);//making the frame visible 
        
        // Title
        JLabel welcomeLabel = new JLabel("Welcome to Western Ontario GIS");
        welcomeLabel.setBounds(200, 150, 800, 40);
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
        usernameLabel.setBounds(300, 300, 100, 30);
        welcomeFrame.add(usernameLabel);

        user = new JTextField();
        user.setBounds(400,300,300,20);
        welcomeFrame.add(user);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(300, 350, 100, 30);
        welcomeFrame.add(passwordLabel);

        password = new JPasswordField ();
        password.setBounds(400,350,300,20);
        welcomeFrame.add(password);
        
        JButton logInConsumer = new JButton(new AbstractAction("Log In") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if user in user hashmap and if password matches
                if (consumers.containsKey(user.getText()) && consumers.get(user.getText()).equals(password.getText())) {
                    User oldUser = new User(user.getText(),password.getText());

                    try {
                        Main main = new Main(oldUser, false, createdPois, favourites, consumers, developers);
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
        welcomeFrame.add(logInConsumer);//adding button in JFrame
        logInConsumer.setBounds(350,400,130, 40);//x axis, y axis, width, height 
        
        JButton logInDeveloper = new JButton(new AbstractAction("Developer Log In") {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if user in user hashmap and if password matches
            if (developers.containsKey(user.getText()) && developers.get(user.getText()).equals(password.getText())) {
                User oldUser = new User(user.getText(),password.getText());
                new DeveloperMain();
                welcomeFrame.dispose();
            } else {
            // If password doesn't match, pop up error message
                    errorMessageLogin.setVisible(true);
                    user.setText("");
                    password.setText("");
                }
            }
        });                        
        welcomeFrame.add(logInDeveloper); //adding button in JFrame
        logInDeveloper.setBounds(420,450,150, 40);//x axis, y axis, width, height 
        
        JButton signUp = new JButton(new AbstractAction("Sign Up") {//creating instance of JButton
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if username already exists in hashmap
                String username = user.getText();
                
                /* Uncomment later
                if (consumers.containsKey(username)) {
                    errorMessageSignup.setVisible(true);
                    user.setText("");
                    password.setText("");
                } else {*/
                    String newPassword = password.getText();
                    newUser = new User(username, newPassword);
                    consumers.put(username,newPassword);

                    createdPois.put(username,null);
                    favourites.put(username,null);
                    activeLayers.put(username,null);
                    welcomeFrame.setVisible(false);
                    welcomeFrame.dispose();
                    try {
                        Main main = new Main(newUser, true, createdPois, favourites,  consumers, developers);
                    } catch (IOException ex){
                        Logger.getLogger(Welcome.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            //}
        });
        welcomeFrame.add(signUp);
        signUp.setBounds(500, 400, 130, 40);
    }
   
     public static void main(String[] args) {
        new Welcome();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    

};