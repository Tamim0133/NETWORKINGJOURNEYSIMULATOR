# 🌐 Networking Journey Simulator

Welcome to the **Networking Journey Simulator**\! This is a simple Java Swing application that visually and textually simulates the journey a network request takes when you type a URL into a web browser. It breaks down the complex process into understandable stages, demonstrating key concepts from the Application, Transport, and Network layers of the TCP/IP model.

## ✨ Features

  * **URL Input:** Enter any URL to start the simulation.
  * **Step-by-Step Simulation:** Witness the different stages of a network request, including:
      * **DNS Resolution:** How a domain name is translated into an IP address, including DNS caching.
      * **TCP Connection Establishment:** The classic TCP 3-way handshake in action.
      * **HTTP Request & Response:** The exchange of application-layer messages.
      * **Routing & IP Layer:** A simplified look at how packets traverse the network and NAT.
      * **TCP Congestion Control:** A visualization of the congestion window and how it adapts.
  * **Visual Feedback:** A dedicated panel provides a high-level visual representation of the current simulation stage.
  * **Detailed Log:** A comprehensive log area provides textual explanations and simulated technical details for each step.
  * **Progress Bar:** Track the overall progress of the simulation.

## 🚀 How to Run

### Prerequisites

  * Java Development Kit (JDK) 8 or higher installed on your system.

### Steps

1.  **Clone or Download the Project:**
    If you have Git:

    ```bash
    git clone <repository_url> # Replace with your repository URL if applicable
    cd NetworkingJourneySimulator
    ```

    If you download a ZIP file, extract it and navigate into the `NetworkingJourneySimulator` directory.

2.  **Navigate to the Project Root:**
    Open your terminal or command prompt and change your current directory to the `NetworkingJourneySimulator` folder (the one containing `src`, `lib`, `out`, etc.).

3.  **Compile the Source Code:**
    Use the Java compiler (`javac`) to compile all Java files. The `-d out` flag tells the compiler to place the compiled `.class` files into the `out` directory, maintaining the package structure.

    ```bash
    javac -d out src/main/java/com/example/networking/*.java
    ```

4.  **Run the Application:**
    Once compiled, you can run the application using the `java` command. The `-cp out` flag adds the `out` directory to the classpath, allowing the Java Virtual Machine (JVM) to find your compiled classes.

    ```bash
    java -cp out com.example.networking.NetworkingJourneySimulator
    ```

    The simulator GUI window should now appear\!

## 📂 Project Structure

The project follows a standard Java project structure to ensure modularity and organization:

```
NetworkingJourneySimulator/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── example/
│                   └── networking/
│                       ├── NetworkingJourneySimulator.java   # Main GUI and orchestrator
│                       ├── DnsResolver.java                  # Handles DNS resolution logic
│                       ├── TcpConnector.java                 # Simulates TCP handshake
│                       ├── HttpRequestSender.java            # Manages HTTP request/response
│                       ├── RouterSimulator.java              # Simulates IP routing and NAT
│                       ├── CongestionController.java         # Demonstrates TCP congestion control
│                       └── SimulationUtils.java              # Utility class for logging and UI updates
├── lib/                                  # External libraries (currently empty as only standard Java libs are used)
└── out/                                  # Compiled .class files will be placed here
```

## 💡 Concepts Demonstrated

This simulator aims to illustrate core networking concepts often taught in courses like **CSE 3101** (or similar introductory networking courses), covering aspects of:

  * **Application Layer:** HTTP, DNS
  * **Transport Layer:** TCP (Handshake, Congestion Control)
  * **Network Layer:** IP (Addressing, Routing, NAT)
  * **Link/Physical Layer:** (Implicitly, through data transfer simulation)

## 🤝 Contributing

Feel free to fork this repository, suggest improvements, or add new features\!

## 📄 License

This project is open-source and available under the [MIT License](https://www.google.com/search?q=LICENSE).

-----

Enjoy your networking journey\! 🚀