package com.csc207.tests;

import com.csc207.group.auth.PasswordHasher;
import com.csc207.group.auth.SignupInteractor;
import com.csc207.group.auth.SignupPresenter;
import com.csc207.group.auth.UserDataHandler;
import com.csc207.group.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class SignUpInteractorTest {

    private UserDataHandler handler;
    private SignupPresenter presenter;
    private SignupInteractor interactor;

    @BeforeEach
    void setup() {
        handler = mock(UserDataHandler.class);
        presenter = mock(SignupPresenter.class);
        interactor = new SignupInteractor(handler, presenter);
    }

    @Test
    void testUsernameAlreadyExists() throws IOException {
        when(handler.usernameExists("takenUser")).thenReturn(true);

        interactor.handleSignup("takenUser", "password123");

        verify(presenter).updateView(Constants.USERNAME_TAKEN);
        verify(handler, never()).registerUser(anyString(), anyString());
    }

    @Test
    void testSuccessfulSignup() throws IOException {
        when(handler.usernameExists("newUser")).thenReturn(false);

        interactor.handleSignup("newUser", "password123");

        // Verify user is registered with a hashed password
        verify(handler).registerUser(eq("newUser"), argThat(hashed ->
                PasswordHasher.checkPassword("password123", hashed)
        ));

        // View updated with successful signup
        verify(presenter).updateView(Constants.SUCCESSFUL_SIGNUP);
    }

    @Test
    void testInvalidInputs() throws IOException {
        when(handler.usernameExists("")).thenReturn(false); // blank username

        interactor.handleSignup("", "bad pass"); // space in password

        verify(handler, never()).registerUser(anyString(), anyString());
        verify(presenter).updateView(Constants.INVALID_INPUTS);
    }
}
