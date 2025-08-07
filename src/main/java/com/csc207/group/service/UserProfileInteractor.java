package com.csc207.group.service;
import model.GamePreview;
import model.LibraryEntry;
import model.Review;
import com.csc207.group.service.GameService;
import com.csc207.group.service.UserInteractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfileInteractor {

    private final UserInteractor userInteractor;
    private final GameService gameService;
    private Map<Integer, GamePreview> previews;
    private Map<Integer, LibraryEntry> entries;

    public UserProfileInteractor(UserInteractor userInteractor, GameService gameService) {
        this.userInteractor = userInteractor;
        this.gameService = gameService;
        loadPreviews();
        loadEntries();
    }
    public Map<Integer, GamePreview> getPreviews(){
        return this.previews;
    }
    public Map<Integer, LibraryEntry> getEntries(){
        return this.entries;
    }

    public void removePreview(Integer id){
        previews.remove(id);
    }
    public void removeEntry(Integer id){
        entries.remove(id);
    }

    public String getUsername() {
        return userInteractor.getUser().getUsername();
    }

    public String getBio() {
        return userInteractor.getUser().getBio();
    }

    public String getProfilePictureUrl() {
        return userInteractor.getUser().getProfilePictureURL();
    }

    private void loadPreviews(){
        previews = new HashMap<>();
        for (Integer item: this.userInteractor.getUser().getWishlist()){
            previews.put(item, gameService.getGamePreviewById(item));

        }
    }

    private void loadEntries(){
        entries = new HashMap<>();
        for (Integer item: this.userInteractor.getUser().getLibrary()){
            entries.put(item, gameService.getLibraryEntryById(item));
        }
    }

    public GameService getGameService() {
        return this.gameService;
    }

    public void reload(){
        loadEntries();
        loadPreviews();
    }


}

