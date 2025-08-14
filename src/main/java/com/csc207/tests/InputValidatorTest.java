package com.csc207.tests;

import com.csc207.group.auth.InputValidator;
import com.csc207.group.util.Constants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InputValidatorTest {
    @Test
    public void testValidInputs() {
        String result = InputValidator.validateInput("validUser", "validPass");
        assertEquals(Constants.SUCCESSFUL_SIGNUP, result);
    }

    @Test
    public void testInvalidUsernameAndPassword() {
        String result = InputValidator.validateInput("in valid", "bad pass");
        assertEquals(Constants.INVALID_INPUTS, result);
    }

    @Test
    public void testInvalidUsernameOnly() {
        String result = InputValidator.validateInput("invalid username", "validPass");
        assertEquals(Constants.INVALID_USERNAME, result);
    }

    @Test
    public void testInvalidPasswordOnly() {
        String result = InputValidator.validateInput("validUser", "invalid password");
        assertEquals(Constants.INVALID_PASSWORD, result);
    }

    @Test
    public void testNullInputs() {
        String result = InputValidator.validateInput(null, null);
        assertEquals(Constants.INVALID_INPUTS, result);
    }
}
