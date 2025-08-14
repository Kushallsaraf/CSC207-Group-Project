package com.csc207.group.auth;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
    /**
     * Hashes password.
     * @param password a given password.
     * @return The hashed password.
     */
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Checks a password.
     * @param password The password.
     * @param hashedPassword The hashed password
     * @return returns boolean if a password is correct.
     */
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);

    }
}
