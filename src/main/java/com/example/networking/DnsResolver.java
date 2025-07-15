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

/*
 * 
 * Here's a summary of how the code handles DNS:
 * 
 * 1. **DNS Cache Implementation:**
 * The `NetworkingJourneySimulator` class maintains a `HashMap` called
 * `dnsCache` to simulate a local DNS cache. This cache stores previously
 * resolved hostnames and their corresponding IP addresses.
 * 
 * 2. **DNS Resolution Process (`DnsResolver`):**
 * When the user enters a URL, the `simulateNetworkJourney` method in
 * `NetworkingJourneySimulator` instantiates a `DnsResolver` object, passing it
 * the `simulationUtils` (for GUI updates) and the shared `dnsCache`.
 * The `DnsResolver.resolve(host)` method is then called to initiate the DNS
 * lookup for the given hostname.
 * 
 * 3. **Cache Check (First Step):**
 * The `resolve` method first checks if the requested hostname already exists in
 * the `dnsCache`.
 * 
 * **Cache Hit:** If found, it's considered a "DNS Cache Hit." The code logs
 * that the IP was found in the cache, updates the progress bar, and immediately
 * returns the cached IP address, demonstrating the speed benefit of caching.
 * 
 * **Cache Miss:** If the hostname is *not* in the cache, it's a
 * "DNS Cache Miss." The simulation proceeds to simulate a live lookup.
 * 
 * 4. **Simulated Hierarchical Lookup (for Cache Misses):**
 * For a cache miss, the code doesn't actually query a series of different DNS
 * servers (root, TLD, authoritative). Instead, it **simulates** this process by
 * printing messages to the log area, like "Querying Root DNS servers...",
 * "Querying .com TLD servers...", and "Querying Authoritative DNS servers...".
 * Small `Thread.sleep()` delays are introduced between these messages to mimic
 * the time taken for these distributed queries.
 * 
 * 5. **Actual IP Resolution:**
 * After the simulated hierarchical lookup, the code uses Java's built-in
 * `InetAddress.getByName(host)` method. This is where a **real DNS lookup**
 * occurs, using the operating system's configured DNS servers to get the actual
 * IP address for the hostname.
 * The time taken for this real lookup is also measured and logged.
 * 
 * 6. **Caching the New Resolution:**
 * Once the `InetAddress.getByName()` call successfully resolves the IP address,
 * this new hostname-to-IP mapping is added to the `dnsCache`. This means that
 * if the same hostname is requested again later in the *same* simulation run
 * (or if the cache wasn't cleared for a new run), it would result in a cache
 * hit.
 * 
 * 7. **Logging and Visualization:**
 * Throughout the entire DNS resolution process, the `SimulationUtils` object is
 * used to:
 * Add descriptive messages to the `logArea` explaining each step (e.g.,
 * "DNS RESOLUTION," "Cache Hit," "Querying Root DNS").
 * Update the `progressBar` to show progress (e.g., 10% for starting DNS, 20%
 * for completion).
 * Change the `visualPanel` to display the current stage of the DNS resolution
 * (e.g., "🔍 DNS Resolution").
 * 
 * In essence, the code provides an educational simulation of DNS by visually
 * and textually explaining the caching mechanism and the step-by-step process
 * of resolving a domain name to an IP address, while using real Java networking
 * capabilities for the actual IP lookup.
 */