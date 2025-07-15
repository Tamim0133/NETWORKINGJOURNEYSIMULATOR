// src/main/java/com/example/networking/RouterSimulator.java
package main.java.com.example.networking;

import java.awt.*;
import java.net.InetAddress;
import java.util.Random;

public class RouterSimulator {
    private SimulationUtils simulationUtils;

    public RouterSimulator(SimulationUtils simulationUtils) {
        this.simulationUtils = simulationUtils;
    }

    public void simulateRouting(String host) {
        simulationUtils.addToLog("🛣️ STAGE 4: ROUTING & IP LAYER");
        simulationUtils.addToLog("-".repeat(30));
        simulationUtils.updateProgress(70, "Routing Simulation...");
        simulationUtils.updateVisualPanel("🛣️ Packet Routing", new Color(255, 218, 185));

        try {
            simulationUtils.addToLog("🔄 Simulating packet routing to " + host + "...");

            // Simulate traceroute
            simulationUtils.addToLog("📍 Traceroute simulation:");
            String[] simulatedHops = {
                    "192.168.1.1 (Home Router)",
                    "10.0.0.1 (ISP Gateway)",
                    "203.112.23.45 (ISP Core)",
                    "172.16.100.1 (Regional Hub)",
                    "8.8.8.8 (Google DNS)",
                    InetAddress.getByName(host).getHostAddress() + " (" + host + ")"
            };

            for (int i = 0; i < simulatedHops.length; i++) {
                int hopTime = 10 + new Random().nextInt(50);
                simulationUtils.addToLog(String.format("   %d. %s (%dms)", i + 1, simulatedHops[i], hopTime));
                Thread.sleep(200);
            }

            simulationUtils.addToLog("");
            simulationUtils.addToLog("📦 IP Packet Information:");
            simulationUtils.addToLog("   • Version: IPv4");
            simulationUtils.addToLog("   • Header Length: 20 bytes");
            simulationUtils.addToLog("   • Total Length: 1500 bytes (MTU)");
            simulationUtils.addToLog("   • TTL: 64");
            simulationUtils.addToLog("   • Protocol: TCP (6)");
            simulationUtils.addToLog("   • Fragmentation: Don't Fragment bit set");

            // Simulate NAT
            simulationUtils.addToLog("");
            simulationUtils.addToLog("🔄 NAT (Network Address Translation):");
            simulationUtils.addToLog("   Private IP: 192.168.1.100:45234");
            simulationUtils.addToLog("   Public IP: 203.112.23.45:45234");

        } catch (Exception e) {
            simulationUtils.addToLog("❌ Routing simulation error: " + e.getMessage());
        }

        simulationUtils.addToLog("");
        simulationUtils.updateProgress(80, "Routing Complete");
    }
}