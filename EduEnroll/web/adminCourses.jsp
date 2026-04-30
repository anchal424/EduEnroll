<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.eduenroll.beans.CourseBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Courses</title>
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

    <div class="back-container">
        <a href="adminDashboard.jsp" class="back-btn">← Back</a>
    </div>

    <div class="page-container">
        <h1>All Courses</h1>

        <table class="table">
            <tr>
                <th>Course ID</th>
                <th>Title</th>
                <th>Category</th>
                <th>Duration</th>
                <th>Price</th>
                <th>Instructor</th>
                <th>Action</th>
            </tr>

            <%
                List<CourseBean> courseList = (List<CourseBean>) request.getAttribute("courseList");
                if (courseList != null) {
                    for (CourseBean c : courseList) {
            %>
            <tr>
                <td><%= c.getId() %></td>
                <td><%= c.getTitle() %></td>
                <td><%= c.getCategory() %></td>
                <td><%= c.getDuration() %></td>
                <td>Rs. <%= c.getPrice() %></td>
                <td><%= c.getInstructor() %></td>
                <td>
                    <a href="EditCourseServlet?courseId=<%= c.getId() %>" class="btn small-btn">Edit</a>

                    <form action="DeleteCourseServlet" method="post" style="display:inline;">
                        <input type="hidden" name="courseId" value="<%= c.getId() %>">
                        <button type="submit" class="btn small-btn" onclick="return confirm('Delete this course?')">Delete</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</body>
</html>