<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard | Foodie</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= contextPath %>/css/admin.css">
</head>
<body class="admin-body">

<% request.setAttribute("currentPage", "dashboard"); %>
    // Mark current page and prepare commonly used values safely
    request.setAttribute("currentPage", "dashboard");

    String contextPath = request.getContextPath();

    String adminNameStr = request.getAttribute("adminName") != null
            ? request.getAttribute("adminName").toString() : "";

    Object totalOrdersObj = request.getAttribute("totalOrders");
    String totalOrdersStr = totalOrdersObj != null ? totalOrdersObj.toString() : "0";

    Object totalUsersObj = request.getAttribute("totalUsers");
    String totalUsersStr = totalUsersObj != null ? totalUsersObj.toString() : "0";

    Object totalRestaurantsObj = request.getAttribute("totalRestaurants");
    String totalRestaurantsStr = totalRestaurantsObj != null ? totalRestaurantsObj.toString() : "0";

    Object todayOrdersObj = request.getAttribute("todayOrders");
    String todayOrdersStr = todayOrdersObj != null ? todayOrdersObj.toString() : "0";

    Object pendingOrdersObj = request.getAttribute("pendingOrders");
    String pendingOrdersStr = pendingOrdersObj != null ? pendingOrdersObj.toString() : "0";

    Object totalMenuItemsObj = request.getAttribute("totalMenuItems");
    String totalMenuItemsStr = totalMenuItemsObj != null ? totalMenuItemsObj.toString() : "0";

    // Revenue values may be numbers (Double/Integer/etc). Handle generically.
    Object totalRevenueObj = request.getAttribute("totalRevenue");
    double totalRevenueVal = 0.0;
    if (totalRevenueObj instanceof Number) {
        totalRevenueVal = ((Number) totalRevenueObj).doubleValue();
    }
    String totalRevenueStr = String.format("%.0f", totalRevenueVal);

    Object todayRevenueObj = request.getAttribute("todayRevenue");
    double todayRevenueVal = 0.0;
    if (todayRevenueObj instanceof Number) {
        todayRevenueVal = ((Number) todayRevenueObj).doubleValue();
    }
    String todayRevenueStr = String.format("%.0f", todayRevenueVal);
<div class="admin-layout">

    <!-- SIDEBAR -->
    <jsp:include page="components/sidebar.jsp" />

    <!-- MAIN CONTENT -->
    <main class="admin-main">

        <!-- HEADER -->
        <div class="admin-header">
            <div>
                <h1 class="admin-title">Dashboard</h1>
                <p class="admin-subtitle">Welcome back, <strong><%= request.getAttribute("adminName") != null ? request.getAttribute("adminName") : "" %></strong> 👋</p>
            </div>
            <div class="header-date" id="adminDate"></div>
        </div>

        <!-- STAT CARDS ROW 1 -->
        <div class="stats-grid">

            <div class="stat-card stat-blue">
                <div class="stat-icon">🛒</div>
                <div class="stat-info">
                    <div class="stat-value"><%= request.getAttribute("totalOrders") != null ? request.getAttribute("totalOrders") : "0" %></div>
                    <div class="stat-label">Total Orders</div>
                    <div class="stat-value"><%= totalOrdersStr %></div>
            </div>

            <div class="stat-card stat-green">
                <div class="stat-icon">💰</div>
                <div class="stat-info">
                    <div class="stat-value">₹<%= request.getAttribute("totalRevenue") != null ? String.format("%.0f", (Double)request.getAttribute("totalRevenue")) : "0" %></div>
                    <div class="stat-label">Total Revenue</div>
                    <div class="stat-value">₹<%= totalRevenueStr %></div>
            </div>

            <div class="stat-card stat-orange">
                <div class="stat-icon">👤</div>
                <div class="stat-info">
                    <div class="stat-value"><%= request.getAttribute("totalUsers") != null ? request.getAttribute("totalUsers") : "0" %></div>
                    <div class="stat-label">Registered Users</div>
                    <div class="stat-value"><%= totalUsersStr %></div>
            </div>

            <div class="stat-card stat-purple">
                <div class="stat-icon">🏪</div>
                <div class="stat-info">
                    <div class="stat-value"><%= request.getAttribute("totalRestaurants") != null ? request.getAttribute("totalRestaurants") : "0" %></div>
                    <div class="stat-label">Restaurants</div>
                    <div class="stat-value"><%= totalRestaurantsStr %></div>
            </div>

        </div>

        <!-- STAT CARDS ROW 2 -->
        <div class="stats-grid">

            <div class="stat-card stat-teal">
                <div class="stat-icon">📅</div>
                <div class="stat-info">
                    <div class="stat-value"><%= request.getAttribute("todayOrders") != null ? request.getAttribute("todayOrders") : "0" %></div>
                    <div class="stat-label">Today's Orders</div>
                    <div class="stat-value"><%= todayOrdersStr %></div>
            </div>

            <div class="stat-card stat-indigo">
                <div class="stat-icon">💵</div>
                <div class="stat-info">
                    <div class="stat-value">₹<%= request.getAttribute("todayRevenue") != null ? String.format("%.0f", (Double)request.getAttribute("todayRevenue")) : "0" %></div>
                    <div class="stat-label">Today's Revenue</div>
                    <div class="stat-value">₹<%= todayRevenueStr %></div>
            </div>

            <div class="stat-card stat-red">
                <div class="stat-icon">⏳</div>
                <div class="stat-info">
                    <div class="stat-value"><%= request.getAttribute("pendingOrders") != null ? request.getAttribute("pendingOrders") : "0" %></div>
                    <div class="stat-label">Pending Orders</div>
                    <div class="stat-value"><%= pendingOrdersStr %></div>
            </div>

            <div class="stat-card stat-pink">
                <div class="stat-icon">🍽️</div>
                <div class="stat-info">
                    <div class="stat-value"><%= request.getAttribute("totalMenuItems") != null ? request.getAttribute("totalMenuItems") : "0" %></div>
                    <div class="stat-label">Menu Items</div>
                    <div class="stat-value"><%= totalMenuItemsStr %></div>
            </div>

        </div>

        <!-- QUICK ACTIONS -->
        <div class="quick-actions">
            <h2 class="section-heading">Quick Actions</h2>
            <div class="action-cards">

                <a href="${pageContext.request.contextPath}/admin/restaurants?action=add"
                   class="action-card">
                    <span class="action-icon">➕</span>
                    <span>Add Restaurant</span>
                </a>

                <a href="${pageContext.request.contextPath}/admin/menu-items?action=add"
                   class="action-card">
                    <span class="action-icon">🍕</span>
                    <span>Add Menu Item</span>
                </a>

                <a href="${pageContext.request.contextPath}/admin/orders"
                   class="action-card">
                    <span class="action-icon">📦</span>
                    <span>View Orders</span>
                </a>

                <a href="${pageContext.request.contextPath}/home.jsp"
                   class="action-card" target="_blank">
                    <span class="action-icon">🌐</span>
                    <span>View Site</span>
                </a>

            </div>
        </div>

    </main>

</div>

<script>
    // Live date display
    const dateEl = document.getElementById('adminDate');
    const now = new Date();
    dateEl.textContent = now.toLocaleDateString('en-IN', {
        weekday: 'long', year: 'numeric',
        month: 'long', day: 'numeric'
    });
</script>

</body>
</html>
