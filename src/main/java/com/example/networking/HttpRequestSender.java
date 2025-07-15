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
        // Stage initialization: log stage and update visual UI
        simulationUtils.addToLog("📡 STAGE 3: HTTP REQUEST & RESPONSE");
        simulationUtils.addToLog("-".repeat(35));
        simulationUtils.updateProgress(50, "HTTP Request...");
        simulationUtils.updateVisualPanel("📡 HTTP Communication", new Color(255, 182, 193));

        try {
            // Log start of HTTP request sending
            simulationUtils.addToLog("🔄 Sending HTTP Request...");

            // Create and configure the HTTP GET request
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET"); // Use GET method
            connection.setRequestProperty("User-Agent", "NetworkingSimulator/1.0"); // Set user agent
            connection.setConnectTimeout(5000); // Set connect timeout to 5 seconds
            connection.setReadTimeout(5000); // Set read timeout to 5 seconds

            // Log HTTP request headers being sent
            simulationUtils.addToLog("📤 HTTP Request Headers:");
            simulationUtils.addToLog("   GET " + new URL(url).getPath() + " HTTP/1.1"); // Realistic request line
            simulationUtils.addToLog("   Host: " + new URL(url).getHost());
            simulationUtils.addToLog("   User-Agent: NetworkingSimulator/1.0");
            simulationUtils.addToLog("   Accept: text/html,application/xhtml+xml");
            simulationUtils.addToLog("   Connection: keep-alive");

            // Measure response time by recording time before and after request
            long startTime = System.currentTimeMillis();
            int responseCode = connection.getResponseCode(); // Sends request and gets response code
            long endTime = System.currentTimeMillis();

            // Log response metadata
            simulationUtils.addToLog("");
            simulationUtils.addToLog("📥 HTTP Response Received:");
            simulationUtils.addToLog("   Status Code: " + responseCode + " " + connection.getResponseMessage());
            simulationUtils.addToLog("   Content-Type: " + connection.getContentType());
            simulationUtils.addToLog("   Content-Length: " + connection.getContentLength());
            simulationUtils.addToLog("   Server: " + connection.getHeaderField("Server"));
            simulationUtils.addToLog("   Response Time: " + (endTime - startTime) + "ms");

            // Check for redirection and log it
            if (responseCode >= 300 && responseCode < 400) {
                String location = connection.getHeaderField("Location");
                simulationUtils.addToLog("🔄 Redirect detected to: " + location);
            }

            // If successful (HTTP 200), read and preview the content
            if (responseCode == 200) {
                simulationUtils.addToLog("✅ Successfully received webpage content");

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
                    long contentSize = 0;
                    String line;
                    int lines = 0;

                    // Read and measure up to 5 lines of the response content
                    while ((line = reader.readLine()) != null && lines < 5) {
                        contentSize += line.length();
                        lines++;
                    }

                    // Log the size of content previewed
                    simulationUtils.addToLog("📄 Content preview: " + contentSize + " characters read...");
                }
            }

            // Disconnect after completion
            connection.disconnect();

        } catch (Exception e) {
            // Log any errors that occur during request
            simulationUtils.addToLog("❌ HTTP Request Failed: " + e.getMessage());
        }

        // Final log and progress update
        simulationUtils.addToLog("");
        simulationUtils.updateProgress(60, "HTTP Response Received");
    }
}