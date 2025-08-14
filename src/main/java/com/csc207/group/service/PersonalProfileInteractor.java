package com.csc207.group.service;

import java.util.HashMap;
import java.util.Map;

import com.csc207.group.model.GamePreview;
import com.csc207.group.model.LibraryEntry;

public final class PersonalProfileInteractor {

    private final UserInteractor userInteractor;
    private final GameService gameService;
    private Map<Integer, GamePreview> previews;
    private Map<Integer, LibraryEntry> entries;

    public PersonalProfileInteractor(UserInteractor userInteractor, GameService gameService) {
        this.userInteractor = userInteractor;
        this.gameService = gameService;
        loadPreviews();
        loadEntries();
    }

    public Map<Integer, GamePreview> getPreviews() {
        return this.previews;
    }

    public Map<Integer, LibraryEntry> getEntries() {
        return this.entries;
    }

    /**
     * Removes preview.
     * @param id id.
     */
    public void removePreview(Integer id) {
        previews.remove(id);
    }

    /**
     * Removes entry.
     * @param id id.
     */
    public void removeEntry(Integer id) {
        entries.remove(id);
    }

    public String getUsername() {
        return userInteractor.getUser().getUsername();
    }

    public String getBio() {
        return userInteractor.getUser().getBio();
    }

    public String getProfilePictureUrl() {
        return userInteractor.getUser().getProfilePictureUrl();
    }

    private void loadPreviews() {
        previews = new HashMap<>();
        for (Integer item: this.userInteractor.getUser().getWishlist()) {
            previews.put(item, gameService.getGamePreviewById(item));

        }
    }

    private void loadEntries() {
        entries = new HashMap<>();
        for (Integer item: this.userInteractor.getUser().getLibrary()) {
            entries.put(item, gameService.getLibraryEntryById(item));
        }
    }

    public GameService getGameService() {
        return this.gameService;
    }

    /**
     * Reload entries and previews.
     */
    public void reload() {
        loadEntries();
        loadPreviews();
    }

}

