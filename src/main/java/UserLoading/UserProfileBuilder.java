package UserLoading;

import Cache.FirebaseAPICache;
import Firebase.FirebaseUserDataHandler;

/**Takes user data from database and deserializes it to instantiate
 * user related objects like Wishlists, Libraries, etc.
 */
public class UserProfileBuilder {
    private User user;
    private FirebaseUserDataHandler userDataHandler;


    public UserProfileBuilder(User user){
        this.user = user;


    }

    public void buildReviews(){
        this.user.setReviews()
    }


}
