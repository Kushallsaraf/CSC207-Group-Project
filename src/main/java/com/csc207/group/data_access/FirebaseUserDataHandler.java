package com.csc207.group.data_access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.csc207.group.auth.UserDataHandler;
import com.csc207.group.cache.FirebaseRestClient;
import com.csc207.group.model.Review;
import com.csc207.group.model.User;
import kong.unirest.json.JSONObject;

public class FirebaseUserDataHandler implements UserDataHandler {

    public static final String USERS = "Users/";
    public static final String WISHLIST = "wishlist";
    public static final String FOLLOWERS = "followers";
    public static final String FOLLOWING = "following";
    public static final String LIBRARY = "library";
    public static final String REVIEWS = "reviews";
    public static final String BIO = "bio";
    public static final String PROFILE_PICTURE_URL = "profile-picture-url";
    private final FirebaseRestClient client;

    public FirebaseUserDataHandler(FirebaseRestClient client) {
        this.client = client;
    }

    @Override
    public boolean usernameExists(String username) {
        return client.hasPath(USERS + username);
    }

    @Override
    public void registerUser(String username, String hashedPassword) throws IOException {
        if (usernameExists(username)) {
            throw new IOException("Username already exists.");
        }

        String userJson = "{ \"pwd\": \"" + hashedPassword + "\" }";
        client.putData(USERS + username, userJson);

        System.out.println("User '" + username + "' registered.");
    }

    @Override
    public User getUser(String usernameInput) {
        String json = client.getData(USERS + usernameInput);

        if (json != null && !"null".equals(json)) {
            JSONObject jsonObject = new JSONObject(json);
            String pwd = jsonObject.getString("pwd");

            User user = new User(usernameInput, pwd);

            if (jsonObject.has(WISHLIST)) {
                for (Object id : jsonObject.getJSONArray(WISHLIST)) {
                    user.getWishlist().add((Integer) id);
                }
            }

            if (jsonObject.has(FOLLOWERS) && !jsonObject.isNull(FOLLOWERS)) {
                List<String> followersList = new ArrayList<>();
                for (Object u : jsonObject.getJSONArray(FOLLOWERS)) {
                    followersList.add((String) u);
                }
                user.setFollowers(followersList);
            }

            if (jsonObject.has(FOLLOWING) && !jsonObject.isNull(FOLLOWING)) {
                List<String> followingList = new ArrayList<>();
                for (Object u : jsonObject.getJSONArray(FOLLOWING)) {
                    followingList.add((String) u);
                }
                user.setFollowing(followingList);
            }

            if (jsonObject.has(LIBRARY)) {
                for (Object id : jsonObject.getJSONArray(LIBRARY)) {
                    user.getLibrary().add((Integer) id);
                }
            }

            if (jsonObject.has(REVIEWS)) {
                JSONObject reviewsJson = jsonObject.getJSONObject(REVIEWS);
                for (String gameIdStr : reviewsJson.keySet()) {
                    JSONObject reviewJson = reviewsJson.getJSONObject(gameIdStr);

                    String userid = reviewJson.getString("userid");
                    String content = reviewJson.getString("content");
                    int gameid = reviewJson.getInt("gameid");
                    double rating = reviewJson.getDouble("rating");

                    Review review = new Review(userid, content, gameid, rating);
                    user.getReviews().put(gameid, review);
                }
            }
            if (jsonObject.has(BIO)) {
                user.setBio(jsonObject.getString(BIO));
            }
            if (jsonObject.has(PROFILE_PICTURE_URL)) {
                user.setProfilePictureUrl(jsonObject.getString(PROFILE_PICTURE_URL));
            }

            return user;
        }

        return null;

    }

    @Override
    public void saveUser(User user) {
        JSONObject userJson = new JSONObject();
        List<String> following = user.getFollowing();
        List<String> followers = user.getFollowers();
        userJson.put(FOLLOWERS, followers);
        userJson.put(FOLLOWING, following);
        userJson.put("pwd", user.getHashedPassword());

        userJson.put(WISHLIST, user.getWishlist());

        userJson.put(LIBRARY, user.getLibrary());

        JSONObject reviewsJson = new JSONObject();
        for (Map.Entry<Integer, Review> entry : user.getReviews().entrySet()) {
            JSONObject reviewJson = new JSONObject();
            Review review = entry.getValue();

            reviewJson.put("userid", review.getUserId());
            reviewJson.put("content", review.getContent());
            reviewJson.put("gameid", review.getGameId());
            reviewJson.put("rating", review.getRating());

            reviewsJson.put(String.valueOf(entry.getKey()), reviewJson);
        }

        userJson.put(REVIEWS, reviewsJson);
        userJson.put(BIO, user.getBio());
        userJson.put(PROFILE_PICTURE_URL, user.getProfilePictureUrl());

        client.putData(USERS + user.getUsername(), userJson.toString());

    }
}

