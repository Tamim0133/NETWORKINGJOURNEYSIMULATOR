// src/main/java/com/example/networking/TcpConnector.java
package main.java.com.example.networking;

import java.awt.*;
import java.util.Random;

public class TcpConnector {
    private SimulationUtils simulationUtils;
    // connectionEstablished state can be internal if not needed elsewhere
    // private boolean connectionEstablished = false;

    public TcpConnector(SimulationUtils simulationUtils) {
        this.simulationUtils = simulationUtils;
    }

    public boolean establishConnection(String host) {
        simulationUtils.addToLog("🤝 STAGE 2: TCP CONNECTION ESTABLISHMENT");
        simulationUtils.addToLog("-".repeat(35));
        simulationUtils.updateProgress(30, "TCP Handshake...");
        simulationUtils.updateVisualPanel("🤝 TCP 3-Way Handshake", new Color(144, 238, 144));

        try {
            // Simulate TCP 3-way handshake
            simulationUtils.addToLog("🔄 Initiating TCP 3-way handshake...");

            // SYN
            simulationUtils.addToLog("📤 Client → SYN → Server (Port 80/443)");
            Thread.sleep(100);

            // SYN-ACK
            simulationUtils.addToLog("📥 Server → SYN-ACK → Client");
            Thread.sleep(100);

            // ACK
            simulationUtils.addToLog("📤 Client → ACK → Server");
            Thread.sleep(100);

            simulationUtils.addToLog("✅ TCP Connection Established!");
            // connectionEstablished = true; // Update internal state if needed

            // Simulate connection details
            simulationUtils.addToLog("🔗 Connection Details:");
            simulationUtils.addToLog("   • Protocol: TCP");
            simulationUtils.addToLog("   • Local Port: " + (30000 + new Random().nextInt(10000)));
            simulationUtils.addToLog("   • Remote Port: 80");
            simulationUtils.addToLog("   • Window Size: 65535 bytes");
            simulationUtils.addToLog("   • MSS: 1460 bytes");

            // Simulate RTT
            long rtt = 50 + new Random().nextInt(100);
            simulationUtils.addToLog("   • RTT: " + rtt + "ms");

            simulationUtils.addToLog("");
            simulationUtils.updateProgress(40, "TCP Connection Established");
            return true;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            simulationUtils.addToLog("❌ TCP Connection Interrupted: " + e.getMessage());
            simulationUtils.addToLog("");
            simulationUtils.updateProgress(40, "TCP Connection Failed");
            return false;
        }
    }
}