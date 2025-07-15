1.  **DNS Cache Implementation:**
    * The `NetworkingJourneySimulator` class maintains a `HashMap` called `dnsCache` to simulate a local DNS cache. This cache stores previously resolved hostnames and their corresponding IP addresses.


2.  **Cache Check (First Step):**
    * The `resolve` method first checks if the requested hostname already exists in the `dnsCache`.
    
    * **Cache Hit:** If found, it's considered a "DNS Cache Hit." The code logs that the IP was found in the cache, updates the progress bar, and immediately returns the cached IP address, demonstrating the speed benefit of caching.

    * **Cache Miss:** If the hostname is *not* in the cache, it's a "DNS Cache Miss." The simulation proceeds to simulate a live lookup.


4.  **Simulated Hierarchical Lookup (for Cache Misses):**

    * For a cache miss, the code doesn't actually query a series of different DNS servers (root, TLD, authoritative). Instead, it **simulates** this process by printing messages to the log area, like "Querying Root DNS servers...", "Querying .com TLD servers...", and "Querying Authoritative DNS servers...".

    * Small `Thread.sleep()` delays are introduced between these messages to mimic the time taken for these distributed queries.


5.  **Actual IP Resolution:**

    * After the simulated hierarchical lookup, the code uses Java's built-in `InetAddress.getByName(host)` method. This is where a **real DNS lookup** occurs, using the operating system's configured DNS servers to get the actual IP address for the hostname.

    * The time taken for this real lookup is also measured and logged.

6.  **Caching the New Resolution:**

    * Once the `InetAddress.getByName()` call successfully resolves the IP address, this new hostname-to-IP mapping is added to the `dnsCache`. This means that if the same hostname is requested again later in the *same* simulation run (or if the cache wasn't cleared for a new run), it would result in a cache hit.



THEORY : 
--------------
# How DNS Works from a User's Perspective

From a user's perspective, DNS works almost entirely behind the scenes, making the internet seamlessly navigable. Here's a breakdown:

1.  **Typing a URL:** When you type a URL (like `google.com`) into your web browser and press Enter, your computer doesn't immediately know where `google.com` is located.

2.  **Local DNS Check (Cache):** Your computer first checks its own local DNS cache. This cache stores recent DNS resolutions to speed up access to frequently visited websites. If the IP address for `google.com` is already in your cache, your browser can immediately connect to that IP address, making the website load very quickly.

3.  **Resolver Query (ISP's DNS Server):** If the IP address isn't in your local cache, your computer sends a request to a **DNS resolver**. This resolver is typically a DNS server provided by your Internet Service Provider (ISP), though you can configure your computer to use other public DNS resolvers (like Google's 8.8.8.8 or Cloudflare's 1.1.1.1).

4.  **Recursive Queries (Behind the Scenes):** The ISP's DNS resolver then embarks on a journey to find the correct IP address. This involves a series of queries to different types of DNS servers in a hierarchical structure:
    * **Root Servers:** The resolver first asks a **root DNS server** (there are 13 logical root servers worldwide) for directions. The root server doesn't know the IP for `google.com`, but it knows where to find the servers responsible for `.com` domains.
    * **TLD Servers:** The resolver then queries a **Top-Level Domain (TLD) server** (e.g., for `.com`, `.org`, `.net`). The TLD server, in turn, doesn't know the IP for `google.com`, but it knows where to find the authoritative servers for `google.com`.
    * **Authoritative Name Servers:** Finally, the resolver contacts the **authoritative name servers** for `google.com`. These are the servers that hold the definitive records for `google.com` and its subdomains. They will provide the actual IP address for `google.com`.

5.  **IP Address Returned:** Once the authoritative name server provides the IP address, the ISP's DNS resolver sends this IP address back to your computer.

6.  **Connection and Browse:** Your computer then stores this IP address in its local cache and uses it to establish a direct connection with the web server hosting `google.com`. Your browser can then download the website's content and display it to you.

Essentially, from a user's perspective, DNS is the invisible helper that translates friendly website names into the numerical addresses computers understand, making the internet accessible and easy to navigate without needing to remember complex IP addresses.