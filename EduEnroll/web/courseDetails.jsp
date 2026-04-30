<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.eduenroll.beans.CourseBean" %>
<%@ page import="com.eduenroll.beans.EnrollmentBean" %>
<%@ page import="com.eduenroll.beans.UserBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Course Details</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/chatbot.js"></script>
    <script src="js/script.js"></script>
</head>

<body>
<%
    CourseBean course = (CourseBean) request.getAttribute("course");
    EnrollmentBean enrollment = (EnrollmentBean) request.getAttribute("enrollment");
    UserBean user = (UserBean) session.getAttribute("user");

    if (course == null) {
        response.sendRedirect("DashboardServlet");
        return;
    }
%>

<div class="navbar">
    <div class="nav-left">
        <span class="menu-icon" onclick="openSidebar()">☰</span>
        <h2>EduEnroll</h2>
    </div>

    <div class="nav-right">
        <%
            if (user != null) {
        %>
            <span class="welcome">Welcome, <%= user.getName() %></span>
        <%
            }
        %>
    </div>
</div>

<div id="sidebarOverlay" class="sidebar-overlay" onclick="closeSidebar()"></div>

<div id="sidebarMenu" class="sidebar-menu">
    <div class="sidebar-header">
        <span class="close-btn" onclick="closeSidebar()">✕</span>
    </div>

    <div class="sidebar-user">
        Hello, <strong><%= user != null ? user.getName() : "User" %></strong> 👋
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

        <img src="images/courses/<%= course.getImage() %>"
             alt="<%= course.getTitle() %>"
             class="course-banner"
             onerror="this.src='images/courses/default.jpg'">

        <h1><%= course.getTitle() %></h1>
        <p><strong>Category:</strong> <%= course.getCategory() %></p>
        <p><strong>Description:</strong> <%= course.getDescription() %></p>
        <p><strong>Duration:</strong> <%= course.getDuration() %></p>
        <p><strong>Instructor:</strong> <%= course.getInstructor() %></p>
        <p><strong>Price:</strong> Rs. <%= course.getPrice() %></p>
        <p><strong>Total Modules:</strong> <%= course.getModules() != null ? course.getModules().length : 0 %></p>

        <div style="margin-top:20px;">
            <%
                if (user == null) {
            %>
                <a href="login.jsp" class="btn">Login to Enroll</a>
            <%
                } else if (enrollment == null) {
            %>
                <form action="EnrollServlet" method="post">
                    <input type="hidden" name="courseId" value="<%= course.getId() %>">
                    <button type="submit" class="btn">Enroll Now</button>
                </form>
            <%
                } else if ("Completed".equals(enrollment.getStatus())) {
            %>
                <button type="button" class="btn status-completed" disabled>Completed</button>
                <a href="LearningServlet?courseId=<%= course.getId() %>" class="btn">View Course</a>
            <%
                } else {
            %>
                <button type="button" class="btn status-enrolled" disabled>Enrolled</button>
                <a href="LearningServlet?courseId=<%= course.getId() %>" class="btn">Start Course</a>
            <%
                }
            %>
        </div>
    </div>
</div>
        
        <!-- EduBot Chatbot -->
<div class="chatbot-toggle" onclick="toggleChatbot()">💬</div>

<div id="chatbotBox" class="chatbot-box">
    <div class="chatbot-header">
        <span>EduBot</span>
        <button onclick="toggleChatbot()">✕</button>
    </div>

    <div id="chatMessages" class="chatbot-messages">
        <div class="bot-msg">
            Hi! I am EduBot 👋<br>
            Tell me what you want to learn, and I will suggest the best course.
        </div>

        <div class="quick-options">
            <button onclick="sendQuickQuestion('coding')">Coding</button>
            <button onclick="sendQuickQuestion('web')">Web Development</button>
            <button onclick="sendQuickQuestion('data')">Data Analytics</button>
            <button onclick="sendQuickQuestion('cyber')">Cyber Security</button>
        </div>
    </div>

    <div class="chatbot-input">
        <input type="text" id="chatInput" placeholder="Type your requirement...">
        <button onclick="sendBotMessage()">Send</button>
    </div>
</div>


</body>
</html>