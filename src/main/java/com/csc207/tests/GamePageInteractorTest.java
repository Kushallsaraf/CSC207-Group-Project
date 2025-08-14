package com.csc207.tests;

import com.csc207.group.model.Game;
import com.csc207.group.model.Review;
import com.csc207.group.model.User;
import com.csc207.group.service.GamePageInteractor;
import com.csc207.group.service.UserInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GamePageInteractorTest {

    private User mockUser;
    private UserInteractor mockUserInteractor;
    private GamePageInteractor gamePageInteractor;
    private Game game;
    private final Integer gameidf = 101;

    @BeforeEach
    public void setUp() {
        // Mock User
        mockUser = new User("testUser", "hashedPassword");

        // Mock UserInteractor
        mockUserInteractor = mock(UserInteractor.class);
        when(mockUserInteractor.getUser()).thenReturn(mockUser);

        // Initialize GamePageInteractor with mocked UserInteractor
        gamePageInteractor = new GamePageInteractor(mockUserInteractor);

        // Create a test Game
        game = new Game(gameidf);
    }

    @Test
    public void testSaveNewReview() {
        // Ensure the user has not reviewed the game yet
        assertFalse(mockUser.getAllGames().contains(gameidf));

        // Save a review
        gamePageInteractor.saveReview(4.5, "Great game!", gameidf, game);

        // Verify that the review was added to the user
        assertTrue(mockUser.getReviews().containsKey(gameidf));
        Review review = mockUser.getReviews().get(0);
        assertEquals("testUser", review.getUserid());
        assertEquals("Great game!", review.getContent());
        assertEquals(4.5, review.getRating());

        // Verify that the review was added to the game
        assertEquals(1, game.getReviews().size());
        assertEquals(review, game.getReviews().get(0));

        // Verify that UserInteractor.leaveOrUpdateReview() was called
        verify(mockUserInteractor).leaveOrUpdateReview(gameidf, "Great game!", 4.5);
    }

    @Test
    public void testPreventDuplicateReview() {
        // Add a review manually to simulate an existing review
        mockUser.getReviews().put(gameidf, new Review(mockUser.getUsername(), "Initial review", gameidf, 3.0));

        // Attempt to save another review
        gamePageInteractor.saveReview(5.0, "Another review", gameidf, game);

        // Verify that the user's original review has not been overwritten
        Review review = mockUser.getReviews().get(gameidf);
        assertEquals("Initial review", review.getContent());
        assertEquals(3.0, review.getRating());

        // Verify that the game has not received a duplicate review
        assertTrue(game.getReviews().isEmpty() || game.getReviews().stream().noneMatch(r -> r.getContent().equals("Another review")));

        // leaveOrUpdateReview should not create a duplicate because the interactor handles already-reviewed logic
        verify(mockUserInteractor, never()).leaveOrUpdateReview(gameidf, "Another review", 5.0);
    }
}
