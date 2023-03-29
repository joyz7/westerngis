/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.cs2212;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author rrenv
 */
public class Welcome extends JFrame implements ActionListener {

    private JFrame welcomeFrame;
    
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

        JTextField user = new JTextField();
        user.setBounds(500,300,300,20);
        welcomeFrame.add(user);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(400, 400, 100, 30);
        welcomeFrame.add(passwordLabel);

        JTextField password = new JTextField();
        password.setBounds(500,400,300,20);
        welcomeFrame.add(password);
        
        JButton logIn = new JButton(new AbstractAction("Log In") {
            @Override
            public void actionPerformed(ActionEvent e) {
        
            }
        });                        
        welcomeFrame.add(logIn);//adding button in JFrame
        logIn.setBounds(450,500,100, 40);//x axis, y axis, width, height          
        
        JButton signUp = new JButton(new AbstractAction("Sign Up") {//creating instance of JButton
            @Override
            public void actionPerformed(ActionEvent e) {
                
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
