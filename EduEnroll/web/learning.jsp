<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.eduenroll.beans.CourseBean" %>
<%@ page import="com.eduenroll.beans.EnrollmentBean" %>
<%@ page import="com.eduenroll.beans.UserBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Learning Page</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/script.js"></script>
</head>
<body>
<%
    UserBean user = (UserBean) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    CourseBean course = (CourseBean) request.getAttribute("course");
    EnrollmentBean enrollment = (EnrollmentBean) request.getAttribute("enrollment");
    String currentModuleTitle = (String) request.getAttribute("currentModuleTitle");
    String currentModuleContent = (String) request.getAttribute("currentModuleContent");
%>

<div class="navbar">
    <div class="nav-left">
        <span class="menu-icon" onclick="openSidebar()">☰</span>
        <h2>EduEnroll</h2>
    </div>

    <div class="nav-right">
        <span class="welcome">Welcome, <%= user.getName() %></span>
    </div>
</div>

<!-- Sidebar Overlay -->
<div id="sidebarOverlay" class="sidebar-overlay" onclick="closeSidebar()"></div>

<!-- Sidebar -->
<div id="sidebarMenu" class="sidebar-menu">
    <div class="sidebar-header">
        <span class="close-btn" onclick="closeSidebar()">✕</span>
    </div>

    <div class="sidebar-user">
        Hello, <strong><%= user.getName() %></strong> 👋
    </div>

    <a href="DashboardServlet" class="sidebar-link">📘 Dashboard</a>
    <a href="MyCoursesServlet" class="sidebar-link">📚 My Courses</a>
    <a href="UserAnalyticsServlet" class="sidebar-link">📊 Analytics</a>
    <a href="CertificateServlet" class="sidebar-link">🏆 Certificates</a>

    <a href="LogoutServlet" class="sidebar-link logout-link">🚪 Logout</a>
</div>
<div class="back-container">
    <a href="javascript:history.back()" class="back-btn">← Back</a>
</div>

<div class="page-container">
    <div class="learning-box">
        <h1><%= course.getTitle() %></h1>
        <p><strong>Progress:</strong> <%= enrollment.getProgress() %>%</p>
        <div class="progress-wrapper">
    <div class="progress-bar">
        <div class="progress-fill" style="width: <%= enrollment.getProgress() %>%;">
            <span><%= enrollment.getProgress() %>%</span>
        </div>
    </div>
</div>
        <p><strong>Current Module:</strong> <%= enrollment.getCurrentModule() %> / <%= enrollment.getTotalModules() %></p>

        <h3 style="margin-top:20px;"><%= currentModuleTitle %></h3>
        <pre class="material-box"><%= currentModuleContent %></pre>

        <div style="margin-top:20px;">
            <%
                if ("Completed".equals(enrollment.getStatus())) {
            %>
                <button type="button" class="btn status-completed" disabled>Course Completed</button>

                <form action="GenerateCertificateServlet" method="post" style="margin-top:15px;">
                    <input type="hidden" name="courseId" value="<%= course.getId() %>">
                    <button type="submit" class="btn">Generate Certificate</button>
                </form>
            <%
                } else {
            %>
                <form action="NextModuleServlet" method="post">
                    <input type="hidden" name="enrollmentId" value="<%= enrollment.getId() %>">
                    <input type="hidden" name="courseId" value="<%= course.getId() %>">
                    <button type="submit" class="btn">Next Module</button>
                </form>
            <%
                }
            %>
        </div>
    </div>
</div>
</body>
</html>