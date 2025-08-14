package com.csc207.group.service;

import java.util.HashMap;
import java.util.Map;

import com.csc207.group.model.GamePreview;
import com.csc207.group.model.LibraryEntry;
import com.csc207.group.model.User;

public final class UserProfileInteractor {

    private final UserInteractor userInteractor;
    // to fetch target user
    private final GameService gameService;
    // to resolve IDs → previews/entries
    private String targetUsername;

    private Map<Integer, GamePreview> previews;
    // target user's wishlist previews
    private Map<Integer, LibraryEntry> entries;
    // target user's library entries

    public UserProfileInteractor(UserInteractor userInteractor, GameService gameService, String targetUsername) {
        this.userInteractor = userInteractor;
        this.gameService = gameService;
        this.targetUsername = targetUsername;
        reload();
    }

    /**
     * Sets the target username.
     * @param username username to set.
     */
    public void setTargetUsername(String username) {
        this.targetUsername = username;
        reload();
    }

    /**
     * Gets Username of a user.
     * @return username.
     */
    public String getUsername() {
        User u = getTargetUser();
        if (u != null) {
            return u.getUsername();
        }
        return "";
    }

    /**
     * Gets Bio for a user profile.
     * @return bio.
     */
    public String getBio() {
        User u = getTargetUser();
        if (u != null) {
            return u.getBio();
        }
        return "";
    }

    /**
     * Gets url of profile picture.
     * @return profile picture url.
     */
    public String getProfilePictureUrl() {
        User u = getTargetUser();
        if (u != null) {
            return u.getProfilePictureUrl();
        }
        return "";
    }

    /**
     * Gets number of followers.
     * @return number of followers.
     */
    public int getFollowersCount() {
        User u = getTargetUser();
        if (u != null) {
            return u.getFollowers().size();
        }
        return 0;
    }

    /**
     * Gets the number of following.
     * @return number of following.
     */
    public int getFollowingCount() {
        User u = getTargetUser();
        if (u != null) {
            return u.getFollowing().size();
        }
        return 0;
    }

    public Map<Integer, GamePreview> getPreviews() {
        return previews;
    }

    public Map<Integer, LibraryEntry> getEntries() {
        return entries;
    }

    /**
     * Reloads.
     */
    public void reload() {
        loadPreviews();
        loadEntries();
    }

    private User getTargetUser() {
        return userInteractor.getUserByUsername(targetUsername);
    }

    private void loadPreviews() {
        previews = new HashMap<>();
        User u = getTargetUser();
        if (u == null) {
            return;
        }
        for (Integer id : u.getWishlist()) {
            GamePreview gp = gameService.getGamePreviewById(id);
            if (gp != null) {
                previews.put(id, gp);
            }
        }
    }

    private void loadEntries() {
        entries = new HashMap<>();
        User u = getTargetUser();
        if (u == null) {
            return;
        }
        for (Integer id : u.getLibrary()) {
            LibraryEntry le = gameService.getLibraryEntryById(id);
            if (le != null) {
                entries.put(id, le);
            }
        }
    }

    public GameService getGameService() {
        return gameService;
    }
}
