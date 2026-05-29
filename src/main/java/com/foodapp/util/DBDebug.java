package com.foodapp.util;

import java.net.InetSocketAddress;
import java.net.Socket;

public class DBDebug {

    public static void main(String[] args) {

        // 1) Can we load the MySQL driver class?
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ Driver class com.mysql.cj.jdbc.Driver loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver class not found: " + e.getMessage());
        }

        // 2) Check socket reachability to localhost:3306
        String host = "localhost";
        int port = 3306;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 3000);
            System.out.println("✅ TCP connection to " + host + ":" + port + " succeeded");
        } catch (Exception e) {
            System.out.println("❌ TCP connection to " + host + ":" + port + " failed: " + e.getMessage());
        }

        // 3) Print JDBC URL and credentials being used (masked)
        String url = "jdbc:mysql://localhost:3306/foodapp";
        String user = "root";
        String pass = "Sanju@313";

        System.out.println("JDBC URL: " + url);
        System.out.println("DB user: " + user + " (password masked)");
    }
}
