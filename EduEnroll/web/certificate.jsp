<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.eduenroll.beans.CertificateBean" %>
<%@ page import="com.eduenroll.beans.CourseBean" %>
<%@ page import="com.eduenroll.beans.UserBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Certificates</title>
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

    String today = new SimpleDateFormat("dd MMMM yyyy").format(new Date());
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

<div class="back-container no-print">
    <a href="javascript:history.back()" class="back-btn">← Back</a>
</div>

<div class="page-container">
    <h1 class="no-print">My Certificates</h1>

    <%
        List<CertificateBean> certificateList = (List<CertificateBean>) request.getAttribute("certificateList");
        Map<String, CourseBean> courseMap = (Map<String, CourseBean>) request.getAttribute("courseMap");

        if (certificateList != null && !certificateList.isEmpty()) {
            for (CertificateBean cert : certificateList) {
                CourseBean c = courseMap.get(cert.getCourseId());
                if (c != null) {
    %>

    <div class="certificate-pro-card">
        <div class="certificate-inner-border">

            <div class="certificate-top">
                <div class="certificate-brand">EduEnroll</div>
                <div class="certificate-badge">Verified Certificate</div>
            </div>

            <div class="certificate-body">
                <p class="certificate-small-text">Certificate of Achievement</p>
                <h2 class="certificate-main-title">Certificate of Completion</h2>

                <p class="certificate-text">This is proudly presented to</p>

                <h3 class="certificate-student-name"><%= user.getName() %></h3>

                <p class="certificate-text">
                    for successfully completing the online course
                </p>

                <h3 class="certificate-course-name"><%= c.getTitle() %></h3>

                <p class="certificate-description">
                    Awarded in recognition of the learner’s dedication, progress, and successful completion
                    of all required modules and assessments in the course.
                </p>

                <div class="certificate-meta">
                    <div class="certificate-meta-box">
                        <span class="meta-label">Certificate No</span>
                        <span class="meta-value"><%= cert.getCertificateNo() %></span>
                    </div>

                    <div class="certificate-meta-box">
                        <span class="meta-label">Issue Date</span>
                        <span class="meta-value"><%= today %></span>
                    </div>

                    <div class="certificate-meta-box">
                        <span class="meta-label">Instructor</span>
                        <span class="meta-value"><%= c.getInstructor() %></span>
                    </div>
                </div>

                <div class="certificate-signature-row">
                    <div class="signature-block">
                        <div class="signature-line"></div>
                        <p>Course Instructor</p>
                    </div>

                    <div class="signature-block">
                        <div class="signature-line"></div>
                        <p>Authorized by EduEnroll</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="certificate-actions no-print">
        <a href="DownloadCertificateServlet?courseId=<%= cert.getCourseId() %>" class="btn">Download PDF</a>
       
    </div>

    <%
                }
            }
        } else {
    %>
        <p>No certificates generated yet.</p>
    <%
        }
    %>
</div>

</body>
</html>