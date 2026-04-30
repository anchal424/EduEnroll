<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.eduenroll.beans.UserAnalyticsBean" %>
<%@ page import="com.eduenroll.beans.UserBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Analytics</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/chatbot.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="js/script.js"></script>
</head>


<body>
<%
    UserBean user = (UserBean) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<UserAnalyticsBean> progressData = (List<UserAnalyticsBean>) request.getAttribute("progressData");
    UserAnalyticsBean completionSummary = (UserAnalyticsBean) request.getAttribute("completionSummary");
    int certificateCount = (Integer) request.getAttribute("certificateCount");
    int enrolledCount = (Integer) request.getAttribute("enrolledCount");

    StringBuilder progressLabels = new StringBuilder();
    StringBuilder progressCounts = new StringBuilder();

    if (progressData != null) {
        for (int i = 0; i < progressData.size(); i++) {
            UserAnalyticsBean bean = progressData.get(i);
            progressLabels.append("'").append(bean.getLabel().replace("'", "\\'")).append("'");
            progressCounts.append(bean.getCount());

            if (i < progressData.size() - 1) {
                progressLabels.append(",");
                progressCounts.append(",");
            }
        }
    }

    int completed = 0;
    int ongoing = 0;
    if (completionSummary != null) {
        completed = completionSummary.getCount();
        String[] parts = completionSummary.getLabel().split(",");
        if (parts.length > 1) {
            ongoing = Integer.parseInt(parts[1]);
        }
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
    <h1>My Analytics</h1>

    <div class="stats-grid">
        <div class="stat-card">
            <h3>Enrolled Courses</h3>
            <p><%= enrolledCount %></p>
        </div>
        <div class="stat-card">
            <h3>Certificates Earned</h3>
            <p><%= certificateCount %></p>
        </div>
        <div class="stat-card">
            <h3>Completed Courses</h3>
            <p><%= completed %></p>
        </div>
    </div>

    <div class="chart-card">
        <h3>My Course Progress</h3>
        <canvas id="progressChart"></canvas>
    </div>

    <div class="chart-card">
        <h3>Completed vs Ongoing</h3>
        <canvas id="completionChart"></canvas>
    </div>
</div>

<script>
    const progressCtx = document.getElementById('progressChart').getContext('2d');
    new Chart(progressCtx, {
        type: 'bar',
        data: {
            labels: [<%= progressLabels.toString() %>],
            datasets: [{
                label: 'Progress %',
                data: [<%= progressCounts.toString() %>],
                backgroundColor: '#8E1FA3',
                borderColor: '#4F2D86',
                borderWidth: 1,
                borderRadius: 8
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                x: {
                    ticks: {
                        color: '#4F2D86',
                        maxRotation: 0,
                        minRotation: 0,
                        autoSkip: false,
                        font: {
                            size: 11
                        }
                    },
                    grid: {
                        color: '#F0E5F6'
                    }
                },
                y: {
                    beginAtZero: true,
                    max: 100,
                    ticks: {
                        color: '#4F2D86'
                    },
                    grid: {
                        color: '#F0E5F6'
                    }
                }
            },
            plugins: {
                legend: {
                    labels: {
                        color: '#4F2D86'
                    }
                }
            }
        }
    });

    const completionCtx = document.getElementById('completionChart').getContext('2d');
    new Chart(completionCtx, {
        type: 'doughnut',
        data: {
            labels: ['Completed', 'Ongoing'],
            datasets: [{
                data: [<%= completed %>, <%= ongoing %>],
                backgroundColor: ['#4F2D86', '#E964B7'],
                borderColor: ['#4F2D86', '#E964B7'],
                hoverBackgroundColor: ['#3D2168', '#D84AA8'],
                hoverBorderColor: ['#3D2168', '#D84AA8'],
                borderWidth: 2
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            cutout: '60%',
            plugins: {
                legend: {
                    labels: {
                        color: '#4F2D86'
                    }
                }
            }
        }
    });
</script>

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