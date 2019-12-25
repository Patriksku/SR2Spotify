package Spotify.Functions;

import Spotify.Beans.Token;

import java.util.HashMap;

public class UserSessions {

    private HashMap<String, Token> userSessionsMap;

    public UserSessions(){
        this.userSessionsMap = new HashMap<>();
    }

    public boolean contains(String session_id) {
        return userSessionsMap.containsKey(session_id);
    }

    //Kolla om användaren existerar igen?
    public void put(String session_id, Token token){
        if(!userSessionsMap.containsKey(session_id)) {
            userSessionsMap.put(session_id, token);
        } else System.out.println("User with session_id: " + session_id + " already exists");
    }

    public Token get(String session_id) {
        if(userSessionsMap.containsKey(session_id)) {
            return userSessionsMap.get(session_id);
        } else System.out.println("User with session_id: " + session_id + " does not exist.");
        return null;
    }

    public HashMap<String, Token> getHashMap() {
        return this.userSessionsMap;
    }
}
