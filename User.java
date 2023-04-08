/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212;

/**
 * This class represents a user object with a username and password.
 * @author rrenv
 */
public class User {
    
    private String username;
    private String password;
    
    /**
     * Constructs a new User object with the specified username and password.
     * @param username the username of the user
     * @param password the password of the user
     */
    public User (String username, String password) {
        this.username = username;
        this.password = password;  
    }
    
    /**
     * Returns the username of the user.
     * @return the username of the user
     */
    public String getUsername() {
        return this.username;
    }
    
    /**
     * Returns the password of the user.
     * @return the password of the user
     */
    public String getPassword() {
        return this.password;
    }
    
    /**
     * Sets the username of the user.
     * @param newUsername the new username to set
     * @return true if the username was successfully set, false otherwise
     */
    public boolean setUsername (String newUsername) {
        // Check if username exists
        boolean usernameExists = false;
        
        if (usernameExists) {
            return false;
        } else {
            this.username = newUsername;
            return true;
        }
    }
    
    /**
     * Sets the password of the user.
     * @param newPassword the new password to set
     * @return true if the password was successfully set
     */
    public boolean setPassword (String newPassword) {
        this.password = newPassword;
        return true;
    } 
}
    
