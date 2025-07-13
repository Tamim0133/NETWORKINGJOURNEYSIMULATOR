// src/main/java/com/example/networking/DnsResolver.java
package main.java.com.example.networking;

import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class DnsResolver {
    private SimulationUtils simulationUtils;
    private Map<String, String> dnsCache;

    public DnsResolver(SimulationUtils simulationUtils, Map<String, String> dnsCache) {
        this.simulationUtils = simulationUtils;
        this.dnsCache = dnsCache;
    }

    public String resolve(String host) {
        simulationUtils.addToLog("🔍 STAGE 1: DNS RESOLUTION");
        simulationUtils.addToLog("-".repeat(30));
        simulationUtils.updateProgress(10, "DNS Resolution...");
        simulationUtils.updateVisualPanel("🔍 DNS Resolution", new Color(173, 216, 230));

        try {
            // Check DNS Cache first
            if (dnsCache.containsKey(host)) {
                simulationUtils.addToLog("✅ DNS Cache Hit for " + host);
                simulationUtils.addToLog("🎯 Cached IP: " + dnsCache.get(host));
                simulationUtils.updateProgress(20, "DNS Resolution Complete");
                return dnsCache.get(host);
            } else {
                simulationUtils.addToLog("🔄 DNS Cache Miss - Querying DNS servers...");

                // Simulate DNS hierarchy
                simulationUtils.addToLog("📡 1. Querying Root DNS servers...");
                Thread.sleep(200);
                simulationUtils.addToLog("📡 2. Querying .com TLD servers...");
                Thread.sleep(200);
                simulationUtils.addToLog("📡 3. Querying Authoritative DNS servers...");
                Thread.sleep(300);

                // Actual DNS resolution
                long startTime = System.currentTimeMillis();
                InetAddress address = InetAddress.getByName(host);
                long endTime = System.currentTimeMillis();

                String ip = address.getHostAddress();
                dnsCache.put(host, ip); // Cache the result

                simulationUtils.addToLog("✅ DNS Resolution Successful!");
                simulationUtils.addToLog("🎯 IP Address: " + ip);
                simulationUtils.addToLog("⏱️ DNS Lookup Time: " + (endTime - startTime) + "ms");
                simulationUtils.addToLog("💾 Added to DNS cache (TTL: 300s)");
                simulationUtils.addToLog("");
                simulationUtils.updateProgress(20, "DNS Resolution Complete");
                return ip;
            }

        } catch (UnknownHostException e) {
            simulationUtils.addToLog("❌ DNS Resolution Failed: " + e.getMessage());
            simulationUtils.addToLog("");
            simulationUtils.updateProgress(20, "DNS Resolution Failed");
            return null;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            simulationUtils.addToLog("❌ DNS Resolution Interrupted: " + e.getMessage());
            simulationUtils.addToLog("");
            simulationUtils.updateProgress(20, "DNS Resolution Interrupted");
            return null;
        }
    }
}