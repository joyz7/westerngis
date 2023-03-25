/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212;

/**
 *
 * @author rrenv
 */
public class User {
    
    private String username;
    private String password;
    
    public User (String username, String password) {
        this.username = username;
        this.password = password;  
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public boolean setUsername (String newUsername) {
        // Check if username exists
        boolean usernameExists = False;
        
        if (usernameExists) {
            return false;
        } else {
            this.username = newUsername;
            return true;
        }
    }
    
    public boolean setPassword (String newPassword) {
        this.password = newPassword;
        return true;
    } 
}
    
