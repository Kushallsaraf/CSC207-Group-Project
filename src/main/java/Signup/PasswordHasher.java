package Signup;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    public static String hashPassword(String password){

        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String normalPassword, String hashedPassword){
        return BCrypt.checkpw(normalPassword, hashedPassword);

    }



}
