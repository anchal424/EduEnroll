<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.eduenroll.beans.AdminAnalyticsBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title> Admin Analytics</title>
    
    <link rel="stylesheet" href="css/style.css">
    <script src="js/script.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    
</head>
<body>
<%
    if (session == null || session.getAttribute("admin") == null) {
        response.sendRedirect("adminLogin.jsp");
        return;
    }

    List<AdminAnalyticsBean> enrollmentData = (List<AdminAnalyticsBean>) request.getAttribute("enrollmentData");
    AdminAnalyticsBean completionData = (AdminAnalyticsBean) request.getAttribute("completionData");
    List<AdminAnalyticsBean> revenueData = (List<AdminAnalyticsBean>) request.getAttribute("revenueData");

    StringBuilder enrollmentLabels = new StringBuilder();
    StringBuilder enrollmentCounts = new StringBuilder();

    if (enrollmentData != null) {
        for (int i = 0; i < enrollmentData.size(); i++) {
            AdminAnalyticsBean bean = enrollmentData.get(i);
            enrollmentLabels.append("'").append(bean.getLabel().replace("'", "\\'")).append("'");
            enrollmentCounts.append(bean.getCount());

            if (i < enrollmentData.size() - 1) {
                enrollmentLabels.append(",");
                enrollmentCounts.append(",");
            }
        }
    }

    StringBuilder revenueLabels = new StringBuilder();
    StringBuilder revenueAmounts = new StringBuilder();

    if (revenueData != null) {
        for (int i = 0; i < revenueData.size(); i++) {
            AdminAnalyticsBean bean = revenueData.get(i);
            revenueLabels.append("'").append(bean.getLabel().replace("'", "\\'")).append("'");
            revenueAmounts.append(bean.getAmount());

            if (i < revenueData.size() - 1) {
                revenueLabels.append(",");
                revenueAmounts.append(",");
            }
        }
    }
%>

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

<div class="back-container">
    <a href="adminDashboard.jsp" class="back-btn">← Back</a>
</div>

<div class="page-container">
    <h1>Analytics Dashboard</h1>

    <div class="chart-card">
        <h3>Course-wise Enrollments</h3>
        <canvas id="enrollmentChart"></canvas>
    </div>

    <div class="chart-card">
        <h3>Completed vs Ongoing</h3>
        <canvas id="completionChart"></canvas>
    </div>

    <div class="chart-card">
        <h3>Revenue by Course</h3>
        <canvas id="revenueChart"></canvas>
    </div>
</div>

<script>
    const enrollmentCtx = document.getElementById('enrollmentChart').getContext('2d');
    new Chart(enrollmentCtx, {
        type: 'bar',
        data: {
            labels: [<%= enrollmentLabels.toString() %>],
            datasets: [{
                label: 'Enrollments',
                data: [<%= enrollmentCounts.toString() %>],
                backgroundColor: '#8E1FA3',
                borderColor: '#4F2D86',
                borderWidth: 1,
                borderRadius: 8
            }]
        },
        options: {
            indexAxis: 'y',
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                x: {
                    beginAtZero: true,
                    ticks: {
                        color: '#4F2D86'
                    },
                    grid: {
                        color: '#F0E5F6'
                    }
                },
                y: {
                    ticks: {
                        color: '#4F2D86',
                        font: {
                            size: 11
                        }
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
                data: [
                    <%= completionData != null ? completionData.getCount() : 0 %>,
                    <%= completionData != null ? (int) completionData.getAmount() : 0 %>
                ],
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

    const revenueCtx = document.getElementById('revenueChart').getContext('2d');
    new Chart(revenueCtx, {
        type: 'bar',
        data: {
            labels: [<%= revenueLabels.toString() %>],
            datasets: [{
                label: 'Revenue',
                data: [<%= revenueAmounts.toString() %>],
                backgroundColor: '#E964B7',
                borderColor: '#8E1FA3',
                borderWidth: 1,
                borderRadius: 8
            }]
        },
        options: {
            indexAxis: 'y',
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                x: {
                    beginAtZero: true,
                    ticks: {
                        color: '#4F2D86'
                    },
                    grid: {
                        color: '#F0E5F6'
                    }
                },
                y: {
                    ticks: {
                        color: '#4F2D86',
                        font: {
                            size: 11
                        }
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
</script>
</script>
</body>
</html>