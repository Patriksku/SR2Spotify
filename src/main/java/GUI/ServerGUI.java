package GUI;

import Server.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame {
    private JPanel mainPanel;
    private JButton startServerButton;
    private JButton stopServerButton;
    private JLabel statusLbl;
    private Server server;

    public ServerGUI() {
        stopServerButton.setEnabled(false);
        startServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server == null) {
                    server = new Server();
                } if (!server.isRunning()) {
                    server.startServer();
                    statusLbl.setText("Server is running.");
                    startServerButton.setEnabled(false);
                    stopServerButton.setEnabled(true);
                }
                }
            });

        stopServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server.isRunning()) {
                    server.shutdownServer();
                    startServerButton.setEnabled(true);
                    stopServerButton.setEnabled(false);
                    statusLbl.setText("Server has successfully stopped.");
                }

            }
        });
    }

    public JPanel mainPanel() {
        return mainPanel;
    }
}
