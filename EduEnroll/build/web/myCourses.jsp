<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.eduenroll.beans.CourseBean" %>
<%@ page import="com.eduenroll.beans.EnrollmentBean" %>
<%@ page import="com.eduenroll.beans.UserBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Courses</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/chatbot.js"></script>
    <script src="js/script.js"></script>
</head>

<body>
<%
    UserBean user = (UserBean) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
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

<div id="sidebarOverlay" class="sidebar-overlay" onclick="closeSidebar()"></div>

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
    <h1>My Courses</h1>

    <div class="card-grid">
        <%
            List<CourseBean> myCourses = (List<CourseBean>) request.getAttribute("myCourses");
            List<EnrollmentBean> enrollments = (List<EnrollmentBean>) request.getAttribute("enrollments");

            if (myCourses != null && !myCourses.isEmpty()) {
                for (int i = 0; i < myCourses.size(); i++) {
                    CourseBean c = myCourses.get(i);
                    EnrollmentBean e = enrollments.get(i);
        %>
        <div class="course-card">
            <img src="images/courses/<%= c.getImage() %>"
                 alt="<%= c.getTitle() %>"
                 class="course-img"
                 onerror="this.src='images/courses/default.jpg'">

            <h3><%= c.getTitle() %></h3>

            <p><strong>Status:</strong> <%= e.getStatus() %></p>
            <p><strong>Progress:</strong> <%= e.getProgress() %>%</p>

            <div class="progress-wrapper">
                <div class="progress-bar">
                    <div class="progress-fill" style="width: <%= e.getProgress() %>%;">
                        <span><%= e.getProgress() %>%</span>
                    </div>
                </div>
            </div>

            <a href="LearningServlet?courseId=<%= c.getId() %>" class="btn">
                <%= "Completed".equals(e.getStatus()) ? "View Course" : "Start Course" %>
            </a>
        </div>
        <%
                }
            } else {
        %>
        <p>You have not enrolled in any course yet.</p>
        <%
            }
        %>
    </div>
</div>
    
    

</body>
</html>