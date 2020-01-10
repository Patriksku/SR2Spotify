package StartApplication;

import GUI.ServerGUI;

import javax.swing.*;

public class StartApplication {

    public static void main(String[]args) {
        ServerGUI gui = new ServerGUI();
        gui.setTitle("SR2Spotify - Server");
        gui.setContentPane(gui.mainPanel());
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.pack();
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);

    }
}
