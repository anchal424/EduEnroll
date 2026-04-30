<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/script.js"></script>
</head>
<body>

<div class="navbar">
    <div class="nav-left">
        <span class="menu-icon" onclick="openSidebar()">☰</span>
        <h2>EduEnroll</h2>
    </div>

    <div class="nav-right">
        <span class="welcome">Welcome, Admin</span>
    </div>
</div>

<div id="sidebarOverlay" class="sidebar-overlay" onclick="closeSidebar()"></div>

<div id="sidebarMenu" class="sidebar-menu">
    <div class="sidebar-header">
        <span class="close-btn" onclick="closeSidebar()">✕</span>
    </div>

    <div class="sidebar-user">
        Hello, <strong>Admin</strong> 👋
    </div>

    <a href="adminDashboard.jsp" class="sidebar-link">🏠 Dashboard</a>
    <a href="addCourse.jsp" class="sidebar-link">➕ Add Course</a>
    <a href="ViewCoursesServlet" class="sidebar-link">📚 Manage Courses</a>
    <a href="ViewEnrollmentsServlet" class="sidebar-link">📝 Enrollments</a>
    <a href="AdminAnalyticsServlet" class="sidebar-link">📊 Analytics</a>
    <a href="index.jsp" class="sidebar-link logout-link">🚪 Logout</a>
</div>



</body>
</html>