# 🌐 What Happens When You Type `google.com`

A detailed breakdown of the networking journey simulated step-by-step.

---

## 🧭 STAGE 1: DNS RESOLUTION

### 🔍 What Is DNS?
**DNS (Domain Name System)** translates human-readable domain names like `google.com` into machine-usable IP addresses like `142.250.194.46`.

### 🔁 Steps in DNS Resolution:

1. **DNS Cache Check**
   - ✅ If found → Returns IP immediately.
   - ❌ If not → Starts recursive query.

2. **Querying Root DNS Servers**
   - Asks: “Who handles `.com` domains?”
   - Root servers refer to **TLD DNS servers**.

3. **Querying TLD DNS Servers**
   - Asks: “Who manages `google.com`?”
   - TLD servers point to **Authoritative DNS**.

4. **Querying Authoritative DNS Servers**
   - Asks: “What’s the IP of `google.com`?”
   - ✅ Final response: e.g., `142.250.194.46`.

5. **Storing in DNS Cache**
   - Stored locally with a **TTL**, e.g., `300 seconds`.

### 📌 Key Takeaways:
- Mimics a real recursive DNS lookup.
- Lookup time is recorded (e.g., `43ms`).
- Matches how real browsers resolve domains.

---

## 🔗 STAGE 2: TCP CONNECTION ESTABLISHMENT

### 🧠 What Is TCP?
**TCP (Transmission Control Protocol)** ensures reliable, ordered, and error-checked communication over the internet.

### 🤝 The 3-Way Handshake:
1. **SYN** – Client initiates connection with a sequence number.
2. **SYN-ACK** – Server acknowledges and sends its own sequence number.
3. **ACK** – Client confirms and establishes connection.

✅ After this, both parties are ready to exchange data.

### 🧾 Connection Details Logged:
- **Local Port**: e.g., `39985`
- **Remote Port**: `80` (HTTP)
- **Window Size**: `65535 bytes`
- **MSS**: `1460 bytes`
- **RTT**: e.g., `91ms`

### 📌 Why It Matters:
- Forms the basis of reliable data transfer.
- Ensures connection is properly synchronized before sending HTTP data.

---

## 📡 STAGE 3: HTTP REQUEST & RESPONSE

### 🌐 What Is HTTP?
**HTTP (HyperText Transfer Protocol)** is the protocol used to fetch web resources like HTML pages, images, scripts, etc.

### 📤 The Request
Client sends an HTTP **GET** request with:
- `GET / HTTP/1.1`
- `Host: google.com`
- `User-Agent: NetworkingSimulator/1.0`
- `Accept` and `Connection` headers

### 📥 The Response
Server responds with:
- **Status Code**: `200 OK`
- **Headers**:
  - `Content-Type`: `text/html; charset=ISO-8859-1`
  - `Content-Length`: `-1` (dynamic)
  - `Server`: `gws` (Google Web Server)
- **Response Time**: e.g., `567ms`
- **Content Preview**: First 5842 characters of HTML

### 🔁 Redirects
- If status is `3xx`, `Location` header is included.
- Client must follow the redirect manually or automatically.

---

## ✅ Final Summary of the Flow

| Stage            | Purpose                        | Example Output                    |
|------------------|--------------------------------|------------------------------------|
| **DNS Resolution** | Find IP address of domain      | `142.250.194.46`                   |
| **TCP Connection** | Establish reliable connection  | `SYN → SYN-ACK → ACK`              |
| **HTTP Request**   | Fetch webpage                  | `200 OK`, content previewed        |

---

## 🗣 What You Can Say in a Presentation

> “When I type `google.com` and hit start, the simulator walks through the real steps of a web request.  
> First, it performs DNS resolution by querying multiple DNS servers until it finds the IP.  
> Then it initiates a TCP connection using the 3-way handshake to ensure a reliable channel.  
> Finally, it sends an HTTP GET request and logs the server’s response — just like a real browser would.  
> This helps us understand what’s happening under the hood every time we visit a website.”

---