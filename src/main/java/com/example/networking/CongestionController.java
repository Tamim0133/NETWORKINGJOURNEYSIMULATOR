// src/main/java/com/example/networking/CongestionController.java
package main.java.com.example.networking;

import java.awt.*;
import java.util.Random;

public class CongestionController {
    private SimulationUtils simulationUtils;

    public CongestionController(SimulationUtils simulationUtils) {
        this.simulationUtils = simulationUtils;
    }

    public void simulateCongestionControl() {
        simulationUtils.addToLog("📊 STAGE 5: TCP CONGESTION CONTROL");
        simulationUtils.addToLog("-".repeat(35));
        simulationUtils.updateProgress(90, "Congestion Control...");
        simulationUtils.updateVisualPanel("📊 TCP Congestion Control", new Color(221, 160, 221));

        try {
            simulationUtils.addToLog("🔄 Simulating TCP congestion control...");
            simulationUtils.addToLog("");

            // Simulate congestion window evolution
            simulationUtils.addToLog("📈 Congestion Window (cwnd) Evolution:");

            int cwnd = 1; // Start with 1 MSS
            int ssthresh = 16; // Slow start threshold
            int round = 1;

            simulationUtils.addToLog("   Initial: cwnd=1, ssthresh=" + ssthresh);

            // Slow Start Phase
            simulationUtils.addToLog("🚀 Slow Start Phase:");
            while (cwnd < ssthresh && round <= 4) { // Limiting rounds for simulation brevity
                simulationUtils.addToLog(String.format("   Round %d: cwnd=%d (exponential growth)", round, cwnd));
                cwnd *= 2; // Double every RTT
                round++;
                Thread.sleep(300);
            }

            // Congestion Avoidance Phase
            simulationUtils.addToLog("⚖️ Congestion Avoidance Phase:");
            for (int i = 0; i < 3; i++) { // Limiting rounds for simulation brevity
                simulationUtils.addToLog(String.format("   Round %d: cwnd=%d (linear growth)", round, cwnd));
                cwnd += 1; // Increase by 1 every RTT
                round++;
                Thread.sleep(300);
            }

            // Simulate packet loss
            simulationUtils.addToLog("⚠️ Packet Loss Detected!");
            ssthresh = cwnd / 2;
            if (ssthresh < 1)
                ssthresh = 1; // Ensure ssthresh doesn't become zero or negative
            cwnd = 1;
            simulationUtils.addToLog(String.format("   Recovery: cwnd=1, ssthresh=%d (TCP Reno)", ssthresh));

            simulationUtils.addToLog("");
            simulationUtils.addToLog("📊 Congestion Control Summary:");
            simulationUtils.addToLog("   • Algorithm: TCP Reno");
            simulationUtils.addToLog("   • Max cwnd reached: " + (ssthresh * 2)); // Approx, depends on loss
            simulationUtils.addToLog("   • Throughput: ~" + (ssthresh * 1460 * 8 / 1024) + " Kbps"); // Rough estimate
            simulationUtils.addToLog("   • Retransmissions: 1");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        simulationUtils.addToLog("");
        simulationUtils.updateProgress(95, "Congestion Control Complete");
    }
}