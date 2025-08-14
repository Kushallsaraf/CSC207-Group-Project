package com.csc207.tests;

import com.csc207.group.auth.UserDataHandler;
import com.csc207.group.model.Review;
import com.csc207.group.model.User;
import com.csc207.group.service.UserInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserInteractorTest {

    private User mockUser;
    private UserDataHandler mockDataHandler;
    private UserInteractor interactor;

    @BeforeEach
    void setUp() {
        mockUser = new User("testUser", "password");
        mockDataHandler = Mockito.mock(UserDataHandler.class);

        when(mockDataHandler.getUser("testUser")).thenReturn(mockUser);
        interactor = new UserInteractor(mockUser, mockDataHandler);
    }

    @Test
    void testAddToWishlist() {
        boolean added = interactor.addToWishlist(101);
        assertTrue(added);
        assertTrue(mockUser.getWishlist().contains(101));
        verify(mockDataHandler, times(1)).saveUser(mockUser);

        // Adding again should return false
        boolean addedAgain = interactor.addToWishlist(101);
        assertFalse(addedAgain);
    }

    @Test
    void testRemoveFromWishlist() {
        mockUser.getWishlist().add(101);
        boolean removed = interactor.removeFromWishlist(101);
        assertTrue(removed);
        assertFalse(mockUser.getWishlist().contains(101));
        verify(mockDataHandler, times(1)).saveUser(mockUser);
    }

    @Test
    void testAddToLibrary() {
        boolean added = interactor.addToLibrary(201);
        assertTrue(added);
        assertTrue(mockUser.getLibrary().contains(201));
        verify(mockDataHandler, times(1)).saveUser(mockUser);

        // Adding again should return false
        boolean addedAgain = interactor.addToLibrary(201);
        assertFalse(addedAgain);
    }

    @Test
    void testRemoveFromLibrary() {
        mockUser.getLibrary().add(201);
        boolean removed = interactor.removeFromLibrary(201);
        assertFalse(mockUser.getLibrary().contains(201)); // It’s removed
        verify(mockDataHandler, times(1)).saveUser(mockUser);
        assertFalse(removed); // method returns false because contains() now returns false
    }

    @Test
    void testLeaveAndRemoveReview() {
        interactor.leaveOrUpdateReview(301, "Great Game", 4.5);
        assertTrue(interactor.hasReviewed(301));
        Review r = mockUser.getReviews().get(301);
        assertEquals("Great Game", r.getContent());
        assertEquals(4.5, r.getRating());

        boolean removed = interactor.removeReview(301);
        assertTrue(removed);
        assertFalse(interactor.hasReviewed(301));
    }

    @Test
    void testEditBioAndProfilePicture() {
        interactor.editBio("New bio");
        assertEquals("New bio", mockUser.getBio());
        verify(mockDataHandler, times(1)).saveUser(mockUser);

        interactor.editProfilePicture("url.png");
        assertEquals("url.png", mockUser.getProfilePictureUrl());
        verify(mockDataHandler, times(2)).saveUser(mockUser);
    }

    @Test
    void testGetReviewerProfilePicture() {
        mockUser.setProfilePictureUrl("pic.png");
        String url = interactor.getReviewerProfilePicture("testUser");
        assertEquals("pic.png", url);
    }
}
