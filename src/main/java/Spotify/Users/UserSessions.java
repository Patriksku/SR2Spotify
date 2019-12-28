package Spotify.Users;

import Spotify.Beans.Token;
import Spotify.Beans.User;

import java.util.HashMap;

/**
 * A class which contains a HashMap with every session id and its corresponding token.
 * @author Patriksku
 */
public class UserSessions {

    private HashMap<String, User> userSessionsMap;

    public UserSessions(){
        this.userSessionsMap = new HashMap<>();
    }

    /**
     * @param session_id of user-
     * @return True if user exists, otherwise False.
     */
    public boolean contains(String session_id) {
        return userSessionsMap.containsKey(session_id);
    }

    /**
     * Puts the session id together with the corresponding token in the HashMap.
     * @param session_id of user.
     * @param user with user information.
     */
    public void put(String session_id, User user){
        if(!userSessionsMap.containsKey(session_id)) {
            userSessionsMap.put(session_id, user);
        } else System.out.println("User with session_id: " + session_id + " already exists");
    }

    /**
     * @param session_id of user.
     * @return Token of the user.
     */
    public User get(String session_id) {
        if(userSessionsMap.containsKey(session_id)) {
            return userSessionsMap.get(session_id);
        } else System.out.println("User with session_id: " + session_id + " does not exist.");
        return null;
    }

    /**
     * @return the userSessionsMap (HashMap).
     */
    public HashMap<String, User> getHashMap() {
        return this.userSessionsMap;
    }
}
