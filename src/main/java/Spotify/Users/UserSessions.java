package Spotify.Users;

import Spotify.Beans.User;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A class which contains a HashMap with every session id and its corresponding token.
 * @author Patriksku
 */
public class UserSessions {

    private ConcurrentHashMap<String, User> userSessionsMap;

    public UserSessions(){
        this.userSessionsMap = new ConcurrentHashMap<>();
    }

    /**
     * @param session_id of user
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
     * @return the userSessionsMap.
     */
    public ConcurrentHashMap<String, User> getHashMap() {
        return this.userSessionsMap;
    }

    /**
     * A method for finding a user with the specified userID.
     * Null is returned if no such user exists.
     * @param userID from the user's Spotify profile.
     * @return session_id for the user.
     */
    public String getUserID(String userID) {
        for (HashMap.Entry<String, User> entry : userSessionsMap.entrySet()) {
            String session_id = entry.getKey();
            User user = entry.getValue();
            if (user.getProfile().getUser_id().equals(userID)) {
                return session_id;
            }
        }
        return null;
    }
}
