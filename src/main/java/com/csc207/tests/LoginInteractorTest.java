package com.csc207.tests;

import com.csc207.group.auth.LoginInteractor;
import com.csc207.group.auth.LoginPresenter;
import com.csc207.group.auth.PasswordHasher;
import com.csc207.group.auth.UserDataHandler;
import com.csc207.group.model.User;
import com.csc207.group.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginInteractorTest {

    private UserDataHandler handler;
    private LoginPresenter presenter;
    private LoginInteractor interactor;

    @BeforeEach
    void setup() {
        handler = mock(UserDataHandler.class);
        presenter = mock(LoginPresenter.class);
        interactor = new LoginInteractor(handler, presenter);
    }

    @Test
    void testUsernameDoesNotExist() {
        when(handler.usernameExists("user")).thenReturn(false);

        boolean result = interactor.handleLogin("user", "pass");

        assertFalse(result);
        verify(presenter).updateView(Constants.FAILED_LOGIN_USERNAME);
    }

    @Test
    void testWrongPassword() {
        User mockUser = new User("user", PasswordHasher.hashPassword("correct"));
        when(handler.usernameExists("user")).thenReturn(true);
        when(handler.getUser("user")).thenReturn(mockUser);

        boolean result = interactor.handleLogin("user", "wrongPass");

        assertFalse(result);
        verify(presenter).updateView(Constants.FAILED_LOGIN_PASSWORD);
    }

    @Test
    void testSuccessfulLogin() {
        String hashed = PasswordHasher.hashPassword("correct");
        User mockUser = new User("user", hashed);

        when(handler.usernameExists("user")).thenReturn(true);
        when(handler.getUser("user")).thenReturn(mockUser);

        boolean result = interactor.handleLogin("user", "correct");

        assertTrue(result);
        verify(presenter).updateView(Constants.SUCCESSFUL_LOGIN);
    }
}
