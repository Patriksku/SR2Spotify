package Spotify.Authentication;

import Spotify.Beans.User;
import Spotify.Users.UserSessions;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class creates a Thread that automatically refreshes the access-tokens
 * every 30 minutes of all users that currently exist in the server.
 * @author Patriksku
 */
public class AutoRefreshToken extends TimerTask {
    private Timer timer = new Timer(true);
    private UserSessions userSessions;

    public AutoRefreshToken(UserSessions userSessions) {
        this.userSessions = userSessions;
        timer.scheduleAtFixedRate(this, 60000, 60000); // 20 sec
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
     * Loops through every user and calls upon the refreshToken method in the
     * Authentication class, which refreshes access tokens of current user.
     * Thread rests for 3 seconds between each refresh.
     */
    private void refresh() {
        ConcurrentHashMap<String, User> tempMap = userSessions.getHashMap();
        for (User user : tempMap.values()) {
            if (!user.getToken().isActive()) {
                System.out.println("Size of HashMap BEFORE deletion: " + userSessions.getHashMap().size());
                userSessions.getHashMap().remove(user.getProfile().getSession_id());
                System.out.println("Removed user: " + user.getProfile().getDisplay_name() + " as this user was inactive.");
            } else {
                System.out.println("User was still active so the user was set to not active.");
                user.getToken().setActive(false);
            }
        }
        System.out.println("Size of HashMap AFTER refresh-process: " + userSessions.getHashMap().size());
    }
}
