// src/main/java/com/example/networking/HttpRequestSender.java
package main.java.com.example.networking;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestSender {
    private SimulationUtils simulationUtils;

    public HttpRequestSender(SimulationUtils simulationUtils) {
        this.simulationUtils = simulationUtils;
    }

    public void sendRequest(String url) {
        simulationUtils.addToLog("📡 STAGE 3: HTTP REQUEST & RESPONSE");
        simulationUtils.addToLog("-".repeat(35));
        simulationUtils.updateProgress(50, "HTTP Request...");
        simulationUtils.updateVisualPanel("📡 HTTP Communication", new Color(255, 182, 193));

        try {
            simulationUtils.addToLog("🔄 Sending HTTP Request...");

            // Create HTTP request
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "NetworkingSimulator/1.0");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            simulationUtils.addToLog("📤 HTTP Request Headers:");
            simulationUtils.addToLog("   GET " + new URL(url).getPath() + " HTTP/1.1"); // Use getPath for a more
                                                                                        // realistic request line
            simulationUtils.addToLog("   Host: " + new URL(url).getHost());
            simulationUtils.addToLog("   User-Agent: NetworkingSimulator/1.0");
            simulationUtils.addToLog("   Accept: text/html,application/xhtml+xml");
            simulationUtils.addToLog("   Connection: keep-alive");

            long startTime = System.currentTimeMillis();
            int responseCode = connection.getResponseCode();
            long endTime = System.currentTimeMillis();

            simulationUtils.addToLog("");
            simulationUtils.addToLog("📥 HTTP Response Received:");
            simulationUtils.addToLog("   Status Code: " + responseCode + " " + connection.getResponseMessage());
            simulationUtils.addToLog("   Content-Type: " + connection.getContentType());
            simulationUtils.addToLog("   Content-Length: " + connection.getContentLength());
            simulationUtils.addToLog("   Server: " + connection.getHeaderField("Server"));
            simulationUtils.addToLog("   Response Time: " + (endTime - startTime) + "ms");

            // Handle redirects
            if (responseCode >= 300 && responseCode < 400) {
                String location = connection.getHeaderField("Location");
                simulationUtils.addToLog("🔄 Redirect detected to: " + location);
            }

            // Simulate reading response content
            if (responseCode == 200) {
                simulationUtils.addToLog("✅ Successfully received webpage content");

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
                    long contentSize = 0;
                    String line;
                    int lines = 0;
                    while ((line = reader.readLine()) != null && lines < 5) {
                        contentSize += line.length();
                        lines++;
                    }
                    simulationUtils.addToLog("📄 Content preview: " + contentSize + " characters read...");
                }
            }

            connection.disconnect();

        } catch (Exception e) {
            simulationUtils.addToLog("❌ HTTP Request Failed: " + e.getMessage());
        }

        simulationUtils.addToLog("");
        simulationUtils.updateProgress(60, "HTTP Response Received");
    }
}