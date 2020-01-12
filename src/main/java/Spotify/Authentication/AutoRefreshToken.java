package Spotify.Authentication;

import Spotify.Beans.User;
import Spotify.Users.UserSessions;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class creates a Thread that automatically refreshes the access-tokens
 * every 45 minutes of all users that currently exist in the server.
 * @author Patriksku
 */
public class AutoRefreshToken extends TimerTask {
    private Timer timer = new Timer(true);
    private UserSessions userSessions;

    public AutoRefreshToken(UserSessions userSessions) {
        this.userSessions = userSessions;
        timer.scheduleAtFixedRate(this, 60000*45, 60000*45); // 20 sec
    }

    /**
     * Method automatically runs when the first user is created server-side.
     */
    @Override
    public void run() {
        System.out.println("Timer task started at:" + new Date());
        refresh();
    }

    /**
     * Checks the activity status of all users currently existing in the server.
     * If a user is labeled as unactive - then this user will be removed.
     */
    private void refresh() {
        ConcurrentHashMap<String, User> tempMap = userSessions.getHashMap();
        for (User user : tempMap.values()) {
            if (!user.getToken().isActive()) {
                userSessions.getHashMap().remove(user.getProfile().getSession_id());
                System.out.println("Removed user: " + user.getProfile().getDisplay_name() + " as this user was inactive.");
            } else {
                user.getToken().setActive(false);
            }
        }
    }
}
