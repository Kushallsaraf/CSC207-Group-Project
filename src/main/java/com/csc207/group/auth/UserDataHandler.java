package com.csc207.group.auth;

import com.csc207.group.model.User;

import java.io.IOException;

/**Stores user data from data source and writes data to data source.
 *
 */
public interface UserDataHandler {

    /**Checks if a username is already taken
     *
     * @param username
     * @return
     */
    public boolean usernameExists(String username);

    public void registerUser(String username, String hashedPassword) throws IOException;


    public User getUser(String usernameInput);

    /** Updates the database with current user info
     *
     * @param user
     */
    void saveUser(User user);
}
