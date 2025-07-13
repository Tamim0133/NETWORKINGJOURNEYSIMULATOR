// src/main/java/com/example/networking/NetworkingJourneySimulator.java
package main.java.com.example.networking;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Map; // Keep this if you want to pass cache from main
import java.util.HashMap; // Keep this if you want to pass cache from main

public class NetworkingJourneySimulator extends JFrame {
    private JTextField urlField;
    private JButton simulateButton;
    private JTextArea logArea;
    private JProgressBar progressBar;
    private JPanel visualPanel;
    private ExecutorService executorService;

    // Use a single instance of SimulationUtils
    private SimulationUtils simulationUtils;

    // DNS Cache simulation (can be managed here or within DnsResolver)
    private Map<String, String> dnsCache = new HashMap<>();

    public NetworkingJourneySimulator() {
        initializeGUI();
        executorService = Executors.newFixedThreadPool(4);
        simulationUtils = new SimulationUtils(logArea, progressBar, visualPanel);
    }

    private void initializeGUI() {
        setTitle("🌐 Networking Journey Simulator - What Happens When You Type a URL?");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel - URL Input
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBorder(new TitledBorder("Enter URL to Simulate"));

        urlField = new JTextField("google.com", 25);
        simulateButton = new JButton("🚀 Start Network Journey");

        simulateButton.addActionListener(e -> handleSimulateAction());

        inputPanel.add(new JLabel("URL: "));
        inputPanel.add(urlField);
        inputPanel.add(simulateButton);

        // Progress Bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("Ready to simulate...");

        // Center Panel - Split between Visual and Log
        JSplitPane centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        // Visual Panel for animations and diagrams
        visualPanel = new JPanel();
        visualPanel.setBorder(new TitledBorder("Network Visualization"));
        visualPanel.setPreferredSize(new Dimension(400, 500));
        visualPanel.setBackground(Color.WHITE);

        // Log Area
        logArea = new JTextArea(20, 40);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        logArea.setEditable(false);
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(new TitledBorder("Network Journey Log"));

        centerSplit.setLeftComponent(visualPanel);
        centerSplit.setRightComponent(logScroll);
        centerSplit.setDividerLocation(400);

        // Bottom Panel - Progress
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(progressBar, BorderLayout.CENTER);

        add(inputPanel, BorderLayout.NORTH);
        add(centerSplit, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Initialize visual panel
        updateVisualPanel("Ready", Color.LIGHT_GRAY);
    }

    // This method remains here as it directly updates the visualPanel,
    // which is a direct component of NetworkingJourneySimulator.
    // It's called from SimulationUtils, but the actual update is done here.
    public void updateVisualPanel(String stage, Color color) {
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

    private void handleSimulateAction() {
        String url = urlField.getText().trim();
        if (url.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a URL!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Clean URL
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }

        simulateButton.setEnabled(false);
        logArea.setText("");
        // Reset cache for each new simulation
        // dnsCache.clear();

        // Start the simulation in a separate thread
        final String finalUrl = url;
        CompletableFuture.runAsync(() -> simulateNetworkJourney(finalUrl), executorService)
                .whenComplete((result, throwable) -> {
                    SwingUtilities.invokeLater(() -> simulateButton.setEnabled(true));
                });
    }

    private void simulateNetworkJourney(String url) {
        try {
            URL urlObj = new URL(url);
            String host = urlObj.getHost();

            simulationUtils.addToLog("🌐 NETWORKING JOURNEY SIMULATOR");
            simulationUtils.addToLog("=" + "=".repeat(50));
            simulationUtils.addToLog("Target URL: " + url);
            simulationUtils.addToLog("Host: " + host);
            simulationUtils.addToLog("");

            // -------------------------------------------
            // Stage 1: DNS Resolution
            // ------------------------------------------
            DnsResolver dnsResolver = new DnsResolver(simulationUtils, dnsCache);
            String ipAddress = dnsResolver.resolve(host);
            Thread.sleep(1000);
            if (ipAddress == null) {
                simulationUtils.addToLog("❌ DNS Resolution failed. Aborting simulation.");
                simulationUtils.updateProgress(100, "Simulation Aborted");
                return;
            }

            // Stage 2: TCP Connection
            TcpConnector tcpConnector = new TcpConnector(simulationUtils);
            boolean connectionEstablished = tcpConnector.establishConnection(host);
            Thread.sleep(1000);
            if (!connectionEstablished) {
                simulationUtils.addToLog("❌ TCP Connection failed. Aborting simulation.");
                simulationUtils.updateProgress(100, "Simulation Aborted");
                return;
            }

            // Stage 3: HTTP Request/Response
            HttpRequestSender httpRequestSender = new HttpRequestSender(simulationUtils);
            httpRequestSender.sendRequest(url);
            Thread.sleep(1000);

            // Stage 4: Routing Simulation
            RouterSimulator routerSimulator = new RouterSimulator(simulationUtils);
            routerSimulator.simulateRouting(host);
            Thread.sleep(1000);

            // Stage 5: TCP Congestion Control Simulation
            CongestionController congestionController = new CongestionController(simulationUtils);
            congestionController.simulateCongestionControl();
            Thread.sleep(1000);

            // Stage 6: Complete
            simulationUtils.updateProgress(100, "Network Journey Complete!");
            simulationUtils.updateVisualPanel("🎉 Journey Complete!", new Color(152, 251, 152));
            simulationUtils.addToLog("🎉 STAGE 6: JOURNEY COMPLETE!");
            simulationUtils.addToLog("=" + "=".repeat(30));
            simulationUtils.addToLog("✅ Network Journey Summary:");
            simulationUtils.addToLog("   ✓ DNS Resolution");
            simulationUtils.addToLog("   ✓ TCP Connection Established");
            simulationUtils.addToLog("   ✓ HTTP Request/Response");
            simulationUtils.addToLog("   ✓ Packet Routing");
            simulationUtils.addToLog("   ✓ Congestion Control");
            simulationUtils.addToLog("");
            simulationUtils.addToLog("🎓 CSE 3101 Concepts Demonstrated:");
            simulationUtils.addToLog("   • Application Layer: HTTP, DNS");
            simulationUtils.addToLog("   • Transport Layer: TCP, Congestion Control");
            simulationUtils.addToLog("   • Network Layer: IP, Routing");
            simulationUtils.addToLog("   • Physical Layer: Network Infrastructure");
            simulationUtils.addToLog("");
            simulationUtils.addToLog("🚀 Simulation Complete! Ready for next URL...");

        } catch (Exception e) {
            simulationUtils.addToLog("❌ Error during simulation: " + e.getMessage());
            simulationUtils.updateProgress(100, "Error occurred");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set look and feel for better appearance
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                // Use default look and feel if Nimbus is not available
            }

            new NetworkingJourneySimulator().setVisible(true);
        });
    }

    // Cleanup
    @Override
    public void dispose() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
        super.dispose();
    }
}