<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.eduenroll.beans.CourseBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Course</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/script.js"></script>
</head>
<body>
<%
    CourseBean course = (CourseBean) request.getAttribute("course");
    if (course == null) {
        response.sendRedirect("ViewCoursesServlet");
        return;
    }

    String modulesText = "";
    if (course.getModules() != null) {
        for (int i = 0; i < course.getModules().length; i++) {
            modulesText += course.getModules()[i];
            if (i < course.getModules().length - 1) {
                modulesText += "||";
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
    <a href="ViewCoursesServlet" class="back-btn">← Back</a>
</div>

<div class="form-container">
    <h2>Edit Course</h2>

    <form action="UpdateCourseServlet" method="post">
        <input type="hidden" name="courseId" value="<%= course.getId() %>">

        <input type="text" name="title" value="<%= course.getTitle() %>" required>
        <input type="text" name="category" value="<%= course.getCategory() %>" required>
        <input type="text" name="description" value="<%= course.getDescription() %>" required>
        <input type="text" name="duration" value="<%= course.getDuration() %>" required>
        <input type="number" step="0.01" name="price" value="<%= course.getPrice() %>" required>
        <input type="text" name="instructor" value="<%= course.getInstructor() %>" required>
        <input type="text" name="image" value="<%= course.getImage() %>" required>

        <label><strong>Modules</strong></label>
        <p style="margin:8px 0 12px; font-size:14px; color:#555;">
            Separate modules using <strong>||</strong>
        </p>

        <textarea name="modules" rows="10" style="width:100%; padding:12px; margin-bottom:14px; border:1px solid #ccc; border-radius:8px;" required><%= modulesText %></textarea>

        <button type="submit" class="btn">Update Course</button>
    </form>
</div>
</body>
</html>