package com.csc207.tests;

import com.csc207.group.auth.PasswordHasher;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordHasherTest {

    @Test
    void testPasswordHashingAndChecking() {
        String password = "mySecret123";
        String hashed = PasswordHasher.hashPassword(password);

        assertNotNull(hashed);
        assertTrue(PasswordHasher.checkPassword(password, hashed));
        assertFalse(PasswordHasher.checkPassword("wrongPassword", hashed));
    }
}
