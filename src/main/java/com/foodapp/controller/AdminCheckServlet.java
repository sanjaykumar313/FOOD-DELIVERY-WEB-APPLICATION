package com.foodapp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.foodapp.dao.AdminDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Lightweight health-check for admin DB queries. NOT under /admin/* so it bypasses AdminFilter.
@WebServlet("/admin-check")
public class AdminCheckServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            AdminDAO dao = new AdminDAO();

            out.println("totalUsers=" + dao.getTotalUsers());
            out.println("totalRestaurants=" + dao.getTotalRestaurants());
            out.println("totalOrders=" + dao.getTotalOrders());
            out.println("totalRevenue=" + dao.getTotalRevenue());
            out.println("todayOrders=" + dao.getTodayOrders());
            out.println("todayRevenue=" + dao.getTodayRevenue());
            out.println("pendingOrders=" + dao.getPendingOrders());
            out.println("totalMenuItems=" + dao.getTotalMenuItems());

        } catch (Exception e) {
            out.println("ERROR: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace(out);
        }
    }
}
