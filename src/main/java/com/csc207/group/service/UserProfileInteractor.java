package com.csc207.group.service;

import com.csc207.group.model.GamePreview;
import com.csc207.group.model.LibraryEntry;
import com.csc207.group.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserProfileInteractor {

    private final UserInteractor userInteractor;   // to fetch target user
    private final GameService gameService;         // to resolve IDs → previews/entries
    private String targetUsername;

    private Map<Integer, GamePreview> previews;    // target user's wishlist previews
    private Map<Integer, LibraryEntry> entries;    // target user's library entries

    public UserProfileInteractor(UserInteractor userInteractor, GameService gameService, String targetUsername) {
        this.userInteractor = userInteractor;
        this.gameService = gameService;
        this.targetUsername = targetUsername;
        reload();
    }

    public void setTargetUsername(String username) {
        this.targetUsername = username;
        reload();
    }

    public String getUsername() {
        User u = getTargetUser();
        return u != null ? u.getUsername() : "";
    }

    public String getBio() {
        User u = getTargetUser();
        return u != null ? u.getBio() : "";
    }

    public String getProfilePictureUrl() {
        User u = getTargetUser();
        return u != null ? u.getProfilePictureURL() : "";
    }

    public int getFollowersCount() {
        User u = getTargetUser();
        return u != null ? u.getFollowers().size() : 0;
    }

    public int getFollowingCount() {
        User u = getTargetUser();
        return u != null ? u.getFollowing().size() : 0;
    }

    public Map<Integer, GamePreview> getPreviews() {
        return previews;
    }

    public Map<Integer, LibraryEntry> getEntries() {
        return entries;
    }

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
        if (u == null) return;
        for (Integer id : u.getWishlist()) {
            GamePreview gp = gameService.getGamePreviewById(id);
            if (gp != null) previews.put(id, gp);
        }
    }

    private void loadEntries() {
        entries = new HashMap<>();
        User u = getTargetUser();
        if (u == null) return;
        for (Integer id : u.getLibrary()) {
            LibraryEntry le = gameService.getLibraryEntryById(id);
            if (le != null) entries.put(id, le);
        }
    }

    public GameService getGameService() {
        return gameService;
    }
}

