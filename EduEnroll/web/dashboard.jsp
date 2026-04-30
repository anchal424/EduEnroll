<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.eduenroll.beans.CourseBean" %>
<%@ page import="com.eduenroll.beans.UserBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
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

<div class="page-container">
    <h1>Available Courses</h1>
    <div class="card-grid">
        <%
            List<CourseBean> courseList = (List<CourseBean>) request.getAttribute("courseList");
            if (courseList != null) {
                for (CourseBean c : courseList) {
        %>
        <div class="course-card">
         <img src="${pageContext.request.contextPath}/images/courses/<%= c.getImage() %>"
     alt="<%= c.getTitle() %>"
     class="course-img"
     onerror="this.src='${pageContext.request.contextPath}/images/courses/default.jpg'">
            <h3><%= c.getTitle() %></h3>
            <a href="CourseDetailsServlet?courseId=<%= c.getId() %>" class="btn">View Details</a>
        </div>
        <%
                }
            }
        %>
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