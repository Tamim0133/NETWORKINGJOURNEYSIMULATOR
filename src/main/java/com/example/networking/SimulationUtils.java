// src/main/java/com/example/networking/SimulationUtils.java
package main.java.com.example.networking;

import javax.swing.*;
import java.awt.*;

public class SimulationUtils {
    private JTextArea logArea;
    private JProgressBar progressBar;
    private JPanel visualPanel; // Need a reference to update it

    public SimulationUtils(JTextArea logArea, JProgressBar progressBar, JPanel visualPanel) {
        this.logArea = logArea;
        this.progressBar = progressBar;
        this.visualPanel = visualPanel;
    }

    public void addToLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    public void updateProgress(int value, String text) {
        SwingUtilities.invokeLater(() -> {
            progressBar.setValue(value);
            progressBar.setString(text);
        });
    }

    public void updateVisualPanel(String stage, Color color) {
        // This effectively calls the method on the main JFrame's visualPanel
        // It's good practice to make the actual update method public in the JFrame
        // if another class needs to trigger it.
        SwingUtilities.invokeLater(() -> {
            if (visualPanel instanceof JPanel) { // Ensure it's a JPanel
                visualPanel.removeAll();
                visualPanel.setLayout(new BoxLayout(visualPanel, BoxLayout.Y_AXIS));

                JLabel stageLabel = new JLabel(stage, JLabel.CENTER);
                stageLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
                stageLabel.setOpaque(true);
                stageLabel.setBackground(color);
                stageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                visualPanel.add(Box.createVerticalGlue());
                visualPanel.add(stageLabel);
                visualPanel.add(Box.createVerticalGlue());

                visualPanel.revalidate();
                visualPanel.repaint();
            }
        });
    }
}