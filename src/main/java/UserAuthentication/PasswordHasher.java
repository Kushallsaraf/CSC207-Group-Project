package UserAuthentication;

import org.mindrot.jbcrypt.BCrypt;
/**
 * Turns normal passwords into hashed ones. Also compares normal passwords
 * to hashed ones in order to verify passwords. 
 */
public class PasswordHasher {

    public static String hashPassword(String password){

        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String normalPassword, String hashedPassword){
        return BCrypt.checkpw(normalPassword, hashedPassword);

    }



}
