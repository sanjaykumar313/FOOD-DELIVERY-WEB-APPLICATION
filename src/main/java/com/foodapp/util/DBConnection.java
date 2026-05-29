package com.foodapp.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection() {

        System.out.println("🚀 DBConnection method called");

        try {

            // LOAD DRIVER

            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("✅ MySQL Driver Loaded");

            // CREATE CONNECTION

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/food_delivery_app",
                    "root",
                    "Sanju@313");

            System.out.println("✅ DATABASE CONNECTED SUCCESSFULLY");

        } catch (Exception e) {

            String message = "❌ DATABASE CONNECTION FAILED: " + e.getMessage();
            System.out.println(message);
            e.printStackTrace();

            // Throw a runtime exception so servlet container logs show the failure
            throw new RuntimeException(message, e);
        }

        return connection;
    }
}