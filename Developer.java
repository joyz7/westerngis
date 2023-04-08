
package com.cs2212;

/**
 * The Developer class represents a user who has the ability to edit built-in pois.
 * This class extends the User class.
 * @author rrenv
 */
public class Developer extends User {
    
    /**
     * Creates a new Developer object with the specified username and password.
     * @param username the username for the new developer
     * @param password the password for the new developer
     */
    public Developer(String username, String password) {
        super(username, password);
    }
}
