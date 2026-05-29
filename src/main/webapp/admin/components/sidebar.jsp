<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="admin-sidebar">

    <div class="sidebar-brand">
        <span class="brand-icon">🍔</span>
        <span class="brand-text">Foodie Admin</span>
    </div>

    <ul class="sidebar-nav">

        <li class="nav-item">
            <a href="<%= request.getContextPath() %>/admin/dashboard"
               class="nav-link <%= "dashboard".equals(request.getAttribute("currentPage")) ? "active" : "" %>">
                <span class="nav-icon">📊</span>
                <span>Dashboard</span>
            </a>
        </li>

        <li class="nav-item">
                <a href="<%= request.getContextPath() %>/admin/restaurants"
                    class="nav-link <%= "restaurants".equals(request.getAttribute("currentPage")) ? "active" : "" %>">
                <span class="nav-icon">🏪</span>
                <span>Restaurants</span>
            </a>
        </li>

        <li class="nav-item">
                <a href="<%= request.getContextPath() %>/admin/menu-items"
                    class="nav-link <%= "menu".equals(request.getAttribute("currentPage")) ? "active" : "" %>">
                <span class="nav-icon">🍽️</span>
                <span>Menu Items</span>
            </a>
        </li>

        <li class="nav-item">
                <a href="<%= request.getContextPath() %>/admin/orders"
                    class="nav-link <%= "orders".equals(request.getAttribute("currentPage")) ? "active" : "" %>">
                <span class="nav-icon">🛒</span>
                <span>Orders</span>
            </a>
        </li>

    </ul>

    <div class="sidebar-footer">
          <a href="<%= request.getContextPath() %>/logout"
              class="logout-btn">
            <span>🚪</span> Logout
        </a>
    </div>

</nav>
