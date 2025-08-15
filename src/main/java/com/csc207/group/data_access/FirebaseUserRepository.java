package com.csc207.group.data_access;

import com.csc207.group.cache.FirebaseRestClient;
import com.csc207.group.model.Review;
import com.csc207.group.model.User;
import com.csc207.group.auth.UserRepository;
import kong.unirest.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebaseUserRepository implements UserRepository {

    private final FirebaseRestClient client;

    public FirebaseUserRepository(FirebaseRestClient client) {
        this.client = client;
    }

    @Override
    public boolean usernameExists(String username) {
        return client.hasPath("Users/" + username);
    }

    @Override
    public void registerUser(String username, String hashedPassword) throws IOException {
        if (usernameExists(username)) {
            throw new IOException("Username already exists.");
        }

        String userJson = "{ \"pwd\": \"" + hashedPassword + "\" }";
        client.putData("Users/" + username, userJson);

        System.out.println("User '" + username + "' registered.");
    }

    @Override
    public User getUser(String usernameInput) {
        String json = client.getData("Users/" + usernameInput);

        if (json != null && !json.equals("null")) {
            JSONObject jsonObject = new JSONObject(json);
            String pwd = jsonObject.getString("pwd");

            User user = new User(usernameInput, pwd);


            if (jsonObject.has("wishlist")) {
                for (Object id : jsonObject.getJSONArray("wishlist")) {
                    user.getWishlist().add((Integer) id);
                }
            }

            if (jsonObject.has("followers") && !jsonObject.isNull("followers")) {
                List<String> followersList = new ArrayList<>();
                for (Object u : jsonObject.getJSONArray("followers")) {
                    followersList.add((String) u);
                }
                user.setFollowers(followersList);
            }

            if (jsonObject.has("following") && !jsonObject.isNull("following")) {
                List<String> followingList = new ArrayList<>();
                for (Object u : jsonObject.getJSONArray("following")) {
                    followingList.add((String) u);
                }
                user.setFollowing(followingList);
            }


            if (jsonObject.has("library")) {
                for (Object id : jsonObject.getJSONArray("library")) {
                    user.getLibrary().add((Integer) id);
                }
            }


            if (jsonObject.has("reviews")) {
                JSONObject reviewsJson = jsonObject.getJSONObject("reviews");
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
            if (jsonObject.has("bio")) {
                user.setBio(jsonObject.getString("bio"));
            }
            if (jsonObject.has("profile-picture-url")) {
                user.setProfilePictureUrl(jsonObject.getString("profile-picture-url"));
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
        userJson.put("followers", followers);
        userJson.put("following", following);
        userJson.put("pwd", user.getHashedPassword());


        userJson.put("wishlist", user.getWishlist());


        userJson.put("library", user.getLibrary());


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

        userJson.put("reviews", reviewsJson);
        userJson.put("bio", user.getBio());
        userJson.put("profile-picture-url", user.getProfilePictureUrl());


        client.putData("Users/" + user.getUsername(), userJson.toString());

    }
}

