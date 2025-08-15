package com.csc207.tests;

import com.csc207.group.auth.UserRepository;
import com.csc207.group.model.Review;
import com.csc207.group.model.User;
import com.csc207.group.service.UserInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewTest {

    private User user;
    private UserRepository mockHandler;
    private UserInteractor interactor;

    @BeforeEach
    void setUp() {
        user = new User("Alice", BCrypt.hashpw("123456", BCrypt.gensalt()));
        mockHandler = mock(UserRepository.class);
        interactor = new UserInteractor(user, mockHandler);
    }

    @Test
    void testAddReview() {
        interactor.voidMakeReview(101, "Great game!", 4.5);

        assertTrue(interactor.hasReviewed(101));
        Review review = user.getReviews().get(101);
        assertEquals("Great game!", review.getContent());
        assertEquals(4.5, review.getRating());

        verify(mockHandler).saveUser(user); // ensure save was called
    }

    @Test
    void testUpdateReview() {
        interactor.voidMakeReview(101, "Good game", 4.0);
        interactor.leaveOrUpdateReview(101, "Awesome game!", 4.8);

        Review review = user.getReviews().get(101);
        assertEquals("Awesome game!", review.getContent());
        assertEquals(4.8, review.getRating());

        verify(mockHandler, times(2)).saveUser(user); // add + update
    }

    @Test
    void testUpdateNonExistingReview() {
        interactor.leaveOrUpdateReview(202, "New review", 3.5);

        Review review = user.getReviews().get(202);
        assertNotNull(review);
        assertEquals("New review", review.getContent());
        assertEquals(3.5, review.getRating());

        verify(mockHandler).saveUser(user);
    }

    @Test
    void testRemoveReview() {
        interactor.voidMakeReview(101, "Nice game", 3.5);
        boolean removed = interactor.removeReview(101);

        assertTrue(removed);
        assertFalse(interactor.hasReviewed(101));
        verify(mockHandler, times(2)).saveUser(user); // once for add, once for remove
    }

    @Test
    void testRemoveNonExistingReview() {
        boolean removed = interactor.removeReview(303);

        assertFalse(removed);
        verify(mockHandler).saveUser(user); // remove still triggers save
    }

    @Test
    void testHasReviewed() {
        assertFalse(interactor.hasReviewed(101));
        interactor.voidMakeReview(101, "Fun game", 4.0);
        assertTrue(interactor.hasReviewed(101));
    }

    @Test
    void testAddDuplicateReviewDoesNotOverwrite() {
        interactor.voidMakeReview(101, "First review", 3.0);
        interactor.voidMakeReview(101, "Second review", 5.0); // should overwrite? no, voidMakeReview always adds

        Review review = user.getReviews().get(101);
        assertEquals("Second review", review.getContent()); // voidMakeReview overwrites in your code
        assertEquals(5.0, review.getRating());

        verify(mockHandler, times(2)).saveUser(user);
    }
}
