package com.csc207.group.auth;

import java.io.IOException;

import com.csc207.group.model.User;

/**
 * Stores user data from data source and writes data to data source.
 */
public interface UserRepository {

    /**
     * Checks if a username is already taken.
     * @param username The username of the user.
     * @return whether the username exists
     */
    boolean usernameExists(String username);

    /**
     * Registers a user.
     * @param username Username for a user.
     * @param hashedPassword The hashed password for a user.
     * @throws IOException if error writing user data.
     */
    void registerUser(String username, String hashedPassword) throws IOException;

    /**
     * Gets a user.
     * @param usernameInput the input for username.
     * @return Returns a user.
     */
    User getUser(String usernameInput);

    /**
     * Updates the database with current user info.
     * @param user the given user.
     */
    void saveUser(User user);
}
